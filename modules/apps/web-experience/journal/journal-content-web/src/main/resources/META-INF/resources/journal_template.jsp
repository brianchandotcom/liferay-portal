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
JournalArticle article = journalContentDisplayContext.getArticle();
DDMTemplate defaultDDMTemplate = journalContentDisplayContext.getDefaultDDMTemplate();
DDMStructure ddmStructure = journalContentDisplayContext.getDDMStructure();
DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();

String refererPortletName = ParamUtil.getString(request, "refererPortletName");
%>

<div class="template-preview-content">
	<liferay-util:include page="/journal_template_resources.jsp" servletContext="<%= application %>" />
</div>

<c:if test="<%= ddmTemplates.size() > 1 %>">
	<aui:script use="aui-io-request,aui-parse-content,liferay-alert">
		var templatePreview = A.one('.template-preview-content');
		var form = A.one('#<%= refererPortletName %>fm');
		var templateKeyInput = A.one('#<%= refererPortletName + "ddmTemplateKey" %>');

		var templateId = <%= ddmTemplate.getTemplateId() %>;

		var updateTemplate = function(ddmTemplateKey, ddmTemplateId) {
			templateId = ddmTemplateId;

			templateKeyInput.setAttribute('value', ddmTemplateKey);

			templatePreview.html('<div class="loading-animation"></div>');

			var data = Liferay.Util.ns(
				'<%= PortalUtil.getPortletNamespace(JournalContentPortletKeys.JOURNAL_CONTENT) %>',
				{
					ddmTemplateKey: ddmTemplateKey
				}
			);

			A.io.request(
				'<liferay-portlet:resourceURL portletName="<%= JournalContentPortletKeys.JOURNAL_CONTENT %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/journal_template_resources.jsp" /><portlet:param name="articleResourcePrimKey" value="<%= String.valueOf(JournalArticleAssetRenderer.getClassPK(article)) %>" /></liferay-portlet:resourceURL>',
				{
					data: data,
					on: {
						failure: function() {
							templatePreview.html('<div class="alert alert-danger hidden"><liferay-ui:message key="an-unexpected-error-occurred" /></div>');
						},
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							templatePreview.plug(A.Plugin.ParseContent);

							templatePreview.setContent(responseData);
						}
					}
				}
			);
		}

		templatePreview.delegate(
			'click',
			function(event) {
				updateTemplate('<%= defaultDDMTemplate.getTemplateKey() %>', '<%= defaultDDMTemplate.getTemplateId() %>');
			},
			'.change-template'
		);

		templatePreview.delegate(
			'click',
			function(event) {
				event.preventDefault();

				Liferay.Util.openDDMPortlet(
					{
						basePortletURL: '<%= PortalUtil.getControlPanelPortletURL(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), PortletRequest.RENDER_PHASE) %>',
						classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
						classPK: <%= ddmStructure.getPrimaryKey() %>,
						dialog: {
							destroyOnHide: true
						},
						eventName: 'selectTemplate',
						groupId: <%= article.getGroupId() %>,
						mvcPath: '/select_template.jsp',
						navigationStartsOn: '<%= DDMNavigationHelper.SELECT_TEMPLATE %>',
						refererPortletName: '<%= JournalContentPortletKeys.JOURNAL_CONTENT %>',
						resourceClassNameId: <%= ddmStructure.getClassNameId() %>,
						showAncestorScopes: true,
						showCacheableInput: true,
						templateId: templateId,
						title: '<liferay-ui:message key="templates" />'
					},
					function(event) {
						updateTemplate(event.ddmtemplatekey, event.ddmtemplateid);

						new Liferay.Alert(
							{
								closeable: true,
								delay: {
									hide: 0,
									show: 0
								},
								duration: 500,
								icon: 'info-circle',
								message: '<%= HtmlUtil.escapeJS(LanguageUtil.get(resourceBundle, "changing-the-template-will-not-affect-the-original-web-content-defautl-template.-the-change-only-applies-to-this-web-content-display")) %>',
								namespace: '<portlet:namespace />',
								title: '',
								type: 'info'
							}
						).render(form);

					}
				);
			},
			'.select-template'
		);
	</aui:script>
</c:if>