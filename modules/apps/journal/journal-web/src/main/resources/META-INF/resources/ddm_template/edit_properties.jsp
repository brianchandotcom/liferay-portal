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

<%@ include file="/init.jsp" %>

<div class="journal-ddm-template-properties">
	<liferay-frontend:form-navigator
		fieldSetCssClass="form-group-sm mb-0 panel-group-flush"
		formModelBean='<%= DDMTemplateLocalServiceUtil.fetchDDMTemplate(ParamUtil.getLong(request, "ddmTemplateId")) %>'
		id="<%= JournalWebConstants.FORM_NAVIGATOR_ID_JOURNAL_DDM_TEMPLATE %>"
		showButtons="<%= false %>"
	/>
</div>