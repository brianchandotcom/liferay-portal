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
long organizationId = ParamUtil.getLong(request, "organizationId");

List<OrgLabor> orgLabors = OrgLaborServiceUtil.getOrgLabors(organizationId);

Format timeFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("HH:mm", locale);
%>

<c:if test="<%= !orgLabors.isEmpty() %>">
	<h3 class="icon-cogs"><liferay-ui:message key="services" /></h3>

	<%
	Calendar cal = CalendarFactoryUtil.getCalendar();
	String[] days = CalendarUtil.getDays(locale);
	String[] paramPrefixes = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

	for (int i = 0; i < orgLabors.size(); i++) {
		OrgLabor orgLabor = orgLabors.get(i);

		int[] openArray = new int[paramPrefixes.length];

		for (int j = 0; j < paramPrefixes.length; j++) {
			openArray[j] = BeanPropertiesUtil.getInteger(orgLabor, paramPrefixes[j] + "Open", -1);
		}

		int[] closeArray = new int[paramPrefixes.length];

		for (int j = 0; j < paramPrefixes.length; j++) {
			closeArray[j] = BeanPropertiesUtil.getInteger(orgLabor, paramPrefixes[j] + "Close", -1);
		}
	%>

		<ul class="property-list">
			<li>
				<h4><%= LanguageUtil.get(request, ListTypeServiceUtil.getListType(orgLabor.getTypeId()).getName()) %></h4>

				<table border="1" class="org-labor-table">
					<tr>
						<td class="no-color"></td>

						<%
						for (String day : days) {
						%>

							<th>
								<label><%= day %></label>
							</th>

						<%
						}
						%>

					</tr>
					<tr>
						<td>
							<strong><liferay-ui:message key="open" /></strong>
						</td>

						<%
						for (int j = 0; j < days.length; j++) {
							int curOpen = openArray[j];

							cal.set(Calendar.HOUR_OF_DAY, curOpen / 100);
							cal.set(Calendar.MINUTE, curOpen % 100);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 0);
						%>

							<td>
								<%= (curOpen != -1) ? timeFormat.format(cal.getTime()) : "" %>
							</td>

						<%
						}
						%>

					</tr>
					<tr>
						<td>
							<strong><liferay-ui:message key="close[status]" /></strong>
						</td>

						<%
						for (int j = 0; j < days.length; j++) {
							int curClose = closeArray[j];

							cal.set(Calendar.HOUR_OF_DAY, curClose / 100);
							cal.set(Calendar.MINUTE, curClose % 100);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 0);
						%>

							<td>
								<%= (curClose != -1) ? timeFormat.format(cal.getTime()) : "" %>
							</td>

						<%
						}
						%>

					</tr>
				</table>
			</li>
		</ul>

	<%
	}
	%>

</c:if>