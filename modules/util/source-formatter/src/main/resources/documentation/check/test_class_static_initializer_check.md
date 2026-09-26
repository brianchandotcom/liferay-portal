## TestClassStaticInitializerCheck

Do not initialize a static variable, or run a static block, of an Arquillian
test with a value that requires the portal.

The Gradle client JVM loads the test class before shipping it to the portal JVM.
Loading a class runs its static initializers, so static initializers that depend
on portal classes will fail.

### Example

Incorrect:

```java
@RunWith(Arquillian.class)
public class FooTest {

	@Test
	public void testFoo() throws Exception {
		_addFileEntry(_FILE_CONTENT);
	}

	private static final byte[] _FILE_CONTENT =
		DLTestUtil.randomTextFileBytes();

}
```

Correct, when the value is used in a single method:

```java
@RunWith(Arquillian.class)
public class FooTest {

	@Test
	public void testFoo() throws Exception {
		_addFileEntry(DLTestUtil.randomTextFileBytes());
	}

}
```

Correct, when the value is shared by several methods:

```java
@RunWith(Arquillian.class)
public class FooTest {

	@Before
	public void setUp() {
		_fileContent = DLTestUtil.randomTextFileBytes();
	}

	@Test
	public void testFoo1() throws Exception {
		_addFileEntry(_fileContent);

		_assertFileEntry(_fileContent);
	}

	@Test
	public void testFoo2() throws Exception {
		_updateFileEntry(_fileContent);

		_assertFileEntry(_fileContent);
	}

	private byte[] _fileContent;

}
```