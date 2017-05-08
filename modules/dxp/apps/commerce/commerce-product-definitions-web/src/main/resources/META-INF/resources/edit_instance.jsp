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
CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpInstanceDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstanceDisplayContext.getCPInstance();

long cpInstanceId = cpInstanceDisplayContext.getCPInstanceId();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
renderResponse.setTitle((cpInstance == null) ? LanguageUtil.get(request, "add-sku") : cpDefinition.getTitle(languageId) + " - " + cpInstance.getSku());
%>

<portlet:actionURL name="editProductInstance" var="editProductInstanceActionURL" />

<aui:form action="<%= editProductInstanceActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveInstance();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpInstance == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= String.valueOf(cpInstanceId) %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= cpInstance %>"
			id="<%= CPInstanceFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_INSTANCE %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />saveInstance(forceDisable) {
		var $ = AUI.$;

		var form = $(document.<portlet:namespace />fm);

		form.fm('ddmFormValues').val(JSON.stringify(Liferay.component("cpDefinitionOptionsRenderDDMForm").toJSON()));

		submitForm(form);
	}
</aui:script>