<#assign
	commerceCurrencyModel = dataFactory.newCommerceCurrencyModel()

	commerceCatalogModels = dataFactory.newCommerceCatalogModels(commerceCurrencyModel)

	commerceCatalogGroupModels = dataFactory.newCommerceCatalogGroupModels(commerceCatalogModels)

	commerceGroupModels = dataFactory.newCommerceGroupModels()

	commerceChannelModels = dataFactory.newCommerceChannelModels(commerceGroupModels, commerceCurrencyModel)

	commerceChannelGroupModels = dataFactory.newCommerceChannelGroupModels(commerceChannelModels)
/>

<#include "commerce_accounts.ftl">

<#include "commerce_inventory_warehouses.ftl">

<#include "commerce_product.ftl">

${dataFactory.toInsertSQL(commerceCurrencyModel)}

<#list commerceCatalogModels as commerceCatalogModel>
	${dataFactory.toInsertSQL(commerceCatalogModel)}

	${dataFactory.toInsertSQL(dataFactory.newCommerceCatalogResourcePermissionModel(commerceCatalogModel))}
</#list>

<#list commerceCatalogGroupModels as commerceCatalogGroupModel>
	<@insertGroup _groupModel=commerceCatalogGroupModel />
</#list>

<#list commerceGroupModels as commerceGroupModel>
	${dataFactory.toInsertSQL(commerceGroupModel)}

	<#assign commerceSiteNavigationPortletPreferencesModels = dataFactory.newCommerceSiteNavigationPortletPreferencesModels(commerceGroupModel) />

	<#list commerceSiteNavigationPortletPreferencesModels as commerceSiteNavigationPortletPreferencesModel>
		${dataFactory.toInsertSQL(commerceSiteNavigationPortletPreferencesModel)}
	</#list>

	<#list dataFactory.newCommerceSiteNavigationPortletDDMTemplateModels(commerceGroupModel.groupId) as commerceSiteNavigationPortletDDMTemplateModel>
		${dataFactory.toInsertSQL(commerceSiteNavigationPortletDDMTemplateModel)}
	</#list>

	<#list dataFactory.newCommerceSiteNavigationPortletPreferenceValueModels(commerceSiteNavigationPortletPreferencesModels) as commerceSiteNavigationPortletPreferenceValueModel>
		${dataFactory.toInsertSQL(commerceSiteNavigationPortletPreferenceValueModel)}
	</#list>

	<#list dataFactory.newLayoutSetModels(commerceGroupModel.groupId, "minium_WAR_miniumtheme") as commerceLayoutSetModel>
		${dataFactory.toInsertSQL(commerceLayoutSetModel)}
	</#list>

	<#list dataFactory.newCommerceLayoutModels(commerceGroupModel.groupId) as commerceLayoutModel>
		<#assign portletPreferencesModels = dataFactory.newCommercePortletPreferencesModels(commerceLayoutModel) />

		<#list portletPreferencesModels as portletPreferencesModel>
			${dataFactory.toInsertSQL(portletPreferencesModel)}
		</#list>

		<#list dataFactory.newCommerceLayoutPortletPreferenceValueModels(portletPreferencesModels) as portletPreferenceValueModel>
			${dataFactory.toInsertSQL(portletPreferenceValueModel)}
		</#list>

		<@insertLayout _layoutModel=commerceLayoutModel />
	</#list>

	${csvFileWriter.write("commerceGroup", commerceGroupModel.groupId + ", " + commerceGroupModel.name + "\n")}
</#list>

<#list commerceChannelModels as commerceChannelModel>
	${dataFactory.toInsertSQL(commerceChannelModel)}
</#list>

<#list commerceChannelGroupModels as commerceChannelGroupModel>
	<#assign
		commerceB2BSiteTypePortletPreferencesModel = dataFactory.newCommerceB2BSiteTypePortletPreferencesModel(commerceChannelGroupModel.groupId)
	/>

	${dataFactory.toInsertSQL(commerceB2BSiteTypePortletPreferencesModel)}

	${dataFactory.toInsertSQL(dataFactory.newCommerceB2BSiteTypePortletPreferenceValueModel(commerceB2BSiteTypePortletPreferencesModel))}

	<@insertGroup _groupModel=commerceChannelGroupModel />
</#list>