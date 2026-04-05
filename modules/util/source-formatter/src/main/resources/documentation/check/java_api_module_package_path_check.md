## JavaAPIModulePackagePathCheck

Do not use `api`, `impl`, or `internal` in package names for classes within
API modules.

If you need some classes or default implementation for an `-api` module create
another module ending in `-impl`, `-service`, or `-web`. If a class in the
`-api` module cannot function without the `internal` package move it to a
different module as well. If needed, replace the class with an interface.