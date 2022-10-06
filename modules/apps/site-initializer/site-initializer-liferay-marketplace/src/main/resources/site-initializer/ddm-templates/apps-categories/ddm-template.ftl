<#assign
	assetCategoryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryLocalService")
	MARKETPLACE_APPS_CATEGORIES_ID = 449511395
	COMMERCE_PRODUCT_CLASS_NAME = "com.liferay.commerce.product.model.CPDefinition"
/>

<#if (cpDefinitionId.getData())??>
	<#assign categories = assetCategoryLocalService.getCategories(COMMERCE_PRODUCT_CLASS_NAME, cpDefinitionId.getData()?number) />

	<#if categories?has_content>
		<div class="color-neutral-3 flex flex-wrap font-size-paragraph-small solution-category-container">
			<#list categories as category>
				<#if category.getVocabularyId() == MARKETPLACE_APPS_CATEGORIES_ID>
					<div class="bg-neutral-8 border-radius-small mb-1 mr-1 px-1 solution-category">
						${category.getName()}
					</div>
				</#if>
			</#list>
		</div>
	</#if>
</#if>