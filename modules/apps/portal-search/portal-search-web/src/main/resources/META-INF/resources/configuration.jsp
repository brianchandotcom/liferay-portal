<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<clay:alert
	cssClass="border-left-0 border-right-0 rounded-0"
	displayType="warning"
>
	<c:set var="deprecatedApps">
		<liferay-learn:message
			key="deprecated-apps"
			resource="portal-search-web"
		/>
	</c:set>

	<liferay-ui:message arguments="${deprecatedApps}" key="classic-search-portlet-has-been-deprecated-x" />

	<c:set var="searchPagesAndWidgets">
		<liferay-learn:message
			key="search-pages-and-widgets"
			resource="portal-search-web"
		/>
	</c:set>

	<liferay-ui:message arguments="${searchPagesAndWidgets}" key="please-migrate-your-search-pages-x" />
</clay:alert>

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "if (" + liferayPortletResponse.getNamespace() + "saveConfiguration) {event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveConfiguration();}" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value='<%= ParamUtil.getString(request, "tabs2") %>' />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:tabs
			formName="fm"
			names="display-settings,spell-check-settings,other-settings"
			param="tabs2"
			refresh="<%= false %>"
		>
			<liferay-ui:section>
				<%@ include file="/display_settings.jspf" %>
			</liferay-ui:section>

			<liferay-ui:section>
				<%@ include file="/spell_check_settings.jspf" %>
			</liferay-ui:section>

			<liferay-ui:section>
				<%@ include file="/other_settings.jspf" %>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>