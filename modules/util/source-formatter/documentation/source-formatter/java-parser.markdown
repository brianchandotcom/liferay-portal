# JavaParser

The following API is available in [JavaParser](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/portal-tools-java-parser/src/main/java/com/liferay/portal/tools/java/parser/JavaParser.java):

```java
public static String parse(File file, int maxLineLength)
    throws CheckstyleException, IOException;

public static String parse(File file, int maxLineLength, boolean writeFile)
    throws CheckstyleException, IOException;

public static String parse(File file, String content, int maxLineLength)
    throws CheckstyleException, IOException;

public static String parse(
        File file, String content, int maxLineLength, boolean writeFile)
    throws CheckstyleException, IOException;

public static String parseSnippet(String content, String indent);
```

### JavaParser parses and creates parsed content in the following way:

1. **Third party parser Checkstyle is used to create a DetailAST tree from the java file that is passed to the 'parse' method**

For example, parsing [ZipReaderImpl.java](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/portal-impl/src/com/liferay/portal/zip/ZipReaderImpl.java) would result in [this DetailAST tree](/modules/util/source-formatter/documentation/source-formatter/ZipReaderImpl.txt)

2. **JavaParser walks through the DetailAST tree and converts `DetailAST` objects into `JavaTerm` objects.**

A list of classes that implement `JavaTerm` can be found here: https://github.com/liferay/liferay-portal/tree/7.4.3.4-ga4/modules/util/portal-tools-java-parser/src/main/java/com/liferay/portal/tools/java/parser

This logic can be found in [JavavParserUtil.parseJavaTerm]()

3. **Each class that implements `JavaTerm` has logic that describes how to display its content.**

An instance of `JavaTerm` should either extend `BaseJavaTerm` or `BaseJavaExpression`

- Classes that extend `BaseJavaTerm` are the top level JavaTerms inside a class or method.
  Examples of these kind of JavaTerms: `JavaIfStatement`, `JavaMethodDefinition` or `JavaConstructorCall`

  Each class that implements `JavaTerm` by extending `BaseJavaTerm` needs to override the following `toString` method where it describes what this `JavaTerm` should look like:

  ```java
  public String toString(
      String indent, String prefix, String suffix, int maxLineLength);
  ````

  Example can be found [here](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/portal-tools-java-parser/src/main/java/com/liferay/portal/tools/java/parser/JavaAnnotationFieldDefinition.java#L38-L74)

- Classes that extend `BaseJavaExpression` are JavaTerms that can be part of one of those top level JavaTerms.
  Examples of these: `JavaMethodCall` (which can be an expression inside the clause of a JavaIfStatment) or `JavaOperatorExpression`

  Each class that implements `JavaTerm` by extending `BaseJavaExpression` needs to override a `getString` method where it describes what this `JavaExpression` should look like:

  ```java
  protected String getString(
      String indent, String prefix, String suffix, int maxLineLength,
      boolean forceLineBreak);
  ```

  Example can be found [here](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/portal-tools-java-parser/src/main/java/com/liferay/portal/tools/java/parser/JavaAnnotation.java#L56-L107)