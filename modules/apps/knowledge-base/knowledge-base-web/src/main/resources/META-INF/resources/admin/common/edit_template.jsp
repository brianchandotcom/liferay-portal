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

String title = BeanParamUtil.getString(kbTemplate, request, "title");
String content = BeanParamUtil.getString(kbTemplate, request, "content");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((kbTemplate == null) ? LanguageUtil.get(request, "new-template") : kbTemplate.getTitle());
%>

<liferay-portlet:actionURL name="updateKBTemplate" var="updateKBTemplateURL" />

<clay:container>
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
				<h1 class="kb-title">
					<aui:input autocomplete="off" label='<%= LanguageUtil.get(request, "title") %>' name="title" required="<%= true %>" type="text" value="<%= HtmlUtil.escape(title) %>" />
				</h1>

				<liferay-editor:editor
					contents="<%= content %>"
					editorName="ckeditor"
					name="contentEditor"
					placeholder="content"
				/>

				<aui:input name="content" type="hidden" />
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
			<aui:button type="submit" value="publish" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container>

<aui:script>
	function <portlet:namespace />updateKBTemplate() {
		Liferay.Util.postForm(document.<portlet:namespace />fm, {
			data: {
				<%= Constants.CMD %>:
					'<%= (kbTemplate == null) ? Constants.ADD : Constants.UPDATE %>',
				title: document.getElementById('<portlet:namespace />title').value,
				content: window.<portlet:namespace />contentEditor.getHTML(),
			},
		});
	}
</aui:script>