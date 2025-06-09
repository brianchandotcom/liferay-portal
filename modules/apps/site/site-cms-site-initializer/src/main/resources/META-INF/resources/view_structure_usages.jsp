<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
StructureUsagesDisplayContext structureUsagesDisplayContext = (StructureUsagesDisplayContext)request.getAttribute(StructureUsagesDisplayContext.class.getName());
%>

<div class="cms-section">
	<frontend-data-set:headless-display
		apiURL="<%= structureUsagesDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= structureUsagesDisplayContext.getBulkActionDropdownItems() %>"
		fdsActionDropdownItems="<%= structureUsagesDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= CMSSiteInitializerFDSNames.STRUCTURE_USAGES %>"
		itemsPerPage="<%= 20 %>"
		propsTransformer="{StructureUsagesFDSPropsTransformer} from site-cms-site-initializer"
		selectedItemsKey="id"
		selectionType="multiple"
		style="fluid"
	/>
</div>