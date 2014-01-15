<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
	modifiedLabel = LanguageUtil.get(pageContext, "any-time");
}

Calendar localeCal = CalendarFactoryUtil.getCalendar(timeZone, locale);

int firstDayOfWeek = localeCal.getFirstDayOfWeek() - 1;
%>

<div class="<%= cssClass %>" data-facetFieldName="<%= facet.getFieldId() %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldId() %>" type="hidden" value="<%= fieldParam %>" />
	<aui:input name='<%= facet.getFieldId() + "selection" %>' type="hidden" value="<%= fieldParamSelection %>" />

	<aui:field-wrapper cssClass='<%= randomNamespace + "calendar calendar_" %>' label="" name="<%= facet.getFieldId() %>">
		<ul class="modified nav nav-pills nav-stacked">
			<li class="facet-value default<%= (fieldParamSelection.equals("0") ? " active" : StringPool.BLANK) %>">
				<aui:a href="javascript:;" onClick='<%= renderResponse.getNamespace() + facet.getFieldId() + "clearFacet(0);" %>'>
					<aui:icon image="time" /> <liferay-ui:message key="any-time" />
				</aui:a>
			</li>

			<%
			for (int i = 0; i < rangesJSONArray.length(); i++) {
				JSONObject rangesJSONObject = rangesJSONArray.getJSONObject(i);

				String label = rangesJSONObject.getString("label");
				String range = rangesJSONObject.getString("range");

				index = (i + 1);

				if (fieldParamSelection.equals(String.valueOf(index))) {
					modifiedLabel = LanguageUtil.get(pageContext, label);
				}
			%>

				<li class="facet-value<%= fieldParamSelection.equals(String.valueOf(index)) ? " active" : StringPool.BLANK %>">

					<%
					String taglibSetRange = renderResponse.getNamespace() + facet.getFieldId() + "setRange(" + index + ", '" + range + "');";
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
					modifiedLabel = LanguageUtil.get(pageContext, "custom-range");

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
					<aui:input label="from" name='<%= facet.getFieldId() + "from" %>' size="14" />
				</div>

				<div id="<%= randomNamespace %>custom-range-to">
					<aui:input label="to" name='<%= facet.getFieldId() + "to" %>' size="14" />
				</div>

				<aui:button disabled="<%= Validator.isNull(fieldParamFrom) || Validator.isNull(fieldParamTo) %>" name="searchCustomRangeButton" onClick='<%= renderResponse.getNamespace() + facet.getFieldId() + "searchCustomRange(" + (index + 1) + ");" %>' value="search" />
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

			tokenLabel = UnicodeLanguageUtil.format(pageContext, "from-x-to-x", new Object[] {"<strong>" + fromDateLabel + "</strong>", "<strong>" + toDateLabel + "</strong>"}, false);
		}
		%>

		Liferay.Search.tokenList.add(
			{
				clearFields: '<%= fieldName %>',
				fieldValues: '<%= fieldName + "selection|0" %>',
				html: '<%= tokenLabel %>'
			}
		);
	</aui:script>
