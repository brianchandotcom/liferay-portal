<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
String fieldParamSelection = ParamUtil.getString(request, facet.getFieldName() + "selection", "0");
String fieldParamFrom = ParamUtil.getString(request, facet.getFieldName() + "from");
String fieldParamTo = ParamUtil.getString(request, facet.getFieldName() + "to");

Calendar cal = Calendar.getInstance(timeZone);

Date now = new Date();

cal.setTime(now);

DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss", timeZone);

String nowString = dateFormat.format(cal.getTime());

String modifiedLabel = StringPool.BLANK;

if (fieldParamSelection.equals("0")) {
	modifiedLabel = LanguageUtil.get(pageContext, "any-time");
}
else if (fieldParamSelection.equals("1")) {
	modifiedLabel = LanguageUtil.get(pageContext, "past-hour");
}
else if (fieldParamSelection.equals("2")) {
	modifiedLabel = LanguageUtil.get(pageContext, "past-24-hours");
}
else if (fieldParamSelection.equals("3")) {
	modifiedLabel = LanguageUtil.get(pageContext, "past-week");
}
else if (fieldParamSelection.equals("4")) {
	modifiedLabel = LanguageUtil.get(pageContext, "past-month");
}
else if (fieldParamSelection.equals("5")) {
	modifiedLabel = LanguageUtil.get(pageContext, "past-year");
}
else if (fieldParamSelection.equals("6")) {
	modifiedLabel = LanguageUtil.get(pageContext, "custom-range");
}
%>

<div class="<%= cssClass %>" data-facetFieldName="<%= facet.getFieldName() %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />
	<aui:input name='<%= facet.getFieldName() + "selection" %>' type="hidden" value="<%= fieldParamSelection %>" />

	<aui:field-wrapper cssClass='<%= randomNamespace + "calendar calendar_" %>' label="" name="<%= facet.getFieldName() %>">
		<ul class="modified">
			<li class="facet-value default<%= (fieldParamSelection.equals("0") ? " current-term" : StringPool.BLANK) %>">
				<aui:a href="javascript:;" onClick='<%= renderResponse.getNamespace() + facet.getFieldName() + "clearFacet(0);" %>'>
					<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/common/time.png" %>' /><liferay-ui:message key="any-time" />
				</aui:a>
			</li>
			<li class="facet-value<%= fieldParamSelection.equals("1") ? " current-term" : StringPool.BLANK %>">

				<%
				cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 1);

				String taglibSetRange = renderResponse.getNamespace() + facet.getFieldName() + "setRange(1, '[" + dateFormat.format(cal.getTime()) + " TO " + nowString + "]');";
				%>

				<aui:a href="javascript:;" onClick="<%= taglibSetRange %>">
					<liferay-ui:message key="past-hour" />
				</aui:a>
			</li>
			<li class="facet-value<%= fieldParamSelection.equals("2") ? " current-term" : StringPool.BLANK %>">

				<%
				cal.setTime(now);

				cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);

				taglibSetRange = renderResponse.getNamespace() + facet.getFieldName() + "setRange(2, '[" + dateFormat.format(cal.getTime()) + " TO " + nowString + "]');";
				%>

				<aui:a href="javascript:;" onClick="<%= taglibSetRange %>">
					<liferay-ui:message key="past-24-hours" />
				</aui:a>
			</li>
			<li class="facet-value<%= fieldParamSelection.equals("3") ? " current-term" : StringPool.BLANK %>">

				<%
				cal.setTime(now);

				cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 7);

				taglibSetRange = renderResponse.getNamespace() + facet.getFieldName() + "setRange(3, '[" + dateFormat.format(cal.getTime()) + " TO " + nowString + "]');";
				%>

				<aui:a href="javascript:;" onClick="<%= taglibSetRange %>">
					<liferay-ui:message key="past-week" />
				</aui:a>
			</li>
			<li class="facet-value<%= fieldParamSelection.equals("4") ? " current-term" : StringPool.BLANK %>">

				<%
				cal.setTime(now);

				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);

				taglibSetRange = renderResponse.getNamespace() + facet.getFieldName() + "setRange(4, '[" + dateFormat.format(cal.getTime()) + " TO " + nowString + "]');";
				%>

				<aui:a href="javascript:;" onClick="<%= taglibSetRange %>">
					<liferay-ui:message key="past-month" />
				</aui:a>
			</li>
			<li class="facet-value<%= fieldParamSelection.equals("5") ? " current-term" : StringPool.BLANK %>">

				<%
				cal.setTime(now);

				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);

				taglibSetRange = renderResponse.getNamespace() + facet.getFieldName() + "setRange(5, '[" + dateFormat.format(cal.getTime()) + " TO " + nowString + "]');";
				%>

				<aui:a href="javascript:;" onClick="<%= taglibSetRange %>">
					<liferay-ui:message key="past-year" />
				</aui:a>
			</li>
			<li class="facet-value<%= fieldParamSelection.equals("6") ? " current-term" : StringPool.BLANK %>">
				<aui:a href="javascript:;" onClick='<%= renderResponse.getNamespace() + facet.getFieldName() + "customRange();" %>'>
					<liferay-ui:message key="custom-range" />&hellip;
				</aui:a>
			</li>

			<div class="<%= !fieldParamSelection.equals("6") ? "aui-helper-hidden" : StringPool.BLANK %> modified-custom-range" id="<%= randomNamespace %>custom-range">
				<div id="<%= randomNamespace %>custom-range-from">
					<aui:input label="from" name='<%= facet.getFieldName() + "from" %>' size="14" />
				</div>

				<div id="<%= randomNamespace %>custom-range-to">
					<aui:input label="to" name='<%= facet.getFieldName() + "to" %>' size="14" />
				</div>

				<aui:button onClick='<%= renderResponse.getNamespace() + facet.getFieldName() + "searchCustomRange(6);" %>' value="search" />
			</div>
		</ul>
	</aui:field-wrapper>
</div>

<c:if test='<%= !fieldParamSelection.equals("0") %>'>
	<%
		String fieldName = renderResponse.getNamespace() + facet.getFieldName();
	%>

	<aui:script use="liferay-token-list">
		<%
		String tokenLabel = modifiedLabel;

		if (fieldParamSelection.equals("6")) {
			String fromDateLabel = fieldParamFrom;
			String toDateLabel = fieldParamTo;

			tokenLabel = LanguageUtil.format(pageContext, "from-x-to-x", new Object[] {"<strong>" + fromDateLabel + "</strong>", "<strong>" + toDateLabel + "</strong>"});
		}
		%>

		Liferay.Search.tokenList.add(
			{
				clearFields: '<%= UnicodeFormatter.toString(fieldName) %>',
				fieldValues: '<%= UnicodeFormatter.toString(fieldName + "selection|0") %>',
				text: '<%= UnicodeFormatter.toString(tokenLabel) %>'
			}
		);
	</aui:script>
</c:if>

<aui:script>
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

			if (fromDate && toDate) {
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
			}
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

<aui:script use="aui-datepicker">
	var fromDatepicker = new A.DatePicker(
		{
			calendar: {
				dateFormat: '%Y-%m-%d',
				dates: [
					<c:if test='<%= fieldParamSelection.equals("6") && Validator.isNotNull(fieldParamFrom) %>'>

						<%
						String[] fieldParamFromParts = StringUtil.split(fieldParamFrom, "-");
						%>

						new Date(<%= fieldParamFromParts[0] %>,<%= GetterUtil.getInteger(fieldParamFromParts[1]) - 1 %>,<%= fieldParamFromParts[2] %>)
					</c:if>
				],
				selectMultipleDates: false
			},
			trigger: '#<portlet:namespace /><%= facet.getFieldName() %>from'
		}
	).render('#<%= randomNamespace %>custom-range-from');

	var toDatepicker = new A.DatePicker(
		{
			calendar: {
				dateFormat: '%Y-%m-%d',
				dates: [
					<c:if test='<%= fieldParamSelection.equals("6") && Validator.isNotNull(fieldParamTo) %>'>

						<%
						String[] fieldParamToParts = StringUtil.split(fieldParamTo, "-");
						%>

						new Date(<%= fieldParamToParts[0] %>,<%= GetterUtil.getInteger(fieldParamToParts[1]) - 1 %>,<%= fieldParamToParts[2] %>)
					</c:if>
				],
				selectMultipleDates: false
			},
			trigger: '#<portlet:namespace /><%= facet.getFieldName() %>to'
		}
	).render('#<%= randomNamespace %>custom-range-to');
</aui:script>