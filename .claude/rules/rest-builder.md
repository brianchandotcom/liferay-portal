# REST Builder

Use these procedures whenever a task requires creating or modifying a Liferay REST Builder module bundle.

## Module Bundle Layout

A REST Builder feature lives in four sibling modules under `modules/apps/<area>`:

- `<name>-rest-api` — generated public API.
- `<name>-rest-impl` — implementation. Hand-written `rest-config.yaml` and `rest-openapi.yaml` drive everything else.
- `<name>-rest-client` — generated Java client.
- `<name>-rest-test` — integration tests.

## Files To Leave Alone

Every generated Java file is tagged `@Generated("")` — do not hand-edit anything carrying that annotation; `buildREST` rewrites it on each run. The same applies to the non-Java generated artifacts under `<name>-rest-impl/src/main/resources/OSGI-INF`.

## Creating a New API

Use this workflow to scaffold a brand-new REST Builder module bundle from scratch.

### Files To Author Before `buildREST`

```
<name>-rest-api/{.lfrbuild-portal, bnd.bnd, build.gradle}
<name>-rest-impl/{.lfrbuild-portal, bnd.bnd, build.gradle, rest-config.yaml, rest-openapi.yaml}
<name>-rest-client/{.lfrbuild-portal, bnd.bnd, build.gradle}
<name>-rest-test/{bnd.bnd, build.gradle}
```

`buildREST` discovers a module by finding `rest-config.yaml` next to `build.gradle` — a missing `build.gradle` silently skips the module. The `.lfrbuild-portal` files are empty marker files.

#### `<name>-rest-api/bnd.bnd`

```
Bundle-Name: Liferay <Title> REST API
Bundle-SymbolicName: com.liferay.<dotted-name>.rest.api
Bundle-Version: 1.0.0
Export-Package:\
	com.liferay.<dotted-name>.rest.dto.v1_0,\
	com.liferay.<dotted-name>.rest.resource.v1_0
```

#### `<name>-rest-api/build.gradle`

```
dependencies {
	compileOnly group: "com.fasterxml.jackson.core", name: "jackson-annotations", version: "2.18.6"
	compileOnly group: "com.liferay", name: "jakarta.ws.rs", version: "3.1.0.LIFERAY-PATCHED-1"
	compileOnly group: "com.liferay.portal", name: "com.liferay.portal.kernel", version: "default"
	compileOnly group: "io.swagger.core.v3", name: "swagger-annotations-jakarta", version: "2.2.28"
	compileOnly group: "jakarta.annotation", name: "jakarta.annotation-api", version: "2.1.1"
	compileOnly group: "jakarta.servlet", name: "jakarta.servlet-api", version: "6.0.0"
	compileOnly group: "jakarta.validation", name: "jakarta.validation-api", version: "3.1.0"
	compileOnly group: "jakarta.xml.bind", name: "jakarta.xml.bind-api", version: "4.0.2"
	compileOnly group: "org.osgi", name: "org.osgi.annotation.versioning", version: "1.1.0"
	compileOnly project(":apps:portal-odata:portal-odata-api")
	compileOnly project(":apps:portal-vulcan:portal-vulcan-api")
	compileOnly project(":core:petra:petra-function")
	compileOnly project(":core:petra:petra-string")
}
```

#### `<name>-rest-impl/bnd.bnd`

```
Bundle-Name: Liferay <Title> REST Implementation
Bundle-SymbolicName: com.liferay.<dotted-name>.rest.impl
Bundle-Version: 1.0.0
```

#### `<name>-rest-impl/build.gradle`

```
dependencies {
	compileOnly group: "com.liferay", name: "jakarta.ws.rs", version: "3.1.0.LIFERAY-PATCHED-1"
	compileOnly group: "com.liferay.portal", name: "com.liferay.portal.kernel", version: "default"
	compileOnly group: "io.swagger.core.v3", name: "swagger-annotations-jakarta", version: "2.2.28"
	compileOnly group: "jakarta.annotation", name: "jakarta.annotation-api", version: "2.1.1"
	compileOnly group: "jakarta.servlet", name: "jakarta.servlet-api", version: "6.0.0"
	compileOnly group: "org.osgi", name: "org.osgi.service.component", version: "1.4.0"
	compileOnly group: "org.osgi", name: "org.osgi.service.component.annotations", version: "1.4.0"
	compileOnly group: "org.osgi", name: "osgi.core", version: "6.0.0"
	compileOnly project(":apps:<area>:<name>-rest-api")
	compileOnly project(":apps:portal-odata:portal-odata-api")
	compileOnly project(":apps:portal-vulcan:portal-vulcan-api")
	compileOnly project(":core:petra:petra-function")
}
```

#### `<name>-rest-impl/rest-config.yaml`

```yaml
apiDir: "../<name>-rest-api/src/main/java"
apiPackagePath: "com.liferay.<dotted-name>.rest"
application:
    baseURI: "/<name>"
    className: "<Title>Application"
    name: "Liferay.<Title>.REST"
author: "<Your Name>"
clientDir: "../<name>-rest-client/src/main/java"
compatibilityVersion: 15
forcePredictableOperationId: true
javaEEPackage: "jakarta"
testDir: "../<name>-rest-test/src/testIntegration/java"
```

Endpoints land at `/o/<baseURI>/v1.0/...` — do not collide with existing servlet patterns.

#### `<name>-rest-impl/rest-openapi.yaml`

The file is standard OpenAPI 3.0.1, with the following minimum shape:

- `info`: set `title`, `description`, `version: "v1.0"`. Title and description are not validator-required, but tooling (including the MCP `/discover` endpoint) relies on them.
- `paths`: each operation needs `operationId`, `description`, and `tags: [<Tag>]`. The first tag becomes the resource name (`<Tag>Resource`); use one tag per resource.
- `components.schemas`: each schema needs `description`, typed `properties`, and `required`.

Response shape drives the generated return type. Two cases are worth knowing:

- `application/json` with `{type: array, items: {$ref: ...}}` → `Page<DTO>`.
- `application/json` with `$ref` → the DTO.

For anything else, run `buildREST` and read the signature on the generated `Base<Tag>ResourceImpl`.

#### `<name>-rest-client/bnd.bnd`

```
Bundle-Name: Liferay <Title> REST Client
Bundle-SymbolicName: com.liferay.<dotted-name>.rest.client
Bundle-Version: 1.0.0
Export-Package:\
	com.liferay.<dotted-name>.rest.client.dto.v1_0,\
	com.liferay.<dotted-name>.rest.client.function
```

#### `<name>-rest-client/build.gradle`

```
dependencies {
	compileOnly group: "jakarta.annotation", name: "jakarta.annotation-api", version: "2.1.1"
}
```

#### `<name>-rest-test/bnd.bnd`

```
Bundle-Name: Liferay <Title> REST Test
Bundle-SymbolicName: com.liferay.<dotted-name>.rest.test
Bundle-Version: 1.0.0
```

#### `<name>-rest-test/build.gradle`

```
dependencies {
	testIntegrationImplementation group: "com.fasterxml.jackson.core", name: "jackson-databind", version: "2.18.6"
	testIntegrationImplementation group: "com.liferay", name: "jakarta.ws.rs", version: "3.1.0.LIFERAY-PATCHED-1"
	testIntegrationImplementation group: "jakarta.annotation", name: "jakarta.annotation-api", version: "2.1.1"
	testIntegrationImplementation project(":apps:<area>:<name>-rest-api")
	testIntegrationImplementation project(":apps:<area>:<name>-rest-client")
	testIntegrationImplementation project(":apps:portal-odata:portal-odata-api")
	testIntegrationImplementation project(":apps:portal-vulcan:portal-vulcan-api")
	testIntegrationImplementation project(":test:arquillian-extension-junit-bridge")
}
```

### Files To Edit After `buildREST`

`buildREST` scaffolds `<name>-rest-impl/src/main/java/.../internal/resource/v1_0/<Tag>ResourceImpl.java` (one per OpenAPI tag) on first run and preserves it on every subsequent run. Implement the resource logic here by overriding the methods declared on the generated `Base<Tag>ResourceImpl`.

The matching `<Tag>ResourceTest.java` under `<name>-rest-test/src/testIntegration/java/.../resource/v1_0/test` follows the same scaffold-then-edit pattern.

### Workflow

Run every step without asking for confirmation, including the commits.

1. Commit the hand-written files.

1. Run `<gradlew> buildREST` from the impl module.

1. Commit the changes the tool produces or rewrites.

1. Continue with the work.

## Editing an Existing API

Use this workflow when changing `rest-config.yaml` or `rest-openapi.yaml` on a REST Builder module bundle that already exists. Every YAML edit must be regenerated and committed before any implementation work continues.

### Workflow

Run every step without asking for confirmation, including the commits.

1. Commit the hand-written YAML files.

1. Run `<gradlew> buildREST` from the impl module.

1. Commit the changes the tool produces or rewrites.

1. Continue with the work.

## Editing REST Builder Itself

Use this workflow when editing the REST Builder generator itself — the shared FreeMarker templates in `modules/util/portal-tools-rest-builder/src/main/resources/com/liferay/portal/tools/rest/builder/dependencies` and the Java that drives them — rather than the artifacts of any single bundle.

`buildREST` reads these files from the local repository, not from the REST Builder artifact published to Maven, so a change to a shared template or generator class takes effect on the next `buildREST` run with no need to rebuild or republish the tool. Because a shared file feeds every generated module, that change affects all of them, and the regenerated output of every module must be committed so it stays consistent with the new generator.

Every generator change must be exercised by the dummy `portal-tools-rest-builder-test-impl` module, which acts as the generator's test bed: its `rest-openapi.yaml` is meant to cover each generator feature, so the regenerated Java becomes the visible record of what the change produces. When the regenerated output shows no difference, the existing cases do not cover the new behavior — add a case to the module's `rest-openapi.yaml` that does (for example, a schema carrying a description but no required properties to cover description generation).

### Workflow

Run every step without asking for confirmation, including the commits.

1. Commit the hand-written generator change, including any new `portal-tools-rest-builder-test-impl` `rest-openapi.yaml` case needed to cover it.

1. Regenerate every REST Builder module by running:

	```bash
	(cd "${REPO_ROOT}/portal-impl" && ant build-rests)
	```

1. Confirm the regenerated output reflects the change, especially under `portal-tools-rest-builder-test-impl`.

1. Commit the regenerated output on its own, titled `<TICKET> BuildREST` (for example, `LPD-XXXXX BuildREST`), so the mechanical regeneration stays distinct from the hand-written change and reviewers can skip past it.

1. Continue with the work.