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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionsDisplayContext.getCPDefinitionId();
%>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionActionURL" />

<aui:form action="<%= editProductDefinitionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateCategorization" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= String.valueOf(cpDefinitionId) %>" />

	<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="categories" type="assetCategories" />

			<aui:input name="tags" type="assetTags" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

	<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
</aui:form>