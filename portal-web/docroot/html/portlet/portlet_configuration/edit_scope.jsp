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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

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
	for (Layout scopeGroupLayout : LayoutLocalServiceUtil.getScopeGroupLayouts(layout.getGroupId())) {
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

for (Layout scopeGroupLayout : LayoutLocalServiceUtil.getScopeGroupLayouts(layout.getGroupId())) {
	availableGroups.add(scopeGroupLayout.getScopeGroup());
}
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="scope" />
</liferay-util:include>

<aui:fieldset>
	<aui:field-wrapper label="scope" name="scopeId">
		<liferay-ui:icon-menu direction="down" icon="<%= group.getIconURL(themeDisplay) %>" localizeMessage="<%= false %>" message="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>" showWhenSingleIcon="<%= true %>">

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
			%>

				<liferay-portlet:actionURL var="setScopeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="struts_action" value="/portlet_configuration/edit_scope" />
					<portlet:param name="scopeType" value="<%= availableGroupScopeType %>" />
					<portlet:param name="scopeLayoutUuid" value="<%= availableGroupScopeLayoutUuid %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="<%= availableGroup.getIconCssClass() %>"
					id='<%= "scope" + availableGroup.getGroupId() %>'
					localizeMessage="<%= false %>"
					message="<%= HtmlUtil.escape(availableGroup.getDescriptiveName(locale)) %>"
					method="post"
					onClick='<%= renderResponse.getNamespace() + "changeScope();" %>'
					url="<%= setScopeURL %>"
				/>

			<%
			}
			%>

			<c:if test="<%= !layout.hasScopeGroup() %>">
				<liferay-portlet:actionURL var="createNewScopeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="struts_action" value="/portlet_configuration/edit_scope" />
					<portlet:param name="scopeType" value="layout" />
					<portlet:param name="scopeLayoutUuid" value="<%= layout.getUuid() %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-plus"
					id="scopeCurLayout"
					message='<%= HtmlUtil.escape(layout.getName(locale)) + " (" + LanguageUtil.get(request, "create-new") + ")" %>'
					method="post"
					onClick='<%= renderResponse.getNamespace() + "changeScope();" %>'
					url="<%= createNewScopeURL %>"
				/>
			</c:if>
		</liferay-ui:icon-menu>
	</aui:field-wrapper>
</aui:fieldset>

<aui:script>
	function <portlet:namespace />changeScope() {
		Liferay.Util.getTop().Liferay.fire('changeScope');
	}
</aui:script>