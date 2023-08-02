<div class="search-results" id="searchResults">
	<h2 class="pb-3 search-results-heading">
		${searchContainer.getTotal()} ${languageUtil.get(locale, "results-for", "results for")} "${searchResultsPortletDisplayContext.getKeywords()}"
	</h2>

	<#if entries?has_content>
		<#list entries as entry>
			<#assign
				searchEntryTitle = entry.getTitle()!""
				searchEntryContent = entry.getContent()!languageUtil.get(locale, "no-content-preview", "No content preview")
			/>

			<#if searchEntryTitle?has_content>
				<div class="pb-5 search-results-entry">
					<a class="search-results-entry-title" href="${entry.getViewURL()}">
						${searchEntryTitle}
					</a>

					<div class="pt-2 search-results-entry-content">
						${searchEntryContent}
					</div>
				</div>
			</#if>
		</#list>
	</#if>
</div>