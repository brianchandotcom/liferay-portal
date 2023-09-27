<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:script>
	var nestedPortlet = document.getElementById(
		'_<%= portletDisplay.getId() %>__main-content'
	);

	if (nestedPortlet != null) {
		nestedPortlet.removeAttribute('role');
	}
</aui:script>

<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE) %>">
	<div class="alert alert-info hide" id="<portlet:namespace />nested-portlets-msg">
		<liferay-ui:message key="drag-applications-below-to-nest-them" />
	</div>

	<aui:script>
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
				nestedPortletsMsg.classList.replace('hide', 'show');
			}
		}
	</aui:script>
</c:if>

<%
try {
	String templateId = (String)request.getAttribute(NestedPortletsWebKeys.TEMPLATE_ID + portletDisplay.getId());
	String templateContent = (String)request.getAttribute(NestedPortletsWebKeys.TEMPLATE_CONTENT + portletDisplay.getId());

	if (Validator.isNotNull(templateId) && Validator.isNotNull(templateContent)) {
		RuntimePageUtil.processTemplate(nestedPortletsDisplayContext.getLastForwardHttpServletRequest(), response, null, templateId, templateContent, TemplateConstants.LANG_TYPE_FTL);
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
private static final Log _log = LogFactoryUtil.getLog("com_liferay_nested_portlets_web.view_jsp");
%>