<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
DLFileEntryTypeDetailsDisplayContext dlFileEntryTypeDetailsDisplayContext = new DLFileEntryTypeDetailsDisplayContext(request);
%>

<aui:model-context bean="<%= dlFileEntryTypeDetailsDisplayContext.getDLFileEntryType() %>" model="<%= DLFileEntryType.class %>" />

<aui:field-wrapper>
	<c:if test="<%= dlFileEntryTypeDetailsDisplayContext.isForeignDLFileEntryType() %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="this-document-type-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-document-type" />
		</div>
	</c:if>
</aui:field-wrapper>

<aui:input name="description" />