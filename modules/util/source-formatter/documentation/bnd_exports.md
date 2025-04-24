## BND Exports

Exporting packages for modules in the `modules/apps` directory is not allowed.

Exceptions:

* Module name ending with `-api`
* Module name ending with `-client`
* Module name ending with `-spi`
* Module name ending with `-taglib`
* Module name ending with `-test-util`

If you need to export a class, you should create an interface for it and put it
in the appropriate `-api` module or create one if one does not already exist. If
you need to export constants, those should also be moved to `-api`.