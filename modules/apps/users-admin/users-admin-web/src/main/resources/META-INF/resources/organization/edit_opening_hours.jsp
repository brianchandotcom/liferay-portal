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
long orgLaborId = ParamUtil.getLong(request, "primaryKey", 0L);

OrgLabor orgLabor = null;

if (orgLaborId > 0L) {
	orgLabor = OrgLaborServiceUtil.getOrgLabor(orgLaborId);
}
%>

<aui:form cssClass="modal-body" name="fm">
	<aui:model-context bean="<%= orgLabor %>" model="<%= OrgLabor.class %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= orgLaborId %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.ORGANIZATION_SERVICE %>" />

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

</aui:form>