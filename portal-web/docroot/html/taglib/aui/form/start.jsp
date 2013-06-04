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

<%@ include file="/html/taglib/aui/form/init.jsp" %>

<form action="<%= HtmlUtil.escape(action) %>" class="form <%= cssClass %><aui:spacer /><%= inlineLabels ? "field-labels-inline" : StringPool.BLANK %>" id="<%= namespace + name %>" method="<%= method %>" name="<%= namespace + name %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
	<input name="<%= namespace %>formDate" type="hidden" value="<%= System.currentTimeMillis() %>" />