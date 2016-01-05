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
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:disabled"));
String dayParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:dayParam"));
int dayValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:dayValue"));
int firstDayOfWeek = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:firstDayOfWeek"));
String monthAndYearParam = namespace + request.getAttribute("liferay-ui:input-date:monthAndYearParam");
String monthParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:monthParam"));
int monthValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:monthValue"));
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:name"));
boolean nullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:nullable"));
String yearParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:yearParam"));
int yearValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:yearValue"));

String dayParamId = namespace + HtmlUtil.getAUICompatibleId(dayParam);
String monthParamId = namespace + HtmlUtil.getAUICompatibleId(monthParam);
String nameId = namespace + HtmlUtil.getAUICompatibleId(name);
String yearParamId = namespace + HtmlUtil.getAUICompatibleId(yearParam);

Calendar calendar = CalendarFactoryUtil.getCalendar(yearValue, monthValue, dayValue);

String mask = _MASK_MDY;
String simpleDateFormatPattern = _SIMPLE_DATE_FORMAT_PATTERN_MDY;

if (BrowserSnifferUtil.isMobile(request)) {
	simpleDateFormatPattern = _SIMPLE_DATE_FORMAT_PATTERN_HTML5;
}
else {
	DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);

	SimpleDateFormat shortDateFormatSimpleDateFormat = (SimpleDateFormat)shortDateFormat;

	String shortDateFormatSimpleDateFormatPattern = shortDateFormatSimpleDateFormat.toPattern();

	if (shortDateFormatSimpleDateFormatPattern.indexOf("y") == 0) {
		mask = _MASK_YMD;
		simpleDateFormatPattern = _SIMPLE_DATE_FORMAT_PATTERN_YMD;
	}
	else if (shortDateFormatSimpleDateFormatPattern.indexOf("d") == 0) {
		mask = _MASK_DMY;
		simpleDateFormatPattern = _SIMPLE_DATE_FORMAT_PATTERN_DMY;
	}
}

Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(simpleDateFormatPattern, locale);
%>

<span class="lfr-input-date <%= cssClass %>" id="<%= randomNamespace %>displayDate">
	<c:choose>
		<c:when test="<%= BrowserSnifferUtil.isMobile(request) %>">
			<input class="input-medium" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= nameId %>" name="<%= namespace + HtmlUtil.escapeAttribute(name) %>" type="date" value="<%= format.format(calendar.getTime()) %>" />
		</c:when>
		<c:otherwise>
			<aui:input disabled="<%= disabled %>" id="<%= HtmlUtil.getAUICompatibleId(name) %>" label="" name="<%= name %>" placeholder="<%= StringUtil.toLowerCase(simpleDateFormatPattern) %>" title="" type="text" value="<%= nullable ? StringPool.BLANK : format.format(calendar.getTime()) %>" wrappedField="<%= true %>">
				<aui:validator customValidatorRequired="<%= Boolean.FALSE %>" errorMessage="please-enter-a-valid-date" name="custom">
					function(val) {
						return AUI().use('aui-datatype-date-parse').Parsers.date('<%= mask %>', val);
					}
				</aui:validator>
			</aui:input>
		</c:otherwise>
	</c:choose>

	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= dayParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(dayParam) %>" type="hidden" value="<%= (!BrowserSnifferUtil.isMobile(request) && nullable) ? "" : dayValue %>" />
	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= monthParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(monthParam) %>" type="hidden" value="<%= (!BrowserSnifferUtil.isMobile(request) && nullable) ? "" : monthValue %>" />
	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= yearParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(yearParam) %>" type="hidden" value="<%= (!BrowserSnifferUtil.isMobile(request) && nullable) ? "" : yearValue %>" />
</span>

<aui:script use='<%= "aui-datepicker" + (BrowserSnifferUtil.isMobile(request) ? "-native" : StringPool.BLANK) %>'>
	Liferay.component(
		'<%= nameId %>DatePicker',
		function() {
			var datePicker = new A.DatePicker<%= BrowserSnifferUtil.isMobile(request) ? "Native" : StringPool.BLANK %>(
				{
					calendar: {

						<%
						String calendarOptions = StringPool.BLANK;

						if (firstDayOfWeek != -1) {
							calendarOptions += String.format("'strings.first_weekday': %d", firstDayOfWeek);
						}
						%>

						<%= calendarOptions %>
					},
					container: '#<%= randomNamespace %>displayDate',
					mask: '<%= mask %>',
					on: {
						disabledChange: function(event) {
							var instance = this;

							var container = instance.get('container');

							var newVal = event.newVal;

							container.one('#<%= dayParamId %>').attr('disabled', newVal);
							container.one('#<%= monthParamId %>').attr('disabled', newVal);
							container.one('#<%= nameId %>').attr('disabled', newVal);
							container.one('#<%= yearParamId %>').attr('disabled', newVal);
						},
						selectionChange: function(event) {
							if (isNaN(event.newSelection[0])) {
								event.newSelection[0] = new Date();
							}

							datePicker.updateValue(event.newSelection[0]);
						}
					},
					popover: {
						zIndex: Liferay.zIndex.TOOLTIP
					},
					trigger: '#<%= nameId %>'
				}
			);

			datePicker.getDate = function() {
				var instance = this;

				var container = instance.get('container');

				return new Date(container.one('#<%= yearParamId %>').val(), container.one('#<%= monthParamId %>').val(), container.one('#<%= dayParamId %>').val());
			};

			datePicker.updateValue = function(date) {
				var instance = this;

				var container = instance.get('container');

				if (date) {
					container.one('#<%= dayParamId %>').val(date.getDate());
					container.one('#<%= monthParamId %>').val(date.getMonth());
					container.one('#<%= yearParamId %>').val(date.getFullYear());
				}
			};

			datePicker.after(
				'selectionChange',
				function(event) {
					var input = A.one('#<%= nameId %>');

					if (input) {
						var form = input.get('form');

						var formId = form.get('id');

						var formInstance = Liferay.Form.get(formId);

						formInstance.formValidator.validateField('<%= namespace + HtmlUtil.escapeAttribute(name) %>');
					}
				}
			);

			return datePicker;
		}
	);

	Liferay.component('<%= nameId %>DatePicker');
</aui:script>

<%!
private static final String _SIMPLE_DATE_FORMAT_PATTERN_DMY = "dd/MM/yyyy";

private static final String _SIMPLE_DATE_FORMAT_PATTERN_HTML5 = "yyyy-MM-dd";

private static final String _SIMPLE_DATE_FORMAT_PATTERN_MDY = "MM/dd/yyyy";

private static final String _SIMPLE_DATE_FORMAT_PATTERN_YMD = "yyyy/MM/dd";

private static final String _MASK_DMY = "%d/%m/%Y";

private static final String _MASK_MDY = "%m/%d/%Y";

private static final String _MASK_YMD = "%Y/%m/%d";
%>