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
String className = (String)request.getAttribute("contact_information.jsp-className");
long classPK = (long)request.getAttribute("contact_information.jsp-classPK");

String emptyResultsMessage = ParamUtil.getString(request, "emptyResultsMessage");

List<EmailAddress> emailAddresses = EmailAddressServiceUtil.getEmailAddresses(className, classPK);
%>

<clay:content-row
	containerElement="h3"
	cssClass="sheet-subtitle"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<span class="heading-text"><liferay-ui:message key="additional-email-addresses" /></span>
	</clay:content-col>

	<clay:content-col>
		<span class="heading-end">
			<liferay-ui:icon
				label="<%= true %>"
				linkCssClass="add-email-address-link btn btn-secondary btn-sm"
				message="add"
				url='<%=
					PortletURLBuilder.createRenderURL(
						liferayPortletResponse
					).setMVCPath(
						"/common/edit_email_address.jsp"
					).setRedirect(
						currentURL
					).setParameter(
						"className", className
					).setParameter(
						"classPK", classPK
					).buildString()
				%>'
			/>
		</span>
	</clay:content-col>
</clay:content-row>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="lfr-search-container-wrapper"
	curParam="emailAddressesCur"
	deltaParam="emailAddressesDelta"
	emptyResultsMessage="<%= emptyResultsMessage %>"
	headerNames="email-address,type,"
	id="emailAddressesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= emailAddresses.size() %>"
>
	<liferay-ui:search-container-results
		calculateStartAndEnd="<%= true %>"
		results="<%= emailAddresses %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.EmailAddress"
		escapedModel="<%= true %>"
		keyProperty="emailAddressId"
		modelVar="emailAddress"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
			name="email-address"
			property="address"
		/>

		<%
		ListType emailAddressListType = ListTypeServiceUtil.getListType(emailAddress.getTypeId());

		String emailAddressTypeKey = emailAddressListType.getName();
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-small"
			name="type"
			value="<%= LanguageUtil.get(request, emailAddressTypeKey) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-smaller"
		>
			<c:if test="<%= emailAddress.isPrimary() %>">
				<clay:label
					displayType="primary"
					label="primary"
				/>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/common/email_address_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>