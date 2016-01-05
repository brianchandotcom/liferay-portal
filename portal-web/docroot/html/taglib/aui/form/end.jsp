<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/aui/form/init.jsp" %>

	<c:if test="<%= Validator.isNotNull(onSubmit) %>">
		</fieldset>
	</c:if>
</form>

<aui:script use="liferay-form">
	Liferay.Form.register(
		{
			id: '<%= namespace + name %>'

			<c:if test="<%= validatorTagsMap != null %>">
				, fieldRules: [

					<%
					int i = 0;

					for (String fieldName : validatorTagsMap.keySet()) {
						List<ValidatorTag> validatorTags = validatorTagsMap.get(fieldName);

						for (ValidatorTag validatorTag : validatorTags) {
					%>

							<%= i != 0 ? StringPool.COMMA : StringPool.BLANK %>

							{
								body: <%= validatorTag.getBody() %>,
								custom: <%= validatorTag.isCustom() %>,
								customValidatorRequired: <%= validatorTag.isCustomValidatorRequired() %>,
								errorMessage: '<%= UnicodeLanguageUtil.get(pageContext, validatorTag.getErrorMessage()) %>',
								fieldName: '<%= namespace + HtmlUtil.escapeJS(fieldName) %>',
								validatorName: '<%= validatorTag.getName() %>'
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
		}
	);

	<c:if test="<%= Validator.isNotNull(onSubmit) %>">
		A.all('#<%= namespace + name %> .input-container').removeAttribute('disabled');
	</c:if>
</aui:script>