## Module Tests

For Arquillian integration test, we have to namespace the test class into a
`test` subpackage, to avoid the package name conflicts with the production code.

For unit tests, there is no such requirement.

- Integration test: the package name must end with `.test`
- Unit test: the package name should not end with `.test`