<#assign
	assetVocabularyModels = dataFactory.newAssetVocabularyModels(groupId)
	pageCounts = dataFactory.getSequence(dataFactory.maxAssetPublisherPageCount)
/>

<#list assetVocabularyModels as assetVocabularyModel>
	${dataFactory.toInsertSQL(assetVocabularyModel)}
</#list>

<#list dataFactory.newAssetCategoryModels(groupId, assetVocabularyModels) as assetCategoryModel>
	${dataFactory.toInsertSQL(assetCategoryModel)}
</#list>

<#list dataFactory.newAssetTagModels(groupId) as assetTagModel>
	${dataFactory.toInsertSQL(assetTagModel)}
</#list>

<#list pageCounts as pageCount>
	<#assign
		assetListEntryModel = dataFactory.newAssetListEntryModel(groupId, pageCount)
		portletId = dataFactory.getPortletId("com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_INSTANCE_")

		layoutModel = dataFactory.newLayoutModel(groupId, groupId + "_asset_publisher_" + pageCount, "", portletId)
	/>

	${dataFactory.toInsertSQL(assetListEntryModel)}

	${dataFactory.toInsertSQL(dataFactory.newAssetListEntrySegmentsEntryRelModel(assetListEntryModel, defaultJournalDDMStructureModel, pageCount))}
	${dataFactory.toInsertSQL(dataFactory.newAssetListEntryUsageModel(assetListEntryModel, portletId, layoutModel))}

	${csvFileWriter.write("assetPublisher", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + layoutModel.friendlyURL + "\n")}

	<@insertLayout _layoutModel = layoutModel />

	<#assign portletPreferencesModels = dataFactory.newAssetPublisherPortletPreferencesModels(layoutModel.plid) />

	<#list portletPreferencesModels as portletPreferencesModel>
		${dataFactory.toInsertSQL(portletPreferencesModel)}
	</#list>

	<#assign assetPublisherPortletPreferencesModel = dataFactory.newPortletPreferencesModel(layoutModel.plid, groupId, portletId, pageCount) />

	${dataFactory.toInsertSQL(assetPublisherPortletPreferencesModel)}

	${dataFactory.toInsertSQL(dataFactory.newAssetPublisherPortletPreferenceValueModels(assetListEntryModel, assetPublisherPortletPreferencesModel))}
</#list>