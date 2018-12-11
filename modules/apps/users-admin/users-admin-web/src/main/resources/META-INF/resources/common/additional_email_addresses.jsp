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
String contactInformationRequireJS = (String)request.getAttribute("contact_information.jsp-contactInformationRequireJS");

String emptyResultsMessage = ParamUtil.getString(request, "emptyResultsMessage");

List<EmailAddress> emailAddresses = EmailAddressServiceUtil.getEmailAddresses(className, classPK);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="additionalEmailAddresses"
/>

<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.EMAIL_ADDRESS %>" message="please-select-a-type" />

<h3 class="autofit-row sheet-subtitle">
	<span class="autofit-col autofit-col-expand">
		<span class="heading-text"><liferay-ui:message key="additional-email-addresses" /></span>
	</span>
	<span class="autofit-col">
		<span class="heading-end">
			<liferay-ui:icon
				cssClass="modify-email-address-link"
				data="<%=
					new HashMap<String, Object>() {
						{
							put("title", LanguageUtil.get(request, "add-email-address"));
						}
					}
				%>"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message="add"
				url="javascript:;"
			/>
		</span>
	</span>
</h3>

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
		results="<%= emailAddresses.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
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
				<span class="label label-primary">
					<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
				</span>
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

<portlet:renderURL var="editEmailAddressRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/common/edit_email_address.jsp" />
	<portlet:param name="className" value="<%= className %>" />
</portlet:renderURL>

<aui:script require="<%= contactInformationRequireJS %>">
	ContactInformation.registerContactInformationListener(
		'.modify-email-address-link a',
		'<%= editEmailAddressRenderURL.toString() %>',
		390
	);
</aui:script>