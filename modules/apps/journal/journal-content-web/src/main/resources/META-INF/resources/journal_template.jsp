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
DDMStructure ddmStructure = journalContentDisplayContext.getDDMStructure();

String refererPortletName = ParamUtil.getString(request, "refererPortletName");
%>

<clay:sheet-section>
	<div class="sheet-subtitle">
		<liferay-ui:message key="template" />
	</div>

	<div>
		<liferay-ui:message key="please-select-one-option" />
	</div>

	<%
	String defaultDDMTemplateName = LanguageUtil.get(request, "no-template");

	DDMTemplate defaultDDMTemplate = journalContentDisplayContext.getDefaultDDMTemplate();

	if (defaultDDMTemplate != null) {
		defaultDDMTemplateName = HtmlUtil.escape(defaultDDMTemplate.getName(locale));
	}
	%>

	<aui:input checked="<%= journalContentDisplayContext.isDefaultTemplate() %>" id='<%= refererPortletName + "ddmTemplateTypeDefault" %>' label='<%= LanguageUtil.format(request, "use-default-template-x", defaultDDMTemplateName, false) %>' name='<%= refererPortletName + "ddmTemplateType" %>' type="radio" useNamespace="<%= false %>" value="default" />

	<aui:input checked="<%= !journalContentDisplayContext.isDefaultTemplate() %>" id='<%= refererPortletName + "ddmTemplateTypeCustom" %>' label="use-a-specific-template" name='<%= refererPortletName + "ddmTemplateType" %>' type="radio" useNamespace="<%= false %>" value="custom" />

	<div id="<%= refererPortletName %>customDDMTemplateContainer">
		<div class="template-preview-content">
			<c:choose>
				<c:when test="<%= journalContentDisplayContext.isDefaultTemplate() %>">
					<p class="text-default">
						<liferay-ui:message key="no-template" />
					</p>
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/journal_template_resources.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</div>

		<aui:button id='<%= refererPortletName + "selectDDMTemplateButton" %>' useNamespace="<%= false %>" value="select" />

		<aui:button id='<%= refererPortletName + "clearddmTemplateButton" %>' useNamespace="<%= false %>" value="clear" />
	</div>
</clay:sheet-section>

<%
AssetRendererFactory<JournalArticle> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(JournalArticle.class);

AssetRenderer<JournalArticle> assetRenderer = assetRendererFactory.getAssetRenderer(article, 0);

String className = DDMTemplate.class.getName() + "_" + JournalArticle.class.getName();

String portletId = PortletProviderUtil.getPortletId(className, PortletProvider.Action.BROWSE);
%>

<liferay-portlet:resourceURL portletName="<%= JournalContentPortletKeys.JOURNAL_CONTENT %>" var="actionURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcPath" value="/journal_template_resources.jsp" />
	<portlet:param name="articleResourcePrimKey" value="<%= String.valueOf(assetRenderer.getClassPK()) %>" />
</liferay-portlet:resourceURL>

<liferay-frontend:component
	componentId="journalTemplate"
	context='<%=
		HashMapBuilder.<String, Object>put(
			"actionURL", actionURL
		).put(
			"ddmStructure", ddmStructure
		).put(
			"ddmStructureId", (ddmStructure != null) ? String.valueOf(ddmStructure.getStructureId()) : StringPool.BLANK
		).put(
			"eventName", PortalUtil.getPortletNamespace(portletId) + "selectDDMTemplate"
		).put(
			"portletNamespace", PortalUtil.getPortletNamespace(JournalContentPortletKeys.JOURNAL_CONTENT)
		).put(
			"portletURL",
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(renderRequest, className, PortletProvider.Action.BROWSE)
			).buildString()
		).put(
			"windowState", LiferayWindowState.POP_UP.toString()
		).build()
	%>'
	module="js/JournalTemplate"
/>