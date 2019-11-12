<#assign fragmentCollectionModel = dataFactory.newFragmentCollectionModel(groupId) />

${dataFactory.toInsertSQL(fragmentCollectionModel)}

<#assign fragmentEntryModels = dataFactory.newFragmentEntryModels(groupId, fragmentCollectionModel) />

<#list fragmentEntryModels?keys as fragmentEntryModelName>
	${dataFactory.toInsertSQL(fragmentEntryModels["${fragmentEntryModelName}"])}
</#list>

<#assign contentLayoutModels = dataFactory.newContentLayoutModels(groupId) />

<#list contentLayoutModels as contentLayoutModel>
	<@insertContentLayout
		_fragmentEntryModels=fragmentEntryModels
		_layoutModel=contentLayoutModel
	/>

	${dataFactory.getCSVWriter("fragment").write(contentLayoutModel.friendlyURL + "\n")}
</#list>