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
CPSpecificationOption cpSpecificationOption = (CPSpecificationOption)request.getAttribute(CPWebKeys.CP_SPECIFICATION_OPTION);

long cpSpecificationOptionId = BeanParamUtil.getLong(cpSpecificationOption, request, "CPSpecificationOptionId");

String title = LanguageUtil.get(request, "add-specification-label");

if (cpSpecificationOption != null) {
	title = cpSpecificationOption.getTitle(locale);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "specification-labels"), specificationOptionsURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "catalog"));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= CPNavigationItemRegistryUtil.getNavigationItems(renderRequest) %>"
/>

<%@ include file="/navbar_specifications.jspf" %>
<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editProductSpecificationOption" var="editProductSpecificationOptionActionURL" />

<aui:form action="<%= editProductSpecificationOptionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpSpecificationOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= specificationOptionsURL %>" />
	<aui:input name="cpSpecificationOptionId" type="hidden" value="<%= String.valueOf(cpSpecificationOptionId) %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= specificationOptionsURL %>"
			formModelBean="<%= cpSpecificationOption %>"
			id="<%= CPSpecificationOptionFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_SPECIFICATION_OPTION %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>