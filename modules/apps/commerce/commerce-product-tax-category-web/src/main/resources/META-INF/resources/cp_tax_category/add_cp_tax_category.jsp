<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<portlet:actionURL name="/cp_tax_category/edit_cp_tax_category" var="editCPTaxCategoryActionURL" />

<aui:form action="<%= editCPTaxCategoryActionURL %>" cssClass="container-fluid container-fluid-max-xl mt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="cpTaxCategoryId" type="hidden" value="0" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CPTaxCategoryNameException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= DuplicateCPTaxCategoryException.class %>" message="please-enter-a-unique-external-reference-code" />

		<aui:model-context model="<%= CPTaxCategory.class %>" />

		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<aui:input name="externalReferenceCode" />

					<aui:input name="name" />

					<aui:input name="description" />
				</aui:fieldset>

				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" />

					<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</div>
		</div>
	</div>
</aui:form>