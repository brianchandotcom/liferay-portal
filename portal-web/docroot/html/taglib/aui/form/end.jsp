<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/aui/form/init.jsp" %>

	<c:if test="<%= (checkboxNames != null) && !checkboxNames.isEmpty() %>">
		<aui:input name="checkboxNames" type="hidden" value="<%= StringUtil.merge(checkboxNames) %>" />
	</c:if>

	<c:if test="<%= Validator.isNotNull(onSubmit) %>">
		</div>
	</c:if>
</form>

<%
String fullName = namespace + HtmlUtil.escapeJS(name);
%>

<aui:script use="liferay-form">
	Liferay.Form.register(
		{
			id: '<%= fullName %>'

			<c:if test="<%= validatorTagsMap != null %>">
				, fieldRules: [

					<%
					int i = 0;

					for (Map.Entry<String, List<ValidatorTag>> entry : validatorTagsMap.entrySet()) {
						String fieldName = entry.getKey();

						List<ValidatorTag> validatorTags = entry.getValue();

						for (ValidatorTag validatorTag : validatorTags) {
							String errorMessage = validatorTag.getErrorMessage();

							if (Objects.equals(validatorTag.getName(), "required") && Validator.isNull(errorMessage)) {
								errorMessage = UnicodeLanguageUtil.format(resourceBundle, "the-x-field-is-required", TextFormatter.format(fieldName, TextFormatter.K), true);
							}
							else {
								errorMessage = UnicodeLanguageUtil.get(resourceBundle, validatorTag.getErrorMessage());
							}
					%>

							<%= (i != 0) ? StringPool.COMMA : StringPool.BLANK %>

							{
								body: <%= validatorTag.getBody() %>,
								custom: <%= validatorTag.isCustom() %>,
								errorMessage: '<%= errorMessage %>',
								fieldName: '<%= namespace + HtmlUtil.escapeJS(fieldName) %>',
								validatorName: '<%= HtmlUtil.escapeJS(validatorTag.getName()) %>'
							}

					<%
							i++;
						}
					}
					%>

				]
			</c:if>

			<c:if test="<%= Validator.isNotNull(onSubmit) %>">
				, onSubmit: function(event) {
					<%= onSubmit %>
				}
			</c:if>

			, validateOnBlur: <%= validateOnBlur %>
		}
	);

	var onDestroyPortlet = function(event) {
		if (event.portletId === '<%= portletDisplay.getId() %>') {
			delete Liferay.Form._INSTANCES['<%= fullName %>'];
		}
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);

	<c:if test="<%= Validator.isNotNull(onSubmit) %>">
		A.all('#<%= fullName %> .input-container').removeAttribute('disabled');
	</c:if>

	Liferay.fire(
		'<portlet:namespace />formReady',
		{
			formName: '<%= fullName %>'
		}
	);
</aui:script>