<#list dataFactory.newCTCollectionResourcePermissionModels(ctCollectionId, "com.liferay.journal", publicationGroupId) as ctCollectionResourcePermissionModel>
	${dataFactory.toInsertSQL(ctCollectionResourcePermissionModel)}
	${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(ctCollectionId, ctCollectionResourcePermissionModel, dataFactory.ctChangeTypeAddition))}
</#list>

<#list dataFactory.getSequence(dataFactory.maxPublicationJournalArticlePageCount) as publicationJournalArticlePageCount>
	<#assign
		portletIdPrefix = "com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_TEST_" + publicationJournalArticlePageCount + "_"

		ctCollectionLayoutModel = dataFactory.newCTCollectionLayoutModel(ctCollectionId, publicationGroupId, publicationGroupId + "_ct_journal_article_" + publicationJournalArticlePageCount, "", dataFactory.getJournalArticleLayoutColumn(portletIdPrefix))
	/>

	${csvFileWriter.write("layout", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + ctCollectionLayoutModel.friendlyURL + "\n")}

	<@insertLayout
		_ctCollectionId = ctCollectionIdString
		_layoutModel = ctCollectionLayoutModel
	/>

	<#list dataFactory.getSequence(dataFactory.maxCTJournalArticleCount) as ctJournalArticleCount>
		<#assign ctCollectionJournalArticleResourceModel = dataFactory.newCTCollectionJournalArticleResourceModel(ctCollectionId, publicationGroupId) />

		${dataFactory.toCTCollectionInsertSQL(ctCollectionJournalArticleResourceModel, ctCollectionId)}

		<#list dataFactory.getSequence(dataFactory.maxPublicationJournalArticleVersionCount) as publicationVersionCount>
			<#assign
				ctCollectionJournalArticleModel = dataFactory.newCTCollectionJournalArticleModel(ctCollectionId, ctCollectionJournalArticleResourceModel, ctJournalArticleCount, publicationVersionCount)
			/>

			<@insertJournalArticle
				_ctCollectionId = ctCollectionIdString
				_insertAssetEntry = (publicationVersionCount==dataFactory.maxPublicationJournalArticleVersionCount)
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