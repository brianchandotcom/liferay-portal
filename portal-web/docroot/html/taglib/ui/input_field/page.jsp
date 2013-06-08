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
boolean autoSize = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:autoSize"));
Object bean = request.getAttribute("liferay-ui:input-field:bean");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:cssClass"));
String dateTogglerCheckboxLabel = GetterUtil.getString((String) request.getAttribute("liferay-ui:input-field:dateTogglerCheckboxLabel"));
String defaultLanguageId = (String)request.getAttribute("liferay-ui:input-field:defaultLanguageId");
Object defaultValue = request.getAttribute("liferay-ui:input-field:defaultValue");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:disabled"));
String field = (String)request.getAttribute("liferay-ui:input-field:field");
String fieldParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:fieldParam"));
Format format = (Format)request.getAttribute("liferay-ui:input-field:format");
String formName = (String)request.getAttribute("liferay-ui:input-field:formName");
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:ignoreRequestValue"));
String languageId = (String)request.getAttribute("liferay-ui:input-field:languageId");
String model = (String)request.getAttribute("liferay-ui:input-field:model");
String placeholder = (String)request.getAttribute("liferay-ui:input-field:placeholder");

String type = ModelHintsUtil.getType(model, field);

Map<String, String> hints = ModelHintsUtil.getHints(model, field);

if (hints != null) {
	type = GetterUtil.getString(hints.get("type"), type);
}
%>

