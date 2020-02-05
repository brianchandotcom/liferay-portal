## JavaModuleTestCheck

### Package Names

For Arquillian integration test, we have to namespace the test class into a
`test` subpackage, to avoid the package name conflicts with the production code.

For unit tests, there is no such requirement.

- Integration test: the package name must end with `.test`
- Unit test: the package name should not end with `.test`

### PowerMock

Do not use `PowerMock` in Arquillian integration tests.
In a true intergration test, a single test does not own the JVM and is not
supposed to leave anything behind. Powermock uses a custom classloader to enable
mocking and for class generation and injection, which could cause serious damage
when it is used for a true integration test.