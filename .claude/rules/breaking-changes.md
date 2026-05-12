# Breaking Changes

Liferay reports Java API breaking changes via commit messages so they can be extracted and published. Before assuming a change is breaking, ask bnd — it is the source of truth.

## When To Apply

Any change to a public Java type in `portal-kernel`, `portal-impl`, or any OSGi module. Examples that may or may not be breaking depending on context: changing a method return type, removing or renaming a public method, changing a parameter list, removing a public constant, narrowing a class or method's visibility.

## Step 1 — Run Baseline

This determines whether the change is actually breaking and what version bump is required.

- In the API module:

	```bash
	gw baseline
	```

- At `portal-kernel`:

	```bash
	ant -Dbaseline.jar.report.level=persist clean jar
	```

Both commands write the new version directly into `packageinfo` and `bnd.bnd` when a bump is required. Check `git status` afterwards:

- If neither file was modified, the change is not breaking from bnd's perspective. Stop. No commit message edit needed.
- If either file was modified, the bump has already been applied. Continue to Step 2.

## Step 2 — Enrich The Commit Message

Add a `# breaking` block to the commit that introduces the change. Commit with `git commit --cleanup=verbatim` so the `#` headers are not stripped.

Template (the `## Alternatives` section is optional and should be omitted when there is no meaningful alternative):

````
<Jira ticket> <Subject as usual>

# breaking

## What <full/path/from/repo/root/File.java>

<Concrete description: name the class, method, signature change, removed constant, etc.>

## Why

<Motivation: the requirement or constraint that made the break necessary.>

## Alternatives

<Optional. If callers have a migration path other than adopting the new signature, describe it here.>

----
````

## Amendments

If a breaking-change commit message is wrong or missing after the fact, do not rewrite history. Append an entry to `readme/BREAKING_CHANGES_AMENDMENTS.md` instead, following the format used in that file.