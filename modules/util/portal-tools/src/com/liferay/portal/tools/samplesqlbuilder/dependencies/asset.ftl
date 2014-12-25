<#list dataFactory.assetVocabularyModels as assetVocabularyModel>
	insert into AssetVocabulary values ('${assetVocabularyModel.uuid}', ${assetVocabularyModel.vocabularyId}, ${assetVocabularyModel.groupId}, ${assetVocabularyModel.companyId}, ${assetVocabularyModel.userId}, '${assetVocabularyModel.userName}', '${dataFactory.getDateString(assetVocabularyModel.createDate)}', '${dataFactory.getDateString(assetVocabularyModel.modifiedDate)}', '${assetVocabularyModel.name}', '${assetVocabularyModel.title}', '${assetVocabularyModel.description}', '${assetVocabularyModel.settings}');

	<@insertResourcePermissions
		_entry = assetVocabularyModel
	/>
</#list>

<#list dataFactory.assetCategoryModels as assetCategoryModel>
	insert into AssetCategory values ('${assetCategoryModel.uuid}', ${assetCategoryModel.categoryId}, ${assetCategoryModel.groupId}, ${assetCategoryModel.companyId}, ${assetCategoryModel.userId}, '${assetCategoryModel.userName}', '${dataFactory.getDateString(assetCategoryModel.createDate)}', '${dataFactory.getDateString(assetCategoryModel.modifiedDate)}', ${assetCategoryModel.parentCategoryId}, ${assetCategoryModel.leftCategoryId}, ${assetCategoryModel.rightCategoryId}, '${assetCategoryModel.name}', '${assetCategoryModel.title}', '${assetCategoryModel.description}', ${assetCategoryModel.vocabularyId});

	<@insertResourcePermissions
		_entry = assetCategoryModel
	/>
</#list>

<#list dataFactory.assetTagModels as assetTagModel>
	insert into AssetTag values (${assetTagModel.tagId}, ${assetTagModel.groupId}, ${assetTagModel.companyId}, ${assetTagModel.userId}, '${assetTagModel.userName}', '${dataFactory.getDateString(assetTagModel.createDate)}', '${dataFactory.getDateString(assetTagModel.modifiedDate)}', '${assetTagModel.name}', ${assetTagModel.assetCount});

	<@insertResourcePermissions
		_entry = assetTagModel
	/>
</#list>

<#list dataFactory.assetTagStatsModels as assetTagStatsModel>
	insert into AssetTagStats values (${assetTagStatsModel.tagStatsId}, ${assetTagStatsModel.tagId}, ${assetTagStatsModel.classNameId}, ${assetTagStatsModel.assetCount});
</#list>