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
String backURL = ParamUtil.getString(request, "backURL");
Long classPK = ParamUtil.getLong(request, "classPK");
long primaryKey = ParamUtil.getLong(request, "primaryKey", 0L);
String redirect = ParamUtil.getString(request, "redirect");

OrgLabor orgLabor = null;

if (primaryKey > 0L) {
	orgLabor = OrgLaborServiceUtil.getOrgLabor(primaryKey);
}

if (!portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
//	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);

	String portletTitle = (String)request.getAttribute(UsersAdminWebKeys.PORTLET_TITLE);

	renderResponse.setTitle(portletTitle);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "contact-information"), redirect);

String sheetTitle;

if (primaryKey > 0) {
	sheetTitle = LanguageUtil.get(request, "edit-opening-hours");
}
else {
	sheetTitle = LanguageUtil.get(request, "add-opening-hours");
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, sheetTitle), null);
%>

<portlet:actionURL name="/users_admin/update_contact_information" var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="className" type="hidden" value="<%= Organization.class.getName() %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="errorMVCRenderCommandName" type="hidden" value="/users_admin/edit_opening_hours" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.ORGANIZATION_SERVICE %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= String.valueOf(primaryKey) %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<div class="container-fluid container-fluid-max-xl">
		<div class="sheet-lg" id="breadcrumb">
			<liferay-ui:breadcrumb
				showCurrentGroup="<%= false %>"
				showGuestGroup="<%= false %>"
				showLayout="<%= false %>"
				showPortletBreadcrumb="<%= true %>"
			/>
		</div>

		<div class="sheet sheet-lg">
			<div class="sheet-header">
				<h2 class="sheet-title"><liferay-ui:message key="<%= sheetTitle %>" /></h2>
			</div>

			<div class="sheet-section">
				<aui:model-context bean="<%= orgLabor %>" model="<%= OrgLabor.class %>" />

				<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.ORGANIZATION_SERVICE %>" message="please-select-a-type" />

				<aui:select label="type-of-service" listType="<%= ListTypeConstants.ORGANIZATION_SERVICE %>" name='<%= "orgLaborTypeId" %>' />

				<table border="0">

					<%
					OrgLaborFormDisplay orgLaborFormDisplay = new OrgLaborFormDisplay(locale, orgLabor);

					for (OrgLaborFormDisplay.DayRowDisplay dayRowDisplay : orgLaborFormDisplay.getDayRowDisplays()) {
					%>

						<tr>
							<td><h5><%= dayRowDisplay.getLongDayName() %></h5></td>

							<td>
								<aui:select cssClass="input-container" label="" name='<%= dayRowDisplay.getShortDayName() + "Open" %>'>
									<aui:option value="-1" />

									<%
									for (OrgLaborFormDisplay.SelectOptionDisplay selectOptionDisplay : dayRowDisplay.getOpenSelectOptionDisplays()) {
									%>

										<aui:option label="<%= selectOptionDisplay.getLabel() %>" selected="<%= selectOptionDisplay.isSelected() %>" value="<%= selectOptionDisplay.getValue() %>" />

									<%
									}
									%>

								</aui:select>
							</td>
							<td><h5><%= StringUtil.lowerCase(LanguageUtil.get(request, "to")) %></h5></td>

							<td>
								<aui:select cssClass="input-container" label="" name='<%= dayRowDisplay.getShortDayName() + "Close" %>'>
									<aui:option value="-1" />

									<%
									for (OrgLaborFormDisplay.SelectOptionDisplay selectOptionDisplay : dayRowDisplay.getCloseSelectOptionDisplays()) {
									%>

										<aui:option label="<%= selectOptionDisplay.getLabel() %>" selected="<%= selectOptionDisplay.isSelected() %>" value="<%= selectOptionDisplay.getValue() %>" />

									<%
									}
									%>

								</aui:select>
							</td>
						</tr>

					<%
					}
					%>

				</table>
			</div>

			<div class="sheet-footer">
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= redirect %>" type="cancel" />
			</div>
		</div>
	</div>
</aui:form>