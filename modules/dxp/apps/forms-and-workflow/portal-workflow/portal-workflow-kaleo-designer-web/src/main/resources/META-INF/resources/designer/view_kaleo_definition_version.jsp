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

KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)request.getAttribute(KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);

KaleoDefinition kaleoDefinition = kaleoDefinitionVersion.getKaleoDefinition();

String content = kaleoDefinitionVersion.getContent();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(kaleoDefinitionVersion.getTitle(locale));
%>

<aui:model-context bean="<%= kaleoDefinitionVersion %>" model="<%= KaleoDefinitionVersion.class %>" />

<liferay-frontend:info-bar>
	<div class="container-fluid-1280">
		<div class="info-bar-item">
			<c:choose>
				<c:when test="<%= (kaleoDefinition != null) && kaleoDefinition.isActive() %>">
					<span class="label label-info">
						<liferay-ui:message key="published" />
					</span>
				</c:when>
				<c:otherwise>
					<span class="label label-secondary">
						<liferay-ui:message key="not-published" />
					</span>
				</c:otherwise>
			</c:choose>
		</div>

		<%
		String userName = kaleoDesignerDisplayContext.getUserName(kaleoDefinitionVersion);
		%>

		<span>
			<c:choose>
				<c:when test="<%= userName == null %>">
					<%= dateFormatTime.format(kaleoDefinitionVersion.getModifiedDate()) %>
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(kaleoDefinitionVersion.getModifiedDate()), userName} %>" key="revision-from-x-by-x" translateArguments="<%= false %>" />
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
					<liferay-ui:input-localized disabled="<%= true %>" name="title" xml='<%= BeanPropertiesUtil.getString(kaleoDefinitionVersion, "title") %>' />
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

	var xmlFormatter = new Liferay.XMLFormatter();

	var editorContentElement = A.one('#<portlet:namespace />content');

	if (editorContentElement) {
		var content = xmlFormatter.format(editorContentElement.val());

		contentEditor.set(STR_VALUE, content);
	}
</aui:script>