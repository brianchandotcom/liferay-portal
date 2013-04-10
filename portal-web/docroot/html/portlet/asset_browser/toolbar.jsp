<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_browser/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "browse");

long groupId = ParamUtil.getLong(request, "groupId");
String typeSelection = ParamUtil.getString(request, "typeSelection");

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);

Map<Long, String> classTypes = assetRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), themeDisplay.getScopeGroupId()}, locale);
%>

<div class="lfr-portlet-toolbar">
	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("browse") ? "current" : StringPool.BLANK %>">
		<a href="<%= portletURL %>"><liferay-ui:message key="browse" /></a>
	</span>

	<c:choose>
		<c:when test="<%= classTypes.isEmpty() %>">

			<%
			PortletURL addPortletURL = AssetUtil.getAddPortletURL(liferayPortletRequest, liferayPortletResponse, typeSelection, 0, null, null, portletURL.toString());
			%>

			<c:if test="<%= addPortletURL != null %>">

				<%
				addPortletURL.setParameter("groupId", String.valueOf(groupId));

				String addPortletURLString = addPortletURL.toString();

				addPortletURLString = HttpUtil.addParameter(addPortletURLString, "doAsGroupId", groupId);
				addPortletURLString = HttpUtil.addParameter(addPortletURLString, "refererPlid", plid);
				%>

				<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add") ? "current" : StringPool.BLANK %>">
					<a href="<%= addPortletURLString %>"><liferay-ui:message arguments="<%= assetRendererFactory.getTypeName(locale, false) %>" key="add-x" /></a>
				</span>
			</c:if>
		</c:when>
		<c:otherwise>
			<liferay-ui:icon-menu align="left" cssClass='<%= "lfr-toolbar-button add-button " + (toolbarItem.equals("add") ? "current" : StringPool.BLANK) %>' direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message="add">

				<%
				PortletURL addPortletURL = AssetUtil.getAddPortletURL(liferayPortletRequest, liferayPortletResponse, typeSelection, 0, null, null, portletURL.toString());
				%>

				<c:if test="<%= addPortletURL != null %>">

					<%
					addPortletURL.setParameter("groupId", String.valueOf(groupId));

					String addPortletURLString = addPortletURL.toString();

					addPortletURLString = HttpUtil.addParameter(addPortletURLString, "doAsGroupId", groupId);
					addPortletURLString = HttpUtil.addParameter(addPortletURLString, "refererPlid", plid);
					%>

					<liferay-ui:icon
						message="<%= assetRendererFactory.getTypeName(locale, true) %>"
						method="get"
						src="<%= assetRendererFactory.getIconPath(renderRequest) %>"
						url="<%= addPortletURLString %>"
					/>
				</c:if>

				<%
				for (long classTypeId : classTypes.keySet()) {
					addPortletURL = AssetUtil.getAddPortletURL(liferayPortletRequest, liferayPortletResponse, typeSelection, classTypeId, null, null, portletURL.toString());

					if (addPortletURL == null) {
						continue;
					}

					addPortletURL.setParameter("groupId", String.valueOf(groupId));

					String addPortletURLString = addPortletURL.toString();

					addPortletURLString = HttpUtil.addParameter(addPortletURLString, "doAsGroupId", groupId);
					addPortletURLString = HttpUtil.addParameter(addPortletURLString, "refererPlid", plid);
				%>

					<liferay-ui:icon
						message="<%= HtmlUtil.escape(classTypes.get(classTypeId)) %>"
						method="get"
						src="<%= assetRendererFactory.getIconPath(renderRequest) %>"
						url="<%= addPortletURLString %>"
					/>

				<%
				}
				%>

			</liferay-ui:icon-menu>
		</c:otherwise>
	</c:choose>
</div>