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

<%@ include file="/html/taglib/aui/button/init.jsp" %>

<c:if test="<%= dropdown %>">
	<div class="btn-group dropdown" id="<%= id %>BtnGroup">
</c:if>

<c:choose>
	<c:when test="<%= Validator.isNotNull(escapedHREF) %>">
		<a
			class="<%= AUIUtil.buildCss(AUIUtil.BUTTON_PREFIX, disabled, false, false, cssClass) %> <%= type.equals("cancel") ? "btn-link" : "btn-default" %>"
			href="<%= escapedHREF %>"
			id="<%= id %>"

			<c:if test="<%= Validator.isNotNull(onClick) %>">
				onClick="<%= onClick %>"
			</c:if>

			<%= AUIUtil.buildData(data) %>
			<%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>
		>
	</c:when>
	<c:otherwise>
		<button
			class="<%= AUIUtil.buildCss(AUIUtil.BUTTON_PREFIX, disabled, false, false, cssClass) %> <%= type.equals("cancel") ? "btn-link" : "btn-default" %>"

			<c:if test="<%= disabled %>">
				disabled
			</c:if>

			id="<%= id %>"

			<c:if test="<%= Validator.isNotNull(name) %>">
				name="<%= namespace %><%= name %>"
			</c:if>

			<c:if test="<%= Validator.isNotNull(onClick) %>">
				onClick="<%= onClick %>"
			</c:if>

			type="<%= type.equals("cancel") ? "button" : type %>"

			<%= AUIUtil.buildData(data) %>
			<%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>
		>
	</c:otherwise>
</c:choose>

<c:if test='<%= Validator.isNotNull(icon) && iconAlign.equals("left") %>'>
	<i class="<%= icon %>"></i>
</c:if>

<c:if test="<%= Validator.isNotNull(value) %>">
	<span class="lfr-btn-label"><%= LanguageUtil.get(request, value) %></span>
</c:if>

<c:if test='<%= Validator.isNotNull(icon) && iconAlign.equals("right") %>'>
	<i class="<%= icon %>"></i>
</c:if>

<c:choose>
	<c:when test="<%= Validator.isNotNull(escapedHREF) %>">
		</a>
	</c:when>
	<c:otherwise>
		</button>
	</c:otherwise>
</c:choose>

<c:if test="<%= dropdown %>">
	<button aria-expanded="false" class="btn btn-primary dropdown-toggle <%= cssClass %>" data-toggle="dropdown" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>Toggle" type="button">
		<span class="caret"></span>
		<span class="sr-only"><liferay-ui:message key="toggle-dropdown" /></span>
	</button>
</c:if>

<c:if test="<%= dropdown %>">
		<ul class="dropdown-menu" role="menu">
			<%= bodyContentString %>
		</ul>
	</div>
</c:if>

<c:if test="<%= useDialog %>">
	<aui:script>
		Liferay.delegateClick('<%= namespace + name %>', Liferay.Util.openInDialog);
	</aui:script>
</c:if>