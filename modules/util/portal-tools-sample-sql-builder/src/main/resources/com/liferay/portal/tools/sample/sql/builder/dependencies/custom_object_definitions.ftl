<#assign
	dlFolderModel = dataFactory.newDLFolderModel()
	listTypeDefinitionModel = dataFactory.newListTypeDefinitionModel()
	listTypeEntryModels = dataFactory.newListTypeEntryModels(listTypeDefinitionModel.getListTypeDefinitionId())
	objectDefinitionModel = dataFactory.newObjectDefinitionModel(objectFolderModel.getObjectFolderId())
	objectFieldModels = dataFactory.newObjectFieldModels(objectDefinitionModel.getObjectDefinitionId(), objectDefinitionModel.getDBTableName(), listTypeDefinitionModel.getListTypeDefinitionId())
/>

${dataFactory.toInsertSQL(objectDefinitionModel)}

<#list dataFactory.newResourcePermissionModels(objectDefinitionModel) as resourcePermissionModel>
	${dataFactory.toInsertSQL(resourcePermissionModel)}
</#list>

${dataFactory.toInsertSQL(listTypeDefinitionModel)}

<#list listTypeEntryModels as listTypeEntryModel>
	${dataFactory.toInsertSQL(listTypeEntryModel)}
</#list>

<#list objectFieldModels as objectFieldModel>
	${dataFactory.toInsertSQL(objectFieldModel)}

	<#list dataFactory.newObjectFieldSettingModels(objectFieldModel) as objectFieldSettingModel>
		${dataFactory.toInsertSQL(objectFieldSettingModel)}
	</#list>

	<#if objectFieldModel.getState()>
		<#assign
			objectStateFlowModel = dataFactory.newObjectStateFlowModel(objectFieldModel.getObjectFieldId())
			objectStates = dataFactory.newObjectStateModels(listTypeEntryModels, objectStateFlowModel.getObjectStateFlowId())
		 />

		${dataFactory.toInsertSQL(objectStateFlowModel)}

		<#list objectStates as objectStateModel>
			${dataFactory.toInsertSQL(objectStateModel)}
		</#list>

		<#list dataFactory.newObjectStateTransitionModels(objectStates) as objectStateTransitionModel>
			${dataFactory.toInsertSQL(objectStateTransitionModel)}
		</#list>
	</#if>
</#list>

${dataFactory.toInsertSQL(dataFactory.newObjectRelationshipModel(userObjectDefinitionModel.getObjectDefinitionId(), objectDefinitionModel.getObjectDefinitionId()))}

<#list dataFactory.newSystemObjectFieldModels(objectDefinitionModel.getObjectDefinitionId(), "ObjectEntry", "objectEntryId") as systemObjectFieldModel>
	${dataFactory.toInsertSQL(systemObjectFieldModel)}
</#list>

${dataFactory.toInsertSQL(dlFolderModel)}

<@insertAssetEntry _entry = dlFolderModel />

${dataFactory.getDynamicObjectDefinitionTableCreateSQL(objectDefinitionModel, objectFieldModels)}

${dataFactory.getExtensionDynamicObjectDefinitionTableCreateSQL(objectDefinitionModel)}

<#list dataFactory.newObjectEntryModels(objectDefinitionModel.getObjectDefinitionId()) as objectEntryModel>
	<#assign
		dlFileEntryModel = dataFactory.newDLFileEntryModel(dlFolderModel, "FileEntry" + objectEntryModel.getObjectEntryId(), "txt", "text/plain", dataFactory.getCounterNext())
		dlFileVersionModel = dataFactory.newDLFileVersionModel(dlFileEntryModel)
	 />

	${dataFactory.toInsertSQL(dlFileEntryModel)}

	<@insertAssetEntry _entry = dlFileEntryModel />

	${dataFactory.toInsertSQL(dlFileVersionModel)}

	${dataFactory.toInsertSQL(objectEntryModel)}

	<@insertAssetEntry _entry = objectEntryModel />

	${dataFactory.getInsertIntoDynamicObjectDefinitionTable(dlFileEntryModel.getFileEntryId(), objectDefinitionModel.getDBTableName(), objectEntryModel.getObjectEntryId(), objectFieldModels, objectEntryModel.getUserId())}

	${dataFactory.getInsertIntoDynamicExtensionObjectDefinitionTable(objectDefinitionModel.getDBTableName(), objectEntryModel.getObjectEntryId())}
</#list>