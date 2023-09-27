<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long countryId = ParamUtil.getLong(request, "countryId");

Country country = CountryLocalServiceUtil.fetchCountry(countryId);
%>

<portlet:actionURL name="/address/edit_country" var="editCountryURL" />

<liferay-frontend:edit-form
	action="<%= editCountryURL %>"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (country == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="countryId" type="hidden" value="<%= String.valueOf(countryId) %>" />

	<liferay-ui:error exception="<%= CountryA2Exception.class %>" message="please-enter-a-valid-two-letter-iso-code" />
	<liferay-ui:error exception="<%= CountryA3Exception.class %>" message="please-enter-a-valid-three-letter-iso-code" />
	<liferay-ui:error exception="<%= CountryNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= CountryNumberException.class %>" message="please-enter-a-valid-number" />
	<liferay-ui:error exception="<%= DuplicateCountryException.class %>" message="the-two-letter-iso-code-is-already-used" />

	<%
	int titleMaxLength = ModelHintsUtil.getMaxLength(CountryLocalization.class.getName(), "title");
	%>

	<liferay-ui:error exception="<%= CountryTitleException.MustNotExceedMaximumLength.class %>">
		<liferay-ui:message arguments="<%= String.valueOf(titleMaxLength) %>" key="please-enter-a-name-with-fewer-than-x-characters" />
	</liferay-ui:error>

	<aui:model-context bean="<%= country %>" model="<%= Country.class %>" />

	<liferay-frontend:edit-form-body>
		<div class="form-group">
			<label>
				<liferay-ui:message key="name" />

				<liferay-ui:icon-help message="country-name-field-help" />
			</label>

			<liferay-ui:input-localized
				cssClass="form-group"
				maxLength="<%= String.valueOf(titleMaxLength) %>"
				name="title"
				xml="<%= (country == null) ? StringPool.BLANK : country.getTitleMapAsXML() %>"
			/>
		</div>

		<aui:input helpMessage="country-key-field-help" label="key" name="name" />

		<aui:input checked="<%= (country == null) ? false : country.getBillingAllowed() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="billingAllowed" type="toggle-switch" />

		<aui:input checked="<%= (country == null) ? false : country.getShippingAllowed() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="shippingAllowed" type="toggle-switch" />

		<aui:input id="twoLettersISOCode" label="two-letter-iso-code" name="a2" />

		<aui:input id="threeLettersISOCode" label="three-letter-iso-code" name="a3" />

		<aui:input id="numericISOCode" name="number" />

		<aui:input label="country-calling-code" name="idd" />

		<aui:input checked="<%= (country == null) ? false : country.getSubjectToVAT() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="subjectToVAT" type="toggle-switch" />

		<aui:input id="priority" label="priority" name="position" />

		<aui:input checked="<%= (country == null) ? true : country.isActive() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="active" type="toggle-switch" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect='<%= ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())) %>'
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<c:if test="<%= country == null %>">
	<aui:script require="frontend-js-web/index as frontendJsWeb">
		var {debounce} = frontendJsWeb;

		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var nameInput = form.querySelector('#<portlet:namespace />name');
			var titleInput = form.querySelector('#<portlet:namespace />title');

			if (nameInput && titleInput) {
				var handleOnTitleInput = function (event) {
					var value = event.target.value;

					if (nameInput.hasAttribute('maxLength')) {
						value = value.substring(0, nameInput.getAttribute('maxLength'));
					}

					nameInput.value = value;
				};

				titleInput.addEventListener('input', debounce(handleOnTitleInput, 200));
			}
		}
	</aui:script>
</c:if>