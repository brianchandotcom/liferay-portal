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

List<Phone> phones = PhoneServiceUtil.getPhones(className, classPK);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="phoneNumbers"
/>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.PHONE %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= PhoneNumberException.class %>" message="please-enter-a-valid-phone-number" />
<liferay-ui:error exception="<%= PhoneNumberExtensionException.class %>" message="please-enter-a-valid-phone-number-extension" />

<h3 class="autofit-row sheet-subtitle">
	<span class="autofit-col autofit-col-expand">
		<span class="heading-text"><liferay-ui:message key="phone-numbers" /></span>
	</span>
	<span class="autofit-col">
		<span class="heading-end">
			<liferay-ui:icon
				cssClass="modify-phone-number-link"
				data="<%=
					new HashMap<String, Object>() {
						{
							put("title", LanguageUtil.get(request, "add-phone-number"));
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
	curParam="phoneNumberCur"
	deltaParam="phoneNumbersDelta"
	emptyResultsMessage="<%= emptyResultsMessage %>"
	headerNames="phone-number,type,extension,"
	id="phonesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= phones.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= phones.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Phone"
		escapedModel="<%= true %>"
		keyProperty="phoneId"
		modelVar="phone"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
			name="phone-number"
			property="number"
		/>

		<%
		ListType phoneListType = ListTypeServiceUtil.getListType(phone.getTypeId());

		String phoneTypeKey = phoneListType.getName();
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-small"
			name="type"
			value="<%= LanguageUtil.get(request, phoneTypeKey) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-small"
			name="extension"
			property="extension"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-smaller"
		>
			<c:if test="<%= phone.isPrimary() %>">
				<span class="label label-primary">
					<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
				</span>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/common/phone_number_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:renderURL var="editPhoneRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/common/edit_phone_number.jsp" />
	<portlet:param name="className" value="<%= className %>" />
</portlet:renderURL>

<aui:script require="<%= contactInformationRequireJS %>">
	ContactInformation.registerContactInformationListener(
		'.modify-phone-number-link a',
		'<%= editPhoneRenderURL.toString() %>',
		545
	);
</aui:script>