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

<%@ include file="/compare_product/init.jsp" %>

<%
boolean checked = (boolean)request.getAttribute("liferay-commerce:compare-product:checked");
CPDefinition cpDefinition = (CPDefinition)request.getAttribute("liferay-commerce:compare-product:cpDefinition");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_commerce_compare_product_page") + StringPool.UNDERLINE;
%>

<liferay-portlet:actionURL name="editCompareProduct" portletName="<%= CPPortletKeys.CP_COMPARE_CONTENT_WEB %>" var="editCompareProductActionURL" />

<div class="commerce-compare-product-container">
	<aui:form action="<%= editCompareProductActionURL %>" name='<%= randomNamespace + "Fm" %>' portletNamespace="<%= PortalUtil.getPortletNamespace(CPPortletKeys.CP_COMPARE_CONTENT_WEB) %>">
		<aui:input name="redirect" type="hidden" value="<%= PortalUtil.getCurrentURL(request) %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />

		<aui:input checked="<%= checked %>" ignoreRequestValue="<%= true %>" label="compare" name='<%= cpDefinition.getCPDefinitionId() + "Compare" %>' onClick="this.form.submit();" type="checkbox" />
	</aui:form>
</div>