## XMLServiceFinderNameCheck

Finder names in `service.xml` should be combined by finder colume names(at least the first character) following by each comparator prefix with delimiter `_`.

- Comparator prefixes:

Comparator | Prefix
-------------- | ------------
`!=` | `Not`
`<` | `Lt`
`<=` | `Lt`
`=` |
`>` | `Gt`
`>=` | `Gt`
`is` | `Is`
`LIKE` | `Like`

### Example

```
<finder db-index="false" name="GtF_C_P" return-type="Collection">
	<finder-column comparator="&gt;" name="folderId" />
	<finder-column name="companyId" />
	<finder-column name="parentFolderId" />
</finder>
```