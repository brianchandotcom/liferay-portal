<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCompareContentMiniDisplayContext cpCompareContentMiniDisplayContext = (CPCompareContentMiniDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:fieldset markupView="lexicon">
	<div class="display-template">
		<liferay-template:template-selector
			className="<%= CPCompareContentMiniPortlet.class.getName() %>"
			displayStyle="<%= cpCompareContentMiniDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= cpCompareContentMiniDisplayContext.getDisplayStyleGroupId() %>"
			refreshURL="<%= PortalUtil.getCurrentURL(request) %>"
			showEmptyOption="<%= true %>"
		/>
	</div>
</aui:fieldset>