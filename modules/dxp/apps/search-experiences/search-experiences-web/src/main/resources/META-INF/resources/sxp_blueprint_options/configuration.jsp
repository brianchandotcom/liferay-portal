<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<div>
					<span aria-hidden="true" class="loading-animation"></span>

					<%
					long sxpBlueprintId = PrefsParamUtil.getLong(portletPreferences, request, "sxpBlueprintId");

					SXPBlueprint sxpBlueprint = SXPBlueprintLocalServiceUtil.fetchSXPBlueprint(sxpBlueprintId);
					%>

					<react:component
						module="sxp_blueprint_options/js/configuration/index"
						props='<%=
							HashMapBuilder.<String, Object>put(
								"initialFederatedSearchKey", SXPBlueprintOptionsPortletPreferencesUtil.getValue(portletPreferences, "federatedSearchKey")
							).put(
								"initialSXPBlueprintId", SXPBlueprintOptionsPortletPreferencesUtil.getValue(portletPreferences, "sxpBlueprintId")
							).put(
								"initialSXPBlueprintTitle", (sxpBlueprint != null) ? HtmlUtil.escape(sxpBlueprint.getTitle(locale)) : StringPool.BLANK
							).put(
								"portletNamespace", liferayPortletResponse.getNamespace()
							).put(
								"preferenceKeyFederatedSearchKey", _getInputName("federatedSearchKey")
							).put(
								"preferenceKeySXPBlueprintId", _getInputName("sxpBlueprintId")
							).build()
						%>'
					/>
				</div>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<%!
public String _getInputName(String key) {
	return ParameterMapSettings.PREFERENCES_PREFIX + key + StringPool.DOUBLE_DASH;
}
%>