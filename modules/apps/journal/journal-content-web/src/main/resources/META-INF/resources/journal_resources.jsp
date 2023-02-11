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
String refererPortletName = ParamUtil.getString(request, "refererPortletName");

JournalArticle article = journalContentDisplayContext.getArticle();
%>

<aui:input id='<%= refererPortletName + "ddmTemplateKey" %>' name='<%= refererPortletName + "preferences--ddmTemplateKey--" %>' type="hidden" useNamespace="<%= false %>" value="<%= journalContentDisplayContext.isDefaultTemplate() ? StringPool.BLANK : journalContentDisplayContext.getDDMTemplateKey() %>" />

<clay:sheet-section>
	<div class="sheet-subtitle">
		<liferay-ui:message key="layout.types.article" />
	</div>

	<clay:row>
		<clay:col
			md="4"
		>
			<c:if test="<%= article != null %>">
				<liferay-util:include page="/journal_article_resources.jsp" servletContext="<%= application %>" />
			</c:if>
		</clay:col>
	</clay:row>

	<div>
		<aui:button cssClass="web-content-selector" name="webContentSelector" value='<%= Validator.isNull(article) ? "select" : "change" %>' />

		<c:if test="<%= article != null %>">
			<aui:button cssClass="selector-button" name="removeWebContent" value="remove" />
		</c:if>
	</div>
</clay:sheet-section>

<c:if test="<%= article != null %>">
	<liferay-util:include page="/journal_template.jsp" servletContext="<%= application %>" />

	<clay:sheet-section>
		<div class="sheet-subtitle">
			<liferay-ui:message key="user-tools" />
		</div>

		<aui:input checked='<%= journalContentDisplayContext.isEnabledUserToolAssetAddonEntry("showAvailableLocales") %>' id='<%= refererPortletName + "showAvailableLocales" %>' label="translations" name="userToolAssetAddonEntryKeys" type="checkbox" value="showAvailableLocales" />

		<aui:input checked='<%= journalContentDisplayContext.isEnabledUserToolAssetAddonEntry("enablePrint") %>' id='<%= refererPortletName + "enablePrint" %>' label="print" name="userToolAssetAddonEntryKeys" type="checkbox" value="enablePrint" />

		<c:if test='<%= journalContentDisplayContext.isEnabledConversion("pdf") %>'>
			<aui:input checked='<%= journalContentDisplayContext.isEnabledUserToolAssetAddonEntry("enablePDF") %>' id='<%= refererPortletName + "enablePDF" %>' label='<%= LanguageUtil.format(request, "download-as-x", "PDF") %>' name="userToolAssetAddonEntryKeys" type="checkbox" value="enablePDF" />
		</c:if>

		<c:if test='<%= journalContentDisplayContext.isEnabledConversion("doc") %>'>
			<aui:input checked='<%= journalContentDisplayContext.isEnabledUserToolAssetAddonEntry("enableDOC") %>' id='<%= refererPortletName + "enableDOC" %>' label='<%= LanguageUtil.format(request, "download-as-x", "DOC") %>' name="userToolAssetAddonEntryKeys" type="checkbox" value="enableDOC" />
		</c:if>

		<c:if test='<%= journalContentDisplayContext.isEnabledConversion("odt") %>'>
			<aui:input checked='<%= journalContentDisplayContext.isEnabledUserToolAssetAddonEntry("enableODT") %>' id='<%= refererPortletName + "enableODT" %>' label='<%= LanguageUtil.format(request, "download-as-x", "ODT") %>' name="userToolAssetAddonEntryKeys" type="checkbox" value="enableODT" />
		</c:if>

		<c:if test='<%= journalContentDisplayContext.isEnabledConversion("txt") %>'>
			<aui:input checked='<%= journalContentDisplayContext.isEnabledUserToolAssetAddonEntry("enableTXT") %>' id='<%= refererPortletName + "enableTXT" %>' label='<%= LanguageUtil.format(request, "download-as-x", "TXT") %>' name="userToolAssetAddonEntryKeys" type="checkbox" value="enableTXT" />
		</c:if>
	</clay:sheet-section>

	<clay:sheet-section>
		<div class="sheet-subtitle">
			<liferay-ui:message key="content-metadata" />
		</div>

		<aui:input checked='<%= journalContentDisplayContext.isEnabledContentMetadataAssetAddonEntry("enableRelatedAssets") %>' id='<%= refererPortletName + "enableRelatedAssets" %>' label="related-assets" name="contentMetadataAssetAddonEntryKeys" type="checkbox" value="enableRelatedAssets" />

		<aui:input checked='<%= journalContentDisplayContext.isEnabledContentMetadataAssetAddonEntry("enableRatings") %>' id='<%= refererPortletName + "enableRatings" %>' label="ratings" name="contentMetadataAssetAddonEntryKeys" type="checkbox" value="enableRatings" />

		<c:if test="<%= journalContentDisplayContext.articleCommentsEnabled() %>">
			<aui:input checked='<%= journalContentDisplayContext.isEnabledContentMetadataAssetAddonEntry("enableComments") %>' id='<%= refererPortletName + "enableComments" %>' label="comments" name="contentMetadataAssetAddonEntryKeys" type="checkbox" value="enableComments" />

			<aui:input checked='<%= journalContentDisplayContext.isEnabledContentMetadataAssetAddonEntry("enableCommentRatings") %>' id='<%= refererPortletName + "enableCommentRatings" %>' label="comment-ratings" name="contentMetadataAssetAddonEntryKeys" type="checkbox" value="enableCommentRatings" />
		</c:if>
	</clay:sheet-section>

	<clay:sheet-section>
		<div class="sheet-subtitle">
			<liferay-ui:message key="enable" />
		</div>

		<aui:input id='<%= refererPortletName + "enableViewCountIncrement" %>' inlineLabel="right" label="view-count-increment" labelCssClass="simple-toggle-switch" name="preferences--enableViewCountIncrement--" type="toggle-switch" value="<%= journalContentDisplayContext.isEnableViewCountIncrement() %>" />
	</clay:sheet-section>
</c:if>