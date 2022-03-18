<@liferay_ui["panel-container"]
	extended=true
	id="${namespace + 'facetScopePanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet search-facet-display-label"
		id="${namespace + 'facetScopePanel'}"
		markupView="lexicon"
		persistState=true
		title="site"
	>
		<#if entries?has_content>
			<div class="label-container">
				<#list entries as entry>
					<button
						class="btn label label-lg facet-term term-name ${(entry.isSelected())?then('label-primary facet-term-selected', 'label-secondary facet-term-unselected')}"
						data-term-id="${entry.getGroupId()}"
						disabled
						onClick="Liferay.Search.FacetUtil.changeSelection(event);"
						type="button"
					>
						${htmlUtil.escape(entry.getDescriptiveName())}

						<#if entry.isShowCount()>
							(${entry.getCount()})
						</#if>
					</button>
				</#list>
			</div>
		</#if>

		<#if !scopeSearchFacetDisplayContext.isNothingSelected()>
			<@liferay_aui.button
				cssClass="btn-link btn-unstyled facet-clear-btn"
				onClick="Liferay.Search.FacetUtil.clearSelections(event);"
				value="clear"
			/>
		</#if>
	</@>
</@>