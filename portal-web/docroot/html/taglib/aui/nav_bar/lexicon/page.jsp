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

<%@ include file="/html/taglib/aui/nav_bar/init.jsp" %>

<c:if test="<%= Validator.isContent(bodyContentString) %>">
	<div class="navbar <%= cssClass %>" id="<%= id %>" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
		<div class="container-fluid container-fluid-max-xl">
			<c:if test="<%= Validator.isNotNull(dataTarget) %>">
				<button class="<%= (navItemCount.getValue() > 1) ? "collapsed" : StringPool.BLANK %> navbar-toggler navbar-toggler-link" data-target="<%= (navItemCount.getValue() > 1) ? "#" + dataTarget + "NavbarCollapse" : StringPool.BLANK %>" data-toggle="<%= (navItemCount.getValue() > 1) ? "collapse" : StringPool.BLANK %>" id="<%= dataTarget %>NavbarBtn" type="button">
					<span class="c-inner" tabindex="-1">
						<span class="sr-only"><liferay-ui:message key="toggle-navigation" /></span>

						<span class="navbar-text-truncate page-name"><%= LanguageUtil.get(resourceBundle, selectedItemName) %></span>

						<aui:icon image="caret-bottom" markupView="lexicon" />
					</span>
				</button>
			</c:if>

			<%= bodyContentString %>
		</div>
	</div>
</c:if>