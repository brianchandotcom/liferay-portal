## JavaTestMethodAnnotationsCheck

Methods with one of the following annotations, should follow our naming
standards.

### JUnit 4

Annotation | Method Name
---------- | -----------
`@After` | `tearDown`
`@AfterClass` | `tearDownClass`
`@Before` | `setUp`
`@BeforeClass` | `setUpClass`
`@Test` | `test*`

---

### JUnit 5

Annotation | Method Name
---------- | -----------
`@AfterAll` | `tearDownClass`
`@AfterEach` | `tearDown`
`@BeforeAll` | `setUpClass`
`@BeforeEach` | `setUp`
`@Test` | `test*`