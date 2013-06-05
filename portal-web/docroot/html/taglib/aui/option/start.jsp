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

<%@ include file="/html/taglib/aui/option/init.jsp" %>

<option class="<%= cssClass %>" <%= disabled ? "disabled" : StringPool.BLANK %><aui:spacer /><%= selected ? "selected" : StringPool.BLANK %><aui:spacer /><%= Validator.isNotNull(style) ? "style=\"" + style + "\"" : StringPool.BLANK %> value="<%= (value != null) ? String.valueOf(value) : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %><aui:spacer /><%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>

<c:if test="<%= Validator.isNotNull(label) %>">
	<liferay-ui:message key="<%= String.valueOf(label) %>" />
</c:if>