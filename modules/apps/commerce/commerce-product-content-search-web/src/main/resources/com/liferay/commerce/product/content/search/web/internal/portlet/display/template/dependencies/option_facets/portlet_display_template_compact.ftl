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
		<#if cpOptionsSearchFacetDisplayContext.isShowClear(companyId, fieldName)>
			<@liferay_aui.button
				cssClass="btn-link btn-unstyled facet-clear-btn"
				onClick="Liferay.Search.FacetUtil.clearSelections(event);"
				value="clear"
			/>
		</#if>

		<ul class="list-unstyled">
			<#if entries?has_content>
				<#list entries as entry>
					<li class="facet-value">
						<#assign id = stringUtil.randomId() />

						<button
							class="btn btn-link btn-unstyled facet-term term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}"
							data-term-id="${entry.getTerm()}"
							name="${name + entry?index}"
							id="${id}"
						>
							${htmlUtil.escape(entry.getTerm())}
								<#if showFrequencies>
									<small class="term-count">
										(${entry.getFrequency()})
									</small>
								</#if>
						</button>

						<@liferay_aui.script>
							document.getElementById('${id}').onclick = function() {
								Liferay.Search.FacetUtil.changeSelection(event);
							}
						</@liferay_aui.script>
					</li>
				</#list>
			</#if>
		</ul>
	</@>
</@>