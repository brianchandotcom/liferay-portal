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
String displayStyle = journalDisplayContext.getDisplayStyle();
%>

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

		<%
		User userDisplay = UserLocalServiceUtil.fetchUserById(mbMessage.getUserId());

		boolean translate = mbMessage.isFormatBBCode();

		String content = mbMessage.getBody(translate);
		%>

		<c:choose>
			<c:when test='<%= displayStyle.equals("descriptive") %>'>
				<liferay-ui:search-container-column-image
					src="<%= (userDisplay != null) ? userDisplay.getPortraitURL(themeDisplay) : UserConstants.getPortraitURL(themeDisplay.getPathImage(), true, 0, null) %>"
					toggleRowChecker="<%= false %>"
				/>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<h6 class="text-default">
						<%= HtmlUtil.extractText(content) %>
					</h6>

					<h6 class="text-default">
						<strong><liferay-ui:message key="last-updated" />:</strong>

						<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - mbMessage.getModifiedDate().getTime(), true), HtmlUtil.escape(mbMessage.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
					</h6>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:when test='<%= displayStyle.equals("icon") %>'>

				<%
				row.setCssClass("col-md-2 col-sm-4 col-xs-6");
				%>

				<liferay-ui:search-container-column-text>
					<liferay-frontend:vertical-card
						cssClass="entry-display-style"
						imageUrl="<%= (userDisplay != null) ? userDisplay.getPortraitURL(themeDisplay) : UserConstants.getPortraitURL(themeDisplay.getPathImage(), true, 0, null) %>"
						resultRow="<%= row %>"
					>
						<liferay-frontend:vertical-card-header>
							<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - mbMessage.getModifiedDate().getTime(), true), HtmlUtil.escape(mbMessage.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
						</liferay-frontend:vertical-card-header>

						<liferay-frontend:vertical-card-footer>
							<%= content %>
						</liferay-frontend:vertical-card-footer>
					</liferay-frontend:vertical-card>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>
				<liferay-ui:search-container-column-text
					name="author"
					property="userName"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					property="modifiedDate"
				/>

				<liferay-ui:search-container-column-text
					name="message"
					value="<%= HtmlUtil.extractText(content) %>"
				/>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
</liferay-ui:search-container>