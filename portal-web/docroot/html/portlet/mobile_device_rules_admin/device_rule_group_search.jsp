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

<%@ include file="/html/portlet/mobile_device_rules_admin/init.jsp" %>

<%
String chooseCallback = ParamUtil.getString(request, "chooseCallback");

RuleGroupSearch searchContainer = (RuleGroupSearch) request.getAttribute("liferay-ui:search:searchContainer");

RuleGroupDisplayTerms displayTerms = (RuleGroupDisplayTerms) searchContainer.getDisplayTerms();
RuleGroupSearchTerms searchTerms = (RuleGroupSearchTerms) searchContainer.getSearchTerms();

if (displayTerms.getGroupId() == 0) {
	displayTerms.setGroupId(groupId);
	searchTerms.setGroupId(groupId);
}
%>

<div class="aui-search-bar taglib-search-toggle">
	<aui:fieldset>
		<aui:input name="<%= displayTerms.NAME %>" label="name" inlineField="<%=true%>" size="20" type="text" value="<%= displayTerms.getName() %>"/>
		<%
		if (Validator.isNotNull(chooseCallback) && MDRPermissionUtil.contains(permissionChecker, globalGroupId, ActionKeys.VIEW)) {

			Group curGroup = GroupLocalServiceUtil.getGroup(groupId);
		%>
			<aui:select name="<%= displayTerms.GROUP_ID %>" label="scope" inlineField="<%=true%>">
				<aui:option label="global" value="<%= globalGroupId %>" selected="<%= displayTerms.getGroupId() == globalGroupId %>"/>
				<aui:option label="<%= curGroup.getName() %>" value="<%= groupId %>" selected="<%= displayTerms.getGroupId() == groupId %>"/>
			</aui:select>
		<%
		}
		else {
		%>
			<aui:input name="<%= displayTerms.GROUP_ID %>" type="hidden" value="<%= groupId %>"/>
		<%
		}
		%>
	</aui:fieldset>
	</br>
	<aui:button type="submit" value="search"/>
</div>
</br>