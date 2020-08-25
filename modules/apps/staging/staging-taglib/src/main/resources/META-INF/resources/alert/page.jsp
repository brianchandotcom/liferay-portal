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

<%@ include file="/alert/init.jsp" %>

<liferay-util:buffer
	var="icon"
>
	<c:choose>
		<c:when test="<%= type == AlertType.ERROR.getAlertCode() %>">
			<svg aria-hidden="true" class="lexicon-icon lexicon-icon-exclamation-full">
				<use xlink:href="<%= themeDisplayPath %>/lexicon/icons.svg#exclamation-full" />
			</svg>

			<strong class="lead"><%= LanguageUtil.get(request, "alert-helper-error") %>: </strong>
		</c:when>
		<c:when test="<%= type == AlertType.INFO.getAlertCode() %>">
			<svg aria-hidden="true" class="lexicon-icon lexicon-icon-info-circle">
				<use xlink:href="<%= themeDisplayPath %>/lexicon/icons.svg#info-circle" />
			</svg>

			<strong class="lead"><%= LanguageUtil.get(request, "alert-helper-info") %>: </strong>
		</c:when>
		<c:when test="<%= type == AlertType.SUCCESS.getAlertCode() %>">
			<i class="icon-ok-sign"></i>

			<strong class="lead"><%= LanguageUtil.get(request, "alert-helper-success") %>: </strong>
		</c:when>
		<c:when test="<%= type == AlertType.WARNING.getAlertCode() %>">
			<i class="icon-warning-sign"></i>

			<strong class="lead"><%= LanguageUtil.get(request, "alert-helper-warning") %>: </strong>
		</c:when>
	</c:choose>
</liferay-util:buffer>

<liferay-util:buffer
	var="close"
>

	<%
	if (dismissible) {
	%>

		<button aria-label="<%= LanguageUtil.get(request, "close") %>" class="close" data-dismiss="alert" type="button">
			<svg aria-hidden="true" class="icon-monospaced lexicon-icon lexicon-icon-times">
				<use xlink:href="<%= themeDisplayPath %>/lexicon/icons.svg#times" />
			</svg>

			<span class="sr-only"><%= LanguageUtil.get(request, "close") %></span>
		</button>

	<%
	}
	%>

</liferay-util:buffer>

<div class="alert alert-<%= type %><%= dismissible ? " alert-dismissible" : "" %><%= fluid ? " alert-fluid" : "" %>">
	<div class="container">
		<%= icon %>

		<span><%= bodyContentString %></span>

		<%= close %>
	</div>
</div>