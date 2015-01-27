<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/search/facets/init.jsp" %>

<%
String fieldParamSelection = ParamUtil.getString(request, facet.getFieldId() + "selection", "0");
String fieldParamFrom = ParamUtil.getString(request, facet.getFieldId() + "from");
String fieldParamTo = ParamUtil.getString(request, facet.getFieldId() + "to");

JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");

String modifiedLabel = StringPool.BLANK;

int index = 0;

if (fieldParamSelection.equals("0")) {
	modifiedLabel = LanguageUtil.get(request, "any-time");
}

Calendar localeCal = CalendarFactoryUtil.getCalendar(timeZone, locale);

int firstDayOfWeek = localeCal.getFirstDayOfWeek() - 1;
%>

<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" type="hidden" value="<%= fieldParam %>" />
	<aui:input name='<%= HtmlUtil.escapeAttribute(facet.getFieldId()) + "selection" %>' type="hidden" value="<%= fieldParamSelection %>" />

	<aui:field-wrapper cssClass='<%= randomNamespace + "calendar calendar_" %>' label="" name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>">
		<ul class="modified nav nav-pills nav-stacked">
			<li class="default<%= (fieldParamSelection.equals("0") ? " active" : StringPool.BLANK) %> facet-value">

				<%
				String taglibClearFacet = "window['" + renderResponse.getNamespace() + HtmlUtil.escapeJS(facet.getFieldId()) + "clearFacet'](0);";
				%>

				<aui:a href="javascript:;" onClick="<%= taglibClearFacet %>">
					<aui:icon image="time" /> <liferay-ui:message key="any-time" />
				</aui:a>
			</li>

			<%
			for (int i = 0; i < rangesJSONArray.length(); i++) {
				JSONObject rangesJSONObject = rangesJSONArray.getJSONObject(i);

				String label = HtmlUtil.escape(rangesJSONObject.getString("label"));
				String range = rangesJSONObject.getString("range");

				index = (i + 1);

				if (fieldParamSelection.equals(String.valueOf(index))) {
					modifiedLabel = LanguageUtil.get(request, label);
				}
			%>

				<li class="facet-value<%= fieldParamSelection.equals(String.valueOf(index)) ? " active" : StringPool.BLANK %>">

					<%
					String taglibSetRange = "window['" + renderResponse.getNamespace() + HtmlUtil.escapeJS(facet.getFieldId()) + "setRange'](" + index + ", '" + HtmlUtil.escapeJS(range) + "');";
					%>

					<aui:a href="javascript:;" onClick="<%= taglibSetRange %>">
						<liferay-ui:message key="<%= label %>" />

						<%
						TermCollector termCollector = facetCollector.getTermCollector(range);
						%>

						<c:if test="<%= termCollector != null %>">
							<span class="badge badge-info frequency"><%= termCollector.getFrequency() %></span>
						</c:if>
					</aui:a>
				</li>

			<%
			}
			%>

			<li class="facet-value<%= fieldParamSelection.equals(String.valueOf(index + 1)) ? " active" : StringPool.BLANK %>">

				<%
				TermCollector termCollector = null;

				if (fieldParamSelection.equals(String.valueOf(index + 1))) {
					modifiedLabel = LanguageUtil.get(request, "custom-range");

					termCollector = facetCollector.getTermCollector(fieldParam);
				}
				%>

				<aui:a cssClass='<%= randomNamespace + "custom-range-toggle" %>' href="javascript:;">
					<liferay-ui:message key="custom-range" />&hellip;

					<c:if test="<%= termCollector != null %>">
						<span class="badge badge-info frequency"><%= termCollector.getFrequency() %></span>
					</c:if>
				</aui:a>
			</li>

			<div class="<%= !fieldParamSelection.equals(String.valueOf(index + 1)) ? "hide" : StringPool.BLANK %> modified-custom-range" id="<%= randomNamespace %>custom-range">
				<div id="<%= randomNamespace %>custom-range-from">
					<aui:input label="from" name='<%= HtmlUtil.escapeAttribute(facet.getFieldId()) + "from" %>' size="14" />
				</div>

				<div id="<%= randomNamespace %>custom-range-to">
					<aui:input label="to" name='<%= HtmlUtil.escapeAttribute(facet.getFieldId()) + "to" %>' size="14" />
				</div>

				<%
				String taglibSearchCustomRange = "window['" + renderResponse.getNamespace() + HtmlUtil.escapeJS(facet.getFieldId()) + "searchCustomRange'](" + (index + 1) + ");";
				%>

				<aui:button disabled="<%= Validator.isNull(fieldParamFrom) || Validator.isNull(fieldParamTo) %>" name="searchCustomRangeButton" onClick="<%= taglibSearchCustomRange %>" value="search" />
			</div>
		</ul>
	</aui:field-wrapper>
