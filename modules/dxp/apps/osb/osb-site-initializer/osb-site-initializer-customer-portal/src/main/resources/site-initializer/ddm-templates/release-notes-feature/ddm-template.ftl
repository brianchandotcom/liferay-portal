<#assign
	featureAvailability = ""
	journalArticleId = .vars["reserved-article-id"].data
	moreInfoURLs = []
	productCapabilities = ""
	releaseStatusPrevious = ""
	restArticle = restClient.get("/headless-delivery/v1.0/sites/${groupId}/structured-contents/by-key/${journalArticleId}?nestedFields=embeddedTaxonomyCategory")
	ticketURLs = []
/>

<#list restArticle.contentFields as contentField>
	<#if stringUtil.equals(contentField.label, "Description")>
		<#assign description = contentField.contentFieldValue.data />
	<#elseif stringUtil.equals(contentField.label, "More Info")>
		<#list contentField.nestedContentFields as nestedContentField>
			<#if stringUtil.equals(nestedContentField.label, "URL Title")>
				<#assign urlTitle = nestedContentField.contentFieldValue.data />
			<#elseif stringUtil.equals(nestedContentField.label, "URL")>
				<#assign url = nestedContentField.contentFieldValue.data />
			</#if>
		</#list>

		<#if validator.isNotNull(url) && validator.isNotNull(urlTitle)>
			<#assign moreInfoURLs = arrayUtil.append(moreInfoURLs, "<a href=\"" + url + "\">" + urlTitle + "</a>") />
		</#if>
	<#elseif stringUtil.equals(contentField.label, "Name")>
		<#assign name = contentField.contentFieldValue.data />
	<#elseif stringUtil.equals(contentField.label, "Ticket")>
		<#list contentField.nestedContentFields as nestedContentField>
			<#if stringUtil.equals(nestedContentField.label, "URL Title")>
				<#assign urlTitle = nestedContentField.contentFieldValue.data />
			<#elseif stringUtil.equals(nestedContentField.label, "URL")>
				<#assign url = nestedContentField.contentFieldValue.data />
			</#if>
		</#list>

		<#if validator.isNotNull(url) && validator.isNotNull(urlTitle)>
			<#assign ticketURLs = arrayUtil.append(ticketURLs, "<a href=\"" + url + "\">" + urlTitle + "</a>") />
		</#if>
	</#if>
</#list>

<#list restArticle.taxonomyCategoryBriefs as taxonomyCategoryBrief>
	<#assign taxonomyVocabularyName = taxonomyCategoryBrief.embeddedTaxonomyCategory.parentTaxonomyVocabulary.name />

	<#if stringUtil.equals(taxonomyVocabularyName, "Product Capabilities")>
		<#assign productCapabilities = productCapabilities + "<span class=\"font-weight-normal label label-secondary label-tonal-info m-0 px-2 text-paragraph-sm\">${taxonomyCategoryBrief.taxonomyCategoryName}</span>" />
	<#elseif stringUtil.equals(taxonomyVocabularyName, "Feature Availability")>
		<#assign featureAvailability = taxonomyCategoryBrief.taxonomyCategoryName />
	<#elseif stringUtil.equals(taxonomyVocabularyName, "Release Status Previous")>
		<#assign releaseStatusPrevious = taxonomyCategoryBrief.taxonomyCategoryName />
	</#if>
</#list>

<@clay.panel displayTitle="${name} ${productCapabilities}">
	<#if description??>
		${description}
	</#if>
</@clay.panel>

<#list moreInfoURLs as moreInfoURL>
	<p class="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
		<@clay.icon symbol="link" /> ${moreInfoURL}
	</p>
</#list>

<#list ticketURLs as ticketURL>
	<p class="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
		<@clay.icon symbol="check" /> ${ticketURL}
	</p>
</#list>

<div class="release-status">
	<#if releaseStatusPrevious?has_content>
		<span class="release-status-previous">
			${releaseStatusPrevious}
		</span>

		->
	</#if>

	<span class="feature-availability">
		${featureAvailability}
	</span>
</div>