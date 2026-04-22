---

allowed-tools: [Bash, Edit, Glob, Grep, Read, Write]
argument-hint: "[new | status | list | cleanup]"
description: Use when creating a Git worktree for parallel Liferay development, setting up a worktree bundle after ant all, checking which ports a worktree uses, listing all worktrees and their status, or tearing down a worktree. Covers the full lifecycle from Git worktree add through cleanup.
name: worktree-setup

---

# Worktree Setup

Set up, inspect, or tear down an isolated Liferay worktree with unique ports and database.

## Determine Action

Parse `${ARGUMENTS}` and conversation context:

- **"new"** or user wants a new worktree → Full Setup
- **"status"** or user asks what ports a worktree uses → Status Check
- **"list"** or user asks which worktrees exist / what is running → List All
- **"cleanup"** or user wants to tear down → Cleanup
- **No argument** but worktree exists with a bundle → Configure Ports
- **No argument** and no worktree context → ask what the user needs

## Port Defaults

**Offset 0 is RESERVED for the main worktree. Never use it in a secondary worktree.**

| Service | Default | Config Location |
|---|---|---|
| Tomcat HTTP | 8080 | `tomcat-*/conf/server.xml` |
| Tomcat Shutdown | 8005 | `tomcat-*/conf/server.xml` |
| Tomcat AJP | 8009 | `tomcat-*/conf/server.xml` |
| HTTPS Redirect | 8443 | `tomcat-*/conf/server.xml` |
| OSGi Console | 11311 | `tomcat-*/webapps/ROOT/WEB-INF/classes/portal-developer.properties` |
| ES Sidecar HTTP | 9201 | `osgi/configs/...ElasticsearchConfiguration.config` |
| ES Transport | 9301 | `osgi/configs/...ElasticsearchConfiguration.config` |
| Glowroot | 4000 | `glowroot/admin.json` |
| Arquillian | 32763 | `osgi/configs/...ArquillianConnector.config` |
| DataGuard | 42763 | `osgi/configs/...DataGuardConnector.config` |

All ports shift by the same offset N: `port = default + N`.

## Database Naming

Derive from the worktree directory name, NOT the offset:

```
liferay-portal-LPD-12345  →  lportal_lpd_12345
liferay-portal-hotfix     →  lportal_hotfix
liferay-portal            →  lportal (main — never touch)
```

Rule: strip `liferay-portal-` prefix, lowercase, replace non-alphanumeric with `_`, collapse consecutive `_`, truncate to 56 characters, prepend `lportal_`.

## Full Setup

### 1. Create the Worktree

```bash
git worktree add -b <BRANCH> ../<DIR_NAME> master
```

If this fails because the branch already exists, offer the user two options:
- **Reuse** the existing branch: `git worktree add ../<DIR_NAME> <BRANCH>`
- **New branch** with a suggested alternative name (e.g., append `-v2`, `-wt`, or a short descriptor)

### 2. Configure Bundle Directory

Create `app.server.<username>.properties` in the worktree root:

```properties
app.server.parent.dir=${project.dir}/bundle
```

### 3. Build

Check whether a bundle already exists by looking for a `tomcat-*` directory inside the resolved bundle parent directory (see step 4a).

- **Bundle exists:** Skip `ant all`. Inform the user the existing bundle will be reused. If the user explicitly requests a rebuild, run `ant all`.
- **No bundle:** Run `ant all`:

```bash
cd <WORKTREE_DIR> && ant all
```

If `ant all` fails, stop and surface the full error to the user — do not continue to port configuration.

### 4. Configure Ports

#### 4a. Resolve the Bundle Directory

1. Read `app.server.<username>.properties` in the worktree root
2. Parse `app.server.parent.dir`, replacing `${project.dir}` with the repo root
3. Fallback: `<REPO_ROOT>/bundle/`
4. Find the `tomcat-*` directory inside it

#### 4b. Determine the Offset

