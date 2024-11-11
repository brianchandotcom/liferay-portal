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

<#list dataFactory.newSystemObjectFieldModels(commerceOrderObjectDefinitionModel.getObjectDefinitionId(), commerceOrderObjectDefinitionModel.getDBTableName(), "commerceOrderId") as systemObjectFieldModel>
	${dataFactory.toInsertSQL(systemObjectFieldModel)}
</#list>

${dataFactory.toInsertSQL(userObjectDefinitionModel)}

<#list dataFactory.newResourcePermissionModels(userObjectDefinitionModel) as resourcePermissionModel>
	${dataFactory.toInsertSQL(resourcePermissionModel)}
</#list>

${dataFactory.getExtensionDynamicObjectDefinitionTableCreateSQL(userObjectDefinitionModel)}

<#list dataFactory.newSystemObjectFieldModels(userObjectDefinitionModel.getObjectDefinitionId(), userObjectDefinitionModel.getDBTableName(), "userId") as systemObjectFieldModel>
	${dataFactory.toInsertSQL(systemObjectFieldModel)}
</#list>