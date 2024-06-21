<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<react:component
	module="{FDSAdminItemSelector} from frontend-data-set-admin-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"className", fdsAdminItemSelectorDisplayContext.getClassName()
		).put(
			"classNameId", fdsAdminItemSelectorDisplayContext.getClassNameId()
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).build()
	%>'
/>