</c:if>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace /><%= facet.getFieldId() %>clearFacet',
		function(selection) {
			document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>'].value = '';
			document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>selection'].value = selection;

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace /><%= facet.getFieldId() %>searchCustomRange',
		function(selection) {
			var fromDate = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>from'].value;
			var toDate = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>to'].value;

			if (fromDate && toDate) {
				if (fromDate > toDate) {
					fromDate = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>to'].value;
					toDate = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>from'].value;

					document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>to'].value = toDate;
					document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>from'].value = fromDate;
				}

				var range = '[' + fromDate.replace(/-/g, '') + '000000 TO ' + toDate.replace(/-/g, '') + '235959]';

				document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>'].value = range;
				document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>selection'].value = selection;

				submitForm(document.<portlet:namespace />fm);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace /><%= facet.getFieldId() %>setRange',
		function(selection, range) {
			document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>'].value = range;
			document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldId() %>selection'].value = selection;

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);
</aui:script>

<aui:script use="aui-datepicker-deprecated,aui-form-validator">
	var Util = Liferay.Util;

	var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

	var REGEX_DATE = /^\d{4}(-)(0[1-9]|1[012])\1(0[1-9]|[12][0-9]|3[01])$/;

	var customRangeFrom = A.one('#<portlet:namespace /><%= facet.getFieldId() %>from');
	var customRangeTo = A.one('#<portlet:namespace /><%= facet.getFieldId() %>to');

	var dateFrom = null;
	var dateTo = null;

	var searchButton = A.one('#<portlet:namespace />searchCustomRangeButton');

	A.mix(
		DEFAULTS_FORM_VALIDATOR.STRINGS,
		{
			<portlet:namespace />dateFormat: '<%= UnicodeLanguageUtil.get(pageContext, "search-custom-range-date-format") %>',
			<portlet:namespace />dateRange: '<%= UnicodeLanguageUtil.get(pageContext, "search-custom-range-invalid-date-range") %>'
		},
		true
	);

	A.mix(
		DEFAULTS_FORM_VALIDATOR.RULES,
		{
			<portlet:namespace />dateFormat: function(val, fieldNode, ruleValue) {
				var validDate = (REGEX_DATE.test(val) === true);

				if (validDate) {
					var dateValue = A.Date.parse(val);

					if (fieldNode === customRangeFrom) {
						dateFrom = dateValue;
					}
					else if (fieldNode === customRangeTo) {
						dateTo = dateValue;
					}
				}

				return validDate;
			},

			<portlet:namespace />dateRange: function(val, fieldNode, ruleValue) {
				return A.Date.isGreaterOrEqual(dateTo, dateFrom);
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
					var field = event.validator.field;

					if (field === customRangeFrom) {
						dateFrom = null;
					}
					else if (field === customRangeTo) {
						dateTo = null;
					}

					Util.toggleDisabled(searchButton, true);
				},
				validField: function(event) {
					if (A.Date.isValidDate(dateFrom) && A.Date.isValidDate(dateTo)) {
						Util.toggleDisabled(searchButton, false);
					}
				}
			},
			rules: {
				<portlet:namespace /><%= facet.getFieldId() %>from: {
					<portlet:namespace />dateFormat: true
				},
				<portlet:namespace /><%= facet.getFieldId() %>to: {
					<portlet:namespace />dateFormat: true,
					<portlet:namespace />dateRange: true
				}
			}
		}
	);

	var fromDatepicker = new A.DatePicker(
		{
			after: {
				'calendar:dateChange': function(e) {
					customRangeValidator.validateField(customRangeFrom);
				}
			},
			calendar: {
				dateFormat: '%Y-%m-%d',
				firstDayOfWeek: <%= firstDayOfWeek %>,
				locale: '<%= locale %>',

				<c:if test='<%= fieldParamSelection.equals("6") && Validator.isNotNull(fieldParamFrom) %>'>
					selectedDates: [

						<%
						String[] fieldParamFromParts = StringUtil.split(fieldParamFrom, "-");
						%>

						new Date(<%= fieldParamFromParts[0] %>,<%= GetterUtil.getInteger(fieldParamFromParts[1]) - 1 %>,<%= fieldParamFromParts[2] %>)
					],
				</c:if>

				selectionMode: 'single',

				strings: {
					next: '<liferay-ui:message key="next" />',
					none: '<liferay-ui:message key="none" />',
					previous: '<liferay-ui:message key="previous" />',
					today: '<liferay-ui:message key="today" />'
				}
			},
			trigger: '#<portlet:namespace /><%= facet.getFieldId() %>from'
		}
	).render('#<%= randomNamespace %>custom-range-from');

	var toDatepicker = new A.DatePicker(
		{
			after: {
				'calendar:dateChange': function(e) {
					customRangeValidator.validateField(customRangeTo);
				}
			},
			calendar: {
				dateFormat: '%Y-%m-%d',
				firstDayOfWeek: <%= firstDayOfWeek %>,
				locale: '<%= locale %>',

				<c:if test='<%= fieldParamSelection.equals("6") && Validator.isNotNull(fieldParamTo) %>'>
					selectedDates: [

						<%
						String[] fieldParamToParts = StringUtil.split(fieldParamTo, "-");
						%>

						new Date(<%= fieldParamToParts[0] %>,<%= GetterUtil.getInteger(fieldParamToParts[1]) - 1 %>,<%= fieldParamToParts[2] %>)
					],
				</c:if>

				selectionMode: 'single',

				strings: {
					next: '<liferay-ui:message key="next" />',
					none: '<liferay-ui:message key="none" />',
					previous: '<liferay-ui:message key="previous" />',
					today: '<liferay-ui:message key="today" />'
				}
			},
			trigger: '#<portlet:namespace /><%= facet.getFieldId() %>to'
		}
	).render('#<%= randomNamespace %>custom-range-to');

	A.one('.<%= randomNamespace %>custom-range-toggle').on(
		'click',
		function(event) {
			event.halt();

			A.one('#<%= randomNamespace + "custom-range" %>').toggle();
		}
	);
</aui:script>