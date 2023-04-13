<#if !entries?has_content>
	<div class="sheet">
		<@liferay_frontend["empty-result-message"]
			description='${languageUtil.format(locale, "no-results-were-found-that-matched-the-keywords-x", "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>", false)}'
			title='${languageUtil.format(request, "no-results-were-found", false)}'
		/>
	</div>

<#else>
	<div class="c-mb-4 c-mt-4 search-total-label">
		<#if searchContainer.getTotal() == 1>
			${languageUtil.format(locale, "x-result-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
		<#else>
			${languageUtil.format(locale, "x-results-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
		</#if>
	</div>

	<div class="display-compact">
		<ul class="list-unstyled">
			<#list entries as entry>
				<li class="c-mb-3 c-mt-3">
					<a class="link-primary single-link" href="${entry.getViewURL()}">
						${entry.getHighlightedTitle()}
					</a>
				</li>
			</#list>
		</ul>
	</div>
</#if>