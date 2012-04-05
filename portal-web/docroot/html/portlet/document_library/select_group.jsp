<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<aui:form method="post" name="fm">
	<liferay-ui:header
		title="sites"
	/>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/document_library/select_group");
	%>

	<liferay-ui:search-container
		searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-form
			page="/html/portlet/users_admin/group_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<div class="separator"><!-- --></div>

		<%
		List<Group> mySites = user.getMySites();

		if (PortalUtil.isCompanyControlPanelPortlet(portletId, themeDisplay)) {
			mySites = ListUtil.copy(mySites);

			mySites.add(0, GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyGroupId()));
		}
		%>

		<liferay-ui:search-container-results
			results="<%= mySites %>"
			total="<%= mySites.size() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Group"
			escapedModel="<%= true %>"
			keyProperty="groupId"
			modelVar="group"
			rowIdProperty="friendlyURL"
		>

			<%
			String groupName = HtmlUtil.escape(group.getDescriptiveName(locale));

			if (group.isUser()) {
				groupName = LanguageUtil.get(pageContext, "my-site");
			}

			StringBundler sb = new StringBundler(7);

			sb.append("javascript:opener.");
			sb.append(renderResponse.getNamespace());
			sb.append("selectGroup('");
			sb.append(group.getGroupId());
			sb.append("', '");
			sb.append(UnicodeFormatter.toString(groupName));
			sb.append("'); window.close();");

			String rowHREF = sb.toString();
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="name"
				value="<%= groupName %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="type"
				value="<%= LanguageUtil.get(pageContext, group.getTypeLabel()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>