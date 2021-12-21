# SourceFormatter

Configuration for SourceFormatter can be found in:

- [checkstyle.xml](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/source-formatter/src/main/resources/checkstyle.xml): main configuraton file for `Checkstyle` checks
- [sourcechecks.xml](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/source-formatter/src/main/resources/sourcechecks.xml): main configuration file for `SourceCheck` checks
- [source-formatter.properties](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/source-formatter.properties): In `properties-formatter.properties`, property values (defined in the configuration files) can be overwritten or added
- [source-formatter-suppressions.xml](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/apps/wiki/source-formatter-suppressions.xml): In `source-formatter-suppressions.xml` files (or directories) can be suppressed for specific checks

## Main Classes in SourceFormatter:

### 1. SourceProcessor

- For every file extension we have a SourceProcessor (JavaSourceProcessor, XMLSourceProcessor etc).
- For every instance of SourceProcessor → `sourceProcessor.format` (different thread for each instance)

These [global variables](https://github.com/brianchandotcom/liferay-portal/pull/111392/commits/1d785936710250cda5b0b907861ed67af68cf26b) hold information that applies to SourceProcessor.

During setting up SourceProcessors (SourceFormatter.init), the following variables are populated in each instance of SourceProcessor:

```java
Map<String, Properties> _propertiesMap;
```
> We find all relevant `source-formatter.properties` and store in a map, where the key is the fileLocation of the properties file

```java
SourceFormatterArgs _sourceFormatterArgs;
```
> [Arguments](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/portal-impl/build.xml#L712-L733) that are passed when executing SF are set in `_sourceFormatterArgs`

```java
SourceFormatterConfiguration _sourceFormatterConfiguration;
```
> Configuration retrieved from `sourcechecks.xml`

![source-formatter-configuration](/modules/util/source-formatter/documentation/source-formatter/source-formatter-configuration.png)

```java
SourceFormatterExcludes _sourceFormatterExcludes;
```
> SourceFormatter will exclude these default directories combined with additional locations that can be specified in `source-formatter.properties#source.formatter.excludes`

```java
SourceFormatterSuppressions _sourceFormatterSuppressions;
```
> `_sourceFormatterSuppressions` is retrieved from a collection of `source-formatter-suppressions.xml` files

![source-formatter-suppressions](/modules/util/source-formatter/documentation/source-formatter/source-formatter-suppressions.png)

### 2. SourceCheck

For every sourceProcessor, we grab the list of sourceChecks from `SourceProcessor._sourceFormatterConfiguration` and perform this check on every file associated with the sourceProcessor.

These [global variables](https://github.com/brianchandotcom/liferay-portal/pull/111392/commits/47292df35e86e1421be99c4a8be9d9220ea69bca) hold information that applies to SourceCheck.

When setting up SourceChecks (BaseSourceProcessor._getSourceChecks), the following variable is populated in each instance of SourceCheck:

```java
JSONObject _attributesJSONObject;
```
> This JSONObject stores all attributes from the configuration file (`sourcechecks.xml`) and additional attributes that are set in `source-formatter.properties`:

![attributes-json](/modules/util/source-formatter/documentation/source-formatter/attributes-json.png)

## Different types of SourceCheck

We have two different types of SourceCheck

1. Check that implements `FileCheck` (by extending `BaseFileCheck`)
    - When a check extends `BaseFileCheck`, the following method needs to be implemented:

        ```java
        protected String doProcess(
                String fileName, String absolutePath, String content)
            throws Exception;
        ```
The method `doProcess` will take the fileName, absolutePath and content of the file that is being formatted. When the logic in the `doProcess` method makes changes to the content and returns that modified content, these changes will be applied to the file.

If we want to warn instead of changing the content of the file, we can call `BaseSourceCheck.addMessage`

Example of a [FileCheck](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/checks/BNDRangeCheck.java)

2. Check that implements `JavaTermCheck` (by extending `BaseJavaTermCheck`)
    - When a check extends `BaseJavaTermCheck`, the following methods need to be implemented:

        ```java
        protected String doProcess(
                String fileName, String absolutePath, JavaTerm javaTerm,
                String fileContent)
            throws Exception;

        protected String[] getCheckableJavaTermNames();
        ```

Only checks that are associated with `JavaSourceProcessor` can extend `BaseJavaTermCheck`.

In order to perform a JavaTermCheck, we first parse the java file:

```java
JavaClass javaClass = JavaClassParser.parseJavaClass(
    fileName, sourceChecksResult.getContent());
```

`javaClass.getChildJavaTerm` returns a list of JavaTerm Objects.
The following classes implement JavaTerm:
  - JavaClass
  - JavaConstructor
  - JavaMethod
  - JavaStaticBlock
  - JavaVariable

The check will only be performed on the JavaTerms that are specified in the method `getCheckableJavaTermNames`
In method `getCheckableJavaTermNames` an array of JavaTerms is specified.

Example of a [JavaTermCheck](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/checks/JavaConstructorParametersCheck.java)

## Checkstyle Checks

For `java` and `jsp` files we can also write Checkstyle checks.

A Checkstyle check should extend `BaseCheck` and the following methods need to be implented:

```java
public int[] getDefaultTokens();

protected void doVisitTokenI(DetailAST detailAST);
```

The Checkstyle checks make use of the third-party Checkstyle parser.
This parser parses a `java` file into `DetailAST` objects.

For example, parsing [ZipReaderImpl.java](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/portal-impl/src/com/liferay/portal/zip/ZipReaderImpl.java) would result in [this DetailAST tree](/modules/util/source-formatter/documentation/source-formatter/ZipReaderImpl.txt)

In method `getDefaultTokens` an array of [TokenTypes](https://checkstyle.sourceforge.io/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html) is specified. Only `DetailAST` objects in the [tree](/modules/util/source-formatter/documentation/source-formatter/ZipReaderImpl.txt) that have a type that matches a `TokenType` specified in method `getDefaultTokens` will be checked.
Each DetailAST that will be checked will be passed to method `doVisitToken` which contains the check logic.

SourceFormatter calls [Checker.process](https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/Checker.html#process-java.util.List-) and thus makes full use of the Checkstyle processing, but SourceFormatter has implemented its own class [Checker](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/checkstyle/Checker.java) and [CheckstyleLogger](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/checkstyle/util/CheckstyleLogger.java) in order to do its own messsage handling (treating it the same way as it treats messages created by SourceChecks).

*Checkstyle in `jsp` files*

Checkstyle only checks java source code. Since there is a lot java source inside `jsp` files, SourceFormatter uses logic that finds java source code blocks in `jsp` files, converts it into a temporary java file, which inserts placeholders on lines that are not java source, in order to keep the correct line numbers, and passes that temporary file to the Checkstyle checks.

For example, [view.jsp](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/apps/account/account-admin-web/src/main/resources/META-INF/resources/account_entries_admin/view.jsp) would be converted to a [temporary java file](/modules/util/source-formatter/documentation/source-formatter/view.java) and this temporary file would be processed by Checkstyle.