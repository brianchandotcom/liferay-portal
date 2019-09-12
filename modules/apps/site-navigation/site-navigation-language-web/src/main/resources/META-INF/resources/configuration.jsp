<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				label="display-settings"
			>
				<div class="display-template">
					<liferay-ddm:template-selector
						className="<%= LanguageEntry.class.getName() %>"
						displayStyle="<%= languagePortletInstanceConfiguration.displayStyle() %>"
						displayStyleGroupId="<%= siteNavigationLanguageDisplayContext.getDisplayStyleGroupId() %>"
						refreshURL="<%= configurationRenderURL %>"
					/>
				</div>

				<aui:input name="preferences--displayCurrentLocale--" type="toggle-switch" value="<%= languagePortletInstanceConfiguration.displayCurrentLocale() %>" />
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
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
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
					languageIds: Liferay.Util.listSelect(currentLanguageIdsInput)
				}
			});
		}
	}
</script>