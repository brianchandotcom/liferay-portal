<style>
	.price-model-facet {
		border-radius: 10px;
	}

	.price-model-facet .panel a {
		padding: 1rem;
	}

	.price-model-facet .collapse-icon .collapse-icon-closed .lexicon-icon,
	.price-model-facet .collapse-icon .collapse-icon-open .lexicon-icon {
		margin-top: 0.3rem;
	}

	.price-model-facet .panel-body {
		padding: 0.5rem 1rem 1rem;
	}

	.price-model-facet .list-unstyled {
		margin-bottom: 0;
	}
</style>

<@liferay_ui["panel-container"]
	cssClass="price-model-facet bg-white border-radius-xlarge my-2"
	extended=true
	id="${namespace + 'facetPriceModelPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="font-size-paragraph-small font-weight-semi-bold search-facet"
		extended=!browserSniffer.isMobile(request)
		id="${namespace + 'facetPriceModelCategoriesPanel'}"
		markupView="lexicon"
		persistState=true
		title="${cpSpecificationOptionsSearchFacetDisplayContext.getParameterName()?replace('-', ' ')?upper_case}">
		<ul class="list-unstyled">
			<#list entries as entry>
				<li class="color-neutral-2 facet-value py-1">
					<div class="custom-checkbox custom-control font-weight-normal">
						<label class="facet-checkbox-label" for="${namespace}_term_${entry.getDisplayName()}">
							<input
								${(entry.isSelected())?then("checked","")}
								class="custom-control-input facet-term"
								data-term-id="${entry.getDisplayName()}"
								id="${namespace}_term_${entry.getDisplayName()}"
								name="${namespace}_term_${entry.getDisplayName()}"
								onChange="Liferay.Search.FacetUtil.changeSelection(event);"
								type="checkbox" />

							<span class="custom-control-label font-size-paragraph-small term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
								<span class="custom-control-label-text">
									${htmlUtil.escape(entry.getDisplayName())}
								</span>
							</span>
						</label>
					</div>
				</li>
			</#list>
		</ul>
	</@>
</@>