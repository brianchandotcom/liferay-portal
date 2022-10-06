<#assign
	MARKETPLACE_LIFERAY_VERSION_CATEGORIES_ID = 449415075

	assetCategoryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryLocalService")
	assetVocabularyLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetVocabularyLocalService")
	COMMERCE_PRODUCT_CLASS_NAME = "com.liferay.commerce.product.model.CPDefinition"
/>

<#if (cpDefinitionId.getData())??>
	<#assign
		productCategories = assetCategoryLocalService.getCategories(COMMERCE_PRODUCT_CLASS_NAME, cpDefinitionId.getData()?number)
		tagCategories = []
	/>

	<#if productCategories?has_content>
		<#list productCategories as category>
			<#if category.getVocabularyId() == MARKETPLACE_LIFERAY_VERSION_CATEGORIES_ID>
				<#assign tagCategories = tagCategories + [ category ] />
			</#if>
		</#list>
	</#if>

	<#if tagCategories?size gt 0>
			<#assign vocabulary = assetVocabularyLocalService.getVocabulary(MARKETPLACE_LIFERAY_VERSION_CATEGORIES_ID?number) />

			<div class="category-container bg-whiteColor d-lg-block mb-lg-1 ml-lg-0 mr-lg-0 mt-lg-0 pb-lg-4 pl-lg-4 pr-lg-4 pt-lg-4 text-lg-left mb-1 pt-4 pb-4 pl-4 pr-4 mb-sm-1 pt-sm-4 pb-sm-4 pl-sm-4 pr-sm-4 mb-md-1 pt-md-4 pb-md-4 pl-md-4 pr-md-4" style="background-color: var(--white);border-radius: var(--border-radius-sm);border-style: solid; border-width: 0px;">
				<div alt="${vocabulary.getDescription()}" class="f-text font-size-paragraph-base font-size-small-caps vocabulary-name">
					${languageUtil.get(locale, "supported-versions", "Supported Versions")}
				</div>

				<#list tagCategories?sort_by("name")?reverse as category>
					<span class="category-name f-text font-size-paragraph-base">
						${category.getName()}<#if category?counter lt tagCategories?size>, </#if>
					</span>
				</#list>
			</div>
		</#if>
</#if>