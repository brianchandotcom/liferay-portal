<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceWarehousesDisplayContext commerceWarehousesDisplayContext = (CommerceWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceWarehouse commerceWarehouse = commerceWarehousesDisplayContext.getCommerceWarehouse();

String title = LanguageUtil.get(request, "add-warehouse");

if (commerceWarehouse != null) {
	title = LanguageUtil.format(request, "edit-x", commerceWarehouse.getName(), false);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceWarehouse" var="editCommerceWarehouseActionURL" />

<aui:form action="<%= editCommerceWarehouseActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceWarehouse();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceWarehouse == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceWarehouseId" type="hidden" value="<%= (commerceWarehouse == null) ? 0 : commerceWarehouse.getCommerceWarehouseId() %>" />

	<liferay-ui:form-navigator
		formModelBean="<%= commerceWarehouse %>"
		id="<%= CommerceWarehouseFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_WAREHOUSE %>"
		markupView="lexicon"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceWarehouse() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>