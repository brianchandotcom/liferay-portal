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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_date_page") + StringPool.UNDERLINE;

if (GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:disableNamespace"))) {
	namespace = StringPool.BLANK;
}

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:cssClass"));
String dateTogglerCheckboxLabel = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:dateTogglerCheckboxLabel"), "disable");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:disabled"));
String dayParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:dayParam"));
int dayValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:dayValue"));
int firstDayOfWeek = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:firstDayOfWeek"));
Date firstEnabledDate = GetterUtil.getDate(request.getAttribute("liferay-ui:input-date:firstEnabledDate"), DateFormatFactoryUtil.getDate(locale), null);
String formName = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:formName"));
Date lastEnabledDate = GetterUtil.getDate(request.getAttribute("liferay-ui:input-date:lastEnabledDate"), DateFormatFactoryUtil.getDate(locale), null);
String monthParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:monthParam"));
int monthValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:monthValue"));
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:name"));
boolean nullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:nullable"));
boolean required = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:required"));
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
			<input class="form-control" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= nameId %>" name="<%= namespace + HtmlUtil.escapeAttribute(name) %>" type="date" value="<%= format.format(calendar.getTime()) %>" />
		</c:when>
		<c:otherwise>
			<aui:input disabled="<%= disabled %>" id="<%= HtmlUtil.getAUICompatibleId(name) %>" label="" name="<%= name %>" placeholder="<%= StringUtil.toLowerCase(simpleDateFormatPattern) %>" required="<%= required %>" title="" type="text" value="<%= format.format(calendar.getTime()) %>" wrappedField="<%= true %>">
				<aui:validator errorMessage="please-enter-a-valid-date" name="custom">
					function(val) {
						return AUI().use('aui-datatype-date-parse').Parsers.date('<%= mask %>', val);
					}
				</aui:validator>
			</aui:input>
		</c:otherwise>
	</c:choose>

	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= dayParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(dayParam) %>" type="hidden" value="<%= dayValue %>" />
	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= monthParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(monthParam) %>" type="hidden" value="<%= monthValue %>" />
	<input <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= yearParamId %>" name="<%= namespace + HtmlUtil.escapeAttribute(yearParam) %>" type="hidden" value="<%= yearValue %>" />
</span>

<c:if test="<%= nullable %>">

	<%
	String dateTogglerCheckboxName = TextFormatter.format(dateTogglerCheckboxLabel, TextFormatter.M);
	%>

	<aui:input label="<%= dateTogglerCheckboxLabel %>" name="<%= randomNamespace + dateTogglerCheckboxName %>" type="checkbox" value="<%= disabled %>" />

	<aui:script sandbox="<%= true %>">
		var checkbox = $('#<%= namespace + randomNamespace + dateTogglerCheckboxName %>');

		checkbox.on(
			'click mouseover',
			function(event) {
				var checked = checkbox.prop('checked');

				var form = $(document.forms.<%= namespace + formName %>);

				if (!form.length) {
					form = $(checkbox.prop('form'));
				}

				form.fm('<%= HtmlUtil.getAUICompatibleId(name) %>').prop('disabled', checked);
				form.fm('<%= HtmlUtil.escapeJS(dayParam) %>').prop('disabled', checked);
				form.fm('<%= HtmlUtil.escapeJS(monthParam) %>').prop('disabled', checked);
				form.fm('<%= HtmlUtil.escapeJS(yearParam) %>').prop('disabled', checked);
			}
		);
	</aui:script>
</c:if>

<aui:script use='<%= "aui-datepicker" + (BrowserSnifferUtil.isMobile(request) ? "-native" : StringPool.BLANK) %>'>
	Liferay.component(
		'<%= nameId %>DatePicker',
		function() {
			var datePicker = new A.DatePicker<%= BrowserSnifferUtil.isMobile(request) ? "Native" : StringPool.BLANK %>(
				{
					calendar: {

						<%
						String calendarOptions = StringPool.BLANK;

						if (lastEnabledDate != null) {
							calendarOptions += String.format("maximumDate: new Date(%s)", lastEnabledDate.getTime());
						}

						if (firstEnabledDate != null) {
							if (Validator.isNotNull(calendarOptions)) {
								calendarOptions += StringPool.COMMA;
							}

							calendarOptions += String.format("minimumDate: new Date(%s)", firstEnabledDate.getTime());
						}

						if (firstDayOfWeek != -1) {
							if (Validator.isNotNull(calendarOptions)) {
								calendarOptions += StringPool.COMMA;
							}

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
						enterKey: function(event) {
							var instance = this;

							var inputVal = instance.get('activeInput').val();

							var date = instance.getParsedDatesFromInputValue(inputVal);

							if (date) {
								datePicker.updateValue(date[0]);
							}
							else if (<%= nullable %> && !date) {
								datePicker.updateValue('');
							}
						},
						selectionChange: function(event) {
							var newSelection = event.newSelection[0];

							var nullable = <%= nullable %>;

							var date = A.DataType.Date.parse(newSelection);
							var invalidNumber = isNaN(newSelection);

							if (invalidNumber && !nullable) {
								event.newSelection[0] = new Date();
							}
							else if (invalidNumber && !date && nullable) {
								var selection = new Date();

								if (!newSelection) {
									selection = '';
								}

								event.newSelection[0] = selection;
							}

							datePicker.updateValue(event.newSelection[0]);
						}
					},
					popover: {
						zIndex: Liferay.zIndex.OVERLAY
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

				var dateVal = '';
				var monthVal = '';
				var yearVal = '';

				if (date && !isNaN(date)) {
					dateVal = date.getDate();
					monthVal = date.getMonth();
					yearVal = date.getFullYear();
				}

				container.one('#<%= dayParamId %>').val(dateVal);
				container.one('#<%= monthParamId %>').val(monthVal);
				container.one('#<%= yearParamId %>').val(yearVal);
			};

			datePicker.after(
				'selectionChange',
				function(event) {
					var input = A.one('#<%= nameId %>');

					if (input) {
						var form = input.get('form');

						var formId = form.get('id');

						var formInstance = Liferay.Form.get(formId);

						if (formInstance && formInstance.formValidator) {
							formInstance.formValidator.validateField('<%= namespace + HtmlUtil.escapeAttribute(name) %>');
						}
					}
				}
			);

			Liferay.once(
				'screenLoad',
				function() {
					datePicker.destroy();
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