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

<%@ include file="/html/taglib/aui/button/init.jsp" %>

<button class="<%= AUIUtil.buildCss(AUIUtil.BUTTON_PREFIX, disabled, false, false, cssClass) %>" <%= disabled ? "disabled" : StringPool.BLANK %><aui:spacer /><%= Validator.isNotNull(name) ? "id=\"" + namespace + name + "\"" : StringPool.BLANK %><aui:spacer /><%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> type='<%= type.equals("cancel") ? "button" : type %>' <%= AUIUtil.buildData(data) %><aui:spacer /><%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
	<c:if test='<%= Validator.isNotNull(icon) && iconAlign.equals("left") %>'>
		<i class="<%= icon %>"></i>
	</c:if>

	<%= LanguageUtil.get(pageContext, value) %>

	<c:if test='<%= Validator.isNotNull(icon) && iconAlign.equals("right") %>'>
		<i class="<%= icon %>"></i>
	</c:if>
</button>