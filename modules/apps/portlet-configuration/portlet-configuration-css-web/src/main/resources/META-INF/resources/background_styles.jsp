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

<liferay-util:include page="/color_picker_input.jsp" servletContext="<%= application %>">
	<liferay-util:param name="color" value="<%= portletConfigurationCSSPortletDisplayContext.getBackgroundColor() %>" />
	<liferay-util:param name="label" value='<%= LanguageUtil.get(request, "background-color") %>' />
	<liferay-util:param name="name" value='<%= renderResponse.getNamespace() + "backgroundColor" %>' />
</liferay-util:include>