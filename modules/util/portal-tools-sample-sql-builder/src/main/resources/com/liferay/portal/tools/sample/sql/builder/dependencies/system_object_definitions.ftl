<#assign
	objectFolderModel = dataFactory.newObjectFolderModel()

	commerceOrderObjectDefinitionModel = dataFactory.newSystemObjectDefinitionModel(objectFolderModel.getObjectFolderId(), "CommerceOrder", "Commerce Order", "com.liferay.commerce.model.CommerceOrder", "CommerceOrder", "commerceOrderId", "L_COMMERCE_ORDER")

	userObjectDefinitionModel = dataFactory.newSystemObjectDefinitionModel(objectFolderModel.getObjectFolderId(), "User_", "User", "com.liferay.portal.kernel.model.User", "User", "userId", "L_USER")
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