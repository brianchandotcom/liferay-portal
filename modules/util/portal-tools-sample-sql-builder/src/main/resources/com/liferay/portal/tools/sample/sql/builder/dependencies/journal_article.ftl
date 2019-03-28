<#assign ddmStructureModel = dataFactory.defaultJournalDDMStructureModel />

<@insertDDMStructure
	_ddmStructureLayoutModel=dataFactory.defaultJournalDDMStructureLayoutModel
	_ddmStructureModel=ddmStructureModel
	_ddmStructureVersionModel=dataFactory.defaultJournalDDMStructureVersionModel
/>

<#assign ddmTemplateModel = dataFactory.defaultJournalDDMTemplateModel />

${dataFactory.toInsertSQL(ddmTemplateModel)}

<#assign ddmTemplateVersionModel = dataFactory.defaultJournalDDMTemplateVersionModel />

${dataFactory.toInsertSQL(ddmTemplateVersionModel)}

<#assign
	journalArticlePageCounts = dataFactory.getSequence(dataFactory.maxJournalArticlePageCount)

	resourcePermissionModels = dataFactory.newResourcePermissionModels("com.liferay.journal", groupId)
/>

<#list resourcePermissionModels as resourcePermissionModel>
	${dataFactory.toInsertSQL(resourcePermissionModel)}
</#list>

<#list journalArticlePageCounts as journalArticlePageCount>
	<#assign
		portletIdPrefix = "com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_TEST_" + journalArticlePageCount + "_"

		layoutModel = dataFactory.newLayoutModel(groupId, groupId + "_journal_article_" + journalArticlePageCount, "", dataFactory.getJournalArticleLayoutColumn(portletIdPrefix))
	/>

	${dataFactory.getCSVWriter("layout").write(layoutModel.friendlyURL + "\n")}

	<@insertLayout _layoutModel=layoutModel />

	<#assign portletPreferencesModels = dataFactory.newJournalPortletPreferencesModels(layoutModel.plid) />

	<#list portletPreferencesModels as portletPreferencesModel>
		${dataFactory.toInsertSQL(portletPreferencesModel)}
	</#list>

	<#assign journalArticleCounts = dataFactory.getSequence(dataFactory.maxJournalArticleCount) />

	<#list journalArticleCounts as journalArticleCount>
		<#assign journalArticleResourceModel = dataFactory.newJournalArticleResourceModel(groupId) />

		${dataFactory.toInsertSQL(journalArticleResourceModel)}

		<#assign versionCounts = dataFactory.getSequence(dataFactory.maxJournalArticleVersionCount) />

		<#list versionCounts as versionCount>
			<#assign journalArticleModel = dataFactory.newJournalArticleModel(journalArticleResourceModel, journalArticleCount, versionCount) />

			${dataFactory.toInsertSQL(journalArticleModel)}

			<#assign journalArticleLocalizationModel = dataFactory.newJournalArticleLocalizationModel(journalArticleModel, journalArticleCount, versionCount) />

			${dataFactory.toInsertSQL(journalArticleLocalizationModel)}

			${dataFactory.toInsertSQL(dataFactory.newDDMTemplateLinkModel(journalArticleModel, ddmTemplateModel.templateId))}

			${dataFactory.toInsertSQL(dataFactory.newDDMStorageLinkModel(journalArticleModel, ddmStructureModel.structureId))}

			${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(journalArticleModel))}

			<#if versionCount = dataFactory.maxJournalArticleVersionCount>
				<@insertAssetEntry
					_categoryAndTag=true
					_entry=dataFactory.newObjectValuePair(journalArticleModel, journalArticleLocalizationModel)
				/>
			</#if>
		</#list>

		<@insertMBDiscussion
			_classNameId=dataFactory.journalArticleClassNameId
			_classPK=journalArticleResourceModel.resourcePrimKey
			_groupId=groupId
			_maxCommentCount=0
			_mbRootMessageId=dataFactory.getCounterNext()
			_mbThreadId=dataFactory.getCounterNext()
		/>

		${dataFactory.toInsertSQL(dataFactory.newPortletPreferencesModel(layoutModel.plid, portletIdPrefix + journalArticleCount, journalArticleResourceModel))}

		${dataFactory.toInsertSQL(dataFactory.newJournalContentSearchModel(journalArticleModel, layoutModel.layoutId))}
	</#list>
</#list>