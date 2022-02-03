## ValidatorEqualsCheck

Use `Objects.equals(Object, Object)` instead of
`Validator.equals(Object, Object)`.
The logic in both methods is identical, while the former does not force the user
to depend on `kernel.util` package.