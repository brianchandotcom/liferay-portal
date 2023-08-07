<style>
	.solution-category-container {
		display: flex;
		font-size: 1rem;
		line-height: 1.5;
	}

	.solution-category-container .solution-category {
		background-color: #ebeef2;
		border-radius: 2px;
	}
</style>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#assign VOCABULARY_NAME = "marketplace solution category" />

<#if productId?has_content>
	<#assign categories = restClient.get("/headless-commerce-admin-catalog/v1.0/products/" + productId + "/categories").items />

	<#if categories?has_content>
		<div class="color-neutral-3 flex flex-wrap font-size-paragraph-small solution-category-container">
			<#list categories as category>
				<#if category.vocabulary == VOCABULARY_NAME>
					<div class="bg-neutral-8 border-radius-small mb-1 mr-1 px-1 solution-category">
						${category.name}
					</div>
				</#if>
			</#list>
		</div>
	</#if>
</#if>