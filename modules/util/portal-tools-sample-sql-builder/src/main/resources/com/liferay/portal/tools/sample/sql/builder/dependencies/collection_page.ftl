<#assign
	collectionPageContentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, "collection_page")

	assetListEntryModelModel = dataFactory.newAssetListEntryModel(groupId)
/>

${dataFactory.toInsertSQL(assetListEntryModelModel)}

<#assign assetListEntrySegmentsEntryRelModel = dataFactory.newAssetListEntrySegmentsEntryRelModel(assetListEntryModelModel) />

${dataFactory.toInsertSQL(assetListEntrySegmentsEntryRelModel)}

<#assign fragmentEntryLinkModels = dataFactory.newCollectionFragmentEntryLinkModels(collectionPageContentLayoutModels) />

<#list fragmentEntryLinkModels as fragmentEntryLinkModel>
	${dataFactory.toInsertSQL(fragmentEntryLinkModel)}
</#list>

<#list collectionPageContentLayoutModels as collectionLayoutModel>
	${dataFactory.toInsertSQL(collectionLayoutModel)}

	${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(collectionLayoutModel))}

	<#assign layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(collectionLayoutModel) />

	${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

	<#assign layoutPageTemplateStructureRelModel = dataFactory.newLayoutPageTemplateStructureRelModel(collectionLayoutModel, layoutPageTemplateStructureModel, assetListEntryModelModel, fragmentEntryLinkModels, "collection_page_layout_definition.json", "layout_definition") />

	${dataFactory.toInsertSQL(layoutPageTemplateStructureRelModel)}
</#list>