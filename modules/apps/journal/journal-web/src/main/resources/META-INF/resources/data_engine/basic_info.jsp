<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalEditDDMStructuresDisplayContext journalEditDDMStructuresDisplayContext = new JournalEditDDMStructuresDisplayContext(request, liferayPortletResponse);

DDMStructure ddmStructure = journalEditDDMStructuresDisplayContext.getDDMStructure();

DDMForm ddmForm = null;

if (ddmStructure != null) {
	ddmForm = ddmStructure.getDDMForm();
}
%>

<aui:model-context bean="<%= ddmStructure %>" model="<%= DDMStructure.class %>" />

<aui:input name="storageType" type="hidden" value="<%= journalEditDDMStructuresDisplayContext.getStorageType() %>" />

<c:if test="<%= journalEditDDMStructuresDisplayContext.isShowStructureKeyInput() %>">
	<aui:input disabled="<%= journalEditDDMStructuresDisplayContext.isStructureKeyInputDisabled() %>" name="structureKey" />
</c:if>

<aui:input activeLanguageIds="<%= journalEditDDMStructuresDisplayContext.getAvailableLanguageIds() %>" defaultLanguageId="<%= (ddmForm == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()) %>" languagesDropdownDirection="downleft" localized="<%= true %>" name="description" type="text" />

<c:if test="<%= ddmStructure != null %>">
	<portlet:resourceURL id="/journal/get_ddm_structure" var="getDDMStructureURL">
		<portlet:param name="ddmStructureId" value="<%= String.valueOf(journalEditDDMStructuresDisplayContext.getDDMStructureId()) %>" />
	</portlet:resourceURL>

	<aui:input name="url" type="resource" value="<%= getDDMStructureURL %>" />

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	%>

	<aui:input name="webDavURL" type="resource" value="<%= ddmStructure.getWebDavURL(themeDisplay, WebDAVUtil.getStorageToken(portlet)) %>" />
</c:if>