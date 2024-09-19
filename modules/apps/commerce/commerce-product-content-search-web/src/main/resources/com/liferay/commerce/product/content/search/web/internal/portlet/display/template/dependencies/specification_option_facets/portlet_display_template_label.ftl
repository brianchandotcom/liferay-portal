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
		<#if cpSpecificationOptionsSearchFacetDisplayContext.isShowClear()>
			<@liferay_aui.button
				cssClass="btn-link btn-unstyled facet-clear-btn"
				onClick="Liferay.Search.FacetUtil.clearSelections(event);"
				value="clear"
			/>
		</#if>

		<#if entries?has_content>
			<div class="label-container">
				<#list entries as entry>
					<#assign id = stringUtil.randomId() />

					<button
						class="btn label label-lg facet-term term-name ${(entry.isSelected())?then('label-primary facet-term-selected', 'label-secondary facet-term-unselected')}"
						data-term-id="${entry.getDisplayName()}"
						id="${id}"
						type="button"
					>
						<span class="label-item label-item-expand">
							${htmlUtil.escape(entry.getDisplayName())}

							<#if entry.isFrequencyVisible()>
								(${entry.getFrequency()})
							</#if>
						</span>
					</button>

					<@liferay_aui.script>
						document.getElementById('${id}').onclick = function() {
							Liferay.Search.FacetUtil.changeSelection(event);
						}
					</@liferay_aui.script>
				</#list>
			</div>
		</#if>
	</@>
</@>