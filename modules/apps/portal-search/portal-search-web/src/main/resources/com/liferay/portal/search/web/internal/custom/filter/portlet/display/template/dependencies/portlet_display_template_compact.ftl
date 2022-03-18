<@liferay_ui["panel-container"]
	extended=true
	id="${namespace + 'filterCustomPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet"
		id="${namespace + 'filterCustomPanel'}"
		markupView="lexicon"
		persistState=true
		title="${htmlUtil.escapeAttribute(customFilterDisplayContext.getHeading())}"
	>
		<div class="form-group-autofit">
			<@liferay_aui["input"]
				cssClass="custom-filter-value-input"
				data\-qa\-id="customFilterValueInput"
				disabled=customFilterDisplayContext.isImmutable()
				id="${namespace + stringUtil.randomId()}"
				label=""
				name="${htmlUtil.escapeAttribute(customFilterDisplayContext.getParameterName())}"
				useNamespace=false
				value="${htmlUtil.escapeAttribute(customFilterDisplayContext.getFilterValue())}"
				wrapperCssClass="form-group-item"
			/>

			<@clay["button"]
				ariaLabel='${languageUtil.get(request, "apply")}'
				disabled=customFilterDisplayContext.isImmutable()
				elementClasses="btn-secondary"
				label='${languageUtil.get(request, "apply")}'
				type="submit"
			/>
		</div>
	</@>
</@>