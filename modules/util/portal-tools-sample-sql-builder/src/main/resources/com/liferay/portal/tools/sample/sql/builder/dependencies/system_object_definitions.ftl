<#assign
	objectFolderModel = dataFactory.newObjectFolderModel()
/>

<#list dataFactory.newObjectDefinitionModels(objectFolderModel.getObjectFolderId()) as objectDefinitionModel>
	${dataFactory.toInsertSQL(objectDefinitionModel)}

	<#list dataFactory.newObjectFieldModels(objectDefinitionModel.getObjectDefinitionId(), objectDefinitionModel.getDBTableName(), 0, objectDefinitionModel.getPKObjectFieldName()) as objectFieldModel>
		${dataFactory.toInsertSQL(objectFieldModel)}
	</#list>

	<#if objectDefinitionModel.getDBTableName() == "CommerceOrder">
		 ${dataFactory.toInsertSQL(dataFactory.newObjectActionModel(objectDefinitionModel.objectDefinitionId, notificationTemplateModel.notificationTemplateId))}
	</#if>

	<#if objectDefinitionModel.getDBTableName() == "User_">
		${dataFactory.getExtensionDynamicObjectDefinitionTableCreateSQL(objectDefinitionModel)}
	</#if>

	<#list dataFactory.newResourcePermissionModels(objectDefinitionModel) as resourcePermissionModel>
		${dataFactory.toInsertSQL(resourcePermissionModel)}
	</#list>
</#list>

${dataFactory.toInsertSQL(objectFolderModel)}