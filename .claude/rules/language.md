# Language Keys

All language keys live in the global file at `modules/apps/portal-language/portal-language-lang/src/main/resources/content/Language.properties`. Do not create a module-local `Language.properties` under any module's `src/main/resources/content` directory.

## Adding or Updating Keys

1. Edit `modules/apps/portal-language/portal-language-lang/src/main/resources/content/Language.properties`, keeping alphabetical order.

1. Commit the source edit.

1. Run `<gradlew> buildLang` from `modules/apps/portal-language/portal-language-lang` to regenerate every `Language_<locale>.properties` file.

1. Commit the generated files.

## Writing Values That Begin with "When Enabled,"

This applies to any value that begins with "When enabled,", regardless of how the key is named — `*-help`, `*-description`, or keys like `when-enabled-a-character-counter-will-be-shown-to-the-user` whose name encodes the full sentence. Follow "When enabled," with an explicit noun-phrase subject and a verb that agrees with that subject. Use the present tense ("a search bar is displayed") rather than the future tense ("a search bar will be displayed"). Do not drop the subject and start with a bare verb ("displays a search bar").

Good:

```
show-search-help=When enabled, a search bar is displayed above the data set.
```

Bad:

```
show-search-help=When enabled, displays a search bar above the data set.
```