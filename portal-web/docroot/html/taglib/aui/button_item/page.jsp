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

<%@ include file="/html/taglib/aui/button_item/init.jsp" %>

<c:if test="<%= useMarkup %>">
	<c:if test="<%= !hasBoundingBox %>">
		<button class="buttonitem buttonitem-content component widget <%= (iconTypeClass != null) ? iconTypeClass : StringPool.BLANK %> <%= cssClass %>" id="<%= uniqueId %>BoundingBox" type="button">
	</c:if>

	<c:if test="<%= Validator.isNotNull(icon) %>">
		<span class="buttonitem-icon icon icon-<%= icon %>"></span>
	</c:if>

	<c:if test="<%= Validator.isNotNull(label) %>">
		<span class="buttonitem-label">
			<%= label %>
		</span>
	</c:if>

	<c:if test="<%= !hasBoundingBox %>">
		</button>
	</c:if>
</c:if>

<aui:component
	excludeAttributes="javaScriptAttributes,useJavaScript,useMarkup,var"
	module="aui-button-item"
	name="ButtonItem"
	options="<%= _options %>"
	scriptPosition='<%= GetterUtil.getString(_options.get("scriptPosition")) %>'
	tagPageContext="<%= pageContext %>"
	useJavaScript='<%= GetterUtil.getBoolean(_options.get("useJavaScript"), true) %>'
	var='<%= GetterUtil.getString(_options.get("var")) %>'
/>