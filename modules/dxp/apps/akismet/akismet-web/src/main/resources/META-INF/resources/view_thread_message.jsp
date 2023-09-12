<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(portletURL.toString());
renderResponse.setTitle(LanguageUtil.get(request, "view-message"));

long messageId = ParamUtil.getLong(request, "messageId");

MBMessage message = null;

if (messageId > 0) {
	message = MBMessageLocalServiceUtil.getMessage(messageId);
}
%>

<a id="<portlet:namespace />message_<%= message.getMessageId() %>"></a>

<clay:container-fluid>
	<div class="spam" style="margin: 10px;">
		<portlet:actionURL name="markNotSpamMBMessages" var="markAsHamURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
			<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<portlet:param name="notSpamMBMessageIds" value="<%= String.valueOf(message.getMessageId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="../mail/compose"
			label="<%= true %>"
			message="not-spam"
			url="<%= markAsHamURL %>"
		/>
	</div>

	<div class="card list-group-card panel">
		<div class="panel-heading">
			<div class="card-row card-row-padded">
				<div class="card-col-field">
					<div class="list-group-card-icon">
						<liferay-user:user-portrait
							size="lg"
							userId="<%= !message.isAnonymous() ? message.getUserId() : 0 %>"
						/>
					</div>
				</div>

				<div class="card-col-content card-col-gutters">

					<%
					String messageUserName = "anonymous";

					if (!message.isAnonymous()) {
						messageUserName = message.getUserName();
					}

					Date modifiedDate = message.getModifiedDate();

					String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);

					String userDisplayText = LanguageUtil.format(request, "x-modified-x-ago", new Object[] {messageUserName, modifiedDateDescription});
					%>

					<h5 class="message-user-display text-default" title="<%= HtmlUtil.escapeAttribute(userDisplayText) %>">
						<%= HtmlUtil.escape(userDisplayText) %>
					</h5>

					<h4 title="<%= HtmlUtil.escape(message.getSubject()) %>">
						<%= HtmlUtil.escape(message.getSubject()) %>

						<c:if test="<%= message.isAnswer() %>">
							(<liferay-ui:message key="answer[noun]" />)
						</c:if>
					</h4>

					<%
					User messageUser = UserLocalServiceUtil.fetchUser(message.getUserId());
					%>

					<c:if test="<%= (messageUser != null) && !messageUser.isGuestUser() %>">

						<%
						String[] ranks = MBStatsUserLocalServiceUtil.getUserRank(themeDisplay.getSiteGroupId(), themeDisplay.getLanguageId(), message.getUserId());
						%>

						<c:if test="<%= Validator.isNotNull(ranks[1]) %>">
							<span class="h5 text-default" title="<%= HtmlUtil.escape(ranks[1]) %>">
								<%= HtmlUtil.escape(ranks[1]) %>
							</span>
						</c:if>

						<c:if test="<%= Validator.isNotNull(ranks[0]) %>">
							<span class="h5 text-default" title="<%= HtmlUtil.escape(ranks[0]) %>">
								<%= HtmlUtil.escape(ranks[0]) %>
							</span>
						</c:if>

						<span class="h5 text-default">
							<span><liferay-ui:message key="posts" />:</span> <%= MBStatsUserLocalServiceUtil.getMessageCount(scopeGroupId, message.getUserId()) %>
						</span>
						<span class="h5 text-default">
							<span><liferay-ui:message key="join-date" />:</span> <%= dateFormatDate.format(messageUser.getCreateDate()) %>
						</span>

						<c:if test="<%= !message.isApproved() %>">
							<span class="h5 text-default">
								<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= message.getStatus() %>" />
							</span>
						</c:if>
					</c:if>
				</div>
			</div>
		</div>

		<div class="divider"></div>

		<div class="panel-body">

			<%
			String msgBody = message.getBody();

			if (message.isFormatBBCode()) {
				msgBody = MBUtil.getBBCodeHTML(msgBody, themeDisplay.getPathThemeImages());
			}
			%>

			<div class="card-row card-row-padded message-content">
				<%= msgBody %>
			</div>
		</div>
	</div>
</clay:container-fluid>