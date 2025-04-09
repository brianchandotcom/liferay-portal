<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditCategoryDisplayContext editCategoryDisplayContext = (EditCategoryDisplayContext)request.getAttribute(EditCategoryDisplayContext.class.getName());
%>

<div class="cms-section">
	<div id="<%= CMSSiteInitializerFDSNames.CATEGORIZATION_SECTION %>">
		<react:component
			module="{EditCategoryPage} from site-cms-site-initializer"
			props="<%= editCategoryDisplayContext.getReactData() %>"
		/>
	</div>
</div>