<#if (dataFactory.maxContentLayoutCount > 0) && (dataFactory.maxFragmentsPerLayoutCount > 0)>
	<#assign
		journalArticleResourceModel = dataFactory.newJournalArticleResourceModel(groupId)

		journalArticleModel = dataFactory.newJournalArticleModel(journalArticleResourceModel, 0, 1)
	/>

	${dataFactory.toInsertSQL(journalArticleResourceModel)}

	<@insertJournalArticle
		_insertAssetEntry = true
		_journalArticleModel = journalArticleModel
		_journalDDMStructureModel = defaultJournalDDMStructureModel
		_journalDDMTemplateModel = defaultJournalDDMTemplateModel
	/>

	<#list dataFactory.newContentLayoutModels(groupId) as contentLayoutModel>
		${dataFactory.toInsertSQL(contentLayoutModel)}

		${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(contentLayoutModel))}

		<#assign layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(contentLayoutModel) />

		${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

		<#assign
			segmentsExperienceModel = dataFactory.newSegmentsExperienceModel(groupId, 0, "DEFAULT", contentLayoutModel.plid, "Default", 0)
		/>

	 	${dataFactory.toInsertSQL(segmentsExperienceModel)}

		<#assign fragmentEntryLinkModels = dataFactory.newFragmentEntryLinkModels(journalArticleModel, contentLayoutModel, segmentsExperienceModel.getSegmentsExperienceId()) />

		<#list fragmentEntryLinkModels as fragmentEntryLinkModel>
			${dataFactory.toInsertSQL(fragmentEntryLinkModel)}

			${dataFactory.toInsertSQL(dataFactory.newLayoutClassedModelUsageModel(groupId, contentLayoutModel.plid, "${fragmentEntryLinkModel.fragmentEntryLinkId}", journalArticleResourceModel))}
		</#list>

		<#assign layoutPageTemplateStructureRelModel = dataFactory.newLayoutPageTemplateStructureRelModel(contentLayoutModel, layoutPageTemplateStructureModel, fragmentEntryLinkModels) />

		${dataFactory.toInsertSQL(layoutPageTemplateStructureRelModel)}

		${csvFileWriter.write("fragment", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + contentLayoutModel.friendlyURL + "\n")}
	</#list>
</#if>