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
CPDefinitionLinkDisplayContext cpDefinitionLinkDisplayContext = (CPDefinitionLinkDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionLink cpDefinitionLink = cpDefinitionLinkDisplayContext.getCPDefinitionLink();
long cpDefinitionLinkId = cpDefinitionLinkDisplayContext.getCPDefinitionLinkId();

CPDefinition cpDefinition = cpDefinitionLink.getCPDefinition();
%>

<commerce-ui:modal-content>
	<portlet:actionURL name="editCPDefinitionLink" var="editCPDefinitionLinkActionURL" />

	<aui:form action="<%= editCPDefinitionLinkActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
		<aui:input name="cpDefinitionLinkId" type="hidden" value="<%= cpDefinitionLinkId %>" />
		<aui:input name="type" type="hidden" value="<%= cpDefinitionLink.getType() %>" />

		<aui:model-context bean="<%= cpDefinitionLink %>" model="<%= CPDefinitionLink.class %>" />

		<div class="lfr-form-content">
			<aui:input name="priority" />

			<c:if test="<%= cpDefinitionLinkDisplayContext.hasCustomAttributesAvailable() %>">
				<liferay-expando:custom-attribute-list
					className="<%= CPDefinitionLink.class.getName() %>"
					classPK="<%= (cpDefinitionLink != null) ? cpDefinitionLink.getCPDefinitionLinkId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</c:if>
		</div>
	</aui:form>
</commerce-ui:modal-content>