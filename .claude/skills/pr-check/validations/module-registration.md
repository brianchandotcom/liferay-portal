# Module Registration

## Trigger

A `.lfrbuild-portal` marker is added or removed. The build discovers modules by scanning for these markers at configuration time, so a newly marked module's `project(":...")` dependencies resolve for the first time — a reference to an unmarked module fails with `Project with path ':...' could not be found`. The marker alone suffices; no `ant setup-sdk` first.

## Match

`(^|/)\.lfrbuild-portal(-private|-public)?$`

## Command

For each added or removed marker, convert its module directory to a Gradle project path (strip `modules/`, `/` to `:`, prefix `:` — `modules/apps/mcp/mcp-server-rest-test` becomes `:apps:mcp:mcp-server-rest-test`), then force configuration:

```bash
("${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules" \
	:<path>:help)
```

`help` evaluates the target's `build.gradle` without compiling, so a dangling `project(":...")` fails at configuration. Include `-test` modules — no other validation configures them.

## Checklist

```
- [ ] (One subitem per added/removed marker:) Configure <module path>
```

## Time Estimate

~10-20 sec per marker (configuration only).