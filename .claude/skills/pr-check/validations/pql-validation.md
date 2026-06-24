# PQL Validation

## Trigger

A `test.properties` file changed. These files contain PQL expressions in `test.batch.run.property.query` properties; a malformed one is otherwise caught only when the batch runs after merging.

## Match

`(^|/)test\.properties$`

## Command

```bash
(cd "${REPO_ROOT}/modules/test/jenkins-results-parser" && ../../../gradlew test --tests com.liferay.jenkins.results.parser.TestPropertiesPQLValidationTest)
```

`TestPropertiesPQLValidationTest` validates every `test.properties` file in the repository, not only the changed file.

## Time Estimate

~30 sec.