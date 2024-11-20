<#assign
	objectFolderModel = dataFactory.newObjectFolderModel()
/>

${dataFactory.toInsertSQL(objectFolderModel)}

<#list dataFactory.newSystemObjectDefinitionModels(objectFolderModel.getObjectFolderId()) as systemObjectDefinitionModel>
	${dataFactory.toInsertSQL(systemObjectDefinitionModel)}

	<#list dataFactory.newResourcePermissionModels(systemObjectDefinitionModel) as resourcePermissionModel>
		${dataFactory.toInsertSQL(resourcePermissionModel)}
	</#list>

	<#list dataFactory.newObjectFieldModels(systemObjectDefinitionModel.getObjectDefinitionId(), systemObjectDefinitionModel.getDBTableName(), 0, systemObjectDefinitionModel.getPKObjectFieldName()) as objectFieldModel>
		${dataFactory.toInsertSQL(objectFieldModel)}
	</#list>

	<#if systemObjectDefinitionModel.getDBTableName() == "CommerceOrder">
		 ${dataFactory.toInsertSQL(dataFactory.newObjectActionModel(systemObjectDefinitionModel.objectDefinitionId, notificationTemplateModel.notificationTemplateId))}
	</#if>

	<#if systemObjectDefinitionModel.getDBTableName() == "User_">
		${dataFactory.getExtensionDynamicObjectDefinitionTableCreateSQL(systemObjectDefinitionModel)}
	</#if>
</#list>