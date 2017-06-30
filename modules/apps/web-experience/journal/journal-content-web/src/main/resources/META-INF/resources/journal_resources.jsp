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

<aui:input id='<%= refererPortletName + "ddmTemplateKey" %>' name='<%= refererPortletName + "preferences--ddmTemplateKey--" %>' type="hidden" useNamespace="<%= false %>" value="<%= journalContentDisplayContext.getDDMTemplateKey() %>" />

<div class="article-preview row">
	<div class="col-md-3 col-sm-6 col-xs-12">
		<p class="text-muted"><liferay-ui:message key="layout.types.article" /></p>

		<div class="article-preview-content-container">
			<c:if test="<%= article != null %>">
				<liferay-util:include page="/journal_article_resources.jsp" servletContext="<%= application %>" />
			</c:if>
		</div>

		<div class="button-holder">
			<aui:button cssClass="web-content-selector" name="webContentSelector" value='<%= Validator.isNull(article) ? "select" : "change" %>' />

			<c:if test="<%= article != null %>">
				<aui:button cssClass="selector-button" name="removeWebContent" value="remove" />
			</c:if>
		</div>
	</div>
</div>

<c:if test="<%= article != null %>">
	<div class="row template-preview">
		<div class="col-md-3 col-sm-6 col-xs-12">
			<liferay-util:include page="/journal_template.jsp" servletContext="<%= application %>" />
		</div>
	</div>

	<div class="configuration-options-container row">
		<div class="col-md-6 col-sm-6 col-xs-12">
			<aui:fieldset>
				<aui:field-wrapper label="user-tools">
					<liferay-ui:asset-addon-entry-selector
						assetAddonEntries="<%= (List<AssetAddonEntry>)(List<?>)journalContentDisplayContext.getEnabledUserToolAssetAddonEntries() %>"
						hiddenInput="preferences--userToolAssetAddonEntryKeys--"
						id="userToolsAssetAddonEntriesSelector"
						selectedAssetAddonEntries="<%= (List<AssetAddonEntry>)(List<?>)journalContentDisplayContext.getSelectedUserToolAssetAddonEntries() %>"
						title="select-user-tools"
					/>
				</aui:field-wrapper>

				<aui:field-wrapper label="content-metadata">
					<liferay-ui:asset-addon-entry-selector
						assetAddonEntries="<%= (List<AssetAddonEntry>)(List<?>)journalContentDisplayContext.getEnabledContentMetadataAssetAddonEntries() %>"
						hiddenInput="preferences--contentMetadataAssetAddonEntryKeys--"
						id="contentMetadataAssetAddonEntriesSelector"
						selectedAssetAddonEntries="<%= (List<AssetAddonEntry>)(List<?>)journalContentDisplayContext.getSelectedContentMetadataAssetAddonEntries() %>"
						title="select-content-metadata"
					/>
				</aui:field-wrapper>

				<aui:input name="preferences--enableViewCountIncrement--" type="toggle-switch" value="<%= journalContentDisplayContext.isEnableViewCountIncrement() %>" />
			</aui:fieldset>
		</div>
	</div>
</c:if>