<@liferay_ui["panel-container"]
	extended=true
	id="${namespace + 'facetAssetCategoriesPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet"
		id="${namespace + 'facetAssetCategoriesPanel'}"
		markupView="lexicon"
		persistState=true
		title="category"
	>
		<@liferay_aui.form
			method="post"
			name="categoryFacetForm"
		>
			<@liferay_aui.input
				autocomplete="off"
				name=htmlUtil.escape(assetCategoriesSearchFacetDisplayContext.getParameterName())
				type="hidden"
				value=assetCategoriesSearchFacetDisplayContext.getParameterValue()
			/>

			<@liferay_aui.input
				cssClass="facet-parameter-name"
				name="facet-parameter-name"
				type="hidden"
				value=assetCategoriesSearchFacetDisplayContext.getParameterName()
			/>

			<ul class="${(assetCategoriesSearchFacetDisplayContext.isCloud())?then('tag-cloud', 'tag-list')} list-unstyled">
				<#if entries?has_content>
					<#list entries as entry>
						<li class="facet-value tag-popularity-${entry.getPopularity()}">
							<div class="custom-checkbox custom-control">
								<label class="facet-checkbox-label" for="${namespace + 'term_' + entry?index}">
									<input class="custom-control-input facet-term" data-term-id=${entry.getAssetCategoryId()} id="${namespace + 'term_' + entry?index}" name="${namespace + 'term_' + entry?index}" onChange="Liferay.Search.FacetUtil.changeSelection(event);" type="checkbox" ${(entry.isSelected())?then("checked", "")} />

									<span class="custom-control-label term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
										<span class="custom-control-label-text">${htmlUtil.escape(entry.getDisplayName())}</span>
									</span>

									<#if entry.isFrequencyVisible()>
										<small class="term-count">
											(${entry.getFrequency()})
										</small>
									</#if>
								</label>
							</div>
						</li>
					</#list>
				</#if>
			</ul>

			<#if !assetCategoriesSearchFacetDisplayContext.isNothingSelected()>
				<@liferay_aui.button
					cssClass="btn-link btn-unstyled facet-clear-btn"
					onClick="Liferay.Search.FacetUtil.clearSelections(event);"
					value="clear"
				/>
			</#if>
		</@>
	</@>
</@>