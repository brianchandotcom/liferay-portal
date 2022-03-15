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

List<Phone> phones = PhoneServiceUtil.getPhones(className, classPK);
%>

<clay:content-row
	containerElement="h3"
	cssClass="sheet-subtitle"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<span class="heading-text"><liferay-ui:message key="phone-numbers" /></span>
	</clay:content-col>

	<clay:content-col>
		<span class="heading-end">
			<liferay-ui:icon
				label="<%= true %>"
				linkCssClass="add-phone-number-link btn btn-secondary btn-sm"
				message="add"
				url='<%=
					PortletURLBuilder.createRenderURL(
						liferayPortletResponse
					).setMVCPath(
						"/common/edit_phone_number.jsp"
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
	curParam="phoneNumberCur"
	deltaParam="phoneNumbersDelta"
	emptyResultsMessage="<%= emptyResultsMessage %>"
	headerNames="phone-number,type,extension,"
	id="phonesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= phones.size() %>"
>
	<liferay-ui:search-container-results
		calculateStartAndEnd="<%= true %>"
		results="<%= phones %>"
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
				<clay:label
					displayType="primary"
					label="primary"
				/>
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