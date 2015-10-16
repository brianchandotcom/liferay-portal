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

<liferay-ui:search-container>
	<liferay-ui:search-container-results
		resultsVar="articleSearchContainerResults"
	>

		<%
		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute(Field.CLASS_NAME_ID, PortalUtil.getClassNameId(JournalArticle.class));

		searchContext.setAttribute("discussion", true);

		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		Hits hits = indexer.search(searchContext);

		List<MBMessage> mbMessages = new ArrayList<MBMessage>();

		for (Document document : hits.getDocs()) {
			long entryClassPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

			MBMessage mbMessage = MBMessageLocalServiceUtil.fetchMBMessage(entryClassPK);

			mbMessages.add(mbMessage);
		}

		searchContainer.setTotal(hits.getLength());
		searchContainer.setResults(mbMessages);
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="MBMessage"
		cssClass="entry-display-style selectable"
		modelVar="mbMessage"
	>
		<liferay-ui:search-container-column-text
			name="author"
			property="userName"
		/>

		<liferay-ui:search-container-column-date
			name="modified-date"
			property="modifiedDate"
		/>

		<%
		boolean translate = mbMessage.isFormatBBCode();

		String content = mbMessage.getBody(translate);
		%>

		<liferay-ui:search-container-column-text
			name="message"
			value="<%= HtmlUtil.extractText(content) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= journalDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
</liferay-ui:search-container>