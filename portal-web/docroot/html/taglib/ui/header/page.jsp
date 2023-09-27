<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/header/init.jsp" %>

<%
if (Validator.isNull(backLabel)) {
	backLabel = LanguageUtil.get(resourceBundle, "back");
}

String headerTitle = localizeTitle ? LanguageUtil.get(resourceBundle, title) : title;
%>

<div class="taglib-header <%= cssClass %>">
	<c:if test="<%= showBackURL && Validator.isNotNull(backURL) %>">
		<liferay-ui:icon
			cssClass="header-back-to"
			icon="angle-left"
			id="TabsBack"
			label="<%= false %>"
			markupView="lexicon"
			message="<%= escapeXml ? HtmlUtil.escape(backLabel) : backLabel %>"
			method="get"
			url="<%= backURL %>"
		/>
	</c:if>

	<h3 class="header-title">
		<span>
			<c:choose>
				<c:when test="<%= escapeXml %>">
					<%= HtmlUtil.escape(headerTitle) %>
				</c:when>
				<c:otherwise>
					<%= headerTitle %>
				</c:otherwise>
			</c:choose>
		</span>
	</h3>
</div>