</div>

<c:if test='<%= !fieldParamSelection.equals("0") %>'>

	<%
	String fieldName = renderResponse.getNamespace() + facet.getFieldId();
	%>

	<aui:script use="liferay-token-list">

		<%
		String tokenLabel = modifiedLabel;

		if (fieldParamSelection.equals(String.valueOf(index + 1))) {
			String fromDateLabel = HtmlUtil.escape(fieldParamFrom);
			String toDateLabel = HtmlUtil.escape(fieldParamTo);

			tokenLabel = UnicodeLanguageUtil.format(request, "from-x-to-x", new Object[] {"<strong>" + fromDateLabel + "</strong>", "<strong>" + toDateLabel + "</strong>"}, false);
		}
		%>

		Liferay.Search.tokenList.add(
			{
				clearFields: '<%= HtmlUtil.escape(HtmlUtil.escapeAttribute(fieldName)) %>',
				fieldValues: '<%= HtmlUtil.escape(HtmlUtil.escapeAttribute(fieldName)) + "selection|0" %>',
				html: '<%= tokenLabel %>'
			}
		);
	</aui:script>
</c:if>

<aui:script>
	function <portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>clearFacet(selection) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>').val('');
		form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>selection').val(selection);

		submitForm(form);
	}

	function <portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>searchCustomRange(selection) {
		var form = AUI.$(document.<portlet:namespace />fm);

		var fromDate = form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>from');
		var toDate = form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>to');

		var fromDateVal = fromDate.val();
		var toDateVal = toDate.val();

		if (fromDateVal && toDateVal) {
			if (fromDateVal > toDateVal) {
				fromDateVal = toDate.val();
				toDateVal = fromDate.val();

				fromDate.val(fromDateVal);
				toDate.val(toDateVal);
			}

			var range = '[' + fromDateVal.replace(/-/g, '') + '000000 TO ' + toDateVal.replace(/-/g, '') + '235959]';

			form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>').val(range);
			form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>selection').val(selection);

			submitForm(form);
		}
	}

	function <portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>setRange(selection, range) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>').val(range);
		form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>selection').val(selection);

		submitForm(form);
	}
</aui:script>

