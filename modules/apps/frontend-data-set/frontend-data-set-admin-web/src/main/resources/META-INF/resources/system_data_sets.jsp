<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="container-fluid container-fluid-max-xxxl">
	<react:component
		module="{SystemDataSets} from frontend-data-set-admin-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"editDataSetURL", fdsAdminDisplayContext.getEditDataSetURL()
			).put(
				"importSystemDataSetURL", fdsAdminDisplayContext.getImportSystemDataSetURL()
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"systemDataSets", fdsAdminDisplayContext.getSystemFDSEntryJSONArray()
			).put(
				"systemFDSEntryInfosDataProviderURL", fdsAdminDisplayContext.getSystemFDSEntryInfosDataProviderURL()
			).build()
		%>'
	/>
</div>