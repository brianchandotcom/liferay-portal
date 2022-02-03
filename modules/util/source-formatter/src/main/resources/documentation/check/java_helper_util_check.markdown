## JavaHelperUtilCheck

- Classes that only have `static` method (`public` or `protected`) should be
ending with `Util` instead of `Helper`.

#### Exceptions:
- `protected` method with annotation `@Activate`, `@Deactivate`, or `@Reference`
- `protected` method that is the `unbind` method for `@Reference` method
- `public` method with name `destroy` or name starting with `set`

#### Possible solutions:
- Rename class to either `*Helper` or `*Util`
- Make `non-static` method `static` (or vice versa)
- Make `protected` method `private`