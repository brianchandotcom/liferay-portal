<#list dataFactory.assetVocabularyModels as assetVocabularyModel>
	${dataFactory.toInsertSQL(assetVocabularyModel)}
</#list>

<#list dataFactory.assetCategoryModels as assetCategoryModel>
	${dataFactory.toInsertSQL(assetCategoryModel)}
</#list>

<#list dataFactory.assetTagModels as assetTagModel>
	${dataFactory.toInsertSQL(assetTagModel)}
</#list>

<#list dataFactory.assetTagStatsModels as assetTagStatsModel>
	${dataFactory.toInsertSQL(assetTagStatsModel)}
</#list>