# Integration Test Compile

## Trigger

A Java file changed in an OSGi module (excluding `modules/dxp/apps/saml/saml-admin-rest-test/**` and `modules/sdk/**`) AND **Full Portal Build** did not fire.

This catches IT compile breaks without running ITs — IT execution is out of scope; use `test-plan` for that.

## Match

`^modules/.+\.java$`

## Command

Per affected module:

```bash
("${REPO_ROOT}/gradlew" \
	--parallel \
	--project-dir "${REPO_ROOT}/modules" \
	:<path>:compileTestIntegrationJava)
```

## Checklist

Add one subitem per affected module:

```
- [ ] Compile testIntegration: <module path>
```

## Time Estimate

~30 sec per module (parallelized).