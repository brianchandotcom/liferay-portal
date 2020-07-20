<#list dataFactory.assetEntryModels as assetEntryModel>
	${dataFactory.toInsertSQL(assetEntryModel)}
</#list>

${dataFactory.toInsertSQL(dataFactory.newCommerceCatalogModel())}

${dataFactory.toInsertSQL(dataFactory.commerceCatalogResourcePermission())}

${dataFactory.toInsertSQL(dataFactory.newCommerceChannelModel())}

${dataFactory.toInsertSQL(dataFactory.newCommerceCurrencyModel())}

<#list dataFactory.CPDefinitionLocalizationModels as cpDefinitionLocalizationModel>
	${dataFactory.toInsertSQL(cpDefinitionLocalizationModel)}
</#list>

<#list dataFactory.CPDefinitionModels as cpDefinitionModel>
	${dataFactory.toInsertSQL(cpDefinitionModel)}
</#list>

<#list dataFactory.CPFriendlyURLEntryModels as cpFriendlyURLEntryModel>
	${dataFactory.toInsertSQL(cpFriendlyURLEntryModel)}

	${csvFileWriter.write("cpFriendlyURLEntry", cpFriendlyURLEntryModel.urlTitle + "\n")}
</#list>

<#list dataFactory.CPInstanceModels as cpInstanceModel>
	${dataFactory.toInsertSQL(cpInstanceModel)}
</#list>

<#list dataFactory.CProductModels as cProductModel>
	${dataFactory.toInsertSQL(cProductModel)}
</#list>

${dataFactory.toInsertSQL(dataFactory.CPTaxCategoryModel)}

<@insertGroup _groupModel=dataFactory.newCommerceCatalogGroupModel() />

<@insertGroup _groupModel=dataFactory.newCommerceChannelGroupModel() />