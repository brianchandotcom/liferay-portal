<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/search/facets/init.jsp" %>

<%
if (termCollectors.isEmpty()) {
	return;
}

int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
int maxTerms = dataJSONObject.getInt("maxTerms", 10);
boolean showAssetCount = dataJSONObject.getBoolean("showAssetCount", true);

Indexer indexer = FolderSearcher.getInstance();

SearchContext searchContext = SearchContextFactory.getInstance(request);
%>

<div class="<%= cssClass %>" data-facetFieldName="<%= facet.getFieldName() %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

	<ul class="folders unstyled">
		<li class="facet-value default <%= Validator.isNull(fieldParam) ? "current-term" : StringPool.BLANK %>">
			<a data-value="" href="javascript:;"><img alt="" src='<%= themeDisplay.getPathThemeImages() + "/common/folder.png" %>' /><liferay-ui:message key="any" /> <liferay-ui:message key="<%= facetConfiguration.getLabel() %>" /></a>
		</li>

		<%
		long folderId = GetterUtil.getLong(fieldParam);

		for (int i = 0; i < termCollectors.size(); i++) {
			TermCollector termCollector = termCollectors.get(i);

			long curFolderId = GetterUtil.getLong(termCollector.getTerm());

			if (curFolderId == 0) {
				continue;
			}

			searchContext.setFolderIds(new long[] {curFolderId});
			searchContext.setKeywords(StringPool.BLANK);

			Hits results = indexer.search(searchContext);

			if (results.getLength() == 0) {
				continue;
			}

			Document document = results.doc(0);

			Field title = document.getField(Field.TITLE);
		%>

			<c:if test="<%= folderId == curFolderId %>">
				<aui:script use="liferay-token-list">
					Liferay.Search.tokenList.add(
						{
							clearFields: '<%= renderResponse.getNamespace() + facet.getFieldName() %>',
							fieldValues: '<%= curFolderId %>',
							text: '<%= HtmlUtil.escapeJS(title.getValue()) %>'
						}
					);
				</aui:script>
			</c:if>

			<%
			if (((maxTerms > 0) && (i >= maxTerms)) || ((frequencyThreshold > 0) && (frequencyThreshold > termCollector.getFrequency()))) {
				break;
			}
			%>

			<li class="facet-value <%= (folderId == curFolderId) ? "current-term" : StringPool.BLANK %>">
				<a data-value="<%= curFolderId %>" href="javascript:;"><%= HtmlUtil.escape(title.getValue()) %></a><c:if test="<%= showAssetCount %>"> <span class="frequency">(<%= termCollector.getFrequency() %>)</span></c:if>
			</li>

		<%
		}
		%>

	</ul>
</div>