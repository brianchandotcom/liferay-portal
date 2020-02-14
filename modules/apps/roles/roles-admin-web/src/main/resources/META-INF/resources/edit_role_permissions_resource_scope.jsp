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

			<liferay-ui:message key="all-sites" />

		<%
		}
		%>

	</span>

	<%
	String targetId = StringUtil.replace(target, '.', "");
	%>

	<c:if test="<%= supportsFilterByGroup %>">
		<liferay-ui:icon
			icon="pencil"
			id="<%= HtmlUtil.escapeAttribute(targetId) %>"
			label="<%= true %>"
			markupView="lexicon"
			message="change"
			url="javascript:;"
		/>

		<aui:script sandbox="<%= true %>">
			var targetNode = document.getElementById(
				'<portlet:namespace /><%= HtmlUtil.escapeJS(targetId) %>'
			);

			if (targetNode) {
				targetNode.addEventListener('click', function(event) {
					var selectedGroupIds = [];

					var selectedGroupIdsNode = document.getElementById(
						'<portlet:namespace />groupIds<%= HtmlUtil.escapeJS(targetId) %>'
					);

					if (selectedGroupIdsNode && selectedGroupIdsNode.value) {
						selectedGroupIds = selectedGroupIdsNode.value.split(',');
					}

					Liferay.Util.selectEntity({
						dialog: {
							constrain: true,
							modal: true,
							width: 600
						},
						id:
							'<portlet:namespace />selectGroup<%= HtmlUtil.escapeJS(targetId) %>',
						selectedData: selectedGroupIds,
						title: '<liferay-ui:message arguments="site" key="select-x" />',

						<%
						ItemSelector itemSelector = (ItemSelector)request.getAttribute(RolesAdminWebKeys.ITEM_SELECTOR);

						GroupItemSelectorCriterion groupItemSelectorCriterion = new GroupItemSelectorCriterion();

						groupItemSelectorCriterion.setAllowNavigation(false);
						groupItemSelectorCriterion.setDesiredItemSelectorReturnTypes(new URLItemSelectorReturnType());
						groupItemSelectorCriterion.setIncludeUserPersonalSite(true);
						groupItemSelectorCriterion.setTarget(target);

						PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(liferayPortletRequest), liferayPortletResponse.getNamespace() + "selectGroup", groupItemSelectorCriterion);
						%>

						uri: '<%= itemSelectorURL.toString() %>'
					});
				});
			}
		</aui:script>
	</c:if>
</div>