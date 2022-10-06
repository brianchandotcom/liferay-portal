<#assign
	MARKETPLACE_TAGS_CATEGORIES_ID = 449457613

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
			<#if category.getVocabularyId() == MARKETPLACE_TAGS_CATEGORIES_ID>
				<#assign tagCategories = tagCategories + [ category ] />
			</#if>
		</#list>
	</#if>

	<#if tagCategories?size gt 0>
			<#assign vocabulary = assetVocabularyLocalService.getVocabulary(MARKETPLACE_TAGS_CATEGORIES_ID?number) />

			<div class="category-container bg-whiteColor d-lg-block mb-1 pt-4 pb-4 pl-4 pr-4" style="background-color: var(--white);border-radius: var(--border-radius-sm);">
				<div alt="${vocabulary.getDescription()}" class="f-text font-size-paragraph-base font-size-small-caps vocabulary-name">
					${languageUtil.get(locale, "tags", "Tags")}
				</div>

				<#list tagCategories as category>
						<span class="app-tag category-name f-text font-size-paragraph-base mb-1 mr-1 px-1" style="background-color: var(--neutral-8); border-radius: var(--border-radius-sm);">
							${category.getName()}
						</span>
				</#list>
			</div>
		</#if>
</#if>