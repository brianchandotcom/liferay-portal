## CamelCaseNameCheck

- Variable names ending with `name` should be CamelCase:

Incorrect Name | Correct Name
-------------- | ------------
`filename` | `fileName`
`username` | `userName`

- Variables starting with `non`, `re` (except `reCaptcha`) or `sub` should
**not** be CamelCase:

Incorrect Name | Correct Name
-------------- | ------------
`nonExistent` | `nonexistent`
`reSubmit` | `resubmit`
`subOrganization` | `suborganization`