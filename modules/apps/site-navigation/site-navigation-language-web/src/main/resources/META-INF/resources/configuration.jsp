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
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset
			collapsed="<%= false %>"
			collapsible="<%= true %>"
			label="display-settings"
		>
			<div class="display-template">
				<liferay-template:template-selector
					className="<%= LanguageEntry.class.getName() %>"
					displayStyle="<%= siteNavigationLanguagePortletInstanceConfiguration.displayStyle() %>"
					displayStyleGroupId="<%= siteNavigationLanguageDisplayContext.getDisplayStyleGroupId() %>"
					refreshURL="<%= configurationRenderURL %>"
				/>
			</div>

			<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--displayCurrentLocale--" type="toggle-switch" value="<%= siteNavigationLanguagePortletInstanceConfiguration.displayCurrentLocale() %>" />
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsed="<%= true %>"
			collapsible="<%= true %>"
			label="languages"
		>
			<aui:input name="preferences--languageIds--" type="hidden" />

			<liferay-ui:input-move-boxes
				leftBoxName="currentLanguageIds"
				leftList="<%= siteNavigationLanguageDisplayContext.getCurrentLanguageIdKVPs() %>"
				leftReorder="<%= Boolean.TRUE.toString() %>"
				leftTitle="current"
				rightBoxName="availableLanguageIds"
				rightList="<%= siteNavigationLanguageDisplayContext.getAvailableLanguageIdKVPs() %>"
				rightTitle="available"
			/>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		var currentLanguageIdsInput = Liferay.Util.getFormElement(
			form,
			'currentLanguageIds'
		);

		if (currentLanguageIdsInput) {
			Liferay.Util.postForm(form, {
				data: {
					languageIds: Liferay.Util.getSelectedOptionValues(
						currentLanguageIdsInput
					),
				},
			});
		}
	}
</script>