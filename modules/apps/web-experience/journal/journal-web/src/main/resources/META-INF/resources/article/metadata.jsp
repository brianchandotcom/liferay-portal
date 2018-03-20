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

DDMStructure ddmStructure = (DDMStructure)request.getAttribute("edit_article.jsp-structure");

boolean changeStructure = GetterUtil.getBoolean(request.getAttribute("edit_article.jsp-changeStructure"));
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="categorization"
/>

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<liferay-asset:asset-categories-error />

<liferay-asset:asset-tags-error />

<%
long classPK = 0;
double priority = 0;

if (article != null) {
	classPK = article.getResourcePrimKey();

	if (!article.isApproved() && (article.getVersion() != JournalArticleConstants.VERSION_DEFAULT)) {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), article.getPrimaryKey());

		if (assetEntry != null) {
			classPK = article.getPrimaryKey();
			priority = assetEntry.getPriority();
		}
	}
	else {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), article.getResourcePrimKey());

		if (assetEntry != null) {
			priority = assetEntry.getPriority();
		}
	}
}
%>

<div class="metadata">
	<aui:field-wrapper>
		<liferay-asset:asset-categories-selector
			className="<%= JournalArticle.class.getName() %>"
			classPK="<%= classPK %>"
			classTypePK="<%= ddmStructure.getStructureId() %>"
			ignoreRequestValue="<%= changeStructure %>"
		/>
	</aui:field-wrapper>

	<aui:field-wrapper label="tags">
		<liferay-asset:asset-tags-selector
			className="<%= JournalArticle.class.getName() %>"
			classPK="<%= classPK %>"
			ignoreRequestValue="<%= changeStructure %>"
		/>
	</aui:field-wrapper>

	<aui:field-wrapper label="priority">
		<aui:input label="" name="assetPriority" type="text" value="<%= priority %>">
			<aui:validator name="number" />

			<aui:validator name="min">[0]</aui:validator>
		</aui:input>
	</aui:field-wrapper>

	<c:if test="<%= CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), JournalArticle.class.getName(), classPK, null) %>">
		<liferay-expando:custom-attribute-list
			className="<%= JournalArticle.class.getName() %>"
			classPK="<%= (article != null) ? article.getPrimaryKey() : 0 %>"
			editable="<%= true %>"
			label="<%= true %>"
		/>
	</c:if>
</div>