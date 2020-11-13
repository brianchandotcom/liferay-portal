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

<%
String catalogNavigationItem = ParamUtil.getString(request, "catalogNavigationItem", "view-all-instances");

CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = cpInstanceDisplayContext.getPortletURL();

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<%@ include file="/navbar_definitions.jspf" %>

<div class="container-fluid container-fluid-max-xl pt-4" id="<portlet:namespace />productInstancesContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCPInstanceIds" type="hidden" />

		<liferay-ui:error exception="<%= NoSuchSkuContributorCPDefinitionOptionRelException.class %>" message="there-are-no-options-set-as-sku-contributor" />

		<clay:data-set-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"cpDefinitionId", String.valueOf(cpInstanceDisplayContext.getCPDefinitionId())
				).build()
			%>'
			dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_ALL_PRODUCT_INSTANCES %>"
			id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_ALL_PRODUCT_INSTANCES %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
			style="stacked"
		/>
	</aui:form>
</div>