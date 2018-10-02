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
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

long organizationId = organizationScreenNavigationDisplayContext.getOrganizationId();

List<OrgLabor> orgLabors = OrgLaborServiceUtil.getOrgLabors(organizationId);
%>

<div class="sheet-title">
	<span class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<h2 class="sheet-title">
				<%= organizationScreenNavigationDisplayContext.getFormLabel() %>
			</h2>
		</span>
		<span class="autofit-col">
			<liferay-ui:icon
				cssClass="modify-opening-hours-link"
				data="<%=
					new HashMap<String, Object>() {
						{
							put("title", LanguageUtil.get(request, "add-opening-hours"));
						}
					}
				%>"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message="add"
				url="javascript:;"
			/>
		</span>
	</span>
</div>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="services"
/>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.ORGANIZATION_SERVICE %>" message="please-select-a-type" />

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="this-organization-does-not-have-any-opening-hours"
	id="openingHoursSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= orgLabors.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= orgLabors.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.OrgLabor"
		escapedModel="<%= true %>"
		keyProperty="orgLaborId"
		modelVar="orgLabor"
	>
		<liferay-ui:search-container-column-text>

			<%
			Format timeFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("HH:mm", locale);

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

			<table>
				<tr>
					<th><%= StringUtil.toUpperCase(orgLabor.getType().getName()) %></th>
				</tr>

				<%
				for (int j = 0; j < days.length; j++) {
					String closeDisplay = "";
					String closeValue = String.valueOf(closeArray[j]);
					String day = days[j];
					String openDisplay = "";
					String openValue = String.valueOf(openArray[j]);

					Calendar closeCal = CalendarFactoryUtil.getCalendar();

					closeCal.set(Calendar.SECOND, 0);
					closeCal.set(Calendar.MILLISECOND, 0);

					if (closeValue.equals("0")) {
						closeCal.set(Calendar.HOUR_OF_DAY, 0);
						closeCal.set(Calendar.MINUTE, 0);
					}
					else if (closeValue.length() == 4) {
						closeCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(closeValue.substring(0, 2)));
						closeCal.set(Calendar.MINUTE, Integer.valueOf(closeValue.substring(2)));
					}
					else if (closeValue.length() == 3) {
						closeCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(closeValue.charAt(0)));
						closeCal.set(Calendar.MINUTE, Integer.valueOf(closeValue.substring(1)));
					}

					if (!closeValue.equals("-1")) {
						closeDisplay = timeFormat.format(closeCal.getTime());
					}

					Calendar openCal = CalendarFactoryUtil.getCalendar();

					openCal.set(Calendar.SECOND, 0);
					openCal.set(Calendar.MILLISECOND, 0);

					if (openValue.equals("0")) {
						openCal.set(Calendar.HOUR_OF_DAY, 0);
						openCal.set(Calendar.MINUTE, 0);
					}
					else if (openValue.length() == 4) {
						openCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(openValue.substring(0, 2)));
						openCal.set(Calendar.MINUTE, Integer.valueOf(openValue.substring(2)));
					}
					else if (openValue.length() == 3) {
						openCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(openValue.charAt(0)));
						openCal.set(Calendar.MINUTE, Integer.valueOf(openValue.substring(1)));
					}

					if (!openValue.equals("-1")) {
						openDisplay = timeFormat.format(openCal.getTime());
					}
				%>

					<tr>
						<td><%= day %></td>

						<c:choose>
							<c:when test='<%= closeDisplay.equals("") && openDisplay.equals("") %>'>
								<td><%= LanguageUtil.get(request, "closed") %></td>
							</c:when>
							<c:otherwise>
								<td><%= openDisplay %> - <%= closeDisplay %></td>
							</c:otherwise>
						</c:choose>
					</tr>

				<%
				}
				%>

			</table>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action"
			path="/organization/opening_hours_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:renderURL var="editOpeningHoursRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_opening_hours.jsp" />
</portlet:renderURL>

<aui:script require="<%= organizationScreenNavigationDisplayContext.getContactInformationJSRequire() %>">
	ContactInformation.registerContactInformationListener(
		'.modify-opening-hours-link a',
		'<%= editOpeningHoursRenderURL.toString() %>',
		690
	);
</aui:script>