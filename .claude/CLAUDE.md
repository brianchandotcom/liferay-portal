# Liferay Portal

This is Liferay Portal's main source code repository.

## High-Level Architecture

### Portal Core (Ant Based)

- `portal-kernel` — Public API: interfaces, base classes, and service utilities.
- `portal-impl` — Core portal implementation.
- `portal-web` — The main web application: JSPs, tag libraries, and WEB-INF configuration.
- `util-bridges`, `util-java`, `util-slf4j`, `util-taglib` — Shared utility libraries.

### OSGi Modules (Gradle Based)

- `modules/apps` — Most of the OSGi modules. This is where most development happens.
- `modules/core` — Core OSGi infrastructure modules.
- `modules/dxp/apps` — DXP-only (commercial) OSGi modules.
- `modules/sdk` — Gradle plugins used throughout the build (source formatter, service builder, etc.).
- `modules/util` — Custom build tools like service-builder, rest-builder, and source formatter.

## Development

Below you will find the main building blocks for daily development.

- `<gradlew>` refers to the Gradle wrapper at the repo root.
- When running `ant`, always set `ANT_OPTS="-Xmx2560m"`.

### Deploy

Code is deployed to a running server. The server lives in a `bundles` directory outside the repo. Its location is configured in `app.server.properties` via `app.server.parent.dir`, and can be overridden per-developer in `app.server.${USER}.properties`. The `<bundles>` placeholder below refers to that resolved path.

- **Most modules** — the default:

    ```bash
    cd <module-root> && <gradlew> deploy
    ```

- **Portal modules** (`portal-kernel`, `portal-impl`, `portal-test`):

    ```bash
    cd <module-root> && ant deploy install-portal-snapshot
    ```
    For `portal-test`, also copy the built jar into the bundle:

    ```bash
    cp <bundles>/osgi/test/com.liferay.portal.test.jar <bundles>/osgi/modules
    ```
    These require restarting the server.

- **`*-test-util` modules**:

    ```bash
    cd <module-root> && <gradlew> deploy
    ```

    Then copy the resulting `com.liferay.<name>.test.util.jar` from `<bundles>/osgi/test` to `<bundles>/osgi/modules`.

- **Gradle plugin modules** (under `modules/sdk/gradle-plugins*`):

    ```bash
    cd <module-root> && <gradlew> installCache updateFileVersions
    cd <repo-root>/modules/sdk/gradle-plugins && <gradlew> installCache updateFileVersions
    cd <repo-root>/modules/sdk/gradle-plugins-defaults && <gradlew> installCache updateFileVersions
    cd <repo-root> && ant setup-sdk
    ```

### Test

Liferay DXP is heavily tested. Every change must include test coverage.

#### Unit Tests

Unit tests are preferred for pure logic.

```bash
# OSGi modules
cd <module-root> && <gradlew> test --tests <TestClassName>

# Portal core
ant test-unit
ant test-unit -Dtest.class=SomeTest
ant test-package -Dtest.package=com.liferay.portal.kernel.util

# Frontend
cd <module-root> && npm test
```

#### Integration Tests

Integration tests are the default for anything that touches services, persistence, or the portal runtime.

```bash
cd <module-root> && <gradlew> testIntegration --tests <TestClassName>
```

#### Functional Tests

Functional tests are a last resort, reserved for complete UI flows that cannot be covered at a lower level.

- **Playwright**
    Tests live under `modules/test/playwright/tests/<component>/<variant>`, and each variant is a project in `modules/test/playwright/playwright.config.ts`:

    ```bash
    cd <repo-root>/modules/test/playwright && yarn test <test-file-path>
    ```
- **Poshi**
    Poshi is a legacy tool kept only for maintenance:

    ```bash
    cd <repo-root> && HOSTNAME=localhost ant -buildfile build-test.xml run-selenium-test -Dtest.class=<TestClassName>
    ```

### Format Source

All the code is strictly formatted using source formatter. Code should be formatted before every commit. This is how it works:

Run for a specific module:

```bash
cd <module-root> && <gradlew> formatSource
```

Run across the entire codebase:

```bash
cd <repo-root>/portal-impl && ant format-source-current-branch
```