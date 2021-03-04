<#assign
	commerceInventoryWarehouseModels = dataFactory.newCommerceInventoryWarehouseModels()
/>

<#list commerceInventoryWarehouseModels as commerceInventoryWarehouseModel>
	${dataFactory.toInsertSQL(commerceInventoryWarehouseModel)}

	<#list commerceChannelModels as commerceChannelModel>
		${dataFactory.toInsertSQL(dataFactory.newCommerceChannelRelModel(dataFactory.commerceInventoryWarehouseClassNameId, commerceInventoryWarehouseModel.commerceInventoryWarehouseId, commerceChannelModel.commerceChannelId))}
	</#list>
</#list>