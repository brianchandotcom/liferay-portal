<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/language/init.jsp" %>

<c:if test="<%= !languageEntries.isEmpty() %>">

	<%
	Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
		"formAction", formAction
	).put(
		"formName", formName
	).put(
		"languageId", languageId
	).put(
		"name", name
	).put(
		"namespace", namespace
	).build();
	%>

	<%= PortletDisplayTemplateManagerUtil.renderDDMTemplate(PortalUtil.getClassNameId(LanguageEntry.class), contextObjects, ddmTemplateKey, languageEntries, displayStyleGroupId, request, response, true) %>
</c:if>