<aui:script use="aui-datepicker-deprecated,aui-form-validator">
	var Util = Liferay.Util;

	var DATE_FORMAT = '%Y-%m-%d';

	var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

	var REGEX_DATE = /^\d{4}(-)(0[1-9]|1[012])\1(0[1-9]|[12][0-9]|3[01])$/;

	var dateFrom = null;
	var dateTo = null;

	var customRangeFrom = A.one('#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from');
	var customRangeTo = A.one('#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to');
	var searchButton = A.one('#<portlet:namespace />searchCustomRangeButton');

	A.mix(
		DEFAULTS_FORM_VALIDATOR.STRINGS,
		{
			<portlet:namespace />dateFormat: '<%= UnicodeLanguageUtil.get(request, "search-custom-range-date-format") %>',
			<portlet:namespace />dateRange: '<%= UnicodeLanguageUtil.get(request, "search-custom-range-invalid-date-range") %>'
		},
		true
	);

	A.mix(
		DEFAULTS_FORM_VALIDATOR.RULES,
		{
			<portlet:namespace />dateFormat: function(val, fieldNode, ruleValue) {
				var validDate = (REGEX_DATE.test(val) === true);

				var dateValue = null;

				if (validDate) {
					dateValue = A.Date.parse(DATE_FORMAT, val);
				}

				if (fieldNode === customRangeFrom) {
					dateFrom = dateValue;
				}
				else if (fieldNode === customRangeTo) {
					dateTo = dateValue;
				}

				return validDate;
			},

			<portlet:namespace />dateRange: function(val, fieldNode, ruleValue) {
				var greaterOrEqual = true;

				if (dateTo && dateFrom) {
					greaterOrEqual = A.Date.isGreaterOrEqual(dateTo, dateFrom);
				}

				return greaterOrEqual;
			}
		},
		true
	);

	var customRangeValidator = new A.FormValidator(
		{
			boundingBox: document.<portlet:namespace />fm,
			fieldContainer: 'div',
			on: {
				errorField: function(event) {
					Util.toggleDisabled(searchButton, true);
				},
				validField: function(event) {
					if (A.Date.isValidDate(dateFrom) && A.Date.isValidDate(dateTo)) {
						Util.toggleDisabled(searchButton, false);
					}
				}
			},
			rules: {
				'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from': {
					<portlet:namespace />dateFormat: true
				},
				'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to': {
					<portlet:namespace />dateFormat: true,
					<portlet:namespace />dateRange: true
				}
			}
		}
	);

	var calendarStrings = {
		next: '<liferay-ui:message key="next" />',
		none: '<liferay-ui:message key="none" />',
		previous: '<liferay-ui:message key="previous" />',
		today: '<liferay-ui:message key="today" />'
	};

	var fromDatepicker = new A.DatePicker(
		{
			after: {
				'calendar:dateChange': function(e) {
					customRangeValidator.validateField(customRangeFrom);
					customRangeValidator.validateField(customRangeTo);
				}
			},
			calendar: {
				dateFormat: DATE_FORMAT,
				firstDayOfWeek: <%= firstDayOfWeek %>,
				locale: '<%= locale %>',

				<c:if test='<%= fieldParamSelection.equals("6") && Validator.isNotNull(fieldParamFrom) %>'>
					selectedDates: [

						<%
						String[] fieldParamFromParts = StringUtil.split(fieldParamFrom, "-");
						%>

						new Date(<%= GetterUtil.getInteger(fieldParamFromParts[0]) %>, <%= GetterUtil.getInteger(fieldParamFromParts[1]) - 1 %>, <%= GetterUtil.getInteger(fieldParamFromParts[2]) %>)
					],
				</c:if>

				selectionMode: 'single'
			},
			trigger: '#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from'
		}
	).render('#<%= randomNamespace %>custom-range-from');

	fromDatepicker.set('strings', calendarStrings);

	var toDatepicker = new A.DatePicker(
		{
			after: {
				'calendar:dateChange': function(e) {
					customRangeValidator.validateField(customRangeTo);
				}
			},
			calendar: {
				dateFormat: DATE_FORMAT,
				firstDayOfWeek: <%= firstDayOfWeek %>,
				locale: '<%= locale %>',

				<c:if test='<%= fieldParamSelection.equals("6") && Validator.isNotNull(fieldParamTo) %>'>
					selectedDates: [

						<%
						String[] fieldParamToParts = StringUtil.split(fieldParamTo, "-");
						%>

						new Date(<%= GetterUtil.getInteger(fieldParamToParts[0]) %>, <%= GetterUtil.getInteger(fieldParamToParts[1]) - 1 %>, <%= GetterUtil.getInteger(fieldParamToParts[2]) %>)
					],
				</c:if>

				selectionMode: 'single'
			},
			trigger: '#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to'
		}
	).render('#<%= randomNamespace %>custom-range-to');

	toDatepicker.set('strings', calendarStrings);

	A.one('.<%= randomNamespace %>custom-range-toggle').on(
		'click',
		function(event) {
			event.halt();

			A.one('#<%= randomNamespace + "custom-range" %>').toggle();
		}
	);
</aui:script>