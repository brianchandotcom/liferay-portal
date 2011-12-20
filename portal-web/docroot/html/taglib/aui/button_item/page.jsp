<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
		<button class="aui-widget aui-component aui-buttonitem aui-buttonitem-content aui-state-default <%= _iconTypeClass %> <%= (String)cssClass %>" id="<%= uniqueId %>BoundingBox" type="button">
	</c:if>

	<span class="aui-buttonitem-icon aui-icon aui-icon-<%= icon %>"></span>

	<c:if test="<%= Validator.isNotNull(label) %>">
		<span class="aui-buttonitem-label">
			<%= label %>
		</span>
	</c:if>

	<c:if test="<%= !hasBoundingBox %>">
		</button>
	</c:if>
</c:if>

<aui:component
	excludeAttributes="var,javaScriptAttributes,useMarkup,useJavaScript"
	tagPageContext="<%= pageContext %>"
	options="<%= _options %>"
	module="aui-button-item"
	name="ButtonItem"
/>