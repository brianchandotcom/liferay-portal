## XMLServiceFinderNameCheck

The rules for finder name:

- Only one finder column: Finder name should starts with the combination of comparator prefix and finder colume name.

- Two or more finder columns: Finder names in `service.xml` should be combined by finder colume names(at least the first character) following by each comparator prefix with delimiter `_`.

### Comparator prefixes:

Comparator | Prefix
-------------- | ------------
`!=` | `Not`
`<` | `Lt`
`<=` | `Lte`
`=` |
`>` | `Gt`
`>=` | `Gte`
`is` | `Is`
`LIKE` | `Like`

### Examples

```
<finder name="AccountEntryId" return-type="Collection">
	<finder-column name="accountEntryId" />
</finder>
```

```
<finder db-index="false" name="GtF_C_P" return-type="Collection">
	<finder-column comparator="&gt;" name="folderId" />
	<finder-column name="companyId" />
	<finder-column name="parentFolderId" />
</finder>
```