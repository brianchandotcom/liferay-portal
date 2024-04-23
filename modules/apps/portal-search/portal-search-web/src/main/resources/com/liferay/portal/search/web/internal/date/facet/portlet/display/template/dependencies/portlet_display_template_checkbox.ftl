<@liferay_ui["panel-container"]
	extended=true
	id="${namespace + 'facetDatePanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="search-facet"
		id="${namespace + 'facetDatePanel'}"
		markupView="lexicon"
		persistState=true
		title="date"
	>
		<#if !dateFacetDisplayContext.isNothingSelected()>
			<@clay.button
				cssClass="btn-unstyled c-mb-4 facet-clear-btn"
				displayType="link"
				id="${namespace + 'facetDateClear'}"
				onClick="Liferay.Search.FacetUtil.clearSelections(event);"
			>
				<strong>${languageUtil.get(locale, "clear")}</strong>
			</@clay.button>
		</#if>

		<ul class="date list-unstyled">
			<#if entries?has_content>
				<#list entries as entry>
					<li class="facet-value">
						<div class="custom-checkbox custom-control">
							<label class="facet-checkbox-label" for="${namespace}${entry.getBucketText()}">
								<input
									${(entry.isSelected())?then("checked", "")}
									class="custom-control-input facet-term"
									data-term-id="${htmlUtil.escape(entry.getBucketText())}"
									disabled
									id="${namespace}${entry.getBucketText()}"
									name="${namespace}${entry.getBucketText()}"
									onChange='Liferay.Search.FacetUtil.changeSelection(event);'
									type="checkbox"
								/>

								<span class="custom-control-label term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
									<span class="custom-control-label-text">
										<#if entry.isSelected()>
											<strong><@liferay_ui["message"] key="${htmlUtil.escape(entry.getBucketText())}" /></strong>
										<#else>
											<@liferay_ui["message"] key="${htmlUtil.escape(entry.getBucketText())}" />
										</#if>
									</span>
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

			<li class="facet-value">
				<div class="custom-checkbox custom-control">
					<label class="facet-checkbox-label" for="${namespace}${customRangeBucketDisplayContext.getBucketText()}">
						<input
							${(customRangeBucketDisplayContext.isSelected())?then("checked", "")}
							class="custom-control-input facet-term"
							data-term-id="${htmlUtil.escape(customRangeBucketDisplayContext.getBucketText())}"
							disabled
							id="${namespace}${customRangeBucketDisplayContext.getBucketText()}"
							name="${namespace}${customRangeBucketDisplayContext.getBucketText()}"
							onChange='Liferay.Search.FacetUtil.changeSelection(event);'
							type="checkbox"
						/>

						<span class="custom-control-label term-name ${(customRangeBucketDisplayContext.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
							<span class="custom-control-label-text">
								<#if customRangeBucketDisplayContext.isSelected()>
									<strong><@liferay_ui["message"] key="${htmlUtil.escape(customRangeBucketDisplayContext.getBucketText())}" /></strong>
								<#else>
									<@liferay_ui["message"] key="${htmlUtil.escape(customRangeBucketDisplayContext.getBucketText())}" />
								</#if>
							</span>
						</span>

						<#if customRangeBucketDisplayContext.isSelected()>
							<small class="term-count">
								(${customRangeBucketDisplayContext.getFrequency()})
							</small>
						</#if>
					</label>
				</div>
			</li>

			<div class="${(!dateFacetCalendarDisplayContext.isSelected())?then("hide", "")} date-custom-range" id="${namespace}customRange">
				<div class="col-md-6" id="${namespace}customRangeFrom">
					<@liferay_aui["field-wrapper"] label="from">
						<@liferay_ui["input-date"]
							cssClass="date-facet-custom-range-input-date-from"
							dayParam="fromDay"
							dayValue=dateFacetCalendarDisplayContext.getFromDayValue()
							disabled=false
							firstDayOfWeek=dateFacetCalendarDisplayContext.getFromFirstDayOfWeek()
							monthParam="fromMonth"
							monthValue=dateFacetCalendarDisplayContext.getFromMonthValue()
							name="fromInput"
							yearParam="fromYear"
							yearValue=dateFacetCalendarDisplayContext.getFromYearValue()
						/>
					</@>
				</div>

				<div class="col-md-6" id="${namespace}customRangeTo">
					<@liferay_aui["field-wrapper"] label="to">
						<@liferay_ui["input-date"]
							cssClass="date-facet-custom-range-input-date-to"
							dayParam="toDay"
							dayValue=dateFacetCalendarDisplayContext.getToDayValue()
							disabled=false
							firstDayOfWeek=dateFacetCalendarDisplayContext.getToFirstDayOfWeek()
							monthParam="toMonth"
							monthValue=dateFacetCalendarDisplayContext.getToMonthValue()
							name="toInput"
							yearParam="toYear"
							yearValue=dateFacetCalendarDisplayContext.getToYearValue()
						/>
					</@>
				</div>

				<@clay["button"]
					cssClass="date-facet-custom-range-filter-button"
					disabled=dateFacetCalendarDisplayContext.isRangeBackwards()
					displayType="secondary"
					id="${namespace + 'searchCustomRangeButton'}"
					label="search"
					name="${namespace + 'searchCustomRangeButton'}"
				/>
			</div>
		</ul>
	</@>
</@>