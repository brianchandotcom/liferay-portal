<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/aui/nav_bar/init.jsp" %>

<c:if test="<%= Validator.isContent(bodyContentString) %>">
	<div class="navbar <%= cssClass %>" id="<%= id %>" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
		<div class="container-fluid container-fluid-max-xl">
			<c:if test="<%= Validator.isNotNull(dataTarget) %>">
				<button class="<%= (navItemCount.getValue() > 1) ? "collapsed" : StringPool.BLANK %> navbar-toggler navbar-toggler-link" data-target="<%= (navItemCount.getValue() > 1) ? "#" + dataTarget + "NavbarCollapse" : StringPool.BLANK %>" data-toggle="<%= (navItemCount.getValue() > 1) ? "collapse" : StringPool.BLANK %>" id="<%= dataTarget %>NavbarBtn" type="button">
					<span class="c-inner" tabindex="-1">
						<span class="sr-only"><liferay-ui:message key="toggle-navigation" /></span>

						<span class="navbar-text-truncate page-name"><liferay-ui:message key="<%= selectedItemName %>" /></span>

						<aui:icon image="caret-bottom" markupView="lexicon" />
					</span>
				</button>
			</c:if>

			<%= bodyContentString %>
		</div>
	</div>
</c:if>