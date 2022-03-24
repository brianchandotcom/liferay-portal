<@liferay_ui["panel-container"]
	extended=true
	id="${panelContainerId}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet search-facet-display-label"
		id="${panelId}"
		markupView="lexicon"
		persistState=true
		title="${panelTitle}"
	>
		<#if entries?has_content>
			<div class="label-container">
				<#list entries as entry>
					<button
						class="btn label label-lg facet-term term-name ${(entry.isSelected())?then('label-primary facet-term-selected', 'label-secondary facet-term-unselected')}"
						data-term-id="${entry.getDisplayName()}"
						onClick="Liferay.Search.FacetUtil.changeSelection(event);"
						type="button"
					>
						<span class="label-item label-item-expand">
							${htmlUtil.escape(entry.getDisplayName())}

							<#if entry.isFrequencyVisible()>
								(${entry.getFrequency()})
							</#if>
						</span>
					</button>
				</#list>
			</div>
		</#if>
	</@>
</@>