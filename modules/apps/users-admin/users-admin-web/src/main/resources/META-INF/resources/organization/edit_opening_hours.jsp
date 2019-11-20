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
EditContactInformationDisplayContext editContactInformationDisplayContext = new EditContactInformationDisplayContext("opening-hours", renderResponse, request);

editContactInformationDisplayContext.setPortletDisplay(portletDisplay, portletName);

OrgLabor orgLabor = null;

if (editContactInformationDisplayContext.getPrimaryKey() > 0) {
	orgLabor = OrgLaborServiceUtil.getOrgLabor(editContactInformationDisplayContext.getPrimaryKey());
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "opening-hours"), editContactInformationDisplayContext.getRedirect());

PortalUtil.addPortletBreadcrumbEntry(request, editContactInformationDisplayContext.getSheetTitle(), null);
%>

<portlet:actionURL name="/users_admin/update_contact_information" var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="errorMVCPath" type="hidden" value="/common/edit_opening_hours.jsp" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="redirect" type="hidden" value="<%= editContactInformationDisplayContext.getRedirect() %>" />
	<aui:input name="className" type="hidden" value="<%= editContactInformationDisplayContext.getClassName() %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(editContactInformationDisplayContext.getClassPK()) %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.ORGANIZATION_SERVICE %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= String.valueOf(editContactInformationDisplayContext.getPrimaryKey()) %>" />

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
				<h2 class="sheet-title"><%= editContactInformationDisplayContext.getSheetTitle() %></h2>
			</div>

			<div class="sheet-section">
				<aui:model-context bean="<%= orgLabor %>" model="<%= OrgLabor.class %>" />

				<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + editContactInformationDisplayContext.getClassName() + ListTypeConstants.ORGANIZATION_SERVICE %>" message="please-select-a-type" />

				<aui:select label="type-of-service" listType="<%= ListTypeConstants.ORGANIZATION_SERVICE %>" name="orgLaborTypeId" />

				<table border="0">

					<%
					OrgLaborFormDisplay orgLaborFormDisplay = new OrgLaborFormDisplay(locale, orgLabor);

					for (OrgLaborFormDisplay.DayRowDisplay dayRowDisplay : orgLaborFormDisplay.getDayRowDisplays()) {
					%>

						<tr>
							<td>
								<h5><%= dayRowDisplay.getLongDayName() %></h5>
							</td>

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
							<td>
								<h5><%= StringUtil.lowerCase(LanguageUtil.get(request, "to")) %></h5>
							</td>

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

				<aui:button href="<%= editContactInformationDisplayContext.getRedirect() %>" type="cancel" />
			</div>
		</div>
	</div>
</aui:form>