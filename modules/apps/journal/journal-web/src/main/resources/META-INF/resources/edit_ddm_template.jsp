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
JournalEditDDMTemplateDisplayContext journalEditDDMTemplateDisplayContext = new JournalEditDDMTemplateDisplayContext(request);

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
	<aui:input name="language" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getLanguage() %>" />

	<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<div class="container-fluid container-fluid-max-xl">
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input cssClass="form-control-inline" defaultLanguageId="<%= (ddmTemplate == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): ddmTemplate.getDefaultLanguageId() %>" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled-x", "template") %>' wrapperCssClass="article-content-title mb-0" />
				</li>
				<li class="tbar-item">
					<div class="journal-article-button-row tbar-section text-right">
						<aui:button cssClass="btn-secondary btn-sm mr-3" href="<%= journalEditDDMTemplateDisplayContext.getRedirect() %>" type="cancel" />

						<%
						String taglibOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTemplate');";
						%>

						<aui:button cssClass="btn-sm mr-3" onClick="<%= taglibOnClick %>" type="submit" value="<%= journalEditDDMTemplateDisplayContext.getSaveButtonLabel() %>" />

						<clay:button
							icon="cog"
							id='<%= renderResponse.getNamespace() + "contextualSidebarButton" %>'
							monospaced="<%= true %>"
							size="sm"
							style="borderless"
						/>
					</div>
				</li>
			</ul>
		</div>
	</nav>

	<div class="contextual-sidebar edit-article-sidebar sidebar-light sidebar-sm" id="<portlet:namespace />contextualSidebarContainer">
		<div class="sidebar-header">
			<h4 class="component-title">
				<liferay-ui:message key="properties" />
			</h4>
		</div>

		<div class="sidebar-body">
			<liferay-frontend:form-navigator
				fieldSetCssClass="panel-group-flush"
				formModelBean="<%= ddmTemplate %>"
				id="<%= JournalWebConstants.FORM_NAVIGATOR_ID_JOURNAL_DDM_TEMPLATE %>"
				showButtons="<%= false %>"
			/>
		</div>
	</div>

	<div class="contextual-sidebar-content">
		<div class="container-fluid container-fluid-max-xl container-view">
			<div class="sheet">
				<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />
				<liferay-ui:error exception="<%= TemplateScriptException.class %>" message="please-enter-a-valid-script" />

				<c:if test="<%= (ddmTemplate != null) && (journalEditDDMTemplateDisplayContext.getGroupId() != scopeGroupId) %>">
					<div class="alert alert-warning">
						<liferay-ui:message key="this-template-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-template" />
					</div>
				</c:if>

				<liferay-util:include page="/edit_ddm_template_display.jsp" servletContext="<%= application %>" />
			</div>
		</div>
	</div>
</aui:form>

<aui:script>
	Liferay.after('<portlet:namespace />saveTemplate', function() {
		submitForm(document.<portlet:namespace />fm);
	});

	var contextualSidebarButton = document.getElementById(
		'<portlet:namespace />contextualSidebarButton'
	);
	var contextualSidebarContainer = document.getElementById(
		'<portlet:namespace />contextualSidebarContainer'
	);

	if (
		contextualSidebarContainer &&
		window.innerWidth > Liferay.BREAKPOINTS.PHONE
	) {
		contextualSidebarContainer.classList.add('contextual-sidebar-visible');
	}

	if (contextualSidebarButton) {
		contextualSidebarButton.addEventListener('click', function(event) {
			if (
				contextualSidebarContainer.classList.contains(
					'contextual-sidebar-visible'
				)
			) {
				contextualSidebarContainer.classList.remove(
					'contextual-sidebar-visible'
				);
			} else {
				contextualSidebarContainer.classList.add(
					'contextual-sidebar-visible'
				);
			}
		});
	}
</aui:script>