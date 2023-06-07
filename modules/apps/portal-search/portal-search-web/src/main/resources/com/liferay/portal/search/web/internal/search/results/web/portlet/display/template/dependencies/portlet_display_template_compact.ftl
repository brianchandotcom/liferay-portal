<div class="c-mb-4 c-mt-4 search-total-label">
	<#if searchContainer.getTotal() == 1>
		${languageUtil.format(locale, "x-result-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
	<#else>
		${languageUtil.format(locale, "x-results-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
	</#if>
</div>

<div class="display-compact">
	<ul class="list-unstyled">
		<#if entries?has_content>
			<#list entries as entry>
				<li class="c-mb-3 c-mt-3">
					<a class="link-primary single-link" href="${entry.getViewURL()}">
						${entry.getHighlightedTitle()}
					</a>
				</li>
			</#list>
		</#if>
	</ul>
</div>