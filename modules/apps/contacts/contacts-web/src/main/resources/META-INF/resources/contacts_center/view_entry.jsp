<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long entryId = ParamUtil.getLong(request, "entryId");
%>

<c:if test="<%= entryId > 0 %>">

	<%
	Entry entry = EntryLocalServiceUtil.getEntry(entryId);

	entry = entry.toEscapedModel();
	%>

	<div class="contacts-profile external-contact">
		<div class="lfr-detail-info">
			<c:if test="<%= showIcon %>">
				<div class="lfr-contact-thumb">
					<img alt="<%= HtmlUtil.escapeAttribute(entry.getFullName()) %>" src="<%= themeDisplay.getPathImage() %>/user_male_portrait?img_id=0&t=" />
				</div>
			</c:if>

			<div class="<%= showIcon ? StringPool.BLANK : "no-icon" %> lfr-contact-info">
				<div class="lfr-contact-name">
					<%= entry.getFullName() %>
				</div>

				<div class="lfr-contact-extra">
					<a href="mailto:<%= entry.getEmailAddress() %>"><%= entry.getEmailAddress() %></a>
				</div>
			</div>
		</div>

		<div class="lfr-detail-info">
			<div class="comments">
				<%= entry.getComments() %>
			</div>
		</div>
	</div>
</c:if>