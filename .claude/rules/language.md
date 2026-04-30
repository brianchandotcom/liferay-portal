# Language Keys

All language keys live in the global file at `modules/apps/portal-language/portal-language-lang/src/main/resources/content/Language.properties`. Do not create a module-local `Language.properties` under any module's `src/main/resources/content` directory.

## Adding or Updating Keys

1. Edit `modules/apps/portal-language/portal-language-lang/src/main/resources/content/Language.properties` keeping alphabetical order.

1. Commit the source edit.

1. Run `<gradlew> buildLang` from `modules/apps/portal-language/portal-language-lang` to regenerate every `Language_<locale>.properties` file.

1. Commit the generated files.