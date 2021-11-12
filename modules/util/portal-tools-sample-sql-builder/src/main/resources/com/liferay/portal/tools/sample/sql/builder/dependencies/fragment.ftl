<#if (dataFactory.maxContentLayoutCount > 0)>
	<#assign
		journalArticleResourceModel = dataFactory.newJournalArticleResourceModel(groupId)

		journalArticleModel = dataFactory.newJournalArticleModel(journalArticleResourceModel, 0, 1)
	/>

	${dataFactory.toInsertSQL(journalArticleResourceModel)}

	<@insertJournalArticle
		_insertAssetEntry=true
		_journalArticleModel=journalArticleModel
		_journalDDMStructureModel=defaultJournalDDMStructureModel
		_journalDDMTemplateModel=defaultJournalDDMTemplateModel
	/>

	<#assign fragmentCollectionModel = dataFactory.newFragmentCollectionModel(groupId) />

	${dataFactory.toInsertSQL(fragmentCollectionModel)}

	<#assign fragmentEntryModel = dataFactory.newFragmentEntryModel(groupId, fragmentCollectionModel) />

	${dataFactory.toInsertSQL(fragmentEntryModel)}

	<#list dataFactory.newContentLayoutModels(groupId) as contentLayoutModel>
		<@insertContentLayout
			_fragmentEntryModel=fragmentEntryModel
			_journalArticleModel=journalArticleModel
			_layoutModel=contentLayoutModel
		/>

		${csvFileWriter.write("fragment", virtualHostModel.hostname + "," + contentLayoutModel.friendlyURL + "\n")}
	</#list>
</#if>