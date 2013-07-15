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

<div id="<portlet:namespace />editLayoutPanel">
	<button class="close pull-right" id="closePanel" type="button">&times;</button>

	<h1><%= LanguageUtil.get(pageContext, "edit-page") %></h1>

	<liferay-util:include page="/html/portlet/layouts_admin/edit_layout.jsp">
		<liferay-util:param name="displayStyle" value="panel" />
		<liferay-util:param name="showToolbar" value="<%= Boolean.FALSE.toString() %>" />
	</liferay-util:include>
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

							var panel = A.one('#<portlet:namespace />editLayoutPanel');

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