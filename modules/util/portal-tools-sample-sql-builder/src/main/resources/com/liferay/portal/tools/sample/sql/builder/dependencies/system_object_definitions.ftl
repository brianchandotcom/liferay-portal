<#assign
	objectFolderModel = dataFactory.newObjectFolderModel()
	commerceOrderObjectDefinitionModel = dataFactory.newSystemObjectDefinitionModel("L_COMMERCE_ORDER", objectFolderModel.getObjectFolderId(), "CommerceOrder", "Commerce Order", "com.liferay.commerce.model.CommerceOrder", "CommerceOrder", "commerceOrderId")
	userObjectDefinitionModel = dataFactory.newSystemObjectDefinitionModel("L_USER", objectFolderModel.getObjectFolderId(), "User_", "User", "com.liferay.portal.kernel.model.User", "User", "userId")
/>

${dataFactory.toInsertSQL(objectFolderModel)}

${dataFactory.toInsertSQL(commerceOrderObjectDefinitionModel)}

<#list dataFactory.newResourcePermissionModels(commerceOrderObjectDefinitionModel) as resourcePermissionModel>
	${dataFactory.toInsertSQL(resourcePermissionModel)}
</#list>

<#list dataFactory.newObjectFieldModels(commerceOrderObjectDefinitionModel.getObjectDefinitionId(), commerceOrderObjectDefinitionModel.getDBTableName(), 0, "commerceOrderId") as objectFieldModel>
	${dataFactory.toInsertSQL(objectFieldModel)}
</#list>

${dataFactory.toInsertSQL(userObjectDefinitionModel)}

<#list dataFactory.newResourcePermissionModels(userObjectDefinitionModel) as resourcePermissionModel>
	${dataFactory.toInsertSQL(resourcePermissionModel)}
</#list>

${dataFactory.getExtensionDynamicObjectDefinitionTableCreateSQL(userObjectDefinitionModel)}

<#list dataFactory.newObjectFieldModels(userObjectDefinitionModel.getObjectDefinitionId(), userObjectDefinitionModel.getDBTableName(), 0, "userId") as objectFieldModel>
	${dataFactory.toInsertSQL(objectFieldModel)}
</#list>

${dataFactory.toInsertSQL(dataFactory.newObjectActionModel(commerceOrderObjectDefinitionModel.objectDefinitionId, notificationTemplateModel.notificationTemplateId))}