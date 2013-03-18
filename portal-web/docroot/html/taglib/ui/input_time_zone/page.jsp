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

<%@ include file="/html/taglib/ui/input_time_zone/init.jsp" %>

<%
boolean autoFocus = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-time-zone:autoFocus"));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-time-zone:cssClass"));
boolean daylight = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-time-zone:daylight"));
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-time-zone:disabled"));
int displayStyle = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-time-zone:displayStyle"));
String name = namespace + request.getAttribute("liferay-ui:input-time-zone:name");
boolean nullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-time-zone:nullable"));
String value = (String)request.getAttribute("liferay-ui:input-time-zone:value");

NumberFormat numberFormat = NumberFormat.getInstance(locale);

numberFormat.setMinimumIntegerDigits(2);
%>

<select class="<%= cssClass %>" <%= disabled ? "disabled=\"disabled\"" : "" %> name="<%= name %>">
	<c:if test="<%= nullable %>">
		<option value=""></option>
	</c:if>

	<%
	Set<TimeZone> timeZones = new TreeSet<TimeZone>(new TimeZoneComparator(locale));

	for (String timeZoneId : PropsUtil.getArray(PropsKeys.TIME_ZONES)) {
		TimeZone curTimeZone = TimeZoneUtil.getTimeZone(timeZoneId);

		timeZones.add(curTimeZone);
	}

	for (TimeZone curTimeZone : timeZones) {
		String offset = StringPool.BLANK;

		int rawOffset = curTimeZone.getRawOffset();

		if (rawOffset > 0) {
			offset = "+";
		}

		if (rawOffset != 0) {
			String offsetHour = numberFormat.format(rawOffset / Time.HOUR);
			String offsetMinute = numberFormat.format(Math.abs(rawOffset % Time.HOUR) / Time.MINUTE);

			offset += offsetHour + ":" + offsetMinute;
		}
	%>

		<option <%= value.equals(curTimeZone.getID()) ? "selected" : "" %> value="<%= curTimeZone.getID() %>">(UTC <%= offset %>) <%= curTimeZone.getDisplayName(daylight, displayStyle, locale) %></option>

	<%
	}
	%>

</select>

<c:if test="<%= autoFocus %>">
	<aui:script>
		Liferay.Util.focusFormField('#<%= name %>');
	</aui:script>
</c:if>