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
int deltaDefault = GetterUtil.getInteger(SessionClicks.get(request, "com.liferay.control.menu.web_addPanelNumItems", "10"));

int delta = ParamUtil.getInteger(request, "delta", deltaDefault);

String displayStyleDefault = GetterUtil.getString(SessionClicks.get(request, "com.liferay.control.menu.web_addPanelDisplayStyle", "descriptive"));

String displayStyle = ParamUtil.getString(request, "displayStyle", displayStyleDefault);
%>

<portlet:resourceURL var="updateContentListURL">
	<portlet:param name="mvcPath" value="/view_resources.jsp" />
</portlet:resourceURL>

<aui:form action="<%= updateContentListURL %>" name="addContentForm" onSubmit="event.preventDefault();">
	<div class="input-group search-bar">
		<input class="form-control" id="<portlet:namespace />searchContent" name="<portlet:namespace />searchContent" placeholder='<%= LanguageUtil.get(request, "search") + StringPool.TRIPLE_PERIOD %>' type="text" />

		<span class="input-group-btn">
			<liferay-ui:icon icon="search" markupView="lexicon" />
		</span>
	</div>

	<div class="display-style-bar">
		<span class="dropdown" id="<portlet:namespace />numItems">
			<a aria-expanded="true" class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
				<span class="item-title"><%= delta %></span>
				<span class="icon-sort"></span>
			</a>

			<ul class="dropdown-menu">

				<%
				for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
					if (curDelta > SearchContainer.MAX_DELTA) {
						continue;
					}

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("delta", curDelta);
				%>

					<li class="num-item <%= (delta == curDelta) ? "active" : StringPool.BLANK %>">
						<aui:a cssClass="num-item" data="<%= data %>" href="javascript:;" label="<%= String.valueOf(curDelta) %>" />
					</li>

				<%
				}
				%>

			</ul>
		</span>

		<span class="pull-right">

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("displaystyle", "icon");
			%>

			<liferay-ui:icon
				data="<%= data %>"
				icon="cards2"
				linkCssClass='<%= displayStyle.equals("icon") ? "display-style active" : "display-style" %>'
				markupView="lexicon"
				url="javascript:;"
			/>

			<%
			data.put("displaystyle", "descriptive");
			%>

			<liferay-ui:icon
				data="<%= data %>"
				icon="list"
				linkCssClass='<%= displayStyle.equals("descriptive") ? "display-style active" : "display-style" %>'
				markupView="lexicon"
				url="javascript:;"
			/>
		</span>
	</div>

	<div class="add-content-button">

		<%
		PortletURL redirectURL = PortletURLFactoryUtil.create(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

		redirectURL.setParameter("mvcPath", "/add_content_redirect.jsp");
		redirectURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<liferay-ui:asset-add-button
			redirect="<%= redirectURL.toString() %>"
		/>
	</div>

	<div id="<portlet:namespace />entriesContainer">
		<liferay-util:include page="/view_resources.jsp" servletContext="<%= application %>" />
	</div>
</aui:form>

<aui:script use="liferay-control-menu-add-content">
	var ControlMenu = Liferay.ControlMenu;

	var searchContent = A.one('#<portlet:namespace />searchContent');

	var addContent = new ControlMenu.AddContent(
		{
			delta: '<%= delta %>',
			displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',
			focusItem: searchContent,
			inputNode: searchContent,
			namespace: '<portlet:namespace />',
			selected: !A.one('#<portlet:namespace />addContentForm').ancestor().hasClass('hide')
		}
	);

	if (ControlMenu.PortletDragDrop) {
		addContent.plug(
			ControlMenu.PortletDragDrop,
			{
				on: {
					dragEnd: function(event) {
						addContent.addPortlet(
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
	}

	Liferay.component('<portlet:namespace />addContent', addContent);
</aui:script>