1. If user specified an offset, use it (reject 0)
2. If `.worktree-port-offset` exists in the bundle dir, read it
3. Otherwise, scan ports starting from offset=1: for each candidate offset, check if ports `8080+N`, `8005+N`, `11311+N`, `9201+N`, `9301+N`, `4000+N` are all free using `nc -z localhost <port>`. First offset where ALL are free wins.
4. Save the chosen offset to `<BUNDLE_DIR>/.worktree-port-offset`

#### 4c. Patch server.xml

File: `<TOMCAT>/conf/server.xml`

Read the current HTTP port from the `protocol="HTTP/1.1"` Connector to determine the current offset. Then replace all port attributes.

**Platform check:** BSD `sed` (macOS) requires an empty-string argument after `-i`, while GNU `sed` (Linux) does not. Detect the platform before any in-place edits:

```bash
if [[ "$(uname)" == "Darwin" ]]; then
	SED_INPLACE=(sed -i '')
else
	SED_INPLACE=(sed -i)
fi
```

Use `"${SED_INPLACE[@]}"` in place of `sed -i` for all subsequent calls:

```bash
# Single sed invocation for atomicity
"${SED_INPLACE[@]}" \
	-e 's/port="<CURRENT_SHUTDOWN>"/port="<TARGET_SHUTDOWN>"/g' \
	-e 's/port="<CURRENT_HTTP>"/port="<TARGET_HTTP>"/g' \
	-e 's/port="<CURRENT_AJP>"/port="<TARGET_AJP>"/g' \
	-e 's/port="<CURRENT_HTTPS>"/port="<TARGET_HTTPS>"/g' \
	-e 's/redirectPort="<CURRENT_HTTPS>"/redirectPort="<TARGET_HTTPS>"/g' \
	<TOMCAT>/conf/server.xml
```

Skip if target HTTP port is already present (idempotent).

#### 4d. Patch portal-developer.properties

File: `<TOMCAT>/webapps/ROOT/WEB-INF/classes/portal-developer.properties`

Replace the OSGi console port (this file gets **wiped on rebuild**):

```
module.framework.properties.osgi.console=<11311+N>
```

Use `"${SED_INPLACE[@]}" 's/osgi\.console=[0-9]*/osgi.console=<TARGET>/'` to handle any current value.

#### 4e. Create OSGi Config Files

These get **wiped on rebuild** — always overwrite.

Detect the Elasticsearch version by checking which configuration file already exists in `<BUNDLE>/osgi/configs/`:
- `com.liferay.portal.search.elasticsearch8.configuration.ElasticsearchConfiguration.config` → elasticsearch8
- `com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration.config` → elasticsearch7
- Neither exists → default to elasticsearch8

`<BUNDLE>/osgi/configs/com.liferay.portal.search.elasticsearch<VERSION>.configuration.ElasticsearchConfiguration.config`:
```
sidecarHttpPort="<9201+N>"
transportTcpPort="<9301+N>"
networkBindHost="127.0.0.1"
networkPublishHost="127.0.0.1"
```

`<BUNDLE>/osgi/configs/com.liferay.arquillian.extension.junit.bridge.connector.ArquillianConnector.config`:
```
port="<32763+N>"
```

`<BUNDLE>/osgi/configs/com.liferay.data.guard.connector.DataGuardConnector.config`:
```
port="<42763+N>"
```

#### 4f. Patch glowroot/admin.json

File: `<BUNDLE>/glowroot/admin.json`

Use `jq` to set `.web.port` to `4000+N`. Skip if already correct (survives rebuild).

```bash
jq '.web.port = <TARGET>' admin.json > admin.json.tmp && mv admin.json.tmp admin.json
```

#### 4g. Patch portal-ext.properties

File: `<BUNDLE>/portal-ext.properties`

Ensure these lines are present (remove old values first, then append):

```properties
include-and-override=portal-developer.properties
portal.instance.inet.socket.address=localhost:<8080+N>
browser.launcher.url=
setup.wizard.enabled=false
terms.of.use.required=false
passwords.default.policy.change.required=false
users.reminder.queries.enabled=false
```

