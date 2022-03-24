<@liferay_ui["panel-container"]
	extended=true
	id="${panelContainerId}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet"
		id="${panelId}"
		markupView="lexicon"
		persistState=true
		title="${panelTitle}"
	>
		<ul class="list-unstyled">
			<#if entries?has_content>
				<#list entries as entry>
					<li class="facet-value">
						<button
							class="btn btn-link btn-unstyled facet-term ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')} term-name"
							data-term-id="${entry.getDisplayName()}"
							onClick="Liferay.Search.FacetUtil.changeSelection(event);"
						>
							${htmlUtil.escape(entry.getDisplayName())}

							<#if entry.isFrequencyVisible()>
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