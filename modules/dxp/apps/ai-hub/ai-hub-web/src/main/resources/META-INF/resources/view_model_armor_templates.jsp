<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewModelArmorTemplatesDisplayContext viewModelArmorTemplatesDisplayContext = (ViewModelArmorTemplatesDisplayContext)request.getAttribute(ViewModelArmorTemplatesDisplayContext.class.getName());
%>

<frontend-data-set:headless-display
	apiURL="<%= viewModelArmorTemplatesDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewModelArmorTemplatesDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewModelArmorTemplatesDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= AIHubFDSNames.MODEL_ARMOR_TEMPLATES %>"
	itemsPerPage="<%= 20 %>"
	propsTransformer="{ListTitlePropsTransformer} from ai-hub-web"
	style="fluid"
/>