**Important:** The property is `portal.instance.inet.socket.address` (with `inet`), NOT `portal.instance.http.socket.address`. Also remove any old `portal.instance.http.socket.address` lines.

#### 4h. Configure MySQL Database

Derive the DB name from the worktree directory (see Database Naming above).

Ensure these lines in `portal-ext.properties`:

```properties
jdbc.default.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.default.url=jdbc:mysql://localhost/<DB_NAME>?characterEncoding=UTF-8&dontTrackOpenResources=true&holdResultsOpenOverStatementClose=true&serverTimezone=GMT&useFastDateParsing=false&useUnicode=true
jdbc.default.username=root
jdbc.default.password=
```

If existing JDBC properties exist, read the username/password from them before replacing.

**Always** attempt to create the database (even on reruns):

```bash
mysql --user root --execute 'CREATE DATABASE IF NOT EXISTS <DB_NAME> CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;'
```

If `mysql` CLI is unavailable or fails, print the command for the user to run manually. Show errors — never swallow them with `2>/dev/null`.

#### 4i. Clear OSGi State

```bash
rm -rf <BUNDLE>/osgi/state
```

#### 4j. Print Summary

Print a table of all assigned ports, the DB name, the Liferay URL, and the Glowroot URL.

### 5. Start

```bash
<BUNDLE>/tomcat-*/bin/catalina.sh start
```

Tell the user how to follow the log:

```bash
tail -f <BUNDLE>/tomcat-*/logs/catalina.out
```

## Rerunnability

After `ant all` rebuilds, rerun the Configure Ports steps. The saved offset is reused. Files that survived the rebuild are skipped; wiped files are reapplied.

| File | Survives Rebuild? | Rerun Behavior |
|---|---|---|
| server.xml | Yes | Skip if already patched |
| glowroot/admin.json | Yes | Skip if already patched |
| portal-ext.properties | Yes | Skip if already correct |
| portal-developer.properties | **No** | Always reapply |
| osgi/configs/* | **No** | Always overwrite |

## Multiple Worktrees at Once

When configuring N worktrees before starting any, use explicit offsets — the port scanner only detects running services:

```
Worktree 1: offset=1
Worktree 2: offset=2
Worktree 3: offset=3
```

## List All

Show a summary table of all Git worktrees with their port, running status, offset, and database.

1. Run `git worktree list --porcelain` to enumerate all worktrees
2. For each, resolve the bundle directory (same logic as step 4a)
3. Read `.worktree-port-offset` to get the offset and derive the HTTP port and DB name
4. Detect running status by matching `-Dcatalina.base` from running Java processes to each worktree's tomcat directory:

```bash
# Get all running Tomcat catalina.base paths
ps -eo args | grep --only-matching '\-Dcatalina\.base=[^ ]*' | sed 's/-Dcatalina.base=//'
```

**Use `-Dcatalina.base` process matching, NOT port scanning.** Port scanning cannot distinguish which Tomcat owns which port.

Print a table like:
```
Worktree                                 Port     Status     Offset    Database
liferay-portal                           -        NO TOMCAT  (none)    -
liferay-portal-LPD-00000                 8081     RUNNING    1         lportal_lpd_00000
liferay-portal-LPD-12345                 8082     STOPPED    2         lportal_lpd_12345
```

## Status Check

Read `<BUNDLE>/.worktree-port-offset` and print the port table for a single worktree. No modifications.

## Cleanup

**Confirm with the user before destructive steps.**

Resolve the worktree's absolute path from `git worktree list --porcelain` output.

```bash
# 1. Stop Tomcat
<BUNDLE>/tomcat-*/bin/catalina.sh stop

# 2. Drop the database
mysql --user root --execute 'DROP DATABASE IF EXISTS <DB_NAME>;'

# 3. Remove the worktree (use absolute path from git worktree list)
git worktree remove <ABSOLUTE_WORKTREE_PATH>

# 4. Delete the branch
git branch --delete --force <BRANCH>
```