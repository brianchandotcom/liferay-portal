<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alert/init.jsp" %>

<liferay-util:buffer
	var="icon"
>
	<c:choose>
		<c:when test="<%= type == AlertType.ERROR.getAlertCode() %>">
			<svg aria-hidden="true" class="lexicon-icon lexicon-icon-exclamation-full">
				<use xlink:href="<%= spritemap %>#exclamation-full" />
			</svg>

			<strong class="lead"><liferay-ui:message key="alert-helper-error" />: </strong>
		</c:when>
		<c:when test="<%= type == AlertType.INFO.getAlertCode() %>">
			<svg aria-hidden="true" class="lexicon-icon lexicon-icon-info-circle">
				<use xlink:href="<%= spritemap %>#info-circle" />
			</svg>

			<strong class="lead"><liferay-ui:message key="alert-helper-info" />: </strong>
		</c:when>
		<c:when test="<%= type == AlertType.SUCCESS.getAlertCode() %>">
			<i class="icon-ok-sign"></i>

			<strong class="lead"><liferay-ui:message key="alert-helper-success" />: </strong>
		</c:when>
		<c:when test="<%= type == AlertType.WARNING.getAlertCode() %>">
			<i class="icon-warning-sign"></i>

			<strong class="lead"><liferay-ui:message key="alert-helper-warning" />: </strong>
		</c:when>
	</c:choose>
</liferay-util:buffer>

<liferay-util:buffer
	var="close"
>
	<c:if test="<%= dismissible %>">
		<button aria-label="<%= LanguageUtil.get(request, "close") %>" class="close" data-dismiss="liferay-alert" type="button">
			<svg aria-hidden="true" class="icon-monospaced lexicon-icon lexicon-icon-times">
				<use xlink:href="<%= spritemap %>#times" />
			</svg>

			<span class="sr-only"><liferay-ui:message key="close" /></span>
		</button>
	</c:if>
</liferay-util:buffer>

<div class="alert alert-<%= type %><%= dismissible ? " alert-dismissible" : "" %><%= fluid ? " alert-fluid" : "" %>">
	<clay:container-fluid>
		<%= icon %>

		<span><%= bodyContentString %></span>

		<%= close %>
	</clay:container-fluid>
</div>