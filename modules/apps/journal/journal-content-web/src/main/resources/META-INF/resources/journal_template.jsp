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

String refererPortletName = ParamUtil.getString(request, "refererPortletName");
%>

<div class="sheet-section">
	<div class="sheet-subtitle">
		<liferay-ui:message key="template" />
	</div>

	<div>
		<liferay-ui:message key="please-select-one-option" />
	</div>

	<aui:input checked="<%= journalContentDisplayContext.isDefaultTemplate() %>" id='<%= refererPortletName + "ddmTemplateTypeDefault" %>' label='<%= LanguageUtil.format(request, "use-default-template-x", defaultDDMTemplate.getName(locale), false) %>' name='<%= refererPortletName + "ddmTemplateType" %>' type="radio" useNamespace="<%= false %>" value="default" />

	<aui:input checked="<%= !journalContentDisplayContext.isDefaultTemplate() %>" id='<%= refererPortletName + "ddmTemplateTypeCustom" %>' label="use-a-specific-template" name='<%= refererPortletName + "ddmTemplateType" %>' type="radio" useNamespace="<%= false %>" value="custom" />

	<div id="<%= refererPortletName + "customDDMTemplateContainer" %>">
		<div class="template-preview-content">
			<c:choose>
				<c:when test="<%= journalContentDisplayContext.isDefaultTemplate() %>">
					<p class="text-default">
						<liferay-ui:message key="none" />
					</p>
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/journal_template_resources.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</div>

		<aui:button id='<%= refererPortletName + "selectDDMTemplateButton" %>' useNamespace="<%= false %>" value="select" />
	</div>
</div>

<%
AssetRendererFactory<JournalArticle> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(JournalArticle.class);

AssetRenderer<JournalArticle> assetRenderer = assetRendererFactory.getAssetRenderer(article, 0);
%>

<aui:script use="aui-io-request,aui-parse-content,liferay-alert">
	var templatePreview = A.one('.template-preview-content');
	var form = A.one('#<%= refererPortletName %>fm');
	var templateKeyInput = A.one('#<%= refererPortletName + "ddmTemplateKey" %>');

	A.one('#<%= refererPortletName + "selectDDMTemplateButton" %>').on(
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
					templateId: 0,
					title: '<liferay-ui:message key="templates" />'
				},
				function(event) {
					templateKeyInput.setAttribute('value', event.ddmtemplatekey);

					templatePreview.html('<div class="loading-animation"></div>');

					var data = Liferay.Util.ns(
						'<%= PortalUtil.getPortletNamespace(JournalContentPortletKeys.JOURNAL_CONTENT) %>',
						{
							ddmTemplateKey: event.ddmtemplatekey
						}
					);

					A.io.request(
						'<liferay-portlet:resourceURL portletName="<%= JournalContentPortletKeys.JOURNAL_CONTENT %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/journal_template_resources.jsp" /><portlet:param name="articleResourcePrimKey" value="<%= String.valueOf(assetRenderer.getClassPK()) %>" /></liferay-portlet:resourceURL>',
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
		}
	);

	A.one('#<%= refererPortletName + "ddmTemplateTypeDefault" %>').on(
		'click',
		function(event) {
			templateKeyInput.setAttribute('value', '');
		}
	);

	Liferay.Util.toggleRadio('<%= refererPortletName + "ddmTemplateTypeCustom" %>', '<%= refererPortletName + "customDDMTemplateContainer" %>', '');
	Liferay.Util.toggleRadio('<%= refererPortletName + "ddmTemplateTypeDefault" %>', '', '<%= refererPortletName + "customDDMTemplateContainer" %>');
</aui:script>