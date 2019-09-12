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
	fluid="<%= true %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<aui:row>
			<aui:col width="<%= 50 %>">
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset
						cssClass="ml-3"
					>
						<aui:row>
							<aui:select name="preferences--sites--" value="<%= sitesDirectoryDisplayContext.getSites() %>">
								<aui:option label="<%= SitesDirectoryTag.SITES_TOP_LEVEL %>" />
								<aui:option label="<%= SitesDirectoryTag.SITES_PARENT_LEVEL %>" />
								<aui:option label="<%= SitesDirectoryTag.SITES_SIBLINGS %>" />
								<aui:option label="<%= SitesDirectoryTag.SITES_CHILDREN %>" />
							</aui:select>
						</aui:row>

						<aui:row>
							<aui:select name="preferences--displayStyle--" value="<%= sitesDirectoryDisplayContext.getDisplayStyle() %>">
								<aui:option label="icon" />
								<aui:option label="descriptive" />
								<aui:option label="list" />
								<aui:option label="list-hierarchy" />
							</aui:select>
						</aui:row>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</aui:col>

			<aui:col width="<%= 50 %>">
				<liferay-portlet:preview
					portletName="<%= portletResource %>"
					queryString="struts_action=/sites_directory/view"
					showBorders="<%= true %>"
				/>
			</aui:col>
		</aui:row>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script sandbox="<%= true %>">
	function refreshPreview(displayStyle, sites) {
		var data = Liferay.Util.ns('_<%= HtmlUtil.escapeJS(portletResource) %>_', {
			displayStyle: displayStyle,
			sites: sites
		});

		Liferay.Portlet.refresh(
			'#p_p_id_<%= HtmlUtil.escapeJS(portletResource) %>_',
			data
		);
	}

	var form = document.<portlet:namespace />fm;

	var displayStyleSelect = Liferay.Util.getFormElement(form, 'displayStyle');
	var sitesSelect = Liferay.Util.getFormElement(form, 'sites');

	if (displayStyleSelect && sitesSelect) {
		form.addEventListener('change', function() {
			refreshPreview(displayStyleSelect.value, sitesSelect.value);
		});

		form.addEventListener('select', function() {
			refreshPreview(displayStyleSelect.value, sitesSelect.value);
		});
	}
</aui:script>