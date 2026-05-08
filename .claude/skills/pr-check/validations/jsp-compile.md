# JSP Compile

## Trigger

A `*.jsp` or `*.jspf` changed in an OSGi module.

`compileJSP` is autowired into every OSGi module by `LiferayOSGiPlugin` (via `JspCPlugin` / `JspCDefaultsPlugin`), but it is **not** in the `assemble`/`build`/`deploy` task graph — so `gradlew :path:deploy` bundles JSPs without compiling them, and scriptlet typos (e.g., `Validator.isURL` instead of `Validator.isUrl`) only fail at Tomcat-Jasper render time. Invoke `compileJSP` explicitly.

## Match

`\.(jsp|jspf)$`

## Selection

Group changed JSPs by their owning module (the nearest ancestor with a `bnd.bnd`).

## Command

Per affected module:

```bash
"${REPO_ROOT}/gradlew" \
	--parallel \
	--project-dir "${REPO_ROOT}/modules" \
	:<path>:compileJSP
```

`compileJSP` runs in two stages — `generateJSPJava` (JSP → Java) then `JavaCompile` — and surfaces both syntax errors and unresolved method/class references.

## Checklist

```
- [ ] JSP compile: <module path>
```

## Time Estimate

~30 sec - 2 min per module.