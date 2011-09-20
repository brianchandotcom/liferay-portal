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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_search_facets_calendar") + StringPool.UNDERLINE;

Facet facet = (Facet)request.getAttribute("view.jsp-facet");

FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

String panelLabel = facetConfiguration.getLabel();
String facetDisplayStyle = facetConfiguration.getDisplayStyle();
String cssClass = "search-facet search-".concat(facetDisplayStyle);

String fieldParam = ParamUtil.getString(request, facet.getFieldName());
String fieldParamSelection = ParamUtil.getString(request, facet.getFieldName() + "selection", "0");
String fieldParamFrom = ParamUtil.getString(request, facet.getFieldName() + "from");
String fieldParamTo = ParamUtil.getString(request, facet.getFieldName() + "to");

String dateString = StringPool.BLANK;

Calendar cal = Calendar.getInstance(timeZone);

DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss", timeZone);

if (Validator.isNotNull(fieldParam)) {
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

	Calendar calEnd = Calendar.getInstance();
	calEnd.setTime(date);

	if ((cal.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)) &&
		(cal.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)) &&
		((cal.get(Calendar.DAY_OF_MONTH) + 1) == calEnd.get(Calendar.DAY_OF_MONTH))) {

		dateString += ",new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + (cal.get(Calendar.DAY_OF_MONTH) + 1) + ",23,59,0,0)";
	}
	else {
		dateString += ",new Date(" + calEnd.get(Calendar.YEAR) + "," + calEnd.get(Calendar.MONTH) + "," + calEnd.get(Calendar.DAY_OF_MONTH) + ",23,59,0,0)";
	}
}

Date now = new Date();

cal.setTime(now);

String nowFormatted = dateFormat.format(cal.getTime());
%>

