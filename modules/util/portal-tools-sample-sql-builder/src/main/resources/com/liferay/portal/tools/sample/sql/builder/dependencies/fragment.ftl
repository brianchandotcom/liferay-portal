<#if (dataFactory.maxContentLayoutCount > 0)>
	<#assign fragmentCollectionModel = dataFactory.newFragmentCollectionModel(groupId) />

	${dataFactory.toInsertSQL(fragmentCollectionModel)}

	<#assign fragmentEntryModel = dataFactory.newFragmentEntryModel(groupId, fragmentCollectionModel) />

	${dataFactory.toInsertSQL(fragmentEntryModel)}

	<#list dataFactory.newContentLayoutModels(groupId) as contentLayoutModel>
		<@insertContentLayout
			_fragmentEntryModel=fragmentEntryModel
			_layoutModel=contentLayoutModel
		/>

		${csvFileWriter.write("fragment", contentLayoutModel.friendlyURL + "\n")}
	</#list>
</#if>