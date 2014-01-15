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
String fieldParamFrom = ParamUtil.getString(request, facet.getFieldId() + "from");
String fieldParamTo = ParamUtil.getString(request, facet.getFieldId() + "to");

String dateString = StringPool.BLANK;

Calendar cal = Calendar.getInstance();

if (Validator.isNotNull(fieldParam)) {
	DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss", timeZone);

	String[] range = RangeParserUtil.parserRange(fieldParam);

	Date date = dateFormat.parse(range[0]);

	cal.setTime(date);

	dateString = "new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + (cal.get(Calendar.DAY_OF_MONTH) + 1) + ")";

	if (range[1].equals(StringPool.STAR)) {
		date = new Date();
	}
	else {
		date = dateFormat.parse(range[1]);
	}

	Calendar endCal = Calendar.getInstance();

	endCal.setTime(date);

	if ((cal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR)) &&
		(cal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH)) &&
		((cal.get(Calendar.DAY_OF_MONTH) + 1) == endCal.get(Calendar.DAY_OF_MONTH))) {

		dateString += ",new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + (cal.get(Calendar.DAY_OF_MONTH) + 1) + ",23,59,0,0)";
	}
	else {
		dateString += ",new Date(" + endCal.get(Calendar.YEAR) + "," + endCal.get(Calendar.MONTH) + "," + endCal.get(Calendar.DAY_OF_MONTH) + ",23,59,0,0)";
	}
}

Calendar localeCal = CalendarFactoryUtil.getCalendar(timeZone, locale);

int firstDayOfWeek = localeCal.getFirstDayOfWeek() - 1;
%>

<c:if test="<%= Validator.isNotNull(fieldParamFrom) && Validator.isNotNull(fieldParamTo) %>">
	<aui:script use="liferay-token-list">
		Liferay.Search.tokenList.add(
			{
				clearFields: '<%= renderResponse.getNamespace() + HtmlUtil.escapeJS(facet.getFieldId()) %>',
				html: '<%= UnicodeLanguageUtil.format(pageContext, "from-x-to-x", new Object[] {"<strong>" + HtmlUtil.escape(fieldParamFrom) + "</strong>", "<strong>" + HtmlUtil.escape(fieldParamTo) + "</strong>"}, false) %>'
			}
		);
	</aui:script>
</c:if>

<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" type="hidden" value="<%= fieldParam %>" />
	<aui:input name='<%= HtmlUtil.escapeAttribute(facet.getFieldId()) + "from" %>' type="hidden" />
	<aui:input name='<%= HtmlUtil.escapeAttribute(facet.getFieldId()) + "to" %>' type="hidden" />

	<div class="date" id="<portlet:namespace /><%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>PlaceHolder"></div>
</div>

<aui:script use="aui-calendar-deprecated">
	var now = new Date();

	var checkDateRange = function(event) {
		var dates = this.get('dates');

		var minDate = null;
		var maxDate = null;

		if (dates.length >= 2) {
			var firstSelected = dates[0];
			var lastSelected = dates[dates.length-1];

			if (A.DataType.DateMath.before(dates[0], dates[1])) {
				minDate = firstSelected;
				maxDate = lastSelected;
			}
			else {
				minDate = lastSelected;
				maxDate = firstSelected;
			}
		}

		this.set('minDate', minDate);
		this.set('maxDate', maxDate);

		this._syncMonthDays();
	};

	var dateSelection = new A.Calendar(
		{
			after: {
				select: function(event) {
					var instance = this;

					var format = instance.get('dateFormat');

					var dates = instance.get('dates');

					if (dates.length == 0) {
						document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>'].value = null;

						document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from'].value = null;
						document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to'].value = null;
					}
					else {
						var firstSelected = dates[0];
						var lastSelected = dates[0];

						if (dates.length > 1) {
							firstSelected = dates[0];
							lastSelected = dates[dates.length-1];

							if (firstSelected > lastSelected) {
								firstSelected = dates[dates.length-1];
								lastSelected = dates[0];
							}
						}

						var fromDate = A.DataType.Date.format(
							firstSelected,
							{
								format: format
							}
						);

						var toDate = A.DataType.Date.format(
							lastSelected,
							{
								format: '%Y%m%d235900'
							}
						);

						document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>'].value = '[' + fromDate + ' TO ' + toDate + ']';

						var displayFormat = {
							format: '%Y-%m-%d'
						};

						document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from'].value = A.DataType.Date.format(firstSelected, displayFormat);
						document.<portlet:namespace />fm['<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to'].value = A.DataType.Date.format(lastSelected, displayFormat);
					}

					checkDateRange.call(instance, event);

					if (dates.length > 1) {
						submitForm(document.<portlet:namespace />fm);
					}
				}
			},
			allowNone: true,
			dateFormat: '%Y%m%d000000',
			dates: [<%= dateString %>],
			firstDayOfWeek: <%= firstDayOfWeek %>,
			locale: '<%= locale %>',
			maxDate: now,
			minDate: A.DataType.DateMath.subtract(now, A.DataType.DateMath.YEAR, 2),
			selectMultipleDates: true,
			setValue: true,
			showToday: true,
			strings: {
				next: '<liferay-ui:message key="next" />',
				none: '<liferay-ui:message key="none" />',
				previous: '<liferay-ui:message key="previous" />',
				today: '<liferay-ui:message key="today" />'
			}
		}
	).render('#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>PlaceHolder');
</aui:script>