<div class="<%= cssClass %>" id='<%= randomNamespace + "facet" %>'>
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />
	<aui:input name='<%= facet.getFieldName() + "selection" %>' type="hidden" value="<%= fieldParamSelection %>" />

	<aui:field-wrapper cssClass='<%= randomNamespace + "calendar calendar_" %>' label="" name="<%= facet.getFieldName() %>">
		<ul class="modified">
			<li class="facet-value default<%= (fieldParamSelection.equals("0") ? " current-term" : "" ) %>">
				<aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "clearFacet(0);" %>'>
					<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/common/time.png" %>' /> <liferay-ui:message key="any-time" />
				</aui:a>
			</li>
			<li class="facet-value<%= (fieldParamSelection.equals("1") ? " current-term" : "" ) %>">

				<%
				cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 1);

				String nowLessXFormatted = dateFormat.format(cal.getTime());
				%>

				<aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "setRange(1, \'[" + nowLessXFormatted + " TO " + nowFormatted + "]\');" %>'>
					<liferay-ui:message key="past-hour" />
				</aui:a>
			</li>
			<li class="facet-value<%= (fieldParamSelection.equals("2") ? " current-term" : "" ) %>">

				<%
				cal.setTime(now);
				cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);

				nowLessXFormatted = dateFormat.format(cal.getTime());
				%>

				<aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "setRange(2, \'[" + nowLessXFormatted + " TO " + nowFormatted + "]\');" %>'>
					<liferay-ui:message key="past-24-hours" />
				</aui:a>
			</li>
			<li class="facet-value<%= (fieldParamSelection.equals("3") ? " current-term" : "" ) %>">

				<%
				cal.setTime(now);
				cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 7);

				nowLessXFormatted = dateFormat.format(cal.getTime());
				%>

				<aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "setRange(3, \'[" + nowLessXFormatted + " TO " + nowFormatted + "]\');" %>'>
					<liferay-ui:message key="past-week" />
				</aui:a>
			</li>
			<li class="facet-value<%= (fieldParamSelection.equals("4") ? " current-term" : "" ) %>">

				<%
				cal.setTime(now);
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);

				nowLessXFormatted = dateFormat.format(cal.getTime());
				%>

				<aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "setRange(4, \'[" + nowLessXFormatted + " TO " + nowFormatted + "]\');" %>'>
					<liferay-ui:message key="past-month" />
				</aui:a>
			</li>
			<li class="facet-value<%= (fieldParamSelection.equals("5") ? " current-term" : "" ) %>">

				<%
				cal.setTime(now);
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);

				nowLessXFormatted = dateFormat.format(cal.getTime());
				%>

				<aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "setRange(5, \'[" + nowLessXFormatted + " TO " + nowFormatted + "]\');" %>'>
					<liferay-ui:message key="past-year" />
				</aui:a>
			</li>
			<li class="facet-value<%= (fieldParamSelection.equals("6") ? " current-term" : "" ) %>">
				<aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "customRange();" %>'>
					<liferay-ui:message key="custom-range" /> ..
				</aui:a>
			</li>

			<div class="<%= (!fieldParamSelection.equals("6") ? "aui-helper-hidden" : "" ) %> modified-custom-range" id="<%= randomNamespace + "custom-range" %>">
				<div id="<%= randomNamespace + "custom-range-from" %>">
					<aui:input inlineField="<%= true %>" inlineLabel="left" label="from" name='<%= facet.getFieldName() + "from" %>' size="14" />
				</div>
				<div id="<%= randomNamespace + "custom-range-to" %>">
					<aui:input inlineField="<%= true %>" inlineLabel="left" label="to" name='<%= facet.getFieldName() + "to" %>' size="14" />
				</div>

				<aui:button onClick='<%= renderResponse.getNamespace() + facet.getFieldName() + "searchCustomRange(6);" %>' value="search" />
			</div>
		</ul>
	</aui:field-wrapper>

	<aui:script position="inline" use="aui-calendar,aui-datepicker">
		var fromDatepicker = new A.DatePicker({
			trigger: '#<portlet:namespace /><%= facet.getFieldName() %>from',
			calendar: {
				dateFormat: '%Y-%m-%d',
				dates: [
					<c:if test='<%= fieldParamSelection.equals("6") && Validator.isNotNull(fieldParamFrom) %>'>

						<%
						String[] fromParts = StringUtil.split(fieldParamFrom, "-");
						%>

						new Date(<%= fromParts[0] %>,<%= (Integer.parseInt(fromParts[1]) - 1) %>,<%= fromParts[2] %>)
					</c:if>
				],
				selectMultipleDates: false
			}
		})
		.render('#<%= randomNamespace %>custom-range-from');

		var toDatepicker = new A.DatePicker({
			trigger: '#<portlet:namespace /><%= facet.getFieldName() %>to',
			calendar: {
				dateFormat: '%Y-%m-%d',
				dates: [
					<c:if test='<%= fieldParamSelection.equals("6") && Validator.isNotNull(fieldParamTo) %>'>
						<%
						String[] toParts = StringUtil.split(fieldParamTo, "-");
						%>

						new Date(<%= toParts[0] %>,<%= (Integer.parseInt(toParts[1]) - 1) %>,<%= toParts[2] %>)
					</c:if>
				],
				selectMultipleDates: false
			}
		})
		.render('#<%= randomNamespace %>custom-range-to');

		Liferay.provide(
			window,
			'<portlet:namespace /><%= facet.getFieldName() %>clearFacet',
			function(selection) {
				document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'].value = '';
				document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>selection'].value = selection;

				submitForm(document.<portlet:namespace />fm);
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<portlet:namespace /><%= facet.getFieldName() %>customRange',
			function() {
				A.one('#<%= randomNamespace + "custom-range" %>').toggle();
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<portlet:namespace /><%= facet.getFieldName() %>searchCustomRange',
			function(selection) {
				var fromDate = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>from'].value;
				var toDate = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>to'].value;

				if (!fromDate || !toDate) {
					return;
				}

				if (fromDate > toDate) {
					fromDate = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>to'].value;
					toDate = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>from'].value;

					document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>to'].value = toDate;
					document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>from'].value = fromDate;
				}

				var range = '[' + fromDate.replace(/-/g, '') + '000000 TO ' + toDate.replace(/-/g, '') + '000000]';

				document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'].value = range;
				document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>selection'].value = selection;

				submitForm(document.<portlet:namespace />fm);
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<portlet:namespace /><%= facet.getFieldName() %>setRange',
			function(selection, range) {
				document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'].value = range;
				document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>selection'].value = selection;

				submitForm(document.<portlet:namespace />fm);
			},
			['aui-base']
		);
	</aui:script>
</div>