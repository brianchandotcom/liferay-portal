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
String validDomains = ParamUtil.getString(request, "validDomains");

String message;

if (Validator.isNotNull(validDomains)) {
	message = "the-following-email-domains-are-shared-across-all-of-this-users-accounts";
}
else {
	message = "there-are-no-shared-email-domains-for-this-users-accounts";
}
%>

<clay:container-fluid>
	<clay:alert
		message="<%= message %>"
	/>

	<c:if test="<%= Validator.isNotNull(validDomains) %>">
		<liferay-ui:search-container>
			<liferay-ui:search-container-results
				results="<%= ListUtil.fromArray(StringUtil.split(validDomains)) %>"
			/>

			<liferay-ui:search-container-row
				className="java.lang.String"
				modelVar="domain"
			>
				<liferay-ui:search-container-column-text
					value="<%= domain %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</c:if>
</clay:container-fluid>