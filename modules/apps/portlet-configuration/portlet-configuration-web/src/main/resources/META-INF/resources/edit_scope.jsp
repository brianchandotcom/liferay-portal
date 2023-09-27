<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<liferay-portlet:actionURL name="editScope" var="setScopeURL">
	<portlet:param name="mvcPath" value="/edit_scope.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletResource" value="<%= portletResource %>" />
	<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
</liferay-portlet:actionURL>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="scope" />
</liferay-util:include>

<div class="cadmin portlet-configuration-edit-scope">
	<liferay-frontend:edit-form
		action="<%= setScopeURL %>"
		cssClass="form"
	>
		<liferay-frontend:edit-form-body>
			<liferay-frontend:fieldset>
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
					%>

						<aui:option label="<%= HtmlUtil.escape(availableGroup.getDescriptiveName(locale)) %>" selected="<%= (group != null) && (group.getGroupId() == availableGroup.getGroupId()) %>" value='<%= availableGroupScopeType + "," + availableGroupScopeLayoutUuid %>' />

					<%
					}
					%>

					<c:if test="<%= !layout.hasScopeGroup() %>">
						<aui:option label='<%= HtmlUtil.escape(layout.getName(locale)) + " (" + LanguageUtil.get(request, "create-new") + ")" %>' value='<%= "layout," + layout.getUuid() %>' />
					</c:if>
				</aui:select>
			</liferay-frontend:fieldset>
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<liferay-frontend:edit-form-buttons />
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</div>