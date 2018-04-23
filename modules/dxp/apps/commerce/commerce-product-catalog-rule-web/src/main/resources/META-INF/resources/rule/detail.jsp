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
CPCatalogRuleDisplayContext cpCatalogRuleDisplayContext = (CPCatalogRuleDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPRule cpRule = cpCatalogRuleDisplayContext.getCPRule();
long cpRuleId = cpCatalogRuleDisplayContext.getCPRuleId();
%>

<portlet:actionURL name="editCPRule" var="editCPRuleActionURL" />

<aui:form action="<%= editCPRuleActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCPRule();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpRule == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpRuleId" type="hidden" value="<%= cpRuleId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= cpRule %>" model="<%= CPRule.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="name" />

				<aui:input name="active" />

				<aui:input name="type" />

				<aui:input name="typeSettings" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCPRule() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>