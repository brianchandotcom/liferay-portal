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

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:cssClass"));
String formName = (String)request.getAttribute("liferay-ui:input-field:formName");
String languageId = (String)request.getAttribute("liferay-ui:input-field:languageId");
String model = (String)request.getAttribute("liferay-ui:input-field:model");
Object bean = request.getAttribute("liferay-ui:input-field:bean");
String field = (String)request.getAttribute("liferay-ui:input-field:field");
String fieldParam = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-field:fieldParam"));
Object defaultValue = request.getAttribute("liferay-ui:input-field:defaultValue");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:disabled"));
Format format = (Format)request.getAttribute("liferay-ui:input-field:format");

String type = ModelHintsUtil.getType(model, field);
Map<String, String> hints = ModelHintsUtil.getHints(model, field);
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

			if (Validator.isNull(fieldParam)) {
				fieldParam = field;
			}

			boolean value = BeanParamUtil.getBoolean(bean, request, field, defaultBoolean);
			%>

			<liferay-ui:input-checkbox cssClass="<%= cssClass %>" formName="<%= formName %>" param="<%= fieldParam %>" defaultValue="<%= value %>" disabled="<%= disabled %>" />
		</c:when>
		<c:when test='<%= type.equals("Date") %>'>

			<%
			Calendar now = CalendarFactoryUtil.getCalendar(timeZone, locale);

			String timeFormatPattern = ((SimpleDateFormat)(DateFormat.getTimeInstance(DateFormat.SHORT, locale))).toPattern();

			boolean timeFormatAmPm = true;

			if (timeFormatPattern.indexOf("a") == -1) {
				timeFormatAmPm = false;
			}

			if (Validator.isNull(fieldParam)) {
				fieldParam = field;
			}

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

			int month = ParamUtil.getInteger(request, fieldParam + "Month", -1);

			if ((month == -1) && (cal != null)) {
				month = cal.get(Calendar.MONTH);
			}

			int defaultMonthDelta = 0;

			if (checkDefaultDelta && (hints != null)) {
				defaultMonthDelta = GetterUtil.getInteger(hints.get("default-month-delta"), defaultMonthDelta);
			}

			month = month + defaultMonthDelta;

			boolean monthNullable = false;

			if (hints != null) {
				monthNullable = GetterUtil.getBoolean(hints.get("month-nullable"), monthNullable);
			}

			int day = ParamUtil.getInteger(request, fieldParam + "Day", -1);

			if ((day == -1) && (cal != null)) {
				day = cal.get(Calendar.DATE);
			}

			int defaultDayDelta = 0;

			if (checkDefaultDelta && (hints != null)) {
				defaultDayDelta = GetterUtil.getInteger(hints.get("default-day-delta"), defaultDayDelta);
			}

			day = day + defaultDayDelta;

			boolean dayNullable = false;

			if (hints != null) {
				dayNullable = GetterUtil.getBoolean(hints.get("day-nullable"), dayNullable);
			}

			int year = ParamUtil.getInteger(request, fieldParam + "Year", -1);

			if ((year == -1) && (cal != null)) {
				year = cal.get(Calendar.YEAR);
			}

			int defaultYearDelta = 0;

			if (checkDefaultDelta && (hints != null)) {
				defaultYearDelta = GetterUtil.getInteger(hints.get("default-year-delta"), defaultYearDelta);
			}

			year = year + defaultYearDelta;

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

			int hour = ParamUtil.getInteger(request, fieldParam + "Hour", -1);

			if ((hour == -1) && (cal != null)) {
				hour = cal.get(Calendar.HOUR_OF_DAY);

				if (timeFormatAmPm) {
					hour = cal.get(Calendar.HOUR);
				}
			}

			int minute = ParamUtil.getInteger(request, fieldParam + "Minute", -1);

			if ((minute == -1) && (cal != null)) {
				minute = cal.get(Calendar.MINUTE);
			}

			int amPm = ParamUtil.getInteger(request, fieldParam + "AmPm", -1);

			if ((amPm == -1) && (cal != null)) {
				amPm = Calendar.AM;

				if (timeFormatAmPm) {
					amPm = cal.get(Calendar.AM_PM);
				}
			}

			boolean showTime = true;

			if (hints != null) {
				showTime = GetterUtil.getBoolean(hints.get("show-time"), showTime);
			}
			%>

			<liferay-ui:input-date
				cssClass="<%= cssClass %>"
				dayParam='<%= fieldParam + "Day" %>'
				dayValue="<%= day %>"
				dayNullable="<%= dayNullable %>"
				disabled="<%= disabled %>"
				firstDayOfWeek="<%= firstDayOfWeek %>"
				formName="<%= formName %>"
				imageInputId='<%= fieldParam + "ImageInputId" %>'
				monthParam='<%= fieldParam + "Month" %>'
				monthValue="<%= month %>"
				monthNullable="<%= monthNullable %>"
				yearParam='<%= fieldParam + "Year" %>'
				yearValue="<%= year %>"
				yearNullable="<%= yearNullable %>"
				yearRangeStart="<%= yearRangeStart %>"
				yearRangeEnd="<%= yearRangeEnd %>"
			/>

			<c:if test="<%= showTime %>">
				<liferay-ui:input-time
					amPmParam='<%= fieldParam + "AmPm" %>'
					amPmValue="<%= amPm %>"
					disabled="<%= disabled %>"
					cssClass="<%= cssClass %>"
					hourParam='<%= fieldParam + "Hour" %>'
					hourValue="<%= hour %>"
					minuteParam='<%= fieldParam + "Minute" %>'
					minuteValue="<%= minute %>"
					minuteInterval="<%= 1 %>"
				/>
			</c:if>
		</c:when>
		<c:when test='<%= type.equals("double") || type.equals("int") || type.equals("long") || type.equals("String") %>'>

			<%
			String defaultString = GetterUtil.DEFAULT_STRING;

			if (defaultValue != null) {
				defaultString = (String)defaultValue;
			}

			String value = null;

			if (Validator.isNull(fieldParam)) {
				fieldParam = field;
			}

			if (type.equals("double")) {
				double doubleValue = BeanParamUtil.getDouble(bean, request, field, GetterUtil.getDouble(defaultString));

				if (format != null) {
					value = format.format(doubleValue);
				}
				else {
					value = String.valueOf(doubleValue);
				}
			}
			else if (type.equals("int")) {
				int intValue = BeanParamUtil.getInteger(bean, request, field, GetterUtil.getInteger(defaultString));

				if (format != null) {
					value = format.format(intValue);
				}
				else {
					value = String.valueOf(intValue);
				}
			}
			else if (type.equals("long")) {
				long longValue = BeanParamUtil.getLong(bean, request, field, GetterUtil.getLong(defaultString));

				if (format != null) {
					value = format.format(longValue);
				}
				else {
					value = String.valueOf(longValue);
				}
			}
			else {
				value = BeanParamUtil.getString(bean, request, field, defaultString);

				String httpValue = request.getParameter(fieldParam);

				if (httpValue != null) {
					value = httpValue;
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
				displayHeight = GetterUtil.getString(hints.get("display-height"), displayHeight);
				displayWidth = GetterUtil.getString(hints.get("display-width"), displayWidth);
				maxLength = GetterUtil.getString(hints.get("max-length"), maxLength);
				secret = GetterUtil.getBoolean(hints.get("secret"), secret);
				upperCase = GetterUtil.getBoolean(hints.get("upper-case"), upperCase);
				checkTab = GetterUtil.getBoolean(hints.get("check-tab"), checkTab);
			}

			boolean localized = ModelHintsUtil.isLocalized(model, field);
			%>

			<c:choose>
				<c:when test='<%= displayHeight.equals(ModelHintsConstants.TEXT_DISPLAY_HEIGHT) %>'>

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
							<liferay-ui:input-localized cssClass='<%= cssClass + " lfr-input-text" %>' disabled="<%= disabled %>" formName="<%= formName %>" languageId="<%= languageId %>" name="<%= fieldParam %>" style='<%= "max-width: " + displayWidth + (Validator.isDigit(displayWidth) ? "px" : "") + "; " + (upperCase ? "text-transform: uppercase;" : "" ) %>' xml="<%= BeanPropertiesUtil.getString(bean, field) %>" />
						</c:when>
						<c:otherwise>
							<input <%= Validator.isNotNull(cssClass) ? "class=\"" + cssClass + " lfr-input-text\"" : StringPool.BLANK %> <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= namespace %><%= fieldParam %>" name="<%= namespace %><%= fieldParam %>" style="max-width: <%= displayWidth %><%= Validator.isDigit(displayWidth) ? "px" : "" %>; <%= upperCase ? "text-transform: uppercase;" : "" %>" type="<%= secret ? "password" : "text" %>" value="<%= autoEscape ? HtmlUtil.escape(value) : value %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="<%= localized %>">
							<liferay-ui:input-localized cssClass='<%= cssClass + " lfr-input-text" %>' disabled="<%= disabled %>" formName="<%= formName %>" languageId="<%= languageId %>" name="<%= fieldParam %>" onKeyDown='<%= (checkTab ? "Liferay.Util.checkTab(this); " : "") + "Liferay.Util.disableEsc();" %>' style='<%= "height: " + displayHeight + (Validator.isDigit(displayHeight) ? "px" : "" ) + "; " + "max-width: " + displayWidth + (Validator.isDigit(displayWidth) ? "px" : "") +";" %>' type="textarea" wrap="soft" xml="<%= BeanPropertiesUtil.getString(bean, field) %>" />
						</c:when>
						<c:otherwise>
							<textarea <%= Validator.isNotNull(cssClass) ? "class=\"" + cssClass + " lfr-textarea\"" : StringPool.BLANK %> <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= namespace %><%= fieldParam %>" name="<%= namespace %><%= fieldParam %>" style="height: <%= displayHeight %><%= Validator.isDigit(displayHeight) ? "px" : "" %>; max-width: <%= displayWidth %><%= Validator.isDigit(displayWidth) ? "px" : "" %>;" wrap="soft" onKeyDown="<%= checkTab ? "Liferay.Util.checkTab(this); " : "" %> Liferay.Util.disableEsc();"><%= autoEscape ? HtmlUtil.escape(value) : value %></textarea>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>

			<aui:script use="aui-char-counter">
				new A.CharCounter(
					{
						input: '#<%= namespace %><%= fieldParam %>',
						maxLength: <%= maxLength %>
					}
				);
			</aui:script>
		</c:when>
	</c:choose>
</c:if>