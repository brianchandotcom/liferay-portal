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

<%
String rootLayoutType = siteNavigationMenuDisplayContext.getRootLayoutType();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:row>
				<aui:col width="<%= 50 %>">
					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset cssClass="ml-3">
							<div class="display-template">
								<liferay-ddm:template-selector
									className="<%= NavItem.class.getName() %>"
									displayStyle="<%= siteNavigationMenuDisplayContext.getDisplayStyle() %>"
									displayStyleGroupId="<%= siteNavigationMenuDisplayContext.getDisplayStyleGroupId() %>"
									refreshURL="<%= configurationRenderURL %>"
								/>
							</div>
						</aui:fieldset>

						<aui:fieldset cssClass="p-3" label="menu-items-to-show">
							<div id="<portlet:namespace />customDisplayOptions">
								<aui:row>
									<aui:col width="<%= 80 %>">
										<aui:select id="rootLayoutType" label="start-with-menu-items-in" name="preferences--rootLayoutType--" value="<%= rootLayoutType %>">
											<aui:option label="level" value="absolute" />
											<aui:option label="level-relative-to-the-current-menu-item" value="relative" />
											<aui:option label="select" value="select" />
										</aui:select>
									</aui:col>

									<aui:col width="<%= 20 %>">
										<div class="mt-4 <%= rootLayoutType.equals("parent-at-level") || rootLayoutType.equals("relative-parent-up-by") ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />rootLayoutLevel">
											<aui:select label="" name="preferences--rootLayoutLevel--">

												<%
												for (int i = 0; i <= 4; i++) {
												%>

													<aui:option label="<%= i %>" selected="<%= siteNavigationMenuDisplayContext.getRootLayoutLevel() == i %>" />

												<%
												}
												%>

											</aui:select>
										</div>
									</aui:col>
								</aui:row>

								<aui:row>
									<aui:col width="<%= 80 %>">
										<div class="mb-3 <%= rootLayoutType.equals("select") ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />rootLayoutUuidPanel">
											<aui:input label="" name="rootLayoutName" type="resource" value="<%= siteNavigationMenuDisplayContext.getRootLayoutName() %>" />
											<aui:input id="rootLayoutUuid" ignoreRequestValue="<%= true %>" name="preferences--rootLayoutUuid--" type="hidden" value="<%= siteNavigationMenuDisplayContext.getRootLayoutUuid() %>" />

											<aui:button name="chooseRootPage" value="choose" />
										</div>
									</aui:col>
								</aui:row>

								<aui:row>
									<aui:col width="<%= 50 %>">
										<aui:select label="sublevels-to-display" name="preferences--displayDepth--">
											<aui:option label="unlimited" value="0" />

											<%
											for (int i = 1; i <= 20; i++) {
											%>

												<aui:option label="<%= i %>" selected="<%= siteNavigationMenuDisplayContext.getDisplayDepth() == i %>" />

											<%
											}
											%>

										</aui:select>
									</aui:col>

									<aui:col width="<%= 50 %>">
										<aui:select label="expand-sublevels" name="preferences--includedLayouts--" value="<%= siteNavigationMenuDisplayContext.getIncludedLayouts() %>">
											<aui:option label="auto" />
											<aui:option label="all" />
										</aui:select>
									</aui:col>
								</aui:row>
							</div>
						</aui:fieldset>
					</aui:fieldset-group>
				</aui:col>

				<aui:col width="<%= 50 %>">
					<liferay-portlet:preview
						portletName="<%= portletResource %>"
						showBorders="<%= true %>"
					/>
				</aui:col>
			</aui:row>
		</div>
	</div>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script sandbox="<%= true %>">
	var form = $('#<portlet:namespace />fm');

	var selectDisplayDepth = form.fm('displayDepth');
	var selectDisplayStyle = form.fm('displayStyle');
	var selectIncludedLayouts = form.fm('includedLayouts');
	var selectRootLayoutLevel = form.fm('rootLayoutLevel');
	var selectRootLayoutType = form.fm('rootLayoutType');
	var selectRootLayoutUuid = form.fm('rootLayoutUuid');

	var curPortletBoundaryId = '#p_p_id_<%= HtmlUtil.escapeJS(portletResource) %>_';

	form.on(
		'change',
		'select',
		function() {
			var data = {
				displayStyle: selectDisplayStyle.val(),
				preview: true
			};

			data.displayDepth = selectDisplayDepth.val();
			data.includedLayouts = selectIncludedLayouts.val();
			data.rootLayoutLevel = selectRootLayoutLevel.val();
			data.rootLayoutType = selectRootLayoutType.val();
			data.rootLayoutUuid = selectRootLayoutUuid.val();

			data = Liferay.Util.ns('_<%= HtmlUtil.escapeJS(portletResource) %>_', data);

			Liferay.Portlet.refresh(curPortletBoundaryId, data);
		}
	);
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />chooseRootPage').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<%= siteNavigationMenuDisplayContext.getEventName() %>',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							var rootLayoutName = A.one('#<portlet:namespace />rootLayoutName');
							var rootLayoutUuid = A.one('#<portlet:namespace />rootLayoutUuid');

							if (selectedItem) {
								rootLayoutName.val(selectedItem.name);
								rootLayoutUuid.val(selectedItem.id);
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="select-layout" />',
					url: '<%= siteNavigationMenuDisplayContext.getItemSelectorURL() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	Liferay.Util.toggleSelectBox('<portlet:namespace />rootLayoutType', 'select', '<portlet:namespace />rootLayoutUuidPanel');

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />rootLayoutType',
		function(currentValue, value) {
			return currentValue === 'absolute' || currentValue === 'relative';
		},
		'<portlet:namespace />rootLayoutLevel'
	);
</aui:script>