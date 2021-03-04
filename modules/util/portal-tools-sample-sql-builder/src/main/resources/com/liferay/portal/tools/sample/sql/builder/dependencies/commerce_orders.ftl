<#assign
	cancelledCommerceOrderModels = dataFactory.newCommerceOrderModels(commerceChannelGroupModels, defaultCommerceAccountEntryModel.accountEntryId, commerceCurrencyModel.commerceCurrencyId, 0, 0, 8)

	pendingCommerceOrderModels = dataFactory.newCommerceOrderModels(commerceChannelGroupModels, defaultCommerceAccountEntryModel.accountEntryId, commerceCurrencyModel.commerceCurrencyId, 0, 0, 1)
/>

<#list cancelledCommerceOrderModels as cancelledCommerceOrderModel>
	${dataFactory.toInsertSQL(cancelledCommerceOrderModel)}

	${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(cancelledCommerceOrderModel, defaultCommercePriceEntryModel.commercePriceListId, defaultCommercePriceEntryModel.CProductId, defaultCPInstanceModel))}

	${csvFileWriter.write("commerceOrder", cancelledCommerceOrderModel.commerceOrderId + ", " + cancelledCommerceOrderModel.orderStatus + "\n")}
</#list>

<#list pendingCommerceOrderModels as pendingCommerceOrderModel>
	${dataFactory.toInsertSQL(pendingCommerceOrderModel)}

	${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(pendingCommerceOrderModel, defaultCommercePriceEntryModel.commercePriceListId, defaultCommercePriceEntryModel.CProductId, defaultCPInstanceModel))}

	${csvFileWriter.write("commerceOrder", pendingCommerceOrderModel.commerceOrderId + ", " + pendingCommerceOrderModel.orderStatus + "\n")}
</#list>