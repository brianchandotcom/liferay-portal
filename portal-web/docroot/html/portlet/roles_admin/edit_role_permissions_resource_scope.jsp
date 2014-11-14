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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

Role role = (Role)objArray[0];
String target = (String)objArray[3];
Boolean supportsFilterByGroup = (Boolean)objArray[5];
List groups = (List)objArray[6];
long[] groupIdsArray = (long[])objArray[7];
List groupNames = (List)objArray[8];
%>

<aui:input name='<%= "groupIds" + HtmlUtil.escapeAttribute(target) %>' type="hidden" value="<%= StringUtil.merge(groupIdsArray) %>" />
<aui:input name='<%= "groupNames" + HtmlUtil.escapeAttribute(target) %>' type="hidden" value='<%= StringUtil.merge(groupNames, "@@") %>' />

<div id="<portlet:namespace />groupDiv<%= HtmlUtil.escapeAttribute(target) %>">
	<span class="permission-scopes" id="<portlet:namespace />groupHTML<%= HtmlUtil.escapeAttribute(target) %>">

		<%
		if (supportsFilterByGroup && !groups.isEmpty()) {
			for (int i = 0; i < groups.size(); i++) {
				Group group = (Group)groups.get(i);

				String taglibHREF = "javascript:" + liferayPortletResponse.getNamespace() + "removeGroup(" + i + ", '" + HtmlUtil.escapeJS(target) + "');";
		%>

				<span class="lfr-token">
					<span class="lfr-token-text"><%= HtmlUtil.escape(group.getDescriptiveName(locale)) %></span>

					<aui:a cssClass="icon icon-remove lfr-token-close" href="<%= taglibHREF %>" />
				</span>

		<%
			}
		}
		else if (role.getType() == RoleConstants.TYPE_REGULAR) {
		%>

			<%= LanguageUtil.get(request, "all-sites") %>

		<%
		}
		%>

	</span>

	<%
	String targetId = target.replace(".", "");
	%>

	<c:if test="<%= supportsFilterByGroup %>">
		<liferay-ui:icon
			iconCssClass="icon-cog"
			id="<%= HtmlUtil.escapeAttribute(targetId) %>"
			label="<%= true %>"
			message="change"
			url="javascript:;"
		/>

		<aui:script>
			AUI.$('#<portlet:namespace /><%= HtmlUtil.escapeJS(targetId) %>').on(
				'click',
				function(event) {
					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								modal: true,
								width: 600
							},
							id: '<portlet:namespace />selectGroup<%= HtmlUtil.escapeJS(targetId) %>',
							title: '<liferay-ui:message arguments="site" key="select-x" />',

							<portlet:renderURL var="selectCommunityURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="struts_action" value="/roles_admin/select_site" />
								<portlet:param name="includeCompany" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="includeUserPersonalSite" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="target" value="<%= target %>" />
							</portlet:renderURL>

							uri: '<%= selectCommunityURL.toString() %>'
						}
					);
				}
			);
		</aui:script>
	</c:if>
</div>