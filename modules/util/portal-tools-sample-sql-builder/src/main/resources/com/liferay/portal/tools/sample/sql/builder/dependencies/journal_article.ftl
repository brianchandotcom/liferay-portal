<#list dataFactory.newResourcePermissionModels("com.liferay.journal", groupId) as resourcePermissionModel>
	${dataFactory.toInsertSQL(resourcePermissionModel)}
</#list>

<#list dataFactory.getSequence(dataFactory.maxJournalArticlePageCount) as journalArticlePageCount>
	<#assign
		portletIdPrefix = "com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_TEST_" + journalArticlePageCount + "_"

		layoutModel = dataFactory.newLayoutModel(groupId, groupId + "_journal_article_" + journalArticlePageCount, "", dataFactory.getJournalArticleLayoutColumn(portletIdPrefix))
	/>

	${csvFileWriter.write("layout", virtualHostModel.hostname + "," + layoutModel.friendlyURL + "\n")}

	<@insertLayout _layoutModel=layoutModel />

	<#list dataFactory.getSequence(dataFactory.maxJournalArticleCount) as journalArticleCount>
		<#assign journalArticleResourceModel = dataFactory.newJournalArticleResourceModel(groupId) />

		${dataFactory.toInsertSQL(journalArticleResourceModel)}

		<#list dataFactory.getSequence(dataFactory.maxJournalArticleVersionCount) as versionCount>
			<#assign
				journalArticleModel = dataFactory.newJournalArticleModel(journalArticleResourceModel, journalArticleCount, versionCount)
			/>

			<@insertJournalArticle
				_insertAssetEntry=(versionCount==dataFactory.maxJournalArticleVersionCount)
				_journalArticleModel=journalArticleModel
				_journalDDMStructureModel=defaultJournalDDMStructureModel
				_journalDDMTemplateModel=defaultJournalDDMTemplateModel
			/>
		</#list>

		<@insertMBDiscussion
			_classNameId=dataFactory.journalArticleClassNameId
			_classPK=journalArticleResourceModel.resourcePrimKey
			_groupId=groupId
			_maxCommentCount=0
			_mbRootMessageId=dataFactory.getCounterNext()
			_mbThreadId=dataFactory.getCounterNext()
		/>

		${dataFactory.toInsertSQL(dataFactory.newLayoutClassedModelUsageModel(groupId, layoutModel.plid, portletIdPrefix + journalArticleCount, journalArticleResourceModel))}

		<#assign journalArticleResourcePortletPreferencesModel = dataFactory.newPortletPreferencesModel(layoutModel.plid, portletIdPrefix + journalArticleCount) />

		${dataFactory.toInsertSQL(journalArticleResourcePortletPreferencesModel)}

		<#list dataFactory.newJournalArticleResourcePortletPreferenceValueModels(journalArticleResourcePortletPreferencesModel, journalArticleResourceModel) as journalArticleResourcePortletPreferenceValueModel>
			${dataFactory.toInsertSQL(journalArticleResourcePortletPreferenceValueModel)}
		</#list>

		${dataFactory.toInsertSQL(dataFactory.newJournalContentSearchModel(journalArticleModel, layoutModel.layoutId))}
	</#list>
</#list>