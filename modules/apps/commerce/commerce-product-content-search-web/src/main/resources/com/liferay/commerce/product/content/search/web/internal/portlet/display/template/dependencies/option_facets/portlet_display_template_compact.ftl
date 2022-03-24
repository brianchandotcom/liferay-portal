<@liferay_ui["panel-container"]
	extended=true
	id="${namespace + 'facetCPOptionsPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet"
		id="${namespace + 'facetCPOptionsPanel'}"
		markupView="lexicon"
		persistState=true
		title="${title}"
	>
		<ul class="list-unstyled">
			<#if entries?has_content>
				<#list entries as entry>
					<li class="facet-value">
						<button
							class="btn btn-link btn-unstyled facet-term term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}"
							data-term-id="${entry.getTerm()}"
							name="${name + entry?index}"
							onClick="Liferay.Search.FacetUtil.changeSelection(event);"
						>
							${htmlUtil.escape(entry.getTerm())}
								<#if showFrequencies>
									<small class="term-count">
										(${entry.getFrequency()})
									</small>
								</#if>
						</button>
					</li>
				</#list>
			</#if>
		</ul>
	</@>
</@>