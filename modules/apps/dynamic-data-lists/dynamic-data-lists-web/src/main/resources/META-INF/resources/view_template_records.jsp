<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long displayDDMTemplateId = ParamUtil.getLong(request, "displayDDMTemplateId");

DDLDisplayTemplateTransformer ddlDisplayTemplateTransformer = new DDLDisplayTemplateTransformer(displayDDMTemplateId, recordSet, themeDisplay, renderRequest);
%>

<div class="main-content-body mt-4">
	<%= ddlDisplayTemplateTransformer.transform() %>
</div>