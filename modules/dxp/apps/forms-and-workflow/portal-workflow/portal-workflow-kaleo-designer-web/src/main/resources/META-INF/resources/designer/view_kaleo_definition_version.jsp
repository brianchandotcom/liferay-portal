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

<%@ include file="/designer/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

KaleoDefinitionVersion workflowDefinition = (KaleoDefinitionVersion)request.getAttribute(KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);

String content = workflowDefinition.getContent();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(workflowDefinition.getName());
%>

<aui:model-context bean="<%= workflowDefinition %>" model="<%= WorkflowDefinition.class %>" />

<liferay-frontend:info-bar>
	<div class="container-fluid-1280">
		<div class="info-bar-item">
			<c:choose>
				<c:when test="<%= !workflowDefinition.isDraft() && !workflowDefinition.isInactive() %>">
					<span class="label label-info"><%= LanguageUtil.get(request, "published") %></span>
				</c:when>
				<c:otherwise>
					<span class="label label-secondary"><%= LanguageUtil.get(request, "not-published") %></span>
				</c:otherwise>
			</c:choose>
		</div>

		<%
		String userName = "UserName";
		%>

		<span>
			<c:choose>
				<c:when test="<%= userName == null %>">
					<%= dateFormatTime.format(workflowDefinition.getModifiedDate()) %>
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(workflowDefinition.getModifiedDate()), userName} %>" key="revision-from-x-by-x" translateArguments="<%= false %>" />
				</c:otherwise>
			</c:choose>
		</span>
	</div>
</liferay-frontend:info-bar>

<aui:input name="content" type="hidden" value="<%= content %>" />

<div class="card-horizontal main-content-card">
	<div class="card-row-padded">
		<aui:fieldset cssClass="workflow-definition-content">
			<aui:col>
				<aui:field-wrapper label="title">
					<liferay-ui:input-localized disabled="<%= true %>" name="title" xml='<%= BeanPropertiesUtil.getString(workflowDefinition, "title") %>' />
				</aui:field-wrapper>
			</aui:col>

			<aui:col cssClass="workflow-definition-content-source-wrapper" id="contentSourceWrapper">
				<div class="workflow-definition-content-source" id="<portlet:namespace />contentEditor"></div>
			</aui:col>
		</aui:fieldset>
	</div>
</div>

<aui:script use="aui-ace-editor,liferay-xml-formatter">
	var STR_VALUE = 'value';

	var contentEditor = new A.AceEditor(
		{
			boundingBox: '#<portlet:namespace />contentEditor',
			height: 600,
			readOnly: 'true',
			tabSize: 4,
			width: '100%'
		}
	).render();

	var editorContentElement = A.one('#<portlet:namespace />content');

	if (editorContentElement) {
		contentEditor.set(STR_VALUE, editorContentElement.val());
	}
</aui:script>