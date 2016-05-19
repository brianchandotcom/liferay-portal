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

<%@ include file="/admin/common/init.jsp" %>

<%
KBTemplate kbTemplate = (KBTemplate)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_TEMPLATE);

long kbTemplateId = BeanParamUtil.getLong(kbTemplate, request, "kbTemplateId");

String content = BeanParamUtil.getString(kbTemplate, request, "content");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((kbTemplate == null) ? LanguageUtil.get(request, "new-template") : kbTemplate.getTitle());
%>

<liferay-portlet:actionURL name="updateKBTemplate" var="updateKBTemplateURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= updateKBTemplateURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateKBTemplate();" %>'>
		<aui:input name="mvcPath" type="hidden" value='<%= templatePath + "edit_template.jsp" %>' />
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="kbTemplateId" type="hidden" value="<%= String.valueOf(kbTemplateId) %>" />

		<liferay-ui:error exception="<%= KBTemplateContentException.class %>" message="please-enter-valid-content" />
		<liferay-ui:error exception="<%= KBTemplateTitleException.class %>" message="please-enter-a-valid-title" />

		<aui:model-context bean="<%= kbTemplate %>" model="<%= KBTemplate.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="title" />

				<aui:field-wrapper label="content">
					<liferay-ui:input-editor contents="<%= content %>" width="100%" />

					<aui:input name="content" type="hidden" />
				</aui:field-wrapper>
			</aui:fieldset>

			<c:if test="<%= kbTemplate == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<liferay-ui:input-permissions
							modelName="<%= KBTemplate.class.getName() %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="publish" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />updateKBTemplate() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (kbTemplate == null) ? Constants.ADD : Constants.UPDATE %>';
		document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>