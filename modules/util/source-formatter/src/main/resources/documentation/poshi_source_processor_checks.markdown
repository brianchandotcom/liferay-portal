# Checks for .function, .jar, .lar, .macro, .path, .testcase, .war or .zip

Check | Category | Description
----- | -------- | -----------
PoshiAntCommandParametersOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts parameters in AntCommand call in Poshi Script files. |
PoshiDependenciesFileLocationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that dependencies files are located in the correct directory. |
PoshiImportsCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts and groups imports in Poshi Script files. |
PoshiIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | Finds incorrect indentation in Poshi Script files. |
[PoshiPauseUsageCheck](check/poshi_pause_usage_check.markdown#poshipauseusagecheck) | [Performance](performance_checks.markdown#performance-checks) | Finds missing comment with JIRA project when using `Pause`. |
PoshiSmokeTestCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for missing and unnecessary `property ci.retries.disabled = true` in smoke test. |
PoshiStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consistency in code style. |
PoshiVariableNameCheck | [Styling](styling_checks.markdown#styling-checks) | Checks variable names for correct use of `camelCase`. |
PoshiWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary whitespace. |