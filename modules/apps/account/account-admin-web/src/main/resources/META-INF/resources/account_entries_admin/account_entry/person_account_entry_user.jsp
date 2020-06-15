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
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

Optional<User> personAccountEntryUserOptional = accountEntryDisplay.getPersonAccountEntryUserOptional();

List<User> userList = personAccountEntryUserOptional.map(Collections::singletonList).orElse(Collections.emptyList());
long userId = personAccountEntryUserOptional.map(User::getUserId).orElse(0L);
%>

<clay:sheet-section>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="user" /></span>
		</clay:content-col>
	</clay:content-row>

	<aui:input name="personAccountEntryUserId" type="hidden" value="<%= String.valueOf(userId) %>" />

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		emptyResultsMessage="assign-a-user-to-this-person-account"
		id="personAccountEntryUserSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= userList %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				property="fullName"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="email-address"
				property="emailAddress"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="job-title"
				property="jobTitle"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:sheet-section>