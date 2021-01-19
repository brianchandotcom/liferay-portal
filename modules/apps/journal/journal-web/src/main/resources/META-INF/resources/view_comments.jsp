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

<liferay-ui:search-container
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
						<%= HtmlUtil.extractText(content) %>
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
					value="<%= HtmlUtil.extractText(content) %>"
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