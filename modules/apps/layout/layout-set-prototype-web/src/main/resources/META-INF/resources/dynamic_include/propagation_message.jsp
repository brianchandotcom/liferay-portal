<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
PropagationMessageDisplayContext propagationMessageDisplayContext = new PropagationMessageDisplayContext(request);
%>

<li class="control-menu-nav-item">
	<clay:button
		cssClass="control-menu-nav-link"
		displayType="unstyled"
		icon="merge"
		monospaced="<%= true %>"
		small="<%= true %>"
	/>

	<react:component
		data="<%= propagationMessageDisplayContext.getData() %>"
		module="{PropagationMessage} from layout-set-prototype-web"
	/>
</li>