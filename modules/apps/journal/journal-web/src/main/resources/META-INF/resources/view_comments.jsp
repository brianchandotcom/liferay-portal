<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:search-container
	cssClass='<%= journalDisplayContext.isSearch() ? "pt-0" : StringPool.BLANK %>'
	emptyResultsMessage="no-comment-was-found"
	searchContainer="<%= journalDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.message.boards.model.MBMessage"
		modelVar="mbMessage"
	>

		<%
		User userDisplay = UserLocalServiceUtil.fetchUserById(mbMessage.getUserId());

		boolean translate = mbMessage.isFormatBBCode();

		String content = mbMessage.getBody(translate);
		%>

		<c:choose>
			<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "descriptive") %>'>
				<liferay-ui:search-container-column-image
					src="<%= (userDisplay != null) ? userDisplay.getPortraitURL(themeDisplay) : UserConstants.getPortraitURL(themeDisplay.getPathImage(), true, 0, null) %>"
					toggleRowChecker="<%= false %>"
				/>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<h6 class="text-default">
						<%= HtmlParserUtil.extractText(content) %>
					</h6>

					<h6 class="text-default">
						<strong><liferay-ui:message key="last-updated" />:</strong>

						<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - mbMessage.getModifiedDate().getTime(), true), HtmlUtil.escape(mbMessage.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
					</h6>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "icon") %>'>
				<liferay-ui:search-container-column-text>
					<clay:vertical-card
						verticalCard="<%= new JournalArticleCommentsVerticalCard(mbMessage, renderRequest) %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>
				<liferay-ui:search-container-column-text
					name="author"
					property="userName"
					truncate="<%= true %>"
				/>

				<liferay-ui:search-container-column-text
					name="message"
					truncate="<%= true %>"
					value="<%= HtmlParserUtil.extractText(content) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					property="modifiedDate"
				/>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="<%= journalDisplayContext.getDisplayStyle() %>"
		markupView="lexicon"
	/>
</liferay-ui:search-container>