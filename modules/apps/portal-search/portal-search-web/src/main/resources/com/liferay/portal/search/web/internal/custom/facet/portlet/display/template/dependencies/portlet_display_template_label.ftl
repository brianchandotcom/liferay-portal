<@liferay_ui["panel-container"]
	extended=true
	id="${namespace + 'facetCustomPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet search-facet-display-label"
		id="${namespace + 'facetCustomPanel'}"
		markupView="lexicon"
		persistState=true
		title="${customFacetDisplayContext.getDisplayCaption()}"
	>
		<#if !customFacetDisplayContext.isNothingSelected()>
			<@clay.button
				cssClass="btn-unstyled c-mb-4 facet-clear-btn"
				displayType="link"
				id="${namespace + 'facetCustomClear'}"
				onClick="Liferay.Search.FacetUtil.clearSelections(event);"
			>
				<strong>${languageUtil.get(locale, "clear")}</strong>
			</@clay.button>
		</#if>

		<#if entries?has_content>
			<div class="label-container">
				<#list entries as entry>
					<@clay.button
						cssClass="label label-lg facet-term ${(entry.isSelected())?then('label-primary facet-term-selected', 'label-secondary facet-term-unselected')} term-name"
						data\-term\-id="${entry.getBucketText()}"
						disabled="true"
						displayType="unstyled"
						onClick="Liferay.Search.FacetUtil.changeSelection(event);"
					>
						${htmlUtil.escape(entry.getBucketText())}

						<#if entry.isFrequencyVisible()>
							(${entry.getFrequency()})
						</#if>
					</@clay.button>
				</#list>
			</div>
		</#if>
	</@>
</@>