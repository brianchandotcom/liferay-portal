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

<%@ include file="/init.jsp" %>

<%
long entryId = ParamUtil.getLong(request, "entryId", 0L);

OrgLabor orgLabor = null;

if (entryId > 0L) {
	orgLabor = OrgLaborServiceUtil.getOrgLabor(entryId);
}

Format timeFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("HH:mm", locale);
%>

<aui:form cssClass="modal-body" name="fm">

	<%
		Calendar cal = CalendarFactoryUtil.getCalendar();
		String[] days = CalendarUtil.getDays(locale);
		String[] paramPrefixes = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

		int[] openArray = new int[paramPrefixes.length];

		for (int j = 0; j < paramPrefixes.length; j++) {
			openArray[j] = ParamUtil.getInteger(request, paramPrefixes[j] + "Open", BeanPropertiesUtil.getInteger(orgLabor, paramPrefixes[j] + "Open", -1));
		}

		int[] closeArray = new int[paramPrefixes.length];

		for (int j = 0; j < paramPrefixes.length; j++) {
			closeArray[j] = ParamUtil.getInteger(request, paramPrefixes[j] + "Close", BeanPropertiesUtil.getInteger(orgLabor, paramPrefixes[j] + "Close", -1));
		}
	%>

	<aui:model-context bean="<%= orgLabor %>" model="<%= OrgLabor.class %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.ORGANIZATION_SERVICE %>" />

	<aui:select label="type-of-service" listType="<%= ListTypeConstants.ORGANIZATION_SERVICE %>" name='<%= "orgLaborTypeId" %>' />

	<table border="0">

		<%
		for (int j = 0; j < days.length; j++) {
			int close = closeArray[j];
			String day = days[j];
			int open = openArray[j];
			String paramPrefix = paramPrefixes[j];
		%>

		<tr>
			<td><h5><%= day %></h5></td>

			<td>
				<aui:select cssClass="input-container" label="" name='<%= paramPrefix + "Open" %>'>
					<aui:option value="-1" />

					<%
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);

					int today = cal.get(Calendar.DATE);

					while (cal.get(Calendar.DATE) == today) {
						String timeOfDayDisplay = timeFormat.format(cal.getTime());

						int timeOfDayValue = GetterUtil.getInteger(StringUtil.replace(timeOfDayDisplay, CharPool.COLON, StringPool.BLANK));

						cal.add(Calendar.MINUTE, 30);
					%>

					<aui:option label="<%= timeOfDayDisplay %>" selected="<%= open == timeOfDayValue %>" value="<%= timeOfDayValue %>" />

					<%
					}
					%>

				</aui:select>
			</td>
			<td><h5><%= StringUtil.lowerCase(LanguageUtil.get(request, "to")) %></h5></td>

			<td>
				<aui:select cssClass="input-container" label="" name='<%= paramPrefix + "Close" %>'>
					<aui:option value="-1" />

					<%
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);

					int today = cal.get(Calendar.DATE);

					while (cal.get(Calendar.DATE) == today) {
						String timeOfDayDisplay = timeFormat.format(cal.getTime());

						int timeOfDayValue = GetterUtil.getInteger(StringUtil.replace(timeOfDayDisplay, CharPool.COLON, StringPool.BLANK));

						cal.add(Calendar.MINUTE, 30);
					%>

					<aui:option label="<%= timeOfDayDisplay %>" selected="<%= close == timeOfDayValue %>" value="<%= timeOfDayValue %>" />

					<%
					}
					%>

				</aui:select>
			</td>

		</tr>

		<%
		}
		%>

</aui:form>