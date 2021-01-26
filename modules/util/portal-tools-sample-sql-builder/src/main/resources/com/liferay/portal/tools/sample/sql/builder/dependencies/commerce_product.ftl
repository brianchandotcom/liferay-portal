<#if (dataFactory.maxCommerceProductCount > 0)>
	<#assign
		commerceCurrencyModel = dataFactory.newCommerceCurrencyModel()

		commerceCatalogModel = dataFactory.newCommerceCatalogModel(commerceCurrencyModel)

		commerceCatalogGroupModel = dataFactory.newCommerceCatalogGroupModel(commerceCatalogModel)
		commerceChannelModel = dataFactory.newCommerceChannelModel(commerceCurrencyModel)
		cpTaxCategoryModel = dataFactory.newCPTaxCategoryModel()
	/>

	${dataFactory.toInsertSQL(commerceCatalogModel)}

	${dataFactory.toInsertSQL(dataFactory.newCommerceCatalogResourcePermissionModel(commerceCatalogModel))}

	${dataFactory.toInsertSQL(commerceChannelModel)}

	${dataFactory.toInsertSQL(commerceCurrencyModel)}

	<#list dataFactory.getSequence(dataFactory.maxCommerceProductCount) as commerceProductCount>
		<#assign cProductModel = dataFactory.newCProductModel(commerceCatalogGroupModel) />

		<#list dataFactory.getSequence(dataFactory.maxCommerceProductDefinitionCount) as commerceProductDefinitionCount>
			<#assign
				cpDefinitionModel = dataFactory.newCPDefinitionModel(cpTaxCategoryModel, cProductModel, commerceCatalogGroupModel, commerceProductDefinitionCount)

				cProductModel = dataFactory.setCProductModelPublishedCPDefinitionId(cProductModel, commerceProductDefinitionCount, cpDefinitionModel.CPDefinitionId)

				friendlyURLEntryModel = dataFactory.newFriendlyURLEntryModel(cProductModel.groupId, dataFactory.CProductClassNameId, cProductModel.CProductId)

				friendlyURLEntryLocalizationModel = dataFactory.newFriendlyURLEntryLocalizationModel(friendlyURLEntryModel, "definition-" + cProductModel.publishedCPDefinitionId)
			/>

			${dataFactory.toInsertSQL(cpDefinitionModel)}

			${dataFactory.toInsertSQL(friendlyURLEntryModel)}

			${dataFactory.toInsertSQL(friendlyURLEntryLocalizationModel)}

			${dataFactory.toInsertSQL(dataFactory.newFriendlyURLEntryMapping(friendlyURLEntryModel))}

			${csvFileWriter.write("cpFriendlyURLEntry", friendlyURLEntryLocalizationModel.urlTitle + "\n")}

			${dataFactory.toInsertSQL(dataFactory.newCPDefinitionModelAssetEntryModel(cpDefinitionModel, commerceCatalogGroupModel))}

			${dataFactory.toInsertSQL(dataFactory.newCPDefinitionLocalizationModel(cpDefinitionModel))}

			<#list dataFactory.getSequence(dataFactory.maxCommerceProductInstanceCount) as commerceProductInstanceCount>
				${dataFactory.toInsertSQL(dataFactory.newCPInstanceModel(cpDefinitionModel, commerceCatalogGroupModel, commerceProductInstanceCount))}
			</#list>
		</#list>
	</#list>

	${dataFactory.toInsertSQL(cProductModel)}
</#list>

	${dataFactory.toInsertSQL(cpTaxCategoryModel)}

	<@insertGroup _groupModel=commerceCatalogGroupModel />

	<@insertGroup _groupModel=dataFactory.newCommerceChannelGroupModel(commerceChannelModel) />
</#if>