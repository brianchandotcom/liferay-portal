<div class="search-total-label">
	<#if searchContainer.getTotal() == 1>
		${languageUtil.format(locale, "x-result-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
	<#else>
		${languageUtil.format(locale, "x-results-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
	</#if>
</div>

<div class="display-compact">
	<ul>
		<#if entries?has_content>
			<#list entries as entry>
				<#if !entry.isTemporarilyUnavailable()>
					<li>
						<a class="link-primary single-link" href="${entry.getViewURL()}">
							${entry.getHighlightedTitle()}
						</a>
					</li>
				</#if>
			</#list>
		</#if>
	</ul>
</div>