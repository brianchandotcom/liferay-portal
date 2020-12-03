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

		<%
		List<UserToolAssetAddonEntry> selectedUserToolAssetAddonEntries = journalContentDisplayContext.getSelectedUserToolAssetAddonEntries();

		for (UserToolAssetAddonEntry userToolAssetAddonEntry : journalContentDisplayContext.getEnabledUserToolAssetAddonEntries()) {
		%>

			<aui:input checked="<%= selectedUserToolAssetAddonEntries.contains(userToolAssetAddonEntry) %>" id="<%= refererPortletName + userToolAssetAddonEntry.getKey() %>" label="<%= userToolAssetAddonEntry.getLabel(locale) %>" name="userToolAssetAddonEntryKeys" type="checkbox" value="<%= userToolAssetAddonEntry.getKey() %>" />

		<%
		}
		%>

	</clay:sheet-section>

	<clay:sheet-section>
		<div class="sheet-subtitle">
			<liferay-ui:message key="content-metadata" />
		</div>

		<%
		List<ContentMetadataAssetAddonEntry> selectedContentMetadataAssetAddonEntries = journalContentDisplayContext.getSelectedContentMetadataAssetAddonEntries();

		for (ContentMetadataAssetAddonEntry contentMetadataAssetAddonEntry : journalContentDisplayContext.getEnabledContentMetadataAssetAddonEntries()) {
		%>

			<aui:input checked="<%= selectedContentMetadataAssetAddonEntries.contains(contentMetadataAssetAddonEntry) %>" id="<%= refererPortletName + contentMetadataAssetAddonEntry.getKey() %>" label="<%= contentMetadataAssetAddonEntry.getLabel(locale) %>" name="contentMetadataAssetAddonEntryKeys" type="checkbox" value="<%= contentMetadataAssetAddonEntry.getKey() %>" />

		<%
		}
		%>

	</clay:sheet-section>

	<clay:sheet-section>
		<div class="sheet-subtitle">
			<liferay-ui:message key="enable" />
		</div>

		<aui:input id='<%= refererPortletName + "enableViewCountIncrement" %>' inlineLabel="right" label="view-count-increment" labelCssClass="simple-toggle-switch" name="preferences--enableViewCountIncrement--" type="toggle-switch" value="<%= journalContentDisplayContext.isEnableViewCountIncrement() %>" />
	</clay:sheet-section>
</c:if>