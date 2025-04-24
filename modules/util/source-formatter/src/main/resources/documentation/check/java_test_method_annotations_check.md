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

---

Use `@FeatureFlags` on class and method to simplify code, when possible:

### Example

Incorrect:

```java
@RunWith(Arquillian.class)
public class ObjectEntryResourceTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        PropsUtil.addProperties(
            UnicodePropertiesBuilder.setProperty(
                "feature.flag.LPS-153117", "true"
            ).build());
        PropsUtil.addProperties(
            UnicodePropertiesBuilder.setProperty(
                "feature.flag.LPS-154672", "true"
            ).build());

        TaxonomyCategoryResource.Builder builder =
            axonomyCategoryResource.builder();

...

    }
}
```

Correct:

```java
@FeatureFlags({"LPS-153117", "LPS-154672"})
@RunWith(Arquillian.class)
public class ObjectEntryResourceTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TaxonomyCategoryResource.Builder builder =
            axonomyCategoryResource.builder();

...

    }
}
```

---

Incorrect:

```java
@Test
public void testImportFragmentsWithFolderResources() throws Exception {
    PropsUtil.addProperties(
        UnicodePropertiesBuilder.setProperty(
            "feature.flag.LPS-158675", "true"
        ).build());

    File fileWithFolderResources = _generateZipFileWithFolderResources();

...

}
```

Correct:

```java
@FeatureFlags("LPS-158675")
@Test
public void testImportFragmentsWithFolderResources() throws Exception {
    File fileWithFolderResources = _generateZipFileWithFolderResources();

...

}
```