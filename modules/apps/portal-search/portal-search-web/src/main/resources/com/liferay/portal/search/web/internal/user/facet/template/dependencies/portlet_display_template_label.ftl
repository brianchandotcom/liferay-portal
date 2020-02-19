<@liferay_ui["panel-container"]
	extended=true
	id="${namespace + 'facetUserPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet search-facet-display-label"
		id="${namespace + 'facetUserPanel'}"
		markupView="lexicon"
		persistState=true
		title="user"
	>
		<#if entries?has_content>
			<div class="label-container">
				<#list entries as entry>
					<button
						class="btn label label-lg facet-term term-name ${(entry.isSelected())?then('label-primary facet-term-selected', 'label-secondary facet-term-unselected')}"
						data-term-id="${entry.getUserName()}"
						disabled
						onClick="Liferay.Search.FacetUtil.changeSelection(event);"
						type="button"
					>
						<span class="label-item label-item-expand">
							${htmlUtil.escape(entry.getUserName())}

							<#if entry.isFrequencyVisible()>
								(${entry.getFrequency()})
							</#if>
						</span>
					</button>
				</#list>
			</div>
		</#if>

		<#if !userSearchFacetDisplayContext.isNothingSelected()>
			<@liferay_aui.button
				cssClass="btn-link btn-unstyled facet-clear-btn"
				onClick="Liferay.Search.FacetUtil.clearSelections(event);"
				value="clear"
			/>
		</#if>
	</@>
</@>