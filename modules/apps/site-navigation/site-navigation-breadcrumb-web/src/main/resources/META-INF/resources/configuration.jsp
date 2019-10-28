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
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<aui:row>
			<aui:col width="<%= 50 %>">
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<div class="display-template">
							<liferay-ddm:template-selector
								className="<%= BreadcrumbEntry.class.getName() %>"
								displayStyle="<%= siteNavigationBreadcrumbDisplayContext.getDisplayStyle() %>"
								displayStyleGroupId="<%= siteNavigationBreadcrumbDisplayContext.getDisplayStyleGroupId() %>"
								refreshURL="<%= configurationRenderURL %>"
							/>
						</div>
					</liferay-frontend:fieldset>

					<liferay-frontend:fieldset
						id='<%= renderResponse.getNamespace() + "checkBoxes" %>'
					>
						<aui:col width="<%= 50 %>">
							<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) + "_showCurrentGroup" %>' label="show-current-site" name="preferences--showCurrentGroup--" type="toggle-switch" value="<%= siteNavigationBreadcrumbDisplayContext.isShowCurrentGroup() %>" />
							<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) + "_showGuestGroup" %>' label="show-guest-site" name="preferences--showGuestGroup--" type="toggle-switch" value="<%= siteNavigationBreadcrumbDisplayContext.isShowGuestGroup() %>" />
						</aui:col>

						<aui:col width="<%= 50 %>">
							<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) + "_showLayout" %>' label="show-page" name="preferences--showLayout--" type="toggle-switch" value="<%= siteNavigationBreadcrumbDisplayContext.isShowLayout() %>" />
							<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) + "_showParentGroups" %>' label="show-parent-sites" name="preferences--showParentGroups--" type="toggle-switch" value="<%= siteNavigationBreadcrumbDisplayContext.isShowParentGroups() %>" />
							<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) + "_showPortletBreadcrumb" %>' label="show-application-breadcrumb" name="preferences--showPortletBreadcrumb--" type="toggle-switch" value="<%= siteNavigationBreadcrumbDisplayContext.isShowPortletBreadcrumb() %>" />
						</aui:col>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</aui:col>

			<aui:col width="<%= 50 %>">
				<liferay-portlet:preview
					portletName="<%= siteNavigationBreadcrumbDisplayContext.getPortletResource() %>"
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
	var data = {};

	data[
		'_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_displayStyle'
	] = '<%= siteNavigationBreadcrumbDisplayContext.getDisplayStyle() %>';
	data[
		'_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_showCurrentGroup'
	] = <%= siteNavigationBreadcrumbDisplayContext.isShowCurrentGroup() %>;
	data[
		'_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_showGuestGroup'
	] = <%= siteNavigationBreadcrumbDisplayContext.isShowGuestGroup() %>;
	data[
		'_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_showLayout'
	] = <%= siteNavigationBreadcrumbDisplayContext.isShowLayout() %>;
	data[
		'_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_showParentGroups'
	] = <%= siteNavigationBreadcrumbDisplayContext.isShowParentGroups() %>;
	data[
		'_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_showPortletBreadcrumb'
	] = <%= siteNavigationBreadcrumbDisplayContext.isShowPortletBreadcrumb() %>;

	var selectDisplayStyle = document.getElementById(
		'<portlet:namespace />displayStyle'
	);

	if (selectDisplayStyle) {
		selectDisplayStyle.addEventListener('change', function(event) {
			if (selectDisplayStyle.selectedIndex > -1) {
				data[
					'_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_displayStyle'
				] = selectDisplayStyle.value;

				Liferay.Portlet.refresh(
					'#p_p_id_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_',
					data
				);
			}
		});
	}

	var checkBoxes = document.getElementById('<portlet:namespace />checkBoxes');

	if (checkBoxes) {
		checkBoxes.addEventListener('change', function(event) {
			if (event.target.classList.contains('toggle-switch')) {
				var target = event.target;

				data[target.dataset.key] = target.checked;

				Liferay.Portlet.refresh(
					'#p_p_id_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_',
					data
				);
			}
		});
	}

	var handler = Liferay.on('portletReady', function(event) {
		Liferay.Portlet.refresh(
			'#p_p_id_<%= HtmlUtil.escapeJS(siteNavigationBreadcrumbDisplayContext.getPortletResource()) %>_',
			data
		);

		handler.detach();

		handler = null;
	});

	var destroyHandler = Liferay.on('destroyHandler', function(event) {
		if (handler) {
			handler.detach();

			handler = null;
		}
	});
</aui:script>