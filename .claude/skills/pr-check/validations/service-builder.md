# Service Builder

## Trigger

A Service Builder *input* changed anywhere in the diff:

- `service.xml`, `service.properties`
- `META-INF/module-hbm.xml`, `META-INF/portlet-model-hints.xml`, `META-INF/sql/*.sql`
- A developer-editable `*Impl.java` under `model/impl` (e.g. `FooImpl.java`) or `service/impl` (e.g. `FooLocalServiceImpl.java`, `FooServiceImpl.java`)

OR (**Output-Only Catch-Up Regen**) the diff matches Service Builder output patterns (`*BaseImpl.java`, `*CacheModel.java`, `*ModelArgumentsResolver.java`, `*ModelImpl.java`, `*PersistenceConstants.java`, `*PersistenceImpl.java`, `*ServiceBaseImpl.java`, `*ServiceHttp.java`, `packageinfo` adjacent to those, `sql/indexes.sql`) and no `service.xml`/`service.properties`/HBM/model-hints/SQL input changed.

`portal-impl` (portal-core) and OSGi `-service` modules are both in scope — the regen scans every `service.xml` it finds.

Auto-generated outputs are **not** triggers on their own — drift is caught by `git diff --quiet`. An auto-generated `*ModelImpl.java` hand-edited without a corresponding input change is an anti-pattern (the next regen will overwrite it); flag the change but do not fire this validation.

## Command

```bash
(cd "${REPO_ROOT}/portal-impl" && ant build-services)
```

## Auto-Commit

After the regen, check for drift with `git diff --quiet` over the regen output paths. When the check shows drift, stage all changes (`git add --all`) and invoke the `commit` skill with the hint `buildServices`. The commit skill produces `<TICKET> buildServices`.

When the commit fails, record the failure and continue to the next validation.

## Time Estimate

~1-2 min.