<c:if test="<%= type != null %>">
	<c:choose>
		<c:when test='<%= type.equals("boolean") %>'>

			<%
			boolean defaultBoolean = GetterUtil.DEFAULT_BOOLEAN;

			if (defaultValue != null) {
				defaultBoolean = ((Boolean)defaultValue).booleanValue();
			}
			else {
				if (hints != null) {
					defaultBoolean = GetterUtil.getBoolean(hints.get("default-value"));
				}
			}

			boolean value = BeanPropertiesUtil.getBooleanSilent(bean, field, defaultBoolean);

			if (!ignoreRequestValue) {
				value = ParamUtil.getBoolean(request, fieldParam, value);
			}
			%>

			<liferay-ui:input-checkbox cssClass="<%= cssClass %>" defaultValue="<%= value %>" disabled="<%= disabled %>" formName="<%= formName %>" id="<%= namespace + id %>" param="<%= fieldParam %>" />
		</c:when>
		<c:when test='<%= type.equals("Date") %>'>

			<%
			Calendar now = CalendarFactoryUtil.getCalendar(timeZone, locale);

			boolean checkDefaultDelta = false;

			Calendar cal = null;

			if (defaultValue != null) {
				cal = (Calendar)defaultValue;
			}
			else {
				cal = CalendarFactoryUtil.getCalendar(timeZone, locale);

				Date date = (Date)BeanPropertiesUtil.getObject(bean, field);

				if (date == null) {
					checkDefaultDelta = true;

					date = new Date();
				}

				cal.setTime(date);
			}

			boolean updateFromDefaultDelta = false;

			int month = -1;

			if (!ignoreRequestValue) {
				month = ParamUtil.getInteger(request, fieldParam + "Month", month);
			}

			if ((month == -1) && (cal != null)) {
				month = cal.get(Calendar.MONTH);

				if (checkDefaultDelta && (hints != null)) {
					int defaultMonthDelta = GetterUtil.getInteger(hints.get("default-month-delta"));

					cal.add(Calendar.MONTH, defaultMonthDelta);

					updateFromDefaultDelta = true;
				}
			}

			boolean monthNullable = false;

			if (hints != null) {
				monthNullable = GetterUtil.getBoolean(hints.get("month-nullable"), monthNullable);
			}

			int day = -1;

			if (!ignoreRequestValue) {
				day = ParamUtil.getInteger(request, fieldParam + "Day", day);
			}

			if ((day == -1) && (cal != null)) {
				day = cal.get(Calendar.DATE);

				if (checkDefaultDelta && (hints != null)) {
					int defaultDayDelta = GetterUtil.getInteger(hints.get("default-day-delta"));

					cal.add(Calendar.DATE, defaultDayDelta);

					updateFromDefaultDelta = true;
				}
			}

			boolean dayNullable = false;

			if (hints != null) {
				dayNullable = GetterUtil.getBoolean(hints.get("day-nullable"), dayNullable);
			}

			int year = -1;

			if (!ignoreRequestValue) {
				year = ParamUtil.getInteger(request, fieldParam + "Year", year);
			}

			if ((year == -1) && (cal != null)) {
				year = cal.get(Calendar.YEAR);

				if (checkDefaultDelta && (hints != null)) {
					int defaultYearDelta = GetterUtil.getInteger(hints.get("default-year-delta"));

					cal.add(Calendar.YEAR, defaultYearDelta);

					updateFromDefaultDelta = true;
				}
			}

			if (updateFromDefaultDelta) {
				month = cal.get(Calendar.MONTH);
				day = cal.get(Calendar.DATE);
				year = cal.get(Calendar.YEAR);
			}

			boolean yearNullable = false;

			if (hints != null) {
				yearNullable = GetterUtil.getBoolean(hints.get("year-nullable"), yearNullable);
			}

			int yearRangeDelta = 5;

			if (hints != null) {
				yearRangeDelta = GetterUtil.getInteger(hints.get("year-range-delta"), yearRangeDelta);
			}

			int yearRangeStart = year - yearRangeDelta;
			int yearRangeEnd = year + yearRangeDelta;

			if (year == -1) {
				yearRangeStart = now.get(Calendar.YEAR) - yearRangeDelta;
				yearRangeEnd = now.get(Calendar.YEAR) + yearRangeDelta;
			}

			boolean yearRangePast = true;

			if (hints != null) {
				yearRangePast = GetterUtil.getBoolean(hints.get("year-range-past"), true);
			}

			if (!yearRangePast) {
				if (yearRangeStart < now.get(Calendar.YEAR)) {
					yearRangeStart = now.get(Calendar.YEAR);
				}

				if (yearRangeEnd < now.get(Calendar.YEAR)) {
					yearRangeEnd = now.get(Calendar.YEAR);
				}
			}

			boolean yearRangeFuture = true;

			if (hints != null) {
				yearRangeFuture = GetterUtil.getBoolean(hints.get("year-range-future"), true);
			}

			if (!yearRangeFuture) {
				if (yearRangeStart > now.get(Calendar.YEAR)) {
					yearRangeStart = now.get(Calendar.YEAR);
				}

				if (yearRangeEnd > now.get(Calendar.YEAR)) {
					yearRangeEnd = now.get(Calendar.YEAR);
				}
			}

			int firstDayOfWeek = Calendar.SUNDAY - 1;

			if (cal != null) {
				firstDayOfWeek = cal.getFirstDayOfWeek() - 1;
			}

			int hour = -1;

			if (!ignoreRequestValue) {
				hour = ParamUtil.getInteger(request, fieldParam + "Hour", hour);
			}

			if ((hour == -1) && (cal != null)) {
				hour = cal.get(Calendar.HOUR_OF_DAY);

				if (DateUtil.isFormatAmPm(locale)) {
					hour = cal.get(Calendar.HOUR);
				}
			}

			int minute = -1;

			if (!ignoreRequestValue) {
				minute = ParamUtil.getInteger(request, fieldParam + "Minute", minute);
			}

			if ((minute == -1) && (cal != null)) {
				minute = cal.get(Calendar.MINUTE);
			}

			int amPm = -1;

			if (!ignoreRequestValue) {
				amPm = ParamUtil.getInteger(request, fieldParam + "AmPm", amPm);
			}

			if ((amPm == -1) && (cal != null)) {
				amPm = Calendar.AM;

				if (DateUtil.isFormatAmPm(locale)) {
					amPm = cal.get(Calendar.AM_PM);
				}
			}

			boolean showTime = true;

			if (hints != null) {
				showTime = GetterUtil.getBoolean(hints.get("show-time"), showTime);
			}
			%>

			<div class="clearfix">
				<liferay-ui:input-date
					cssClass="<%= cssClass %>"
					dayNullable="<%= dayNullable %>"
					dayParam='<%= fieldParam + "Day" %>'
					dayValue="<%= day %>"
					disabled="<%= disabled %>"
					firstDayOfWeek="<%= firstDayOfWeek %>"
					formName="<%= formName %>"
					imageInputId='<%= fieldParam + "ImageInputId" %>'
					monthNullable="<%= monthNullable %>"
					monthParam='<%= fieldParam + "Month" %>'
					monthValue="<%= month %>"
					name="<%= fieldParam %>"
					yearNullable="<%= yearNullable %>"
					yearParam='<%= fieldParam + "Year" %>'
					yearRangeEnd="<%= yearRangeEnd %>"
					yearRangeStart="<%= yearRangeStart %>"
					yearValue="<%= year %>"
				/>

				<c:if test="<%= showTime %>">
					<liferay-ui:input-time
						amPmParam='<%= fieldParam + "AmPm" %>'
						amPmValue="<%= amPm %>"
						cssClass="<%= cssClass %>"
						disabled="<%= disabled %>"
						hourParam='<%= fieldParam + "Hour" %>'
						hourValue="<%= hour %>"
						minuteInterval="<%= 1 %>"
						minuteParam='<%= fieldParam + "Minute" %>'
						minuteValue="<%= minute %>"
					/>
				</c:if>
			</div>

			<c:if test="<%= Validator.isNotNull(dateTogglerCheckboxLabel) %>">
				<div class="clearfix">
					<aui:input id="<%= formName + fieldParam %>" label="<%= dateTogglerCheckboxLabel %>" name='<%= formName + fieldParam + "DateTogglerCheckbox" %>' type="checkbox" value="<%= disabled %>" />
				</div>

				<aui:script use="aui-base">
					var checkbox = A.one('#<portlet:namespace /><%= formName + fieldParam %>Checkbox');

					checkbox.once(
						['click', 'mouseover'],
						function() {
							Liferay.component('<portlet:namespace /><%= fieldParam %>DatePicker');
						}
					);

					checkbox.on(
						['click', 'mouseover'],
						function(event) {
							var checked = document.getElementById('<portlet:namespace /><%= formName + fieldParam %>Checkbox').checked;

							document.<portlet:namespace /><%= formName %>["<portlet:namespace /><%= fieldParam %>Month"].disabled = checked;
							document.<portlet:namespace /><%= formName %>["<portlet:namespace /><%= fieldParam %>Day"].disabled = checked;
							document.<portlet:namespace /><%= formName %>["<portlet:namespace /><%= fieldParam %>Year"].disabled = checked;

							<c:if test="<%= showTime %>">
								document.<portlet:namespace /><%= formName %>["<portlet:namespace /><%= fieldParam %>Hour"].disabled = checked;
								document.<portlet:namespace /><%= formName %>["<portlet:namespace /><%= fieldParam %>Minute"].disabled = checked;
								document.<portlet:namespace /><%= formName %>["<portlet:namespace /><%= fieldParam %>AmPm"].disabled = checked;
							</c:if>

							var calendarWidget = A.Widget.getByNode(document.<portlet:namespace />fm["<portlet:namespace /><%= fieldParam %>Month"]);

							if (calendarWidget) {
								calendarWidget.set('disabled', checked);
							}
						}
					);
				</aui:script>
			</c:if>
		</c:when>
		<c:when test='<%= type.equals("double") || type.equals("int") || type.equals("long") || type.equals("String") %>'>

			<%
			String defaultString = GetterUtil.DEFAULT_STRING;

			if (defaultValue != null) {
				defaultString = (String)defaultValue;
			}

			String value = null;

			if (type.equals("double")) {
				double doubleValue = BeanPropertiesUtil.getDoubleSilent(bean, field, GetterUtil.getDouble(defaultString));

				if (!ignoreRequestValue) {
					doubleValue = ParamUtil.getDouble(request, fieldParam, doubleValue);
				}

				if (format != null) {
					value = format.format(doubleValue);
				}
				else {
					value = String.valueOf(doubleValue);
				}
			}
			else if (type.equals("int")) {
				int intValue = BeanPropertiesUtil.getIntegerSilent(bean, field, GetterUtil.getInteger(defaultString));

				if (!ignoreRequestValue) {
					intValue = ParamUtil.getInteger(request, fieldParam, intValue);
				}

				if (format != null) {
					value = format.format(intValue);
				}
				else {
					value = String.valueOf(intValue);
				}
			}
			else if (type.equals("long")) {
				long longValue = BeanPropertiesUtil.getLongSilent(bean, field, GetterUtil.getLong(defaultString));

				if (!ignoreRequestValue) {
					longValue = ParamUtil.getLong(request, fieldParam, longValue);
				}

				if (format != null) {
					value = format.format(longValue);
				}
				else {
					value = String.valueOf(longValue);
				}
			}
			else {
				value = BeanPropertiesUtil.getStringSilent(bean, field, defaultString);

				if (!ignoreRequestValue) {
					value = ParamUtil.getString(request, fieldParam, value);
				}
			}

			boolean autoEscape = true;

			if (hints != null) {
				autoEscape = GetterUtil.getBoolean(hints.get("auto-escape"), true);
			}

			String displayHeight = ModelHintsConstants.TEXT_DISPLAY_HEIGHT;
			String displayWidth = ModelHintsConstants.TEXT_DISPLAY_WIDTH;
			String maxLength = ModelHintsConstants.TEXT_MAX_LENGTH;
			boolean secret = false;
			boolean upperCase = false;
			boolean checkTab = false;

			if (hints != null) {
				autoSize = GetterUtil.getBoolean(hints.get("autoSize"), autoSize);
				checkTab = GetterUtil.getBoolean(hints.get("check-tab"), checkTab);
				displayHeight = GetterUtil.getString(hints.get("display-height"), displayHeight);
				displayWidth = GetterUtil.getString(hints.get("display-width"), displayWidth);
				maxLength = GetterUtil.getString(hints.get("max-length"), maxLength);
				secret = GetterUtil.getBoolean(hints.get("secret"), secret);
				upperCase = GetterUtil.getBoolean(hints.get("upper-case"), upperCase);
			}

			if (autoSize) {
				displayHeight = "auto";
			}

			boolean localized = ModelHintsUtil.isLocalized(model, field);

			String xml = StringPool.BLANK;

			if (localized) {
				if (Validator.isNotNull(bean)) {
					xml = BeanPropertiesUtil.getString(bean, field);
				}
				else {
					Map<Locale, String> localizationMap = LocalizationUtil.getLocalizationMap(portletRequest, field);

					xml = LocalizationUtil.updateLocalization(localizationMap, xml, field, defaultLanguageId);
				}
			}
			%>

			<c:choose>
				<c:when test="<%= displayHeight.equals(ModelHintsConstants.TEXT_DISPLAY_HEIGHT) %>">

					<%
					if (Validator.isNotNull(value)) {
						int maxLengthInt = GetterUtil.getInteger(maxLength);

						if (value.length() > maxLengthInt) {
							value = value.substring(0, maxLengthInt);
						}
					}
					%>

					<c:choose>
						<c:when test="<%= localized %>">
							<liferay-ui:input-localized cssClass='<%= cssClass + " lfr-input-text" %>' defaultLanguageId="<%= defaultLanguageId %>" disabled="<%= disabled %>" displayWidth="<%= displayWidth %>" formName="<%= formName %>" id="<%= id %>" ignoreRequestValue="<%= ignoreRequestValue %>" languageId="<%= languageId %>" maxLength="<%= maxLength %>" name="<%= fieldParam %>" style='<%= "max-width: " + displayWidth + (Validator.isDigit(displayWidth) ? "px" : "") + "; " + (upperCase ? "text-transform: uppercase;" : "" ) %>' xml="<%= xml %>" />
						</c:when>
						<c:otherwise>
							<input class="<%= cssClass + " lfr-input-text" %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= namespace %><%= id %>" name="<%= namespace %><%= fieldParam %>" <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(pageContext, placeholder) + "\"" : StringPool.BLANK %> style="max-width: <%= displayWidth %><%= Validator.isDigit(displayWidth) ? "px" : "" %>; <%= upperCase ? "text-transform: uppercase;" : "" %>" type="<%= secret ? "password" : "text" %>" value="<%= autoEscape ? HtmlUtil.escape(value) : value %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="<%= localized %>">
							<liferay-ui:input-localized autoSize="<%= autoSize %>" cssClass='<%= cssClass + " lfr-input-text" %>' defaultLanguageId="<%= defaultLanguageId %>" disabled="<%= disabled %>" displayWidth="<%= displayWidth %>" formName="<%= formName %>" id="<%= id %>" ignoreRequestValue="<%= ignoreRequestValue %>" languageId="<%= languageId %>" maxLength="<%= maxLength %>" name="<%= fieldParam %>" onKeyDown='<%= (checkTab ? "Liferay.Util.checkTab(this); " : "") + "Liferay.Util.disableEsc();" %>' style='<%= !autoSize ? "height: " + displayHeight + (Validator.isDigit(displayHeight) ? "px" : StringPool.BLANK) + ";" : StringPool.BLANK + "max-width: " + displayWidth + (Validator.isDigit(displayWidth) ? "px" : "") +";" %>' type="textarea" wrap="soft" xml="<%= xml %>" />
						</c:when>
						<c:otherwise>
							<textarea class="<%= cssClass + " lfr-textarea" %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= namespace %><%= id %>" name="<%= namespace %><%= fieldParam %>" <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(pageContext, placeholder) + "\"" : StringPool.BLANK %> style="<%= !autoSize ? "height: " + displayHeight + (Validator.isDigit(displayHeight) ? "px" : StringPool.BLANK) + ";" : StringPool.BLANK %> max-width: <%= displayWidth %><%= Validator.isDigit(displayWidth) ? "px" : "" %>;" wrap="soft" onKeyDown="<%= checkTab ? "Liferay.Util.checkTab(this); " : "" %> Liferay.Util.disableEsc();"><%= autoEscape ? HtmlUtil.escape(value) : value %></textarea>
						</c:otherwise>
					</c:choose>

					<c:if test="<%= autoSize && !localized %>">
						<aui:script use="aui-autosize">
							A.one('#<%= namespace %><%= id %>').plug(
								A.Plugin.Autosize,
								{
									<c:if test="<%= Validator.isDigit(displayHeight) %>">
										minHeight: <%= displayHeight %>
									</c:if>
								}
							);
						</aui:script>
					</c:if>
				</c:otherwise>
			</c:choose>

			<c:if test="<%= !localized %>">
				<aui:script use="aui-char-counter">
					new A.CharCounter(
						{
							input: '#<%= namespace %><%= id %>',
							maxLength: <%= maxLength %>
						}
					);
				</aui:script>
			</c:if>
		</c:when>
	</c:choose>
</c:if>