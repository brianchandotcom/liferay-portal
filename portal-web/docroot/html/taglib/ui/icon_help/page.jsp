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

<%@ include file="/html/taglib/ui/icon_help/init.jsp" %>

<span class="taglib-icon-help">
	<img alt="" aria-labelledby="<%= id %>" onBlur="Liferay.Portal.ToolTip.hide();" onFocus="Liferay.Portal.ToolTip.show(this);" onMouseOver="Liferay.Portal.ToolTip.show(this);" src="<%= themeDisplay.getPathThemeImages() %>/portlet/help.png" tabIndex="0" />

	<span class="hide-accessible tooltip-text" id="<%= id %>"><%= message %></span>
</span>