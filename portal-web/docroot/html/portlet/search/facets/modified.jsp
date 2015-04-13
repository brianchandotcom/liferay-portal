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

int fromDay = ParamUtil.getInteger(request, HtmlUtil.escapeJS(facet.getFieldId()) + "dayFrom");
int fromMonth = ParamUtil.getInteger(request, HtmlUtil.escapeJS(facet.getFieldId()) + "monthFrom");
int fromYear = ParamUtil.getInteger(request, HtmlUtil.escapeJS(facet.getFieldId()) + "yearFrom");

Date fromDate = PortalUtil.getDate(fromMonth, fromDay, fromYear);

int toDay = ParamUtil.getInteger(request, HtmlUtil.escapeJS(facet.getFieldId()) + "dayTo");
int toMonth = ParamUtil.getInteger(request, HtmlUtil.escapeJS(facet.getFieldId()) + "monthTo");
int toYear = ParamUtil.getInteger(request, HtmlUtil.escapeJS(facet.getFieldId()) + "yearTo");

Date toDate = PortalUtil.getDate(toMonth, toDay, toYear);

JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");

String modifiedLabel = StringPool.BLANK;

int index = 0;

if (fieldParamSelection.equals("0")) {
	modifiedLabel = LanguageUtil.get(pageContext, "any-time");
}
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
					modifiedLabel = LanguageUtil.get(pageContext, label);
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

			<%
			Calendar fromCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

			if (Validator.isNotNull(fromDate)) {
				fromCalendar.setTime(fromDate);
			}
			else {
				fromCalendar.add(Calendar.DATE, -1);
			}

			Calendar toCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

			if (Validator.isNotNull(toDate)) {
				toCalendar.setTime(toDate);
			}
			%>

			<div class="<%= !fieldParamSelection.equals(String.valueOf(index + 1)) ? "hide" : StringPool.BLANK %> modified-custom-range" id="<%= randomNamespace %>customRange">
				<div id="<%= randomNamespace %>customRangeFrom">
					<aui:field-wrapper label="from">
						<liferay-ui:input-date
							dayParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "dayFrom" %>'
							dayValue="<%= fromCalendar.get(Calendar.DATE) %>"
							disabled="<%= false %>"
							firstDayOfWeek="<%= fromCalendar.getFirstDayOfWeek() - 1 %>"
							monthParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "monthFrom" %>'
							monthValue="<%= fromCalendar.get(Calendar.MONTH) %>"
							name='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "from" %>'
							yearParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "yearFrom" %>'
							yearValue="<%= fromCalendar.get(Calendar.YEAR) %>"
						/>
					</aui:field-wrapper>
				</div>

				<div id="<%= randomNamespace %>customRangeTo">
					<aui:field-wrapper label="to">
						<liferay-ui:input-date
							dayParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "dayTo" %>'
							dayValue="<%= toCalendar.get(Calendar.DATE) %>"
							disabled="<%= false %>"
							firstDayOfWeek="<%= toCalendar.getFirstDayOfWeek() - 1 %>"
							monthParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "monthTo" %>'
							monthValue="<%= toCalendar.get(Calendar.MONTH) %>"
							name='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "to" %>'
							yearParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "yearTo" %>'
							yearValue="<%= toCalendar.get(Calendar.YEAR) %>"
						/>
					</aui:field-wrapper>
				</div>

				<%
				String taglibSearchCustomRange = "window['" + renderResponse.getNamespace() + HtmlUtil.escapeJS(facet.getFieldId()) + "searchCustomRange'](" + (index + 1) + ");";
				%>

				<aui:button disabled="<%= (toCalendar.getTimeInMillis() < fromCalendar.getTimeInMillis()) %>" name="searchCustomRangeButton" onClick="<%= taglibSearchCustomRange %>" value="search" />
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

			tokenLabel = UnicodeLanguageUtil.format(pageContext, "from-x-to-x", new Object[] {"<strong>" + fromDateLabel + "</strong>", "<strong>" + toDateLabel + "</strong>"});
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
	Liferay.provide(
		window,
		'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>clearFacet',
		function(selection) {
			document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>'].value = '';
			document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>selection'].value = selection;

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>searchCustomRange',
		function(selection) {
			var A = AUI();
			var Lang = A.Lang
			var LString = Lang.String;
			var toInt = Lang.toInt;

			var dayFrom = document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>dayFrom'].value;
			var monthFrom = document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>monthFrom'].value;
			var yearFrom = document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>yearFrom'].value;

			var dayTo = document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>dayTo'].value;
			var monthTo = document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>monthTo'].value;
			var yearTo = document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>yearTo'].value;

			var range = '[' + yearFrom + LString.padNumber(toInt(monthFrom) + 1, 2) + LString.padNumber(dayFrom, 2) + '000000 TO ' + yearTo + LString.padNumber(toInt(monthTo) + 1, 2) + LString.padNumber(dayTo, 2) + '235959]';

			document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>'].value = range;
			document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>selection'].value = selection;

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>setRange',
		function(selection, range) {
			document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>'].value = range;
			document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>selection'].value = selection;

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);
</aui:script>

<aui:script use="aui-form-validator">
	var Util = Liferay.Util;

	var customRangeFrom = Liferay.component('<%= renderResponse.getNamespace() %>modifiedfromDatePicker');
	var customRangeTo = Liferay.component('<%= renderResponse.getNamespace() %>modifiedtoDatePicker');
	var searchButton = A.one('#<portlet:namespace />searchCustomRangeButton');

	var preventKeyboardDateChange = function(event) {
		if (!event.isKey('TAB')) {
			event.preventDefault();
		}
	};

	A.one('#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from').on('keydown', preventKeyboardDateChange);
	A.one('#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to').on('keydown', preventKeyboardDateChange);

	var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

	A.mix(
		DEFAULTS_FORM_VALIDATOR.STRINGS,
		{
			<portlet:namespace />dateRange: '<%= UnicodeLanguageUtil.get(pageContext, "search-custom-range-invalid-date-range") %>'
		},
		true
	);

	A.mix(
		DEFAULTS_FORM_VALIDATOR.RULES,
		{
			<portlet:namespace />dateRange: function(val, fieldNode, ruleValue) {
				return A.Date.isGreaterOrEqual(customRangeTo.getDate(), customRangeFrom.getDate());
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
					Util.toggleDisabled(searchButton, false);
				}
			},
			rules: {
				'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from': {
					<portlet:namespace />dateRange: true
				},
				'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to': {
					<portlet:namespace />dateRange: true
				}
			}
		}
	);

	var onRangeSelectionChange = function(event) {
		customRangeValidator.validate();
	};

	customRangeFrom.on('selectionChange', onRangeSelectionChange);
	customRangeTo.on('selectionChange', onRangeSelectionChange);

	A.one('.<%= randomNamespace %>custom-range-toggle').on(
		'click',
		function(event) {
			event.halt();

			A.one('#<%= randomNamespace + "customRange" %>').toggle();
		}
	);
</aui:script>