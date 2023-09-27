<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset>
			<div>
				<span aria-hidden="true" class="loading-animation"></span>

				<%
				String sxpBlueprintExternalReferenceCode = PrefsParamUtil.getString(portletPreferences, request, "sxpBlueprintExternalReferenceCode");

				SXPBlueprint sxpBlueprint = SXPBlueprintLocalServiceUtil.fetchSXPBlueprintByExternalReferenceCode(sxpBlueprintExternalReferenceCode, themeDisplay.getCompanyId());
				%>

				<react:component
					module="sxp_blueprint_options/js/configuration/index"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"initialFederatedSearchKey", SXPBlueprintOptionsPortletPreferencesUtil.getValue(portletPreferences, "federatedSearchKey")
						).put(
							"initialSXPBlueprintExternalReferenceCode", SXPBlueprintOptionsPortletPreferencesUtil.getValue(portletPreferences, "sxpBlueprintExternalReferenceCode")
						).put(
							"initialSXPBlueprintTitle", (sxpBlueprint != null) ? LanguageUtil.get(request, HtmlUtil.escape(sxpBlueprint.getTitle(locale))) : StringPool.BLANK
						).put(
							"learnMessages", LearnMessageUtil.getJSONObject("search-experiences-web")
						).put(
							"portletNamespace", liferayPortletResponse.getNamespace()
						).put(
							"preferenceKeyFederatedSearchKey", _getInputName("federatedSearchKey")
						).put(
							"preferenceKeySXPBlueprintExternalReferenceCode", _getInputName("sxpBlueprintExternalReferenceCode")
						).build()
					%>'
				/>
			</div>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<%!
public String _getInputName(String key) {
	return ParameterMapSettings.PREFERENCES_PREFIX + key + StringPool.DOUBLE_DASH;
}
%>