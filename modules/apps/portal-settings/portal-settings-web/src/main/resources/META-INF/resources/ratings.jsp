<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

CompanyPortletRatingsDefinitionDisplayContext companyPortletRatingsDefinitionDisplayContext = new CompanyPortletRatingsDefinitionDisplayContext(companyPortletPreferences, request);
%>

<p><liferay-ui:message key="select-the-default-ratings-type-for-the-following-applications" /></p>

<aui:fieldset id='<%= liferayPortletResponse.getNamespace() + "ratingsSettingsContainer" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<%
	Map<String, Map<String, RatingsType>> companyRatingsTypeMaps = companyPortletRatingsDefinitionDisplayContext.getCompanyRatingsTypeMaps();

	for (Map.Entry<String, Map<String, RatingsType>> entry : companyRatingsTypeMaps.entrySet()) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(entry.getKey());
	%>

		<p>
			<strong><%= PortalUtil.getPortletTitle(portlet, application, locale) %></strong>
		</p>

		<%
		Map<String, RatingsType> ratingsTypeMap = companyRatingsTypeMaps.get(entry.getKey());

		Set<String> classNames = ratingsTypeMap.keySet();

		for (String className : classNames) {
			String propertyKey = RatingsDataTransformerUtil.getPropertyKey(className);

			RatingsType ratingsType = ratingsTypeMap.get(className);
		%>

			<aui:select label="<%= (classNames.size() > 1) ? ResourceActionsUtil.getModelResource(locale, className) : StringPool.BLANK %>" name='<%= "settings--" + propertyKey + "--" %>'>

				<%
				for (RatingsType curRatingsType : RatingsType.values()) {
				%>

					<aui:option label="<%= LanguageUtil.get(request, curRatingsType.getValue()) %>" selected="<%= Objects.equals(ratingsType, curRatingsType) %>" value="<%= curRatingsType.getValue() %>" />

				<%
				}
				%>

			</aui:select>

	<%
		}
	}
	%>

</aui:fieldset>

<aui:script use="aui-base">
	var ratingsSettingsContainer = A.one(
		'#<portlet:namespace />ratingsSettingsContainer'
	);

	var ratingsTypeChanged = false;

	ratingsSettingsContainer.delegate(
		'change',
		(event) => {
			ratingsTypeChanged = true;
		},
		'select'
	);

	var form = A.one('#<portlet:namespace />fm');

	form.on('submit', (event) => {
		if (ratingsTypeChanged) {
			Liferay.Util.openConfirmModal({
				message:
					'<%= UnicodeLanguageUtil.get(request, "existing-ratings-data-values-will-be-adapted-to-match-the-new-ratings-type-even-though-it-may-not-be-accurate") %>',
				onConfirm: (isConfirmed) => {
					if (!isConfirmed) {
						event.preventDefault();

						event.stopImmediatePropagation();
					}
				},
			});
		}
	});
</aui:script>