<#if (dataFactory.maxContentLayoutCount > 0) && (dataFactory.maxFragmentHeadingsCount > 0)>
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

	<#list dataFactory.getSequence(dataFactory.maxContentLayoutCount) as contentLayoutCount>
		<#assign
			contentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, groupId + "_web_content_" + contentLayoutCount)

			segmentsExperienceModel = dataFactory.newSegmentsExperienceModel(contentLayoutModels)
		 />

		 ${dataFactory.toInsertSQL(segmentsExperienceModel)}

		<#list contentLayoutModels as contentLayoutModel>
			<#assign
				fragmentEntryLinkModels = dataFactory.newFragmentEntryLinkModels(journalArticleModel, contentLayoutModel, segmentsExperienceModel.getSegmentsExperienceId())

				layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(contentLayoutModel)
			/>

			<#list fragmentEntryLinkModels as fragmentEntryLinkModel>
				${dataFactory.toInsertSQL(fragmentEntryLinkModel)}

				${dataFactory.toInsertSQL(dataFactory.newLayoutClassedModelUsageModel(groupId, contentLayoutModel.plid, "${fragmentEntryLinkModel.fragmentEntryLinkId}", journalArticleResourceModel))}
			</#list>

			${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(contentLayoutModel))}

			${dataFactory.toInsertSQL(contentLayoutModel)}

			${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

			${dataFactory.toInsertSQL(dataFactory.newLayoutPageTemplateStructureRelModel(contentLayoutModel, layoutPageTemplateStructureModel, fragmentEntryLinkModels))}

			${csvFileWriter.write("fragment", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + contentLayoutModel.friendlyURL + "\n")}
		</#list>
	</#list>
</#if>