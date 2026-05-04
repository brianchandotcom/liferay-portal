# Poshi Syntax

## Trigger

A Poshi DSL file changed: `*.{function,macro,path,testcase}`.

## Command

```bash
(cd "${REPO_ROOT}" && ant -buildfile build-test.xml run-poshi-validation)
```

This validates syntax only; Poshi *execution* is out of scope (use `test-plan`).

## Time Estimate

~1 min.