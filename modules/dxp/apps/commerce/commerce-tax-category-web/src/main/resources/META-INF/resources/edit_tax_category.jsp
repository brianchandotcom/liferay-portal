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
String redirect = ParamUtil.getString(request, "redirect");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceTaxCategoryDisplayContext commerceTaxCategoryDisplayContext = (CommerceTaxCategoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxCategory commerceTaxCategory = commerceTaxCategoryDisplayContext.getCommerceTaxCategory();

String title = LanguageUtil.get(request, "add-tax-category");

if (commerceTaxCategory != null) {
	title = LanguageUtil.format(request, "edit-x", commerceTaxCategory.getName(languageId), false);
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

<portlet:actionURL name="editCommerceTaxCategory" var="editCommerceTaxCategoryActionURL" />

<aui:form action="<%= editCommerceTaxCategoryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceTaxCategory == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceTaxCategoryId" type="hidden" value="<%= (commerceTaxCategory == null) ? 0 : commerceTaxCategory.getCommerceTaxCategoryId() %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceTaxCategoryNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= commerceTaxCategory %>" model="<%= CommerceTaxCategory.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="name" />

				<aui:input name="description" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>