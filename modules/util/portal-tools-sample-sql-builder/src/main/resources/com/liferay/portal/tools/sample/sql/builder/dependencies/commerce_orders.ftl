<#list dataFactory.newCommerceOrderModels(commerceChannelGroupModels, commerceAccountEntryModels[0].accountEntryId, commerceCurrencyModel.commerceCurrencyId, 0, 0, 8) as cancelledCommerceOrderModel>
	${dataFactory.toInsertSQL(cancelledCommerceOrderModel)}

	${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(cancelledCommerceOrderModel, defaultCommercePriceEntryModel.commercePriceListId, defaultCommercePriceEntryModel.CProductId, defaultCPInstanceModel))}

	${csvFileWriter.write("commerceOrder", cancelledCommerceOrderModel.commerceOrderId + ", " + cancelledCommerceOrderModel.orderStatus + "\n")}
</#list>

<#list dataFactory.newCommerceOrderModels(commerceChannelGroupModels, commerceAccountEntryModels[0].accountEntryId, commerceCurrencyModel.commerceCurrencyId, 0, 0, 1) as pendingCommerceOrderModel>
	${dataFactory.toInsertSQL(pendingCommerceOrderModel)}

	${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(pendingCommerceOrderModel, defaultCommercePriceEntryModel.commercePriceListId, defaultCommercePriceEntryModel.CProductId, defaultCPInstanceModel))}

	${csvFileWriter.write("commerceOrder", pendingCommerceOrderModel.commerceOrderId + ", " + pendingCommerceOrderModel.orderStatus + "\n")}
</#list>