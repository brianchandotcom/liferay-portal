---

name: format-source
description: Format Java source files changed on the current branch to align with Liferay's coding standards, then run formatSource (./gradlew formatSource or ant format-source-current-branch) and fix any reported violations. Must be run before every commit. Use when the user asks to format source, fix code style, or run formatSource on Java files.

---

# Format Source

Format Java source files to align with Liferay's coding style patterns. This must be run before every commit.

## Workflow

### Phase 1: Identify Files to Format

Get all Java files changed on the current branch relative to master:

```bash
git diff --name-only master...HEAD | grep '\.java$'
```

If the command returns no files, inform the user and stop.

### Phase 2: Apply Formatting Rules

For each Java file, analyze and propose corrections. Read the full file before making any judgements.

---

#### Rule 1: Method Parameter Ordering

Method parameters must be sorted **alphabetically by parameter name** (case-insensitive, ignoring leading underscores).

**Default: alphabetical sort**

```java
// Before
public void process(String userId, long companyId, Date createDate)

// After
public void process(long companyId, Date createDate, String userId)
```

**Exception: Service module entity CRUD methods**

When ALL of these conditions hold, use service.xml field order instead of alphabetical:

1. The class is inside a `*-service` module (module directory ends with `-service`)

1. A `service.xml` exists at the module root

1. The method is a CRUD method for an entity defined in that `service.xml` — meaning the method name contains the entity name (e.g., `addAnalyticsMessage`, `updateAnalyticsMessage`, `deleteAnalyticsMessage`)

1. The parameters (by name) are a subset of the fields declared in that entity's `<column>` elements

When the exception applies, order parameters to match the field declaration order in `service.xml` (top to bottom within the entity's `<column>` list), skipping fields that aren't parameters.

**How to determine if the exception applies:**

```bash
# 1. Find the module root (directory containing bnd.bnd or build.gradle)
# 2. Check for service.xml at module root
find <module-root> -maxdepth 1 -name "service.xml"

# 3. Parse entity names and their column order from service.xml
grep -E '<entity name=|<column name=' <module-root>/service.xml
```

**Example:**

service.xml declares for `AnalyticsMessage`:

```xml
<column name="companyId" />
<column name="userId" />
<column name="userName" />
<column name="createDate" />
<column name="body" />
```

Method `addAnalyticsMessage(long userId, byte[] body, long companyId)` → exception applies → reorder to `(long companyId, long userId, byte[] body)`.

**When to skip a method entirely:**
- Parameters don't correspond to entity fields (e.g., `start`, `end`, `orderByComparator` — these are pagination/query params, not entity fields)
- The method name doesn't clearly target a single entity from `service.xml`
- The parameter names are ambiguous (could belong to multiple entities)

---

### Phase 3: Apply Changes

For each file with required changes, apply them directly using the Edit tool. Update:

1. The method **declaration** (signature in the class body)

1. If the method overrides an interface or base class defined in the same codebase, update that signature too

When editing, change only the parameter list order. Do not reformat other parts of the method, add/remove whitespace beyond what's needed, or alter logic.

After processing all files, print a summary of every change made:
- File path (relative to repo root)
- Method signature before → after
- Which rule applied (alphabetical / service.xml field order for `EntityName`)

If no changes were needed, say so.

### Phase 6: Run formatSource

After applying all changes, run formatSource. Use the branch-wide command when multiple modules are affected, or the module-specific command otherwise:

```bash
# For a specific module
cd <module-root> && <gradlew> formatSource

# Across the entire codebase (current branch)
cd <repo-root>/portal-impl && ant format-source-current-branch
```

### Phase 7: Fix formatSource Issues

Parse the output of the formatSource run. If it reports formatting violations:

1. Read each flagged file

1. Apply the exact fixes indicated (e.g. incorrect whitespace, import ordering, brace style)

1. Re-run formatSource to confirm the fixes are clean — prefer `ant format-source-current-branch` for branch-wide verification, or `<gradlew> formatSource` scoped to the affected module

Repeat until formatSource exits with no errors. Report the final outcome to the user.

---

## Edge Cases and Guardrails

- **Overloaded methods**: Treat each overload independently.
- **Constructors**: Apply the same alphabetical rule (no service.xml exception for constructors).
- **Annotations on parameters**: Keep annotations attached to their parameter when reordering.
- **`throws` clause**: Do not touch it.
- **Multi-line signatures**: Preserve the original line-break style after reordering.
- **Generated files**: Skip files in `build/`, `**/generated/**`, or any file with a `@Generated` annotation.
- **Interface files**: If the method being reordered is declared in an interface (`*LocalService.java`, `*Service.java`, `*Persistence.java`) that was auto-generated by Service Builder, do NOT edit it — those are regenerated on build. Only edit hand-written `*Impl` files and other non-generated sources.
- **When unsure**: Flag the method as "needs manual review" in the proposal instead of guessing.