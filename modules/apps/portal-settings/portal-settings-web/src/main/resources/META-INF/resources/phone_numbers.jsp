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

<c:choose>
	<c:when test="<%= RoleLocalServiceUtil.hasUserRole(user.getUserId(), company.getCompanyId(), RoleConstants.ADMINISTRATOR, true) %>">

		<%
		String className = (String)request.getAttribute("phones.className");
		long classPK = (Long)request.getAttribute("phones.classPK");

		List<Phone> phones = Collections.emptyList();

		int[] phonesIndexes = null;

		String phonesIndexesParam = ParamUtil.getString(request, "phonesIndexes");

		if (Validator.isNotNull(phonesIndexesParam)) {
			phones = new ArrayList<Phone>();

			phonesIndexes = StringUtil.split(phonesIndexesParam, 0);

			for (int phonesIndex : phonesIndexes) {
				phones.add(new PhoneImpl());
			}
		}
		else {
			if (classPK > 0) {
				phones = PhoneServiceUtil.getPhones(className, classPK);

				phonesIndexes = new int[phones.size()];

				for (int i = 0; i < phones.size(); i++) {
					phonesIndexes[i] = i;
				}
			}

			if (phones.isEmpty()) {
				phones = new ArrayList<Phone>();

				phones.add(new PhoneImpl());

				phonesIndexes = new int[] {0};
			}

			if (phonesIndexes == null) {
				phonesIndexes = new int[0];
			}
		}
		%>

		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="phoneNumbers"
		/>

		<div class="alert alert-info">
			<liferay-ui:message key="phone-number-and-type-are-required-fields.-extension-must-be-numeric" />
		</div>

		<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.PHONE %>" message="please-select-a-type" />
		<liferay-ui:error exception="<%= PhoneNumberException.class %>" message="please-enter-a-valid-phone-number" />
		<liferay-ui:error exception="<%= PhoneNumberExtensionException.class %>" message="please-enter-a-valid-phone-number-extension" />

		<aui:fieldset id='<%= renderResponse.getNamespace() + "phoneNumbers" %>'>

			<%
			for (int i = 0; i < phonesIndexes.length; i++) {
				int phonesIndex = phonesIndexes[i];

				Phone phone = phones.get(i);
			%>

				<aui:model-context bean="<%= phone %>" model="<%= Phone.class %>" />

				<div class="form-group-autofit lfr-form-row">
					<aui:input name='<%= "phoneId" + phonesIndex %>' type="hidden" value="<%= phone.getPhoneId() %>" />

					<div class="form-group-item">
						<aui:input fieldParam='<%= "phoneNumber" + phonesIndex %>' id='<%= "phoneNumber" + phonesIndex %>' inlineField="<%= true %>" name="number" />
					</div>

					<div class="form-group-item">
						<aui:input fieldParam='<%= "phoneExtension" + phonesIndex %>' id='<%= "phoneExtension" + phonesIndex %>' inlineField="<%= true %>" name="extension" />
					</div>

					<div class="form-group-item">
						<aui:select inlineField="<%= true %>" label="type" listType="<%= className + ListTypeConstants.PHONE %>" name='<%= "phoneTypeId" + phonesIndex %>' />
					</div>

					<div class="form-group-item form-group-item-label-spacer">
						<aui:input checked="<%= phone.isPrimary() %>" id='<%= "phonePrimary" + phonesIndex %>' inlineField="<%= true %>" label="primary" name="phonePrimary" type="radio" value="<%= phonesIndex %>" />
					</div>
				</div>

			<%
			}
			%>

			<aui:input name="phonesIndexes" type="hidden" value="<%= StringUtil.merge(phonesIndexes) %>" />
		</aui:fieldset>

		<aui:script use="liferay-auto-fields">
			new Liferay.AutoFields({
				contentBox: '#<portlet:namespace />phoneNumbers',
				fieldIndexes: '<portlet:namespace />phonesIndexes',
				namespace: '<portlet:namespace />'
			}).render();
		</aui:script>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-have-the-required-permissions-to-access-this-content" />
		</div>
	</c:otherwise>
</c:choose>