<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FriendlyURLSeparatorCompanyConfigurationDisplayContext friendlyURLSeparatorCompanyConfigurationDisplayContext = (FriendlyURLSeparatorCompanyConfigurationDisplayContext)request.getAttribute(FriendlyURLSeparatorCompanyConfigurationDisplayContext.class.getName());

JSONObject errorsJSONObject = friendlyURLSeparatorCompanyConfigurationDisplayContext.getErrorsJSONObject();

String errorMessage = errorsJSONObject.getString("errorMessage");
%>

<c:if test="<%= Validator.isNotNull(errorMessage) %>">
	<clay:alert
		cssClass="mt-4"
		displayType="danger"
		message="<%= errorMessage %>"
	/>
</c:if>

<p class="mt-4 sheet-subtitle text-secondary" id="<portlet:namespace />header">
	<liferay-ui:message key="url-separator" />
</p>

<clay:alert
	cssClass="mb-4"
	displayType="info"
	message="friendly-url-separator-info-message"
/>

<div aria-labelledby="<portlet:namespace />header" role="group">

	<%
	JSONArray friendlyURLSeparatorsJSONArray = friendlyURLSeparatorCompanyConfigurationDisplayContext.getConfigurableFriendlyURLSeparatorsJSONArray();

	for (int i = 0; i < friendlyURLSeparatorsJSONArray.length(); i++) {
		JSONObject friendlyURLSeparatorJSONObject = friendlyURLSeparatorsJSONArray.getJSONObject(i);
	%>

		<div class="form-group">
			<label class="mb-0" for="<%= friendlyURLSeparatorJSONObject.getString("name") %>">
				<%= friendlyURLSeparatorJSONObject.getString("label") %>
			</label>

			<p class="mb-1 small text-secondary">
				<%= friendlyURLSeparatorCompanyConfigurationDisplayContext.getSampleURL() %>
			</p>

			<div class="input-group">
				<div class="input-group-item input-group-item-shrink input-group-prepend">
					<div aria-hidden="true" class="input-group-text">
						/
					</div>
				</div>

				<div class="input-group-append input-group-item">
					<input class="form-control" id="<%= friendlyURLSeparatorJSONObject.getString("name") %>" name="<%= friendlyURLSeparatorJSONObject.getString("name") %>" value="<%= friendlyURLSeparatorJSONObject.getString("value") %>" />
				</div>
			</div>
		</div>

	<%
	}
	%>

	<react:component
		module="{SeparatorFields} from friendly-url-web"
		props="<%= friendlyURLSeparatorCompanyConfigurationDisplayContext.getSeparatorFieldsProps() %>"
	/>
</div>