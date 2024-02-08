<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditSXPBlueprintDisplayContext editSXPBlueprintDisplayContext = (EditSXPBlueprintDisplayContext)request.getAttribute(EditSXPBlueprintDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(editSXPBlueprintDisplayContext.getRedirect());
portletDisplay.setURLBackTitle(portletDisplay.getPortletDisplayName());

renderResponse.setTitle(LanguageUtil.get(request, "edit-blueprint"));
%>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		module="{EditBlueprint} from search-experiences-web"
		props="<%= editSXPBlueprintDisplayContext.getProps() %>"
	/>
</div>