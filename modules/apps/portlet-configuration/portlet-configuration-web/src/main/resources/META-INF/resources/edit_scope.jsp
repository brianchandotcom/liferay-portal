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
String scopeType = GetterUtil.getString(portletPreferences.getValue("lfrScopeType", null));
String scopeLayoutUuid = GetterUtil.getString(portletPreferences.getValue("lfrScopeLayoutUuid", null));

Group group = null;

if (Validator.isNull(scopeType)) {
	group = themeDisplay.getSiteGroup();
}
else if (scopeType.equals("company")) {
	group = GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyGroupId());
}
else if (scopeType.equals("layout")) {
	for (Layout scopeGroupLayout : LayoutLocalServiceUtil.getScopeGroupLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
		if (scopeLayoutUuid.equals(scopeGroupLayout.getUuid())) {
			group = GroupLocalServiceUtil.getLayoutGroup(scopeGroupLayout.getCompanyId(), scopeGroupLayout.getPlid());

			break;
		}
	}

	if (group == null) {
		group = themeDisplay.getSiteGroup();
	}
}

Set<Group> availableGroups = new LinkedHashSet<Group>();

availableGroups.add(group);
availableGroups.add(themeDisplay.getSiteGroup());
availableGroups.add(company.getGroup());

for (Layout scopeGroupLayout : LayoutLocalServiceUtil.getScopeGroupLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
	availableGroups.add(scopeGroupLayout.getScopeGroup());
}
%>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="scope" />
</liferay-util:include>

<liferay-portlet:actionURL name="editScope" var="setScopeURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletResource" value="<%= portletResource %>" />
	<portlet:param name="mvcPath" value="/edit_scope.jsp" />
	<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
</liferay-portlet:actionURL>

<aui:form action="<%= setScopeURL %>" cssClass="container-fluid-1280">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:select label="scope" name="scope">

				<%
				for (Group availableGroup : availableGroups) {
					String availableGroupScopeType = StringPool.BLANK;
					String availableGroupScopeLayoutUuid = StringPool.BLANK;

					if (availableGroup.isCompany()) {
						availableGroupScopeType = "company";
					}
					else if (availableGroup.isLayout()) {
						availableGroupScopeType = "layout";

						Layout availableGroupLayout = LayoutLocalServiceUtil.getLayout(availableGroup.getClassPK());

						availableGroupScopeLayoutUuid = availableGroupLayout.getUuid();
					}

					String value = availableGroupScopeType + "," + availableGroupScopeLayoutUuid;
				%>

					<aui:option label="<%= HtmlUtil.escape(availableGroup.getDescriptiveName(locale)) %>" value="<%= value %>" />

				<%
				}
				%>

				<c:if test="<%= !layout.hasScopeGroup() %>">
					<aui:option label='<%= HtmlUtil.escape(layout.getName(locale)) + " (" + LanguageUtil.get(request, "create-new") + ")" %>' value='<%= "layout," + layout.getUuid() %>' />
				</c:if>
			</aui:select>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>