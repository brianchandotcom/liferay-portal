<#list dataFactory.newCTCollectionResourcePermissionModels(ctCollectionId, "com.liferay.journal", publicationGroupId) as ctCollectionResourcePermissionModel>
	${dataFactory.toInsertSQL(ctCollectionResourcePermissionModel)}
	${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(ctCollectionId, ctCollectionResourcePermissionModel, dataFactory.ctChangeTypeAddition))}
</#list>

<#list dataFactory.getSequence(dataFactory.maxCTJournalArticlePageCount) as ctJournalArticlePageCount>
	<#assign
		portletIdPrefix = "com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_TEST_" + ctJournalArticlePageCount + "_"

		ctCollectionLayoutModel = dataFactory.newCTCollectionLayoutModel(ctCollectionId, publicationGroupId, publicationGroupId + "_ct_journal_article_" + ctJournalArticlePageCount, "", dataFactory.getJournalArticleLayoutColumn(portletIdPrefix))
	/>

	${csvFileWriter.write("layout", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + ctCollectionLayoutModel.friendlyURL + "\n")}

	<@insertLayout
		_ctCollectionId = ctCollectionIdString
		_layoutModel = ctCollectionLayoutModel
	/>

	<#list dataFactory.getSequence(dataFactory.maxCTJournalArticleCount) as ctJournalArticleCount>
		<#assign ctCollectionJournalArticleResourceModel = dataFactory.newCTCollectionJournalArticleResourceModel(ctCollectionId, publicationGroupId) />

		${dataFactory.toCTCollectionInsertSQL(ctCollectionJournalArticleResourceModel, ctCollectionId)}

		<#list dataFactory.getSequence(dataFactory.maxCTJournalArticleVersionCount) as ctJournalArticleVersionCount>
			<#assign
				ctCollectionJournalArticleModel = dataFactory.newCTCollectionJournalArticleModel(ctCollectionId, ctCollectionJournalArticleResourceModel, ctJournalArticleCount, ctJournalArticleVersionCount)
			/>

			<@insertJournalArticle
				_ctCollectionId = ctCollectionIdString
				_insertAssetEntry = (ctJournalArticleVersionCount==dataFactory.maxCTJournalArticleVersionCount)
				_journalArticleModel = ctCollectionJournalArticleModel
				_journalDDMStructureModel = defaultCTJournalDDMStructureModel
				_journalDDMTemplateModel = defaultCTJournalDDMTemplateModel
			/>
		</#list>

		<@insertMBDiscussion
			_classNameId = dataFactory.journalArticleClassNameId
			_classPK = ctCollectionJournalArticleResourceModel.resourcePrimKey
			_groupId = publicationGroupId
			_maxCommentCount = 0
			_mbRootMessageId = dataFactory.getCounterNext()
			_mbThreadId = dataFactory.getCounterNext()
		/>

		${dataFactory.toCTCollectionInsertSQL(dataFactory.newLayoutClassedModelUsageModel(publicationGroupId, ctCollectionLayoutModel.plid, portletIdPrefix + ctJournalArticleCount, ctCollectionJournalArticleResourceModel), ctCollectionId)}

		<#assign journalArticleResourcePortletPreferencesModel = dataFactory.newPortletPreferencesModel(ctCollectionLayoutModel.plid, portletIdPrefix + ctJournalArticleCount) />

		${dataFactory.toCTCollectionInsertSQL(journalArticleResourcePortletPreferencesModel, ctCollectionId)}

		<#list dataFactory.newJournalArticleResourcePortletPreferenceValueModels(journalArticleResourcePortletPreferencesModel, ctCollectionJournalArticleResourceModel) as journalArticleResourcePortletPreferenceValueModel>
			${dataFactory.toCTCollectionInsertSQL(journalArticleResourcePortletPreferenceValueModel, ctCollectionId)}
		</#list>
	</#list>
</#list>