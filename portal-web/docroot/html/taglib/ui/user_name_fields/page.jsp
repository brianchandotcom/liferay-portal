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

<%@ include file="/html/taglib/ui/user_name_fields/init.jsp" %>

<aui:select label="language" name="languageId">

	<%
	for (Locale curLocale : LanguageUtil.getAvailableLocales()) {
	%>

		<aui:option label="<%= curLocale.getDisplayName(curLocale) %>" lang="<%= LocaleUtil.toW3cLanguageId(curLocale) %>" selected="<%= userLocale.getLanguage().equals(curLocale.getLanguage()) && userLocale.getCountry().equals(curLocale.getCountry()) %>" value="<%= LocaleUtil.toLanguageId(curLocale) %>" />

	<%
	}
	%>

</aui:select>

<aui:script use="liferay-portlet-url">
const form = document.getElementById('<portlet:namespace />fm');
const select = document.getElementById('<portlet:namespace />languageId');

if (form && select) {
	const maxLengthsCache = {};
	const userDetailsURL = Liferay.PortletURL.createURL('<%= themeDisplay.getURLCurrent() %>');
	const userNameFields = document.getElementById('<portlet:namespace />userNameFields');

	select.addEventListener(
		'change',
		function(event) {
			const currentFormData = new FormData(form);

			for (const tuple of currentFormData) {
				const fieldName = tuple[0];

				const field = userNameFields.querySelector('#' + fieldName);

				if (field && field.hasAttribute('maxLength')) {
					maxLengthsCache[fieldName] = field.getAttribute('maxLength');
				}
			}

			userNameFields.insertAdjacentHTML('beforebegin', '<div class="loading-animation" id="<portlet:namespace />loadingUserNameFields"></div>');

			userNameFields.style.display = 'none';

			const cleanUp = function() {
				const loadingAnimation = document.getElementById('<portlet:namespace />loadingUserNameFields');

				if (loadingAnimation) {
					loadingAnimation.parentNode.removeChild(loadingAnimation);
				}

				if (userNameFields.style.display === 'none') {
					userNameFields.style.display = '';
				}

				for (const tuple of currentFormData) {
					const fieldName = tuple[0];

					const newField = userNameFields.querySelector('#' + fieldName);

					if (newField) {
						newField.value = tuple[1];

						if (maxLengthsCache.hasOwnProperty(fieldName)) {
							newField.setAttribute('maxLength', maxLengthsCache[fieldName]);
						}
					}
				}
			};

			userDetailsURL.setParameter('languageId', select.value);

			fetch(userDetailsURL.toString())
				.then(
					function(response) {
						return response.text();
					}
				)
				.then(
					function(responseData) {
						const temp = document.implementation.createHTMLDocument();

						temp.body.innerHTML = responseData;

						const newUserNameFields = temp.getElementById('<portlet:namespace />userNameFields');

						if (newUserNameFields) {
							userNameFields.innerHTML = newUserNameFields.innerHTML;
						}
					}
				)
				.then(cleanUp)
				.catch(cleanUp);
		}
	);
}
</aui:script>

<%
FullNameDefinition fullNameDefinition = FullNameDefinitionFactory.getInstance(userLocale);
%>

<liferay-ui:error exception="<%= ContactNameException.MustHaveFirstName.class %>" message="please-enter-a-valid-first-name" />
<liferay-ui:error exception="<%= ContactNameException.MustHaveValidFullName.class %>" message="please-enter-a-valid-first-middle-and-last-name" />

<div id="<portlet:namespace />userNameFields">

	<%
	for (FullNameField fullNameField : fullNameDefinition.getFullNameFields()) {
		String fieldName = CamelCaseUtil.toCamelCase(fullNameField.getName());
	%>

		<c:choose>
			<c:when test="<%= fullNameField.isFreeText() %>">
				<aui:input bean="<%= bean %>" disabled="<%= !UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, fieldName) %>" model="<%= User.class %>" name="<%= fieldName %>">
					<c:if test="<%= fullNameField.isRequired() %>">
						<aui:validator name="required" />
					</c:if>
				</aui:input>
			</c:when>
			<c:otherwise>
				<aui:select disabled="<%= !UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, fieldName) %>" label="<%= fieldName %>" name='<%= fieldName.concat("Value") %>' showEmptyOption="<%= true %>">

					<%
					String listTypeName = StringPool.BLANK;

					if (selContact != null) {
						int listTypeId = BeanParamUtil.getInteger(selContact, request, fieldName.concat("Id"));

						try {
							ListType listType = ListTypeServiceUtil.getListType(listTypeId);

							listTypeName = listType.getName();
						}
						catch (Exception e) {
						}
					}

					for (String value : fullNameField.getValues()) {
					%>

						<aui:option label="<%= value %>" selected="<%= StringUtil.equalsIgnoreCase(listTypeName, value) %>" value="<%= value %>" />

					<%
					}
					%>

				</aui:select>
			</c:otherwise>
		</c:choose>

	<%
	}
	%>

</div>