<#list dataFactory.newAssetVocabularyModels(dataFactory.newDefaultAssetVocabularyModel()) as assetVocabularyModel>
	${dataFactory.toInsertSQL(assetVocabularyModel)}
</#list>

<#list dataFactory.assetCategoryModels as assetCategoryModel>
	${dataFactory.toInsertSQL(assetCategoryModel)}
</#list>

<#list dataFactory.assetTagModels as assetTagModel>
	${dataFactory.toInsertSQL(assetTagModel)}
</#list>