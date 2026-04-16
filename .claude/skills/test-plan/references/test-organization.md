# Liferay Portal Test Organization Reference

## Module Structure

Standard module layout:

```
modules/apps/{module-group}/
├── {module}-api/                   # API and interfaces (rarely has tests)
├── {module}-service/               # Implementation
│   └── src/test/java/              # Unit tests live here
├── {module}-test/                  # Separate test module
│   └── src/testIntegration/java/   # Integration tests live here
├── {module}-test-util/             # Shared test utilities
├── {module}-web/                   # Web and portlet layer
├── {module}-web-test/              # Web integration tests (when applicable)
├── build.gradle
└── test.properties                 # Test-to-component mapping
```

## Test Type Details

### Unit Tests

- **Location**: `{module-group}/{module}-service/src/test/java/**/*Test.java`.
- **Also In**: `portal-impl/src/test/java/**/*Test.java` for core code.
- **Framework**: JUnit 4 with Mockito.
- **Naming**: Source file name with a `Test` suffix (e.g., `BlogsEntryLocalServiceImpl.java` maps to `BlogsEntryLocalServiceImplTest.java`).
- **Run All in a Module**: `cd modules && ../gradlew :apps:{module-group}:{module}-service:test`.
- **Run a Specific Class**: `cd modules && ../gradlew :apps:{module-group}:{module}-service:test --tests "com.liferay.{package}.SomeTest"`.
- **Speed**: Approximately 5–15 seconds per test class.

### Integration Tests

- **Location**: `{module-group}/{module}-test/src/testIntegration/java/**/*Test.java`.
- **Also In**: `{module-group}/{module}-web-test/src/testIntegration/java/` for web tests.
- **Framework**: Arquillian with the JUnit bridge (`@RunWith(Arquillian.class)`).
- **Note**: Integration tests require a running Liferay instance or will bootstrap one.
- **Run All in a Module**: `cd modules && ../gradlew :apps:{module-group}:{module}-test:testIntegration`.
- **Run a Specific Class**: `cd modules && ../gradlew :apps:{module-group}:{module}-test:testIntegration --tests "com.liferay.{package}.SomeTest"`.
- **Speed**: Approximately 30–120 seconds per test class (portal context required).

### Playwright Tests

- **Location**: `modules/test/playwright/tests/{module-web}/**/*.spec.ts`.
- **Config**: `modules/test/playwright/playwright.config.ts` (imports per-module configs).
- **Coverage**: Approximately 131 module directories and 630 spec files.
- **Run All for a Module**: `cd modules/test/playwright && npx playwright test tests/{module-web}/`.
- **Run a Specific File**: `cd modules/test/playwright && npx playwright test tests/{module-web}/main/someTest.spec.ts`.
- **Speed**: Approximately 1–3 minutes per spec file.
- **Structure**: Each module directory typically contains `main/config.ts` and `main/*.spec.ts`.

### Poshi Functional Tests

- **Location**: `portal-web/test/functional/com/liferay/portalweb/`.
- **Annotations**:
	- `@component-name` — links to a component (e.g., `"portal-headless"`).
	- `@priority` — ranges from 1 to 5, with 5 denoting the most critical.
	- `testray.main.component.name` — the component used for test reporting.
- **Macros**: `macros/*.macro` (approximately 516 files).
- **Organization**: `tests/enduser/{category}/{subcategory}/` with categories such as `abtest`, `collaboration`, `contentdashboard`, `documentmanagement`, `exportimport`, `publications`, `staging`, and `wem`.
- **Speed**: Approximately 2–5 minutes per test method.
- **Test Files**: `tests/enduser/**/*.testcase` (approximately 514 files).

## Mapping Source Changes to Tests

### Direct Mapping (Same Module)

A change to `modules/apps/blogs/blogs-service/src/main/java/com/liferay/blogs/internal/util/PingbackMethodImpl.java` maps to:

- Unit test: `modules/apps/blogs/blogs-service/src/test/java/com/liferay/blogs/internal/util/PingbackMethodImplTest.java`.

### Cross-Module Mapping (Test Module)

A change to `modules/apps/blogs/blogs-service/` maps to:

- Integration tests in `modules/apps/blogs/blogs-test/src/testIntegration/java/`.

### Using test.properties for Broader Mapping

Each module group has a `test.properties` file at `modules/apps/{module-group}/test.properties` containing:

```properties
# Maps to the Poshi component name.
testray.main.component.name=Blogs

# Defines which test classes run for this module's changes.
modules.includes.required.test.batch.class.names.includes[modules-unit][relevant][rule-name]=\
    apps/blogs/**/*Test.java

modules.includes.required.test.batch.class.names.includes[modules-integration-postgresql163][relevant][rule-name]=\
    apps/blogs/**/*Test.java,\
    apps/headless/headless-delivery/headless-delivery-test/**/BaseBlog*Test.java,\
    apps/headless/headless-delivery/headless-delivery-test/**/Blog*Test.java
```

Use `testray.main.component.name` to locate related Poshi `.testcase` files by searching for matching `@component-name` or `testray.main.component.name` annotations.

### Playwright Mapping

Map the module's web component name to its Playwright directory. The directory name under `modules/test/playwright/tests/` mirrors the `-web` module name:

- A change in `modules/apps/blogs/blogs-web/` maps to tests in `modules/test/playwright/tests/blogs-web/`.
- A change in `modules/apps/document-library/document-library-web/` maps to tests in `modules/test/playwright/tests/document-library-web/`.
