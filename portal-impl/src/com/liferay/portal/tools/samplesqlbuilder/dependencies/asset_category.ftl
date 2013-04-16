<#list dataFactory.assetVocabularies as assetVocabulary>
	insert into AssetVocabulary values ('${assetVocabulary.uuid}', ${assetVocabulary.vocabularyId}, ${assetVocabulary.groupId}, ${assetVocabulary.companyId}, ${assetVocabulary.userId}, '${assetVocabulary.userName}', '${dataFactory.getDateString(assetVocabulary.createDate)}', '${dataFactory.getDateString(assetVocabulary.modifiedDate)}', '${assetVocabulary.name}', '${assetVocabulary.title}', '${assetVocabulary.description}', '${assetVocabulary.settings}');

	<@insertResourcePermissions
		_entry = assetVocabulary
	/>
</#list>

<#if (maxAssetCategoryCount > 0)>
	<#list dataFactory.assetCategories as assetCategory>
		insert into AssetCategory values ('${assetCategory.uuid}', ${assetCategory.categoryId}, ${assetCategory.groupId}, ${assetCategory.companyId}, ${assetCategory.userId}, '${assetCategory.userName}', '${dataFactory.getDateString(assetCategory.createDate)}', '${dataFactory.getDateString(assetCategory.modifiedDate)}', ${assetCategory.parentCategoryId}, ${assetCategory.leftCategoryId}, ${assetCategory.rightCategoryId}, '${assetCategory.name}', '${assetCategory.title}', '${assetCategory.description}', ${assetCategory.vocabularyId});

		<@insertResourcePermissions
			_entry = assetCategory
		/>
	</#list>
</#if>