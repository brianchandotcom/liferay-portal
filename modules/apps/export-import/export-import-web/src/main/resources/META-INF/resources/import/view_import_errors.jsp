<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/import/init.jsp" %>
<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPD-35914") %>'>
	<liferay-frontend:component
		module="{setupExportImportMocks} from exportimport-web"
	/>

	<aui:form method="post" name="fm">
		<frontend-data-set:headless-display
			apiURL="/group/__mocks__/get-import-error-list"
			id="<%= ExportImportFDSNames.IMPORT_ERRORS %>"
		/>
	</aui:form>
</c:if>