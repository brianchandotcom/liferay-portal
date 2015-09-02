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

<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE) %>">
	<div class="alert alert-info hide" id="<portlet:namespace />nested-portlets-msg">
		<liferay-ui:message key="drag-portlets-below-to-nest-them" />
	</div>

	<aui:script sandbox="<%= true %>">
		var portletWrapper = $('#p_p_id_<%= portletDisplay.getId() %>_');

		if (portletWrapper.length) {
			var message = portletWrapper.find('#<portlet:namespace />nested-portlets-msg');

			var nestedPortlet = portletWrapper.find('.portlet-boundary, .portlet-borderless-container');

			if (!nestedPortlet.length) {
				message.removeClass('hide');
			}
		}
	</aui:script>
</c:if>

<%
try {
	String templateId = (String)request.getAttribute(NestedPortletsConfiguration.TEMPLATE_ID + portletDisplay.getId());
	String templateContent = (String)request.getAttribute(NestedPortletsConfiguration.TEMPLATE_CONTENT + portletDisplay.getId());

	if (Validator.isNotNull(templateId) && Validator.isNotNull(templateContent)) {
		RuntimePageUtil.processTemplate(request, response, new StringTemplateResource(templateId, templateContent));
	}
}
catch (Exception e) {
	_log.error("Cannot render Nested Portlets portlet", e);
}
finally {
	liferayPortletRequest.defineObjects(portletConfig, renderResponse);
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.nested_portlets.view_jsp");
%>