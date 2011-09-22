<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
%>

<div class="<%= cssClass %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

	<div class="date" id="<portlet:namespace /><%= facet.getFieldName() %>PlaceHolder"></div>

	<br />

	<liferay-ui:message key="<%= facetConfiguration.getLabel() %>" />: <aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "clearFacet();" %>'><liferay-ui:message key="clear" /></aui:a>
</div>

<aui:script position="inline" use="aui-calendar">
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
						document.<portlet:namespace />fm.<portlet:namespace /><%= facet.getFieldName() %>.value = null;
					}
					else if (dates.length == 1) {
						var firstSelected = dates[0];

						document.<portlet:namespace />fm.<portlet:namespace /><%= facet.getFieldName() %>.value = '[' + A.DataType.Date.format(firstSelected, {format: format}) + ' TO ' + A.DataType.Date.format(firstSelected, {format: '%Y%m%d235900'}) + ']';
					}
					else if (dates.length > 1) {
						var firstSelected = dates[0];
						var lastSelected = dates[dates.length-1];

						if (firstSelected > lastSelected) {
							firstSelected = dates[dates.length-1];
							lastSelected = dates[0];
						}

						document.<portlet:namespace />fm.<portlet:namespace /><%= facet.getFieldName() %>.value = '[' + A.DataType.Date.format(firstSelected, {format: format}) + ' TO ' + A.DataType.Date.format(lastSelected, {format: '%Y%m%d235900'}) + ']';
					}

					checkDateRange.call(instance, event);
				}
			},
			allowNone: true,
			dateFormat: '%Y%m%d000000',
			dates: [<%= dateString %>],
			firstDayOfWeek: 0,
			maxDate: now,
			minDate: A.DataType.DateMath.subtract(now, A.DataType.DateMath.YEAR, 2),
			selectMultipleDates: true,
			setValue: true,
			showToday: true
		}
	)
	.render('#<portlet:namespace /><%= facet.getFieldName() %>PlaceHolder');

	Liferay.provide(
		window,
		'<portlet:namespace /><%= facet.getFieldName() %>clearFacet',
		function() {
			document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'].value = '';

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);
</aui:script>