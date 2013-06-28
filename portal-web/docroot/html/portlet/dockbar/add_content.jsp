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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<%
int deltaDefault = GetterUtil.getInteger(SessionClicks.get(request, "liferay_addpanel_numitems", "10"));

int delta = ParamUtil.getInteger(request, "delta", deltaDefault);
%>

<portlet:resourceURL var="updateContentListURL">
	<portlet:param name="struts_action" value="/dockbar/view" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:resourceURL>

<aui:form action="<%= updateContentListURL %>" name="addContentForm" onSubmit="event.preventDefault();">
	<div class="row-fluid">
		<div class="btn-toolbar">
			<aui:input cssClass="input-small search-query" inlineField="<%= true %>" label="" name="searchContent" type="text" />

			<%
			String displayStyleDefault = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_displaystyle", "descriptive"));

			String displayStyle = ParamUtil.getString(request, "displayStyle", displayStyleDefault);
			%>

			<div class="btn-group" id="<portlet:namespace />styleButtons">
				<aui:button cssClass='<%= displayStyle.equals("icon") ? "active" : StringPool.BLANK %>' data-style="icon" icon="icon-th-large" />

				<aui:button cssClass='<%= displayStyle.equals("descriptive") ? "active" : StringPool.BLANK %>' data-style="descriptive" icon="icon-th-list" />

				<aui:button cssClass='<%= displayStyle.equals("list") ? "active" : StringPool.BLANK %>' data-style="list" icon="icon-list" />
			</div>

			<aui:select cssClass="input-mini" inlineField="<%= true %>" label="" name="numItems">

				<%
				for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
					if (curDelta > SearchContainer.MAX_DELTA) {
						continue;
					}
				%>

					<aui:option label="<%= curDelta %>" selected="<%= delta == curDelta %>" />

				<%
				}
				%>

			</aui:select>
		</div>

		<span class="add-content-button">

			<%
			long groupId = scopeGroupId;

			long[] groupIds = new long[] {scopeGroupId};

			boolean defaultAssetPublisher = false;

			PortletURL redirectURL = liferayPortletResponse.createLiferayPortletURL(themeDisplay.getPlid(), portletDisplay.getId(), PortletRequest.RENDER_PHASE, false);

			redirectURL.setParameter("struts_action", "/dockbar/add_content_redirect");
			redirectURL.setWindowState(LiferayWindowState.POP_UP);

			Map<String, PortletURL> addPortletURLs = AssetUtil.getAddPortletURLs(liferayPortletRequest, liferayPortletResponse, AssetRendererFactoryRegistryUtil.getClassNameIds(company.getCompanyId()), new long[0], new long[0], new String[0], redirectURL.toString());
			%>

			<%@ include file="/html/portlet/asset_publisher/add_asset.jspf" %>
		</span>

		<div id="<portlet:namespace />entriesContainer">
			<liferay-util:include page="/html/portlet/dockbar/view_resources.jsp" />
		</div>
	</div>
</aui:form>

<aui:script use="liferay-dockbar-portlet-dd,liferay-dockbar-add-content">
	var searchContent = A.one('#<portlet:namespace />searchContent');

	var addContent = new Liferay.Dockbar.AddContent(
		{
			focusItem: searchContent,
			inputNode: searchContent,
			namespace: '<portlet:namespace />',
			selected: !A.one('#<portlet:namespace />addContentForm').ancestor().hasClass('hide')
		}
	);

	addContent.plug(
		Liferay.Dockbar.PortletDragDrop,
		{
			on: {
				dragEnd: function(event) {
					addContent._addPortlet(
						event.portletNode,
						{
							item: event.appendNode
						}
					);
				}
			},
			srcNode: '#<portlet:namespace />entriesContainer'
		}
	);
</aui:script>