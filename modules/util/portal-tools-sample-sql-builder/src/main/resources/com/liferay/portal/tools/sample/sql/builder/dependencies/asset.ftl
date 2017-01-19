<#list dataFactory.assetVocabularyModels as assetVocabularyModel>
	${dataFactory.toInsertSQL(assetVocabularyModel)}

	<@insertResourcePermissions
		_entry = assetVocabularyModel
	/>
</#list>

<#list dataFactory.assetCategoryModels as assetCategoryModel>
	${dataFactory.toInsertSQL(assetCategoryModel)}

	<@insertResourcePermissions
		_entry = assetCategoryModel
	/>
</#list>

<#list dataFactory.assetTagModels as assetTagModel>
	${dataFactory.toInsertSQL(assetTagModel)}

	<@insertResourcePermissions
		_entry = assetTagModel
	/>
</#list>

<#list dataFactory.assetTagStatsModels as assetTagStatsModel>
	${dataFactory.toInsertSQL(assetTagStatsModel)}
</#list>