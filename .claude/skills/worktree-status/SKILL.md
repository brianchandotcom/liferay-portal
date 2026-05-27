---

allowed-tools: [Bash, Read]
argument-hint: "[list | status]"
description: Use to list Liferay worktrees with their assigned ports, running status, offset, and database, or to print the port and database summary for a single worktree.
name: worktree-status

---

# Worktree Status

Inspect Liferay worktrees and the ports and databases they are configured to use. The skill never mutates state.

## Determine Action

Parse `${ARGUMENTS}` and conversation context:

- **"list"** or user asks which worktrees exist or what is running → List All
- **"status"** or user asks what ports a single worktree uses → Status Check
- **No argument** → default to List All

## Port Defaults

Offset 0 is reserved for the main worktree. All ports shift by the same offset N: `port = default + N`.

| Service | Default | Config Location |
| --- | --- | --- |
| Arquillian | 32763 | `<bundles>/osgi/configs/...ArquillianConnector.config` |
| DataGuard | 42763 | `<bundles>/osgi/configs/...DataGuardConnector.config` |
| Elasticsearch Sidecar HTTP | 9201 | `<bundles>/osgi/configs/...ElasticsearchConfiguration.config` |
| Elasticsearch Transport | 9301 | `<bundles>/osgi/configs/...ElasticsearchConfiguration.config` |
| Glowroot | 4000 | `<bundles>/glowroot/admin.json` |
| HTTPS Redirect | 8443 | `<tomcat>/conf/server.xml` |
| JPDA Debug | 8000 | `<tomcat>/bin/setenv.sh` |
| OSGi Console | 11311 | `<tomcat>/webapps/ROOT/WEB-INF/classes/portal-developer.properties` |
| Tomcat AJP | 8009 | `<tomcat>/conf/server.xml` |
| Tomcat HTTP | 8080 | `<tomcat>/conf/server.xml` |
| Tomcat Shutdown | 8005 | `<tomcat>/conf/server.xml` |

## Database Naming

The database name is derived from the worktree directory name, not the offset:

```
liferay-portal-LPD-12345  →  lportal_lpd_12345
liferay-portal-hotfix     →  lportal_hotfix
liferay-portal            →  lportal (main)
```

Apply this rule: strip the `liferay-portal-` prefix, lowercase, replace nonalphanumeric runs with `_`, collapse consecutive `_`, truncate to 56 characters, then prepend `lportal_`.

## List All

Show a summary table of every Git worktree with its HTTP port, running status, offset, and database.

1. Run `git worktree list --porcelain` to enumerate all worktrees.

1. For each worktree, resolve `<bundles>` by reading `app.server.parent.dir` from `app.server.${USER}.properties` in the worktree root (falling back to `app.server.properties`). Substitute `${project.dir}` with the worktree path.

1. Read `<bundles>/.worktree-port-offset` to obtain the offset, then derive the HTTP port and database name.

1. Detect the running status by matching `-Dcatalina.base` against each worktree's `<tomcat>` directory. Use process matching, not port scanning — port scanning cannot distinguish which Tomcat owns which port.

	```bash
	ps -eo args | grep --only-matching "\-Dcatalina\.base=[^ ]*" | sed "s/-Dcatalina.base=//"
	```

Render the output like this:

```
Worktree                                 Port     Status     Offset    Database
liferay-portal                           -        NO TOMCAT  (none)    -
liferay-portal-LPD-00000                 8081     RUNNING    1         lportal_lpd_00000
liferay-portal-LPD-12345                 8082     STOPPED    2         lportal_lpd_12345
```

## Status Check

Read `<bundles>/.worktree-port-offset` for the current worktree and print the full port table plus the derived database name. Make no modifications.