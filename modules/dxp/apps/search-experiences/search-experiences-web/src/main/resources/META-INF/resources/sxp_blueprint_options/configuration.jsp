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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.search.experiences.model.SXPBlueprint" %><%@
page import="com.liferay.search.experiences.service.SXPBlueprintLocalServiceUtil" %><%@
page import="com.liferay.search.experiences.web.internal.blueprint.options.portlet.preferences.SXPBlueprintOptionsPortletPreferences" %><%@
page import="com.liferay.search.experiences.web.internal.blueprint.options.portlet.preferences.SXPBlueprintOptionsPortletPreferencesImpl" %><%@
page import="com.liferay.search.experiences.web.internal.blueprint.options.util.PortletPreferencesJspUtil" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
SXPBlueprintOptionsPortletPreferences sxpBlueprintOptionsPortletPreferences = new SXPBlueprintOptionsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
%>

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
								"initialFederatedSearchKey", sxpBlueprintOptionsPortletPreferences.getFederatedSearchKeyString()
							).put(
								"initialSXPBlueprintId", sxpBlueprintOptionsPortletPreferences.getSXPBlueprintIdString()
							).put(
								"initialSXPBlueprintTitle", (sxpBlueprint != null) ? HtmlUtil.escape(sxpBlueprint.getTitle(locale)) : StringPool.BLANK
							).put(
								"portletNamespace", liferayPortletResponse.getNamespace()
							).put(
								"preferenceKeyFederatedSearchKey", PortletPreferencesJspUtil.getInputName(SXPBlueprintOptionsPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY)
							).put(
								"preferenceKeySXPBlueprintId", PortletPreferencesJspUtil.getInputName(SXPBlueprintOptionsPortletPreferences.PREFERENCE_KEY_SXP_BLUEPRINT_ID)
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