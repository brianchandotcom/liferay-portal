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

String target = (String)objArray[3];
long[] groupIdsArray = (long[])objArray[7];
List<String> groupNames = (List<String>)objArray[8];
String portletId = (String)objArray[9];
%>

<aui:input name='<%= "groupIds" + HtmlUtil.escapeAttribute(target) %>' type="hidden" value="<%= StringUtil.merge(groupIdsArray) %>" />
<aui:input name='<%= "groupNames" + HtmlUtil.escapeAttribute(target) %>' type="hidden" value='<%= StringUtil.merge(groupNames, "@@") %>' />

<div id="<portlet:namespace />groupDiv<%= HtmlUtil.escapeAttribute(target) %>">
	<span id="<portlet:namespace />groupHTML<%= HtmlUtil.escapeAttribute(target) %>">
		<c:choose>
			<c:when test="<%= (Boolean)objArray[5] %>">

				<%
				ItemSelector itemSelector = (ItemSelector)request.getAttribute(RolesAdminWebKeys.ITEM_SELECTOR);

				GroupItemSelectorCriterion groupItemSelectorCriterion = new GroupItemSelectorCriterion();

				groupItemSelectorCriterion.setAllowNavigation(false);
				groupItemSelectorCriterion.setDesiredItemSelectorReturnTypes(new URLItemSelectorReturnType());
				groupItemSelectorCriterion.setIncludeAllVisibleGroups(true);
				groupItemSelectorCriterion.setIncludeFormsSite(true);
				groupItemSelectorCriterion.setIncludeUserPersonalSite(true);
				groupItemSelectorCriterion.setPortletId(portletId);
				groupItemSelectorCriterion.setTarget(target);

				PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(liferayPortletRequest), liferayPortletResponse.getNamespace() + "selectGroup", groupItemSelectorCriterion);
				%>

				<react:component
					module="js/GroupLabels.es"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"itemSelectorURL", itemSelectorURL.toString()
						).put(
							"target", target
						).build()
					%>'
				/>
			</c:when>
			<c:when test="<%= roleDisplayContext.isAllowGroupScope() %>">
				<liferay-ui:message key="all-sites-and-asset-libraries" />
			</c:when>
		</c:choose>
	</span>
</div>