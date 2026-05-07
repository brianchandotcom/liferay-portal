# Workspace Build

## Trigger

A `workspaces/**` file changed.

## Match

`^workspaces/`

## Command

Per affected workspace:

```bash
(cd <workspace-dir> && ./gradlew build)
```

## Checklist

Add one sub-item per affected workspace:

```
- [ ] Build: <workspace dir>
```

## Time Estimate

~1-2 min per workspace.