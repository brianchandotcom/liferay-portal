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
JournalEditDDMTemplateDisplayContext journalEditDDMTemplateDisplayContext = new JournalEditDDMTemplateDisplayContext(request, renderResponse);

DDMTemplate ddmTemplate = journalEditDDMTemplateDisplayContext.getDDMTemplate();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(journalEditDDMTemplateDisplayContext.getRedirect());

renderResponse.setTitle(journalEditDDMTemplateDisplayContext.getTitle());
%>

<portlet:actionURL name="/journal/add_ddm_template" var="addDDMTemplateURL">
	<portlet:param name="mvcPath" value="/edit_ddm_template.jsp" />
</portlet:actionURL>

<portlet:actionURL name="/journal/update_ddm_template" var="updateDDMTemplateURL">
	<portlet:param name="mvcPath" value="/edit_ddm_template.jsp" />
</portlet:actionURL>

<aui:form action="<%= (ddmTemplate == null) ? addDDMTemplateURL : updateDDMTemplateURL %>" cssClass="edit-article-form" enctype="multipart/form-data" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getRedirect() %>" />
	<aui:input name="ddmTemplateId" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getDDMTemplateId() %>" />
	<aui:input name="groupId" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getGroupId() %>" />
	<aui:input name="classPK" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getClassPK() %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= false %>" />

	<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input cssClass="form-control-inline" defaultLanguageId="<%= (ddmTemplate == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): ddmTemplate.getDefaultLanguageId() %>" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled-x", "template") %>' wrapperCssClass="article-content-title mb-0" />
				</li>
				<li class="tbar-item">
					<div class="journal-article-button-row tbar-section text-right">
						<aui:button cssClass="btn-secondary btn-sm mr-3" href="<%= journalEditDDMTemplateDisplayContext.getRedirect() %>" type="cancel" />

						<%
						String taglibOnClickSaveAndContinue = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveAndContinue');";
						%>

						<aui:button cssClass="btn-secondary btn-sm mr-3" onClick="<%= taglibOnClickSaveAndContinue %>" type="submit" value="save-and-continue" />

						<%
						String taglibOnClickSaveTemplate = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTemplate');";
						%>

						<aui:button cssClass="btn-sm mr-3" onClick="<%= taglibOnClickSaveTemplate %>" type="submit" value="save" />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= TemplateScriptException.class %>" message="please-enter-a-valid-script" />

	<c:if test="<%= (ddmTemplate != null) && (journalEditDDMTemplateDisplayContext.getGroupId() != scopeGroupId) %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="this-template-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-template" />
		</div>
	</c:if>

	<div>
		<div id="<portlet:namespace />ddmTemplateEditor">
			<div class="inline-item my-5 p-5 w-100">
				<span aria-hidden="true" class="loading-animation"></span>
			</div>

			<react:component
				componentId="ddmTemplateEditor"
				module="ddm_template_editor/components/TemplateEditor"
				props="<%= journalEditDDMTemplateDisplayContext.getDDMTemplateEditorContext() %>"
			/>
		</div>
	</div>
</aui:form>

<aui:script>
	Liferay.after('<portlet:namespace />saveAndContinue', () => {
		document.<portlet:namespace />fm.<portlet:namespace />saveAndContinue.value = true;

		Liferay.fire('<portlet:namespace />saveTemplate');
	});

	Liferay.after('<portlet:namespace />saveTemplate', () => {
		submitForm(document.<portlet:namespace />fm);
	});
</aui:script>