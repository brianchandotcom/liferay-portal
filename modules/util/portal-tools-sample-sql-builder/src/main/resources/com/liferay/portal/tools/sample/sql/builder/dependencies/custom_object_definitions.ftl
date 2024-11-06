<#assign objectDefinitionModel = dataFactory.newObjectDefinitionModel(objectFolderModel.getObjectFolderId()) />

${dataFactory.toInsertSQL(objectDefinitionModel)}

<#list dataFactory.newResourcePermissionModels(objectDefinitionModel) as resourcePermissionModel>
	${dataFactory.toInsertSQL(resourcePermissionModel)}
</#list>

<#assign relationshipObjectFieldModel = dataFactory.newObjectFieldModel(0, objectDefinitionModel.getObjectDefinitionId(), "Relationship", "r_userTicket_userId", objectDefinitionModel.getDBTableName(), "Long", "Assignee", "r_userTicket_userId", false, false, false) />

${dataFactory.toInsertSQL(relationshipObjectFieldModel)}

<#assign objectFieldSettingModel = dataFactory.newObjectFieldSettingModel(relationshipObjectFieldModel.getObjectFieldId(), "objectRelationshipERCObjectFieldName", "r_userTicket_userERC") />

${dataFactory.toInsertSQL(objectFieldSettingModel)}

<#assign
	objectRelationshipModel = dataFactory.newObjectRelationshipModel(userObjectDefinitionModel.getObjectDefinitionId(), objectDefinitionModel.getObjectDefinitionId(), relationshipObjectFieldModel.getObjectFieldId())
/>

${dataFactory.toInsertSQL(objectRelationshipModel)}

<#assign listTypeDefinitionModel = dataFactory.newListTypeDefinitionModel() />

${dataFactory.toInsertSQL(listTypeDefinitionModel)}

<#assign listTypeEntryModels = dataFactory.newListTypeEntryModels(listTypeDefinitionModel.getListTypeDefinitionId()) />

<#list listTypeEntryModels as listTypeEntryModel>
	${dataFactory.toInsertSQL(listTypeEntryModel)}
</#list>

<#assign objectFieldModels = dataFactory.newObjectFieldModels(listTypeDefinitionModel.getListTypeDefinitionId(), objectDefinitionModel.getObjectDefinitionId(), objectDefinitionModel.getDBTableName()) />

<#list objectFieldModels as objectFieldModel>
	${dataFactory.toInsertSQL(objectFieldModel)}

	<#list dataFactory.newObjectFieldSettingModels(objectFieldModel) as objectFieldSettingModel>
		${dataFactory.toInsertSQL(objectFieldSettingModel)}
	</#list>

	<#if objectFieldModel.getState() == true>
		<#assign objectStateFlowModel = dataFactory.newObjectStateFlowModel(objectFieldModel.getObjectFieldId()) />

		${dataFactory.toInsertSQL(objectStateFlowModel)}

		<#assign objectStates = dataFactory.newObjectStateModels(listTypeEntryModels, objectStateFlowModel.getObjectStateFlowId()) />

		<#list objectStates as objectStateModel>
			${dataFactory.toInsertSQL(objectStateModel)}
		</#list>

		<#list dataFactory.newObjectStateTransitionModels(objectStates) as objectStateTransitionModel>
			${dataFactory.toInsertSQL(objectStateTransitionModel)}
		</#list>
	</#if>
</#list>

<#assign
	objectFieldModels = objectFieldModels + [relationshipObjectFieldModel]

	dlFolderModel = dataFactory.newDLFolderModel("Objects")
/>

${dataFactory.toInsertSQL(dlFolderModel)}

<@insertAssetEntry _entry = dlFolderModel />

${dataFactory.getDynamicObjectDefinitionTableCreateSQL(objectDefinitionModel, objectFieldModels)}
${dataFactory.getExtensionDynamicObjectDefinitionTableCreateSQL(objectDefinitionModel)}

<#list dataFactory.newObjectEntryModels(objectDefinitionModel.getObjectDefinitionId()) as objectEntryModel>
	<#assign dlFileEntryModel = dataFactory.newDLFileEntryModel(dlFolderModel, "FileEntry" + objectEntryModel.getObjectEntryId(), "txt", "text/plain", dataFactory.getCounterNext()) />

	${dataFactory.toInsertSQL(dlFileEntryModel)}

	<@insertAssetEntry _entry = dlFileEntryModel />

	<#assign dlFileVersionModel = dataFactory.newDLFileVersionModel(dlFileEntryModel) />

	${dataFactory.toInsertSQL(dlFileVersionModel)}

	${dataFactory.toInsertSQL(objectEntryModel)}

	<@insertAssetEntry _entry = objectEntryModel />

	${dataFactory.getInsertIntoDynamicObjectDefinitionTable(dlFileEntryModel.getFileEntryId(), objectDefinitionModel, objectEntryModel, objectFieldModels, objectEntryModel.getUserId())}
	${dataFactory.getInsertIntoDynamicExtensionObjectDefinitionTable(objectDefinitionModel, objectEntryModel)}
</#list>