<#assign
	objectDefinitionModel = dataFactory.newObjectDefinitionModel("L_COMMERCE_ORDER", true, "CommerceOrder", 0)
/>

${dataFactory.toInsertSQL(objectDefinitionModel)}

${dataFactory.toInsertSQL(dataFactory.newObjectActionModel("L_COMMERCE_ORDER_NOTIFICATION", "orderStatus == 1", "", "commerceOrderNotification", "notification", "liferay/commerce_order_status", objectDefinitionModel.getObjectDefinitionId(), "", true))}