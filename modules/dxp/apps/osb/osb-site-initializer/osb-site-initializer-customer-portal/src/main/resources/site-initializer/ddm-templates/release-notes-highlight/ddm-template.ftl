<#assign
	journalArticleId = .vars["reserved-article-id"].data
	restArticle = restClient.get("/headless-delivery/v1.0/sites/${groupId}/structured-contents/by-key/${journalArticleId}?nestedFields=embeddedTaxonomyCategory")
/>

<#list restArticle.contentFields as contentField>
	<#if stringUtil.equals(contentField.label, "Description")>
		<#assign description = contentField.contentFieldValue.data />
	<#elseif stringUtil.equals(contentField.label, "Name")>
		<#assign name = contentField.contentFieldValue.data />
	</#if>
</#list>

<div class="product-capabilities">
	<#list restArticle.taxonomyCategoryBriefs as taxonomyCategoryBrief>
		<#assign taxonomyVocabularyName = taxonomyCategoryBrief.embeddedTaxonomyCategory.parentTaxonomyVocabulary.name />

		<#if taxonomyVocabularyName == "Product Capabilities">
			<span class="font-weight-normal label label-secondary label-tonal-info m-0 px-2 text-paragraph-sm">
				${taxonomyCategoryBrief.taxonomyCategoryName}
			</span>
		</#if>
	</#list>
</div>

<h2>
	<#if name??>
		${name}
	</#if>
</h2>

<p>
	<#if description??>
		${description}
	</#if>
</p>

<#if restArticle.relatedContents?has_content>
	<div class="font-weight-bold pr-1 text-paragraph-sm"><@liferay.language key="features" />:</div>

	<#list restArticle.relatedContents as relatedContent>
		<div>
			<a href="/${relatedContent.id}">${relatedContent.title}</a>
		</div>
	</#list>
</#if>