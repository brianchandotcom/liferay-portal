<@liferay_ui["panel-container"]
	extended=true
	id="${namespace + 'facetScopePanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet"
		id="${namespace + 'facetScopePanel'}"
		markupView="lexicon"
		persistState=true
		title="site"
	>
		<ul class="list-unstyled">
			<#if entries?has_content>
				<#list entries as entry>
					<li>
						<button
							class="btn btn-link btn-unstyled facet-term ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')} term-name"
							data-term-id="${entry.getGroupId()}"
							disabled
							onClick="Liferay.Search.FacetUtil.changeSelection(event);"
						>
							${htmlUtil.escape(entry.getDescriptiveName())}

							<#if entry.isShowCount()>
								<small class="term-count">
									(${entry.getCount()})
								</small>
							</#if>
						</button>
					</li>
				</#list>
			</#if>
		</ul>

		<#if !scopeSearchFacetDisplayContext.isNothingSelected()>
			<@liferay_aui.button
				cssClass="btn-link btn-unstyled facet-clear-btn"
				onClick="Liferay.Search.FacetUtil.clearSelections(event);"
				value="clear"
			/>
		</#if>
	</@>
</@>