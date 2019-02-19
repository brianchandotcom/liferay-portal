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
JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);

long classNameId = ParamUtil.getLong(request, "classNameId");

DDMStructure ddmStructure = journalEditArticleDisplayContext.getDDMStructure();
DDMTemplate ddmTemplate = journalEditArticleDisplayContext.getDDMTemplate();
%>

<aui:input name="groupId" type="hidden" value="<%= journalEditArticleDisplayContext.getGroupId() %>" />
<aui:input name="ddmStructureKey" type="hidden" value="<%= ddmStructure.getStructureKey() %>" />
<aui:input name="ddmTemplateKey" type="hidden" value="<%= (ddmTemplate != null) ? ddmTemplate.getTemplateKey() : StringPool.BLANK %>" />

<div class="article-structure">
	<liferay-ui:message key="structure" />:

	<c:choose>
		<c:when test="<%= DDMStructurePermission.contains(permissionChecker, ddmStructure, ActionKeys.UPDATE) %>">
			<aui:a href="javascript:;" id="editDDMStructure" label="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>" />
		</c:when>
		<c:otherwise>
			<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
		<div class="button-holder">
			<aui:button id="selectDDMStructure" value="select" />
		</div>
	</c:if>
</div>

<c:if test="<%= ListUtil.isNotEmpty(ddmStructure.getTemplates()) %>">
	<div class="article-template">
		<liferay-ui:message key="template" />:

		<span id="<portlet:namespace />templateNameLabel">
			<c:if test="<%= (ddmTemplate != null) && ddmTemplate.isSmallImage() %>">
				<img alt="" class="article-template-image" id="<portlet:namespace />templateImage" src="<%= HtmlUtil.escapeAttribute(ddmTemplate.getTemplateImageURL(themeDisplay)) %>" />
			</c:if>

			<c:choose>
				<c:when test="<%= (ddmTemplate != null) && DDMTemplatePermission.contains(permissionChecker, ddmTemplate, ActionKeys.UPDATE) %>">
					<aui:a href="javascript:;" id="editDDMTemplate" label="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>" />
				</c:when>
				<c:otherwise>
					<%= (ddmTemplate != null) ? HtmlUtil.escape(ddmTemplate.getName(locale)) : LanguageUtil.get(request, "none") %>
				</c:otherwise>
			</c:choose>
		</span>

		<div class="button-holder">
			<aui:button id="selectDDMTemplate" value="select" />
		</div>
	</div>
</c:if>

<aui:script>

	<%
	long folderId = journalDisplayContext.getFolderId();

	boolean searchRestriction = false;

	if (journalDisplayContext.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW) {
		searchRestriction = true;
	}

	if (!searchRestriction) {
		folderId = JournalFolderLocalServiceUtil.getOverridedDDMStructuresFolderId(folderId);

		searchRestriction = folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
	%>

	var selectDDMStructureButton = document.querySelector('#<portlet:namespace />selectDDMStructure');

	if (selectDDMStructureButton) {
		selectDDMStructureButton.addEventListener(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true
						},
						eventName: '<portlet:namespace />selectDDMStructure',
						id: '<portlet:namespace />selectDDMStructure',
						title: '<%= UnicodeLanguageUtil.get(request, "structures") %>',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_ddm_structure.jsp" /><portlet:param name="searchRestriction" value="<%= String.valueOf(searchRestriction) %>" /><portlet:param name="searchRestrictionClassNameId" value="<%= String.valueOf(ClassNameLocalServiceUtil.getClassNameId(JournalFolder.class)) %>" /><portlet:param name="searchRestrictionClassPK" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>'
					},
					function(event) {
						var ddmStructureId = '<%= ddmStructure.getPrimaryKey() %>';

						if (document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value != '') {
							ddmStructureId = document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value;
						}

						if (ddmStructureId != event.ddmstructureid) {
							if (confirm('<%= UnicodeLanguageUtil.get(request, "editing-the-current-structure-deletes-all-unsaved-content") %>')) {
								document.<portlet:namespace />fm1.<portlet:namespace />changeDDMStructure.value = 'true';
								document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value = event.ddmstructureid;
								document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureKey.value = event.ddmstructurekey;
								document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateKey.value = '';

								submitForm(document.<portlet:namespace />fm1, null, false, false);
							}
						}
					}
				);
			}
		);
	}

	var editDDMStructureLink = document.querySelector('#<portlet:namespace />editDDMStructure');

	if (editDDMStructureLink) {
		editDDMStructureLink.addEventListener(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(request, "editing-the-current-structure-deletes-all-unsaved-content") %>')) {
					Liferay.Util.openWindow(
						{
							dialog: {
								destroyOnHide: true
							},
							dialogIframe: {
								bodyCssClass: 'dialog-with-footer'
							},
							id: '<portlet:namespace />editDDMStructure',
							title: '<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>',

							<portlet:renderURL var="editDDMStructureURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="mvcPath" value="/edit_ddm_structure.jsp" />
								<portlet:param name="closeRedirect" value="<%= currentURL %>" />
								<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
							</portlet:renderURL>

							uri: '<%= editDDMStructureURL %>'
						}
					);
				}
			}
		);
	}

	var selectDDMTemplateButton = document.querySelector('#<portlet:namespace />selectDDMTemplate');

	if (selectDDMTemplateButton) {
		selectDDMTemplateButton.addEventListener(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true
						},
						eventName: '<portlet:namespace />selectDDMTemplate',
						id: '<portlet:namespace />selectDDMTemplate',
						title: '<%= UnicodeLanguageUtil.get(request, "templates") %>',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_ddm_template.jsp" /><portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" /></portlet:renderURL>'
					},
					function(event) {
						var ddmTemplateId = '<%= (ddmTemplate != null) ? ddmTemplate.getTemplateId() : 0 %>';

						if (document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value != '') {
							ddmTemplateId = document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value;
						}

						if (ddmTemplateId != event.ddmtemplateid) {
							if (confirm('<%= UnicodeLanguageUtil.get(request, "editing-the-current-template-deletes-all-unsaved-content") %>')) {
								document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value = event.ddmtemplateid;

								submitForm(document.<portlet:namespace />fm1, null, false, false);
							}
						}
					}
				);
			}
		);
	}

	var editDDMTemplateLink = document.querySelector('#<portlet:namespace />editDDMTemplate');

	if (editDDMTemplateLink) {
		editDDMTemplateLink.addEventListener(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(request, "editing-the-current-template-deletes-all-unsaved-content") %>')) {
					Liferay.Util.openWindow(
						{
							dialog: {
								destroyOnHide: true
							},
							dialogIframe: {
								bodyCssClass: 'dialog-with-footer'
							},
							id: '<portlet:namespace />editDDMTemplate',
							title: '<%= (ddmTemplate != null) ? HtmlUtil.escape(ddmTemplate.getName(locale)) : StringPool.BLANK %>',

							<portlet:renderURL var="editDDMTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="mvcPath" value="/edit_ddm_template.jsp" />
								<portlet:param name="closeRedirect" value="<%= currentURL %>" />
								<portlet:param name="ddmTemplateId" value="<%= (ddmTemplate != null) ? String.valueOf(ddmTemplate.getTemplateId()) : StringPool.BLANK %>" />
							</portlet:renderURL>

							uri: '<%= editDDMTemplateURL %>'
						}
					);
				}
			}
		);
	}
</aui:script>