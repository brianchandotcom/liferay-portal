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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.search.tuning.blueprints.exception.NoSuchBlueprintException" %><%@
page import="com.liferay.portal.search.tuning.blueprints.model.Blueprint" %><%@
page import="com.liferay.portal.search.tuning.blueprints.options.web.internal.portlet.preferences.BlueprintsOptionsPortletPreferences" %><%@
page import="com.liferay.portal.search.tuning.blueprints.options.web.internal.portlet.preferences.BlueprintsOptionsPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.tuning.blueprints.options.web.internal.util.PortletPreferencesJspUtil" %><%@
page import="com.liferay.portal.search.tuning.blueprints.service.BlueprintLocalServiceUtil" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
BlueprintsOptionsPortletPreferences blueprintsOptionsPortletPreferences = new BlueprintsOptionsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
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
	<aui:input name="<%= PortletPreferencesJspUtil.getInputName(BlueprintsOptionsPortletPreferences.PREFERENCE_KEY_BLUEPRINT_ID) %>" type="hidden" value="<%= blueprintsOptionsPortletPreferences.getBlueprintIdString() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<div class="form-group" id="<portlet:namespace />BlueprintSelection">
					<label><liferay-ui:message key="blueprint" /></label>

					<div class="input-group">
						<div class="input-group-item">

							<%
							long blueprintId = PrefsParamUtil.getLong(portletPreferences, request, "blueprintId");

							Blueprint blueprint = null;

							try {
								blueprint = BlueprintLocalServiceUtil.getBlueprint(blueprintId);
							}
							catch (NoSuchBlueprintException noSuchBlueprintException) {
							}
							%>

							<input class="form-control" id="<%= liferayPortletResponse.getNamespace() + "blueprintName" %>" name="<%= liferayPortletResponse.getNamespace() + "blueprintName" %>" readonly="readonly" value="<%= Validator.isNotNull(blueprint) ? blueprint.getTitle(locale) : StringPool.BLANK %>" />
						</div>

						<div class="input-group-item input-group-item-shrink">
							<aui:button name="selectBlueprintButton" value="select" />
						</div>

						<%
						String taglibHandleRemoveBlueprint = "Liferay.Util.removeEntitySelection('blueprintId', 'blueprintName', this, '" + liferayPortletResponse.getNamespace() + "');";
						%>

						<div class="input-group-item input-group-item-shrink">
							<aui:button name="removeBlueprintButton" onClick="<%= taglibHandleRemoveBlueprint %>" value="remove" />
						</div>
					</div>

					<aui:script>
						var <portlet:namespace />selectBlueprintButton = document.getElementById(
							'<portlet:namespace />selectBlueprintButton'
						);

						if (<portlet:namespace />selectBlueprintButton) {
							<portlet:namespace />selectBlueprintButton.addEventListener(
								'click',
								(event) => {
									Liferay.Util.selectEntity(
										{
											dialog: {
												constrain: true,
												destroyOnHide: true,
												modal: true,
											},

											<%
											String portletId = PortletProviderUtil.getPortletId(Blueprint.class.getName(), PortletProvider.Action.BROWSE);
											%>

											id:
												'<%= PortalUtil.getPortletNamespace(portletId) %>selectBlueprint',
											title:
												'<liferay-ui:message arguments="blueprint" key="select-x" />',

											<%
											PortletURL selectBlueprintURL = PortletProviderUtil.getPortletURL(request, Blueprint.class.getName(), PortletProvider.Action.BROWSE);

											selectBlueprintURL.setWindowState(LiferayWindowState.POP_UP);
											%>

											uri: '<%= selectBlueprintURL.toString() %>',
										},
										(event) => {
											var blueprintId = document.getElementById(
												'<portlet:namespace />blueprintId'
											);

											if (blueprintId) {
												blueprintId.value = event.entityid;
											}

											var blueprintName = document.getElementById(
												'<portlet:namespace />blueprintName'
											);

											if (blueprintName) {
												blueprintName.value = event.entityname;
											}

											Liferay.Util.toggleDisabled(
												'#<portlet:namespace />removeBlueprintButton',
												false
											);
										}
									);
								}
							);
						}
					</aui:script>
				</div>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>