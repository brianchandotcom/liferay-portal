## BNDRangeCheck

Do not use range expressions.

Gradle can potentially choose to download the latest version from the artifact
repository that matches the version range.

If we publish an artifact from master, builds based on release tags (such as fix
packs and hotfix builds) and branches (such as ee-7.0.x) will begin failing
because the individual modules will reference the packageinfo files from the
artifact published from the latest master when building the Import-Package
sections of their manifests.

However, since the actual server will export the packageinfo versions for the
release (which can be lower than the ones in master), many modules will fail to
start because their dependencies failed to be satisfied.