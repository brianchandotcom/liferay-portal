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

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-time:cssClass"));
String hourParam = namespace + request.getAttribute("liferay-ui:input-time:hourParam");
int hourValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-time:hourValue"));
boolean hourNullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-time:hourNullable"));
String minuteParam = namespace + request.getAttribute("liferay-ui:input-time:minuteParam");
int minuteValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-time:minuteValue"));
int minuteInterval = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-time:minuteInterval"));
boolean minuteNullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-time:minuteNullable"));
String amPmParam = namespace + request.getAttribute("liferay-ui:input-time:amPmParam");
int amPmValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-time:amPmValue"));
boolean amPmNullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-time:amPmNullable"));
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-time:disabled"));

NumberFormat numberFormat = NumberFormat.getInstance(locale);
numberFormat.setMinimumIntegerDigits(2);

String timeFormatPattern = ((SimpleDateFormat)(DateFormat.getTimeInstance(DateFormat.SHORT, locale))).toPattern();

boolean timeFormatAmPm = true;

if (timeFormatPattern.indexOf("a") == -1) {
	timeFormatAmPm = false;
}
%>

<div class="lfr-input-time <%= cssClass %>">
	<select <%= disabled ? "disabled=\"disabled\"" : "" %> name="<%= hourParam %>">
		<c:if test="<%= hourNullable %>">
			<option value=""></option>
		</c:if>

		<%
		for (int i = 0; i < (timeFormatAmPm ? 12 : 24); i++) {
			String hourString = String.valueOf(i);

			if (timeFormatAmPm && (i == 0)) {
				hourString = "12";
			}
		%>

			<option <%= (hourValue == i) ? "selected" : "" %> value="<%= i %>"><%= hourString %></option>

		<%
		}
		%>

	</select>

	<select <%= disabled ? "disabled=\"disabled\"" : "" %> name="<%= minuteParam %>">
		<c:if test="<%= minuteNullable %>">
			<option value=""></option>
		</c:if>

		<%
		for (int i = 0; i < 60; i++) {
			String minute = numberFormat.format(i);
		%>

			<c:if test="<%= (minuteInterval == 0) || ((i % minuteInterval) == 0) %>">
				<option <%= (minuteValue == i) ? "selected" : "" %> value="<%= i %>">:<%= minute %></option>
			</c:if>

		<%
		}
		%>

	</select>

	<c:choose>
		<c:when test="<%= ! timeFormatAmPm %>">
			<input name="<%= amPmParam %>" type="hidden" value="<%= Calendar.AM %>" />
		</c:when>
		<c:otherwise>
			<select <%= disabled ? "disabled=\"disabled\"" : "" %> name="<%= amPmParam %>">
				<c:if test="<%= amPmNullable %>">
					<option value=""></option>
				</c:if>

				<option <%= (amPmValue == Calendar.AM) ? "selected" : "" %> value="<%= Calendar.AM %>"><liferay-ui:message key="am" /></option>
				<option <%= (amPmValue == Calendar.PM) ? "selected" : "" %> value="<%= Calendar.PM %>"><liferay-ui:message key="pm" /></option>
			</select>
		</c:otherwise>
	</c:choose>
</div>