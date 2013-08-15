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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_date_page") + StringPool.UNDERLINE;

if (GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:disableNamespace"))) {
	namespace = StringPool.BLANK;
}

boolean autoFocus = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:autoFocus"));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:cssClass"));
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:name"));
String monthParam = namespace + request.getAttribute("liferay-ui:input-date:monthParam");
String monthParamId = namespace + request.getAttribute("liferay-ui:input-date:monthParamId");
int monthValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:monthValue"));
String dayParam = namespace + request.getAttribute("liferay-ui:input-date:dayParam");
String dayParamId = namespace + request.getAttribute("liferay-ui:input-date:dayParamId");
int dayValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:dayValue"));
String yearParam = namespace + request.getAttribute("liferay-ui:input-date:yearParam");
String yearParamId = namespace + request.getAttribute("liferay-ui:input-date:yearParamId");
int yearValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:yearValue"));
String monthAndYearParam = namespace + request.getAttribute("liferay-ui:input-date:monthAndYearParam");
int firstDayOfWeek = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:firstDayOfWeek"));
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:disabled"));

String dateFormatPattern = ((SimpleDateFormat)(DateFormat.getDateInstance(DateFormat.SHORT, locale))).toPattern();

String datePattern = _DATE_PATTERN_MDY;

if (BrowserSnifferUtil.isMobile(request)) {
	datePattern = _DATE_PATTERN_HTML5;
}
else if (dateFormatPattern.indexOf("y") == 0) {
	datePattern = _DATE_PATTERN_YMD;
}
else if (dateFormatPattern.indexOf("d") == 0) {
	datePattern = _DATE_PATTERN_DMY;
}

Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(datePattern);

Calendar calendar = CalendarFactoryUtil.getCalendar(yearValue, monthValue, dayValue);

String datePickerClazz = "A.DatePicker";
String datePickerModuleName = "aui-datepicker";

if (BrowserSnifferUtil.isMobile(request)) {
	datePickerClazz = "A.DatePickerNative";
	datePickerModuleName = "aui-datepicker-native";
}
%>

<span class="lfr-input-date <%= cssClass %>" id="<%= randomNamespace %>displayDate">
	<c:choose>
		<c:when test="<%= BrowserSnifferUtil.isMobile(request) %>">
			<input class="input-medium" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= namespace + name %>" name="<%= namespace + name %>" type="date" value="<%= dateFormat.format(calendar.getTime()) %>" />
		</c:when>
		<c:otherwise>
			<input class="input-medium" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= namespace + name %>" name="<%= namespace + name %>" placeholder="<%= datePattern.toLowerCase() %>" type="text" value="<%= dateFormat.format(calendar.getTime()) %>" />
		</c:otherwise>
	</c:choose>

	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= dayParamId %>" name="<%= dayParam %>" type="hidden" value="<%= dayValue %>" />
	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= monthParamId %>" name="<%= monthParam %>" type="hidden" value="<%= monthValue %>" />
	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= yearParamId %>" name="<%= yearParam %>" type="hidden" value="<%= yearValue %>" />
</span>

<aui:script use="<%= datePickerModuleName %>">
Liferay.component(
	'<%= namespace + name %>DatePicker',
	function() {
		return new <%= datePickerClazz %>({
			container: '#<%= randomNamespace %>displayDate',
			on: {
				selectionChange: function(event) {
					var date = event.newSelection[0];

					if (date) {
						A.one('#<%= dayParamId %>').val(date.getDate());
						A.one('#<%= monthParamId %>').val(date.getMonth());
						A.one('#<%= yearParamId %>').val(date.getFullYear());
					}
				}
			},
			popover: {
				zIndex: Liferay.zIndex.TOOLTIP
			},
			trigger: '#<%= namespace + name %>'
		});
	}
);

Liferay.component('<%= namespace + name %>DatePicker');
</aui:script>

<%!
private static final String _DATE_PATTERN_DMY = "dd/MM/yyyy";

private static final String _DATE_PATTERN_HTML5 = "yyyy-MM-dd";

private static final String _DATE_PATTERN_MDY = "MM/dd/yyyy";

private static final String _DATE_PATTERN_YMD = "yyyy/MM/dd";
%>