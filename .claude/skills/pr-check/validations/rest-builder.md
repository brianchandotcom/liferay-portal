# REST Builder

## Trigger

- A `rest-config.yaml` or `rest-openapi.yaml` actually changed.

- OR (**Output-Only Catch-Up Regen**) the diff matches REST Builder output patterns (DTO `*.java`, serdes `*SerDes.java`, `OpenAPIResource*.java`, `Base*ResourceTestCase.java`, `packageinfo` adjacent to those) and no `rest-config.yaml`/`rest-openapi.yaml` changed.

Auto-generated REST DTOs, resources, and clients are **not** triggers on their own — drift is caught by `git diff --quiet`.

## Command

```bash
(cd "${REPO_ROOT}/portal-impl" && ant build-rests)
```

Faster than `gradlew buildREST` — single JVM scans `${basedir}/../modules` directly via `RESTBuilder`.

## Auto-Commit

After the regen, check for drift with `git diff --quiet` over the regen output paths. When the check shows drift, stage all changes (`git add --all`) and invoke the `commit` skill with the hint `buildREST`. The commit skill produces `<TICKET> buildREST`.

When the commit fails, record the failure and continue to the next validation.

## Time Estimate

~1 min.