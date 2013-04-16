<#list dataFactory.assetVocabularies as assetVocabulary>
	insert into AssetVocabulary values ('${assetVocabulary.uuid}', ${assetVocabulary.vocabularyId}, ${assetVocabulary.groupId}, ${assetVocabulary.companyId}, ${assetVocabulary.userId}, '${assetVocabulary.userName}', '${dataFactory.getDateString(assetVocabulary.createDate)}', '${dataFactory.getDateString(assetVocabulary.modifiedDate)}', '${assetVocabulary.name}', '${assetVocabulary.title}', '${assetVocabulary.description}', '${assetVocabulary.settings}');

	<@insertResourcePermission
		_entry = assetVocabulary
	/>
</#list>