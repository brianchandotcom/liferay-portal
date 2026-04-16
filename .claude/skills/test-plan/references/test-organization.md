# Liferay Portal Test Organization Reference

## Module Structure

Standard module layout:
```
modules/apps/{module-group}/
├── {module}-api/           # API/interfaces (rarely has tests)
├── {module}-service/       # Implementation
│   └── src/test/java/      # Unit tests live here
├── {module}-test/          # Separate test module
│   └── src/testIntegration/java/  # Integration tests live here
├── {module}-web/           # Web/portlet layer
├── {module}-web-test/      # Web integration tests (sometimes)
├── {module}-test-util/     # Shared test utilities
├── build.gradle
└── test.properties         # Test-to-component mapping
```

## Test Type Details

### Unit Tests

- **Location**: `{module-group}/{module}-service/src/test/java/**/*Test.java`
- **Also in**: `portal-impl/src/test/java/**/*Test.java` for core code
- **Framework**: JUnit 4, Mockito
- **Run command**: `cd modules && ../gradlew :apps:{module-group}:{module}-service:test`
- **Run specific class**: `cd modules && ../gradlew :apps:{module-group}:{module}-service:test --tests "com.liferay.{package}.SomeTest"`
- **Speed**: ~5-15 seconds per test class
- **Naming**: Source file name + `Test` suffix (e.g., `BlogsEntryLocalServiceImpl.java` → `BlogsEntryLocalServiceImplTest.java`)

### Integration Tests

- **Location**: `{module-group}/{module}-test/src/testIntegration/java/**/*Test.java`
- **Also in**: `{module-group}/{module}-web-test/src/testIntegration/java/` for web tests
- **Framework**: Arquillian + JUnit bridge (`@RunWith(Arquillian.class)`)
- **Run command**: `cd modules && ../gradlew :apps:{module-group}:{module}-test:testIntegration`
- **Run specific class**: `cd modules && ../gradlew :apps:{module-group}:{module}-test:testIntegration --tests "com.liferay.{package}.SomeTest"`
- **Speed**: ~30-120 seconds per test class (needs portal context)
- **Note**: These require a running Liferay instance or will bootstrap one

### Playwright Tests

- **Location**: `modules/test/playwright/tests/{module-web}/**/*.spec.ts`
- **Config**: `modules/test/playwright/playwright.config.ts` (imports per-module configs)
- **Run all for a module**: `cd modules/test/playwright && npx playwright test tests/{module-web}/`
- **Run specific file**: `cd modules/test/playwright && npx playwright test tests/{module-web}/main/someTest.spec.ts`
- **Speed**: ~1-3 minutes per spec file
- **Structure**: Each module directory typically has `main/config.ts` and `main/*.spec.ts`
- **Coverage**: ~131 module directories, ~630 spec files

### Poshi Functional Tests

- **Location**: `portal-web/test/functional/com/liferay/portalweb/`
- **Test files**: `tests/enduser/**/*.testcase` (~514 files)
- **Macros**: `macros/*.macro` (~516 files)
- **Speed**: ~2-5 minutes per test method
- **Annotations**:
  - `@component-name` — links to a component (e.g., `"portal-headless"`)
  - `@priority` — 1-5 (5 = most critical)
  - `testray.main.component.name` — component for test reporting
- **Organization**: `tests/enduser/{category}/{subcategory}/` (categories: abtest, collaboration, contentdashboard, documentmanagement, exportimport, publications, staging, wem, etc.)

## Mapping Source Changes to Tests

### Direct mapping (same module)
A change to `modules/apps/blogs/blogs-service/src/main/java/com/liferay/blogs/internal/util/PingbackMethodImpl.java` maps to:
- Unit test: `modules/apps/blogs/blogs-service/src/test/java/com/liferay/blogs/internal/util/PingbackMethodImplTest.java`

### Cross-module mapping (test module)
A change to `modules/apps/blogs/blogs-service/` maps to:
- Integration tests in: `modules/apps/blogs/blogs-test/src/testIntegration/java/`

### Using test.properties for broader mapping
Each module group has a `test.properties` at `modules/apps/{module-group}/test.properties` containing:

```properties
# Maps to poshi component name
testray.main.component.name=Blogs

# Defines which test classes run for this module's changes
modules.includes.required.test.batch.class.names.includes[modules-unit][relevant][rule-name]=\
    apps/blogs/**/*Test.java

modules.includes.required.test.batch.class.names.includes[modules-integration-postgresql163][relevant][rule-name]=\
    apps/blogs/**/*Test.java,\
    apps/headless/headless-delivery/headless-delivery-test/**/BaseBlog*Test.java,\
    apps/headless/headless-delivery/headless-delivery-test/**/Blog*Test.java
```

Use `testray.main.component.name` to find related poshi `.testcase` files by searching for matching `@component-name` or `testray.main.component.name` properties inside them.

### Playwright mapping
Map the module's web component name to its playwright directory:
- Change in `modules/apps/blogs/blogs-web/` → tests in `modules/test/playwright/tests/blogs-web/`
- Change in `modules/apps/document-library/document-library-web/` → tests in `modules/test/playwright/tests/document-library-web/`

The directory name in `modules/test/playwright/tests/` matches the `-web` module name.
