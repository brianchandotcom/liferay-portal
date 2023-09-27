<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String catalogNavigationItem = ParamUtil.getString(request, "catalogNavigationItem", "view-all-instances");

CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = cpInstanceDisplayContext.getPortletURL();

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<%@ include file="/navbar_definitions.jspf" %>

<div class="container-fluid container-fluid-max-xl pt-4" id="<portlet:namespace />productInstancesContainer">
	<aui:form action="<%= portletURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCPInstanceIds" type="hidden" />

		<liferay-ui:error exception="<%= NoSuchSkuContributorCPDefinitionOptionRelException.class %>" message="there-are-no-options-set-as-sku-contributor" />

		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"cpDefinitionId", String.valueOf(cpInstanceDisplayContext.getCPDefinitionId())
				).build()
			%>'
			dataProviderKey="<%= CommerceProductFDSNames.ALL_PRODUCT_INSTANCES %>"
			id="<%= CommerceProductFDSNames.ALL_PRODUCT_INSTANCES %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>