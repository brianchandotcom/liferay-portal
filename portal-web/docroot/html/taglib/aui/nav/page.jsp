<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/aui/nav/init.jsp" %>

<c:if test="<%= Validator.isContent(bodyContentString) %>">
	<c:if test="<%= collapsible %>">
		<div class="collapse navbar-collapse" id="<%= id %>NavbarCollapse">
	</c:if>

	<ul aria-label="<%= Validator.isNull(ariaLabel) ? HtmlUtil.escapeAttribute(portletDisplay.getTitle()) : ariaLabel %>" class="lfr-nav nav <%= cssClass %>" id="<%= id %>" role="<%= Validator.isNull(ariaRole) ? "menubar" : ariaRole %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
		<%= bodyContentString %>
	</ul>

	<c:if test="<%= collapsible %>">
		</div>

		<aui:script use="aui-base,event-outside,liferay-menu-toggle">
			var toggleMenu = new Liferay.MenuToggle(
				{
					content: '#<%= id %>NavbarCollapse, #<%= id %>NavbarBtn',
					toggleTouch: true,
					trigger: '#<%= id %>NavbarBtn'
				}
			);
		</aui:script>
	</c:if>
</c:if>