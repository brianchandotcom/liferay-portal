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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%@ include file="/html/portlet/layouts_admin/init_attributes.jspf" %>

<liferay-portlet:renderURL varImpl="redirectURL">
	<portlet:param name="struts_action" value="/layouts_admin/update_layout" />
	<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
</liferay-portlet:renderURL>

<div id="<portlet:namespace />editLayoutContainer">
	<button class="close pull-right" id="closePanel" type="button">&times;</button>

	<h1><%= LanguageUtil.get(pageContext, "edit-page") %></h1>

	<liferay-util:include page="/html/portlet/layouts_admin/edit_layout.jsp">
		<liferay-util:param name="displayStyle" value="panel" />
		<liferay-util:param name="showAddAction" value="<%= Boolean.FALSE.toString() %>" />
	</liferay-util:include>

	<c:if test="<%= themeDisplay.isShowSiteAdministrationIcon() %>">
		<liferay-portlet:renderURL plid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" portletName="<%= PortletKeys.GROUP_PAGES %>" varImpl="siteAdministrationURL" windowState="<%= WindowState.NORMAL.toString() %>">
			<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
			<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
			<portlet:param name="struts_action" value="/group_pages/edit_layouts" />
			<portlet:param name="tabs1" value="public-pages" />
			<portlet:param name="treeId" value="layoutsTree" />
			<portlet:param name="viewLayout" value="true" />
		</liferay-portlet:renderURL>

		<%
		String siteAdministrationURLString = HttpUtil.setParameter(siteAdministrationURL.toString(), "controlPanelCategory", "current_site");

		siteAdministrationURLString = HttpUtil.setParameter(siteAdministrationURLString, "doAsGroupId", String.valueOf(liveGroupId));
		siteAdministrationURLString = HttpUtil.setParameter(siteAdministrationURLString, "refererPlid", String.valueOf(selPlid));
		%>

		<aui:a cssClass="site-admin-link" href="<%= siteAdministrationURLString %>" label="site-administration" />
	</c:if>
</div>

<c:if test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>
	<aui:script>
		window.location.href = themeDisplay.getLayoutURL();
	</aui:script>
</c:if>

<aui:script use="aui-io-request,aui-loading-mask-deprecated,liferay-dockbar">
	A.one('#closePanel').on('click', Liferay.Dockbar.toggleEditLayoutPanel, Liferay.Dockbar);

	var BODY = A.getBody();
	BODY.plug(A.LoadingMask);

	Liferay.once(
		'submitForm',
		function(event) {
			event.preventDefault();

			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", selPlid) %>';

			BODY.loadingmask.show();

			A.io.request(
				event.form.get('action'),
				{
					dataType: 'json',
					form: {
						id: event.form.get('id')
					},
					after: {
						success: function(event, id, obj) {
							var response = this.get('responseData');

							var panel = A.one('#<portlet:namespace />editLayoutContainer');

							panel.empty();

							panel.plug(A.Plugin.ParseContent);

							panel.setContent(response);

							BODY.loadingmask.hide();
						},
						failure: function(event) {
							BODY.loadingMask.hide();
						}
					}
				}
			);
		}
	);
</aui:script>