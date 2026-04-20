# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build System

Liferay Portal uses a **hybrid Ant + Gradle** build system. Ant orchestrates the overall build; Gradle handles OSGi modules under `modules/`.

### Core Portal (Ant)

```bash
ant compile          # Compile core portal (portal-kernel, portal-impl, portal-web)
ant all              # Full build: compile + deploy all modules to app server
ant clean            # Clean build artifacts
ant deploy           # Deploy to configured app server
```

### OSGi Modules (Gradle)

A shell alias `gw` is configured to point at the gradle wrapper in the repo root, so you can invoke it from anywhere inside the tree without computing a relative path. Prefer `gw` over `./gradlew` / `../../../../gradlew`.

```bash
gw classes                         # Compile a module
gw deploy                          # Build and deploy a module
gw test                            # Run unit tests for a module
gw testIntegration                 # Run integration tests for a module
```

### To run a single test class:

```bash
cd modules/apps/<app-name>/<module-name>
gw test --tests "com.example.MyTest"
gw testIntegration --tests "com.example.MyIT"
```

Tests are typically NOT co-located with production code. Each app has a sibling `*-test` module (e.g. `fragment-test/src/testIntegration/java/...`) where integration tests, persistence tests, and shared test utilities live. Unit tests in the `*-service` module go under `src/test/java`.

### Enable Java compiler warnings:

```bash
gw compileJava -DcompileJava.lint=deprecation,unchecked
```

### Source Formatter

```bash
gw formatSource                    # Format source in current module
gw portalYarnCheckFormat           # Check formatting (frontend)
gw portalYarnFormat                # Format frontend code
gw portalYarnFormatCurrentBranch   # Format only files changed on current branch
gw portalYarnFormatLocalChanges    # Format only locally modified files
```

Source formatter configuration lives in `source-formatter.properties` and `source-formatter-suppressions.xml` at the repo root.

## Architecture

### Directory Layout

- `portal-kernel/` — Public API interfaces and SPI (the contract layer)
- `portal-impl/` — Core portal implementation; depends on portal-kernel
- `portal-web/` — JSP pages, web resources, portlet definitions
- `modules/apps/` — ~183 individual OSGi application bundles (blogs, document-library, journal, wiki, etc.)
- `modules/core/` — Core OSGi framework modules (petra utilities, bootstrap, etc.)
- `modules/dxp/` — DXP-only (commercial) modules
- `util-java/`, `util-taglib/`, `util-bridges/` — Shared utility libraries
- `lib/development/`, `lib/global/`, `lib/portal/` — Third-party JARs
- `sql/` — Database DDL/DML for all supported databases
- `tools/` — Development tooling and scripts

### Module System

The project is OSGi-based (Apache Felix). Each module under `modules/` is a separate bundle with its own `bnd.bnd` and `build.gradle`. Modules declare dependencies via Gradle configurations (`compileOnly`, `implementation`, `testIntegration`).

**Build marker files** (dotfiles in module directories) control how modules are built and deployed:

| Marker | Effect |
|--------|--------|
| `.lfrbuild-portal` | Deploy during `ant all` |
| `.lfrbuild-portal-pre` | Build before core portal |
| `.lfrbuild-portal-deprecated` | Skip deployment |
| `.lfrbuild-portal-private` | Internal-only module, not published |
| `.lfrbuild-static` | Deploy to `osgi/static/` instead of `osgi/portal/` |
| `.lfrbuild-app-server-lib` | Deploy to `WEB-INF/lib/` |
| `.lfrbuild-bundle` | Treat as a third-party / pre-built bundle |
| `.lfrbuild-ci` | Build only on Jenkins CI |
| `.lfrbuild-master-only` | Don't fork for release branches |
| `.lfrbuild-releng-ignore` | Skip stale artifact checks |

### Service Builder

Many modules use Liferay Service Builder to generate persistence/service layers. Generated code lives in `-service` and `-api` sub-modules. Regenerate with:

```bash
gw buildService
```

Never edit files in `**/generated/` or files marked with `@generated` — they are overwritten on the next `buildService` run.

### Key Gradle Configurations

- `compileOnly` — provided at runtime by OSGi container (do not bundle)
- `implementation` — bundled inside the JAR
- `compileInclude` — bundled and also exposed on compile classpath
- `testIntegrationImplementation` — integration test dependencies only (the older `testIntegrationCompile` name is no longer used)

### Gradle File Conventions

- Always use double quotes (not single) unless single quotes are required
- Never use `def` for local variables; always declare explicit types
- Sort dependencies alphabetically within each configuration block
- Separate dependency configuration blocks with blank lines

### Java Version

Java 17 (source and target compatibility). Default module compatibility is Java 8 unless overridden in `build.gradle`.

### Java Code Style (enforced by source formatter)

- Private fields and private methods are prefixed with an underscore: `_log`, `_fragmentEntryService`, `_getFoo()`. This is one of the most strictly enforced conventions
- Logger declaration is fixed: `private static final Log _log = LogFactoryUtil.getLog(MyClass.class);` — use `com.liferay.portal.kernel.log.Log`, never SLF4J or `java.util.logging`
- Imports are sorted alphabetically within groups; no static imports; no wildcard imports
- No `var`, no records, no Lombok, no `Stream.toList()` (Java 16+) — modules target Java 8 bytecode unless explicitly raised
- Avoid the Java Streams API (`stream()`, `Collectors`, `flatMap`, etc.) when a plain `for` / enhanced-for loop will do; the codebase consistently prefers explicit iteration for readability, debuggability, and stack-trace clarity. Reach for streams only when the alternative is meaningfully worse
- No `System.out`, no `printStackTrace()` — log via `_log`
- `@Reference`-injected fields follow the same underscore convention (`@Reference private FragmentRendererController _fragmentRendererController;`) and are declared at the bottom of the class, alphabetized after any private constants
- Source headers are SPDX-formatted; the formatter rewrites legacy headers automatically

## Configuration

- `build.properties` — Main build configuration (app server paths, Java version, repository URLs)
- `app.server.properties` — App server location (set `app.server.tomcat.dir`, etc.)
- `test.properties` — Test environment configuration (database, search engine, ports)

Create a `build.<username>.properties` or `app.server.<username>.properties` to override defaults locally without touching tracked files.

## Liferay Conventions (Critical)

### OSGi & Module Layout

- Use `@Reference` (OSGi DS) for dependency injection, NOT Spring `@Autowired`
- Services are registered via `@Component(service = MyService.class)`, consumed via `@Reference`
- Portal kernel APIs live in `portal-kernel` — never reference `portal-impl` classes directly from modules
- Internal classes go in `*.internal.*` packages and must NOT be exported in bnd.bnd; the bnd `Export-Package` instruction relies on the `internal` segment to auto-exclude
- API interfaces go in `*-api` modules under `*.api` packages and MUST be exported
- Component properties use the `key=value` string array form: `@Component(property = {"mvc.command.name=/foo/bar", "javax.portlet.name=" + FooPortletKeys.FOO})`
- For OSGi configuration, define a `@Meta.OCD` interface under `*.configuration` and read it via `ConfigurationProvider` or `@Activate` with `Map<String, Object>` properties

### Service Builder

- For new entities, start with `service.xml` and run `gw buildService` — never write persistence manually
- Method parameter order in service implementation classes follows the order of the fields in `service.xml`
- `ServiceContext` is always the LAST parameter on add/update service methods
- Multilingual fields take a `Map<Locale, String>` parameter (e.g. `nameMap`, `descriptionMap`) — the `*Map` naming is mandatory
- Every entity has an `externalReferenceCode` (ERC) column and `*ByExternalReferenceCode` finder methods; ERC is the stable cross-environment identifier used by staging, headless APIs, and site initializers
- Upgrade steps go in `*-service/src/main/java/*/internal/upgrade/v<X_Y_Z>/` extending `BaseUpgradeProcess`, registered via an `UpgradeStepRegistrator` `@Component`

### Portlet & Web Layer

- Use `PortletURLBuilder` / `ActionURLBuilder` for URL construction, not raw concatenation
- Portlet commands are registered as separate `@Component` classes implementing `MVCActionCommand`, `MVCRenderCommand`, or `MVCResourceCommand`, keyed by `mvc.command.name` and `javax.portlet.name` properties — do NOT subclass `MVCPortlet` to add command logic
- JSPs live under `*-web/src/main/resources/META-INF/resources/`; every page includes `init.jsp` (which itself includes `init-ext.jsp` for ext-plugin overrides)
- JSPs must use `<portlet:defineObjects/>` and `<liferay-theme:defineObjects/>` at the top
- Language keys go in `src/main/resources/content/Language.properties` (English source); translations are auto-generated as `Language_<locale>.properties`
- React/TypeScript code lives in `src/main/resources/META-INF/resources/js/`, built with `liferay-npm-bundler` / `liferay-js-toolkit` (declared in `package.json`)

### Headless REST APIs

- `headless-*-api` modules contain the OpenAPI YAML (`rest-openapi.yaml`) plus generated DTOs and resource interfaces
- `headless-*-impl` modules contain the resource implementations (annotated with `@Path` and `@Component`)
- Regenerate DTOs/resources after editing the YAML with `gw buildREST` — never hand-edit generated classes
- Vulcan (`portal-vulcan-api`) provides the framework: `Page`, `Pagination`, `Sort`, `Filter`, `EntityModel`

### Change Tracking, Staging, Export/Import

- **Batch engine is the currently preferred mechanism for promoting content between environments.** Headless APIs already expose batch import/export endpoints, and new entities should integrate with the batch engine (`modules/apps/batch-engine/`) rather than adding new staged-model machinery
- Staged model data handlers, the legacy export/import LAR pipeline, and publication-based staging are considered **legacy** — keep them working when touching existing code, but do not extend them for new entities
- For change tracking specifically, models still implement `CTModel` / are managed by a `CTService` — Service Builder handles this when `change-tracking-enabled="true"` is set on the entity, and `TableReferenceDefinition` is still required for CT data references
- When working in legacy code paths: `StagedModelDataHandler` + `StagedModelRepository` is the established pattern, and `LinksToLayoutsExportImportContentProcessor` is the canonical reference for processing layout-referencing content during export/import

### Petra & Utility Layers

- Prefer `com.liferay.petra.*` (`StringPool`, `StringBundler`, `petra.io`, `petra.lang`, `petra.string`) over Apache Commons / Guava
- Use `StringBundler` instead of `+` whenever concatenating more than 3 strings (enforced by source formatter)
- Use `StringPool.BLANK`, `StringPool.SPACE`, etc. instead of string literals for common values

## Reference Files

- For Service Builder field types and relationships, see `portal-impl/src/com/liferay/portal/tools/service/builder/dependencies/service.ftl`
- For upgrade process patterns, see an existing example: `modules/apps/blogs/blogs-service/src/main/java/com/liferay/blogs/internal/upgrade/`
- For taglib references, check `util-taglib/src/content/` only when uncertain about tag attributes
- For shared bnd / Gradle configuration consumed by every module, see `tools/sdk/dependencies/`
- For a canonical multi-bundle subsystem (api / impl / web / test / experiment splits), see `modules/apps/portal-search/`
- For ERC migration upgrade processes, see `modules/apps/fragment/fragment-service/src/main/java/com/liferay/fragment/internal/upgrade/v2_11_1/` and `v2_12_0/`
- For staged-model + table-reference + change-tracking integration in one app, see `modules/apps/fragment/fragment-service/src/main/java/com/liferay/fragment/internal/exportimport/` together with `modules/apps/fragment/fragment-service/src/main/java/com/liferay/fragment/internal/change/tracking/`
