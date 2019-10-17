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
		<liferay-ui:message key="drag-applications-below-to-nest-them" />
	</div>

	<aui:script require="metal-dom/src/dom">
		let dom = metalDomSrcDom.default;

		var portletWrapper = document.getElementById(
			'p_p_id_<%= portletDisplay.getId() %>_'
		);

		var portletBoundary = portletWrapper.querySelectorAll('.portlet-boundary');
		var portletBorderlessContainer = portletWrapper.querySelectorAll(
			'.portlet-borderless-container'
		);

		if (!portletBoundary.length && !portletBorderlessContainer.length) {
			var nestedPortletsMsg = portletWrapper.querySelector(
				'#<portlet:namespace />nested-portlets-msg'
			);

			if (nestedPortletsMsg) {
				dom.addClasses(nestedPortletsMsg, 'show');

				dom.removeClasses(nestedPortletsMsg, 'hide');
			}
		}
	</aui:script>
</c:if>

<%
try {
	String templateId = (String)request.getAttribute(NestedPortletsWebKeys.TEMPLATE_ID + portletDisplay.getId());
	String templateContent = (String)request.getAttribute(NestedPortletsWebKeys.TEMPLATE_CONTENT + portletDisplay.getId());

	if (Validator.isNotNull(templateId) && Validator.isNotNull(templateContent)) {
		RuntimePageUtil.processTemplate(nestedPortletsDisplayContext.getLastForwardRequest(), response, new StringTemplateResource(templateId, templateContent), TemplateConstants.LANG_TYPE_FTL);
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
private static Log _log = LogFactoryUtil.getLog("com_liferay_nested_portlets_web.view_jsp");
%>