<#assign
	VOCABULARY_PRODUCT_CATEGORY = "MARKETPLACE LIFERAY VERSION"
/>

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#if currentURL?has_content>
	<#if currentURL?contains('web')>
		<#assign
			index = 2
			partsUrl = currentURL?split('/')
			siteName = partsUrl[index..index]?join('/')
		/>
	</#if>
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#assign
	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&nestedFields=categories")
	categories = product.categories![]
/>

<div class="app-container color-neutral-3 d-flex flex-wrap font-size-paragraph-small justify-content-between w-100">
	<div class="d-flex">
		<#if categories?has_content>
			<#function getVersions(version)>
				<#return version.vocabulary?upper_case == "MARKETPLACE LIFERAY VERSION">
			</#function>

			${categories?sort_by("name")?reverse?filter(getVersions)?map(version -> version.name)?join(", ")}
		</#if>
	</div>
</div>