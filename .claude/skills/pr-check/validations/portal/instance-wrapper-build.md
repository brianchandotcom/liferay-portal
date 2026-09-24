# Instance Wrapper Build

## Trigger

- `portal-impl/src/com/liferay/portal/tools/instance_wrappers.xml` changed.

- OR a Java file whose fully qualified name appears as a `<class name="...">` value in that XML was modified.

- OR (**Output-Only Catch-Up Regen**) the diff contains a class referenced by the XML and no XML change is present.

## Match

`instance_wrappers\.xml$`

## Command

```bash
(cd "${REPO_ROOT}/portal-impl" && ant build-iw)
```

## Autocommit

After the regen, check for real drift with `git diff --quiet` excluding `*-portlet-service.jar`, `packageinfo`, `service.properties`, `yarn.lock` (these always fluctuate). When the check shows drift, stage all changes (`git add --all`) and create a commit titled `<TICKET> buildIW`.

When the commit fails, record the failure and continue to the next validation.

## Time Estimate

~30 sec.