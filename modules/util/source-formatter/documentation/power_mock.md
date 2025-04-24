## PowerMock

Do not use `PowerMock` in Arquillian integration tests.
In a true intergration test, a single test does not own the JVM and is not
supposed to leave anything behind. Powermock uses a custom classloader to enable
mocking and for class generation and injection, which could cause serious damage
when it is used for a true integration test.