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
String rootMenuItemType = siteNavigationMenuDisplayContext.getRootMenuItemType();

SiteNavigationMenu siteNavigationMenu = siteNavigationMenuDisplayContext.getSiteNavigationMenu();

String siteNavigationMenuName = StringPool.BLANK;

if (siteNavigationMenu != null) {
	siteNavigationMenuName = HtmlUtil.escape(siteNavigationMenu.getName());
}
else if (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY) {
	siteNavigationMenuName = LanguageUtil.get(request, "private-pages-hierarchy");
}
else if (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY) {
	siteNavigationMenuName = LanguageUtil.get(request, "public-pages-hierarchy");
}
else {
	siteNavigationMenuName = LanguageUtil.get(request, layout.isPrivateLayout() ? "private-pages-hierarchy" : "public-pages-hierarchy");
}
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
		<aui:row>
			<aui:col width="<%= 50 %>">
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset
						cssClass="p-3"
						label="navigation-menu"
					>
						<aui:input id="siteNavigationMenuId" name="preferences--siteNavigationMenuId--" type="hidden" value="<%= siteNavigationMenuDisplayContext.getSiteNavigationMenuId() %>" />
						<aui:input id="siteNavigationMenuType" name="preferences--siteNavigationMenuType--" type="hidden" value="<%= siteNavigationMenuDisplayContext.getSiteNavigationMenuType() %>" />

						<aui:input checked="<%= !siteNavigationMenuDisplayContext.isSiteNavigationMenuSelected() %>" cssClass="select-navigation" label="select-navigation" name="selectNavigation" type="radio" value="0" />

						<aui:select disabled="<%= siteNavigationMenuDisplayContext.isSiteNavigationMenuSelected() %>" label="" name="selectSiteNavigationMenuType" value="<%= siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() %>">

							<%
							Group scopeGroup = themeDisplay.getScopeGroup();
							%>

							<c:if test="<%= scopeGroup.hasPublicLayouts() && layout.isPublicLayout() %>">
								<aui:option label="public-pages-hierarchy" selected="<%= siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY %>" value="<%= SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY %>" />
							</c:if>

							<c:if test="<%= scopeGroup.hasPrivateLayouts() && layout.isPrivateLayout() %>">
								<aui:option label="private-pages-hierarchy" selected="<%= siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY %>" value="<%= SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY %>" />
							</c:if>

							<aui:option label="primary-navigation" selected="<%= siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PRIMARY %>" value="<%= SiteNavigationConstants.TYPE_PRIMARY %>" />
							<aui:option label="secondary-navigation" selected="<%= siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_SECONDARY %>" value="<%= SiteNavigationConstants.TYPE_SECONDARY %>" />
							<aui:option label="social-navigation" selected="<%= siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_SOCIAL %>" value="<%= SiteNavigationConstants.TYPE_SOCIAL %>" />
						</aui:select>

						<aui:input checked="<%= siteNavigationMenuDisplayContext.isSiteNavigationMenuSelected() %>" cssClass="select-navigation" label="choose-menu" name="selectNavigation" type="radio" value="-1" />

						<div class="mb-2 text-muted">
							<span id="<portlet:namespace />navigationMenuName">
								<c:if test="<%= siteNavigationMenuDisplayContext.isSiteNavigationMenuSelected() && (siteNavigationMenu != null) %>">
									<%= siteNavigationMenuName %>
								</c:if>
							</span>
							<span class="mt-1 <%= (siteNavigationMenuDisplayContext.isSiteNavigationMenuSelected() && (siteNavigationMenu != null)) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />removeSiteNavigationMenu" role="button">
								<aui:icon cssClass="icon-monospaced" image="times-circle" markupView="lexicon" />
							</span>
						</div>

						<aui:button disabled="<%= !siteNavigationMenuDisplayContext.isSiteNavigationMenuSelected() %>" name="chooseSiteNavigationMenu" value="select" />

						<div class="display-template mt-4">
							<liferay-ddm:template-selector
								className="<%= NavItem.class.getName() %>"
								displayStyle="<%= siteNavigationMenuDisplayContext.getDisplayStyle() %>"
								displayStyleGroupId="<%= siteNavigationMenuDisplayContext.getDisplayStyleGroupId() %>"
								refreshURL="<%= configurationRenderURL %>"
							/>
						</div>
					</liferay-frontend:fieldset>

					<liferay-frontend:fieldset
						cssClass="p-3"
						label="menu-items-to-show"
					>
						<div id="<portlet:namespace />customDisplayOptions">
							<aui:row>
								<aui:col width="<%= 75 %>">
									<aui:select id="rootMenuItemType" label="start-with-menu-items-in" name="preferences--rootMenuItemType--" value="<%= rootMenuItemType %>">
										<aui:option label="level" value="absolute" />
										<aui:option label="level-relative-to-the-current-menu-item" value="relative" />
										<aui:option label="select" value="select" />
									</aui:select>
								</aui:col>

								<aui:col width="<%= 25 %>">
									<div class="mt-4 <%= (rootMenuItemType.equals("parent-at-level") || rootMenuItemType.equals("relative-parent-up-by")) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />rootMenuItemLevel">
										<aui:select label="" name="preferences--rootMenuItemLevel--">

											<%
											for (int i = 0; i <= 4; i++) {
											%>

												<aui:option label="<%= i %>" selected="<%= siteNavigationMenuDisplayContext.getRootMenuItemLevel() == i %>" />

											<%
											}
											%>

										</aui:select>
									</div>
								</aui:col>
							</aui:row>

							<aui:row>
								<aui:col width="<%= 80 %>">
									<div class="mb-3 <%= rootMenuItemType.equals("select") ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />rootMenuItemIdPanel">
										<aui:input id="rootMenuItemId" ignoreRequestValue="<%= true %>" name="preferences--rootMenuItemId--" type="hidden" value="<%= siteNavigationMenuDisplayContext.getRootMenuItemId() %>" />

										<%
										String rootMenuItemName = siteNavigationMenuName;

										SiteNavigationMenuItem siteNavigationMenuItem = SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(GetterUtil.getLong(siteNavigationMenuDisplayContext.getRootMenuItemId()));

										if (siteNavigationMenuItem != null) {
											SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry = (SiteNavigationMenuItemTypeRegistry)request.getAttribute(SiteNavigationMenuWebKeys.SITE_NAVIGATION_MENU_ITEM_TYPE_REGISTRY);

											SiteNavigationMenuItemType siteNavigationMenuItemType = siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(siteNavigationMenuItem.getType());

											rootMenuItemName = siteNavigationMenuItemType.getTitle(siteNavigationMenuItem, locale);
										}
										%>

										<div class="card card-horizontal taglib-horizontal-card">
											<div class="card-row card-row-padded ">
												<div class="card-col-field">
													<div class="sticker sticker-secondary sticker-static">
														<aui:icon image="blogs" markupView="lexicon" />
													</div>
												</div>

												<div class="card-col-content card-col-gutters">
													<span class="lfr-card-title-text text-truncate" id="<portlet:namespace />rootMenuItemName">
														<%= rootMenuItemName %>
													</span>
												</div>
											</div>
										</div>

										<aui:button name="chooseRootMenuItem" value="menu-item" />
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
									<aui:select label="expand-sublevels" name="preferences--expandedLevels--" value="<%= siteNavigationMenuDisplayContext.getExpandedLevels() %>">
										<aui:option label="auto" />
										<aui:option label="all" />
									</aui:select>
								</aui:col>
							</aui:row>
						</div>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</aui:col>

			<aui:col width="<%= 50 %>">
				<liferay-portlet:preview
					portletName="<%= portletResource %>"
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

<aui:script require="metal-dom/src/dom as dom">
	var form = document.<portlet:namespace />fm;

	form.addEventListener('change', <portlet:namespace/>resetPreview);
	form.addEventListener('select', <portlet:namespace/>resetPreview);

	function <portlet:namespace/>resetPreview() {
		var displayDepthSelect = Liferay.Util.getFormElement(
			form,
			'displayDepth'
		);
		var displayStyleSelect = Liferay.Util.getFormElement(
			form,
			'displayStyle'
		);
		var expandedLevelsSelect = Liferay.Util.getFormElement(
			form,
			'expandedLevels'
		);
		var rootMenuItemIdInput = Liferay.Util.getFormElement(
			form,
			'rootMenuItemId'
		);
		var rootMenuItemLevelSelect = Liferay.Util.getFormElement(
			form,
			'rootMenuItemLevel'
		);
		var rootMenuItemTypeSelect = Liferay.Util.getFormElement(
			form,
			'rootMenuItemType'
		);
		var siteNavigationMenuIdInput = Liferay.Util.getFormElement(
			form,
			'siteNavigationMenuId'
		);
		var siteNavigationMenuTypeInput = Liferay.Util.getFormElement(
			form,
			'siteNavigationMenuType'
		);

		var data = {
			preview: true
		};

		if (
			displayDepthSelect &&
			displayStyleSelect &&
			expandedLevelsSelect &&
			rootMenuItemIdInput &&
			rootMenuItemLevelSelect &&
			rootMenuItemTypeSelect &&
			siteNavigationMenuIdInput &&
			siteNavigationMenuTypeInput
		) {
			data.displayDepth = displayDepthSelect.value;
			data.displayStyle = displayStyleSelect.value;
			data.expandedLevels = expandedLevelsSelect.value;
			data.rootMenuItemLevel = rootMenuItemLevelSelect.value;
			data.rootMenuItemType = rootMenuItemTypeSelect.value;
			data.rootMenuItemId = rootMenuItemIdInput.value;
			data.siteNavigationMenuId = siteNavigationMenuIdInput.value;
			data.siteNavigationMenuType = siteNavigationMenuTypeInput.value;
		}

		data = Liferay.Util.ns(
			'_<%= HtmlUtil.escapeJS(portletResource) %>_',
			data
		);

		Liferay.Portlet.refresh(
			'#p_p_id_<%= HtmlUtil.escapeJS(portletResource) %>_',
			data
		);
	}

	var chooseRootMenuItemButton = document.getElementById(
		'<portlet:namespace />chooseRootMenuItem'
	);
	var rootMenuItemIdInput = document.getElementById(
		'<portlet:namespace />rootMenuItemId'
	);
	var rootMenuItemNameSpan = document.getElementById(
		'<portlet:namespace />rootMenuItemName'
	);
	var selectSiteNavigationMenuTypeSelect = document.getElementById(
		'<portlet:namespace />selectSiteNavigationMenuType'
	);
	var siteNavigationMenuIdInput = document.getElementById(
		'<portlet:namespace />siteNavigationMenuId'
	);

	if (
		chooseRootMenuItemButton &&
		rootMenuItemIdInput &&
		rootMenuItemNameSpan &&
		selectSiteNavigationMenuTypeSelect &&
		siteNavigationMenuIdInput
	) {
		chooseRootMenuItemButton.addEventListener('click', function(event) {
			event.preventDefault();

			var uri =
				'<%= siteNavigationMenuDisplayContext.getRootMenuItemSelectorURL() %>';

			uri = Liferay.Util.addParams(
				'<%= PortalUtil.getPortletNamespace(ItemSelectorPortletKeys.ITEM_SELECTOR) %>siteNavigationMenuType=' +
					selectSiteNavigationMenuTypeSelect.value,
				uri
			);
			uri = Liferay.Util.addParams(
				'<%= PortalUtil.getPortletNamespace(ItemSelectorPortletKeys.ITEM_SELECTOR) %>siteNavigationMenuId=' +
					siteNavigationMenuIdInput.value,
				uri
			);

			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName:
					'<%= siteNavigationMenuDisplayContext.getRootMenuItemEventName() %>',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							rootMenuItemIdInput.value =
								selectedItem.selectSiteNavigationMenuItemId;
							rootMenuItemNameSpan.innerText =
								selectedItem.selectSiteNavigationMenuItemName;

							<portlet:namespace/>resetPreview();
						}
					}
				},
				'strings.add': '<liferay-ui:message key="done" />',
				title:
					'<liferay-ui:message key="select-site-navigation-menu-item" />',
				url: uri
			});

			itemSelectorDialog.open();
		});
	}

	var chooseSiteNavigationMenuButton = document.getElementById(
		'<portlet:namespace />chooseSiteNavigationMenu'
	);
	var navigationMenuName = document.getElementById(
		'<portlet:namespace />navigationMenuName'
	);
	var removeSiteNavigationMenu = document.getElementById(
		'<portlet:namespace />removeSiteNavigationMenu'
	);

	if (
		chooseSiteNavigationMenuButton &&
		navigationMenuName &&
		removeSiteNavigationMenu &&
		rootMenuItemIdInput &&
		rootMenuItemNameSpan &&
		siteNavigationMenuIdInput
	) {
		chooseSiteNavigationMenuButton.addEventListener('click', function(
			event
		) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName:
						'<%= siteNavigationMenuDisplayContext.getSiteNavigationMenuEventName() %>',
					id: '<portlet:namespace />selectSiteNavigationMenu',
					title:
						'<liferay-ui:message key="select-site-navigation-menu" />',
					uri:
						'<%= siteNavigationMenuDisplayContext.getSiteNavigationMenuItemSelectorURL() %>'
				},
				function(selectedItem) {
					if (selectedItem) {
						navigationMenuName.innerText = selectedItem.name;
						rootMenuItemIdInput.value = '0';
						rootMenuItemNameSpan.innerText = selectedItem.name;
						siteNavigationMenuIdInput.value = selectedItem.id;

						dom.toggleClasses(removeSiteNavigationMenu, 'hide');

						<portlet:namespace/>resetPreview();
					}
				}
			);
		});
	}

	var removeSiteNavigationMenuButton = document.getElementById(
		'<portlet:namespace />removeSiteNavigationMenu'
	);

	if (
		navigationMenuName &&
		removeSiteNavigationMenu &&
		removeSiteNavigationMenuButton &&
		rootMenuItemIdInput &&
		rootMenuItemNameSpan &&
		siteNavigationMenuIdInput
	) {
		removeSiteNavigationMenuButton.addEventListener('click', function(
			event
		) {
			navigationMenuName.innerText = '';
			rootMenuItemIdInput.value = '0';
			rootMenuItemNameSpan.innerText = '';
			siteNavigationMenuIdInput.value = '0';

			dom.toggleClasses(removeSiteNavigationMenu, 'hide');

			<portlet:namespace/>resetPreview();
		});
	}

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />rootMenuItemType',
		'select',
		'<portlet:namespace />rootMenuItemIdPanel'
	);

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />rootMenuItemType',
		function(currentValue, value) {
			return currentValue === 'absolute' || currentValue === 'relative';
		},
		'<portlet:namespace />rootMenuItemLevel'
	);

	var siteNavigationMenuType = document.getElementById(
		'<portlet:namespace />siteNavigationMenuType'
	);

	if (
		rootMenuItemNameSpan &&
		selectSiteNavigationMenuTypeSelect &&
		siteNavigationMenuType
	) {
		selectSiteNavigationMenuTypeSelect.addEventListener(
			'change',
			function() {
				var selectedSelectSiteNavigationMenuType = document.querySelector(
					'#<portlet:namespace />selectSiteNavigationMenuType option:checked'
				);

				if (selectedSelectSiteNavigationMenuType) {
					rootMenuItemNameSpan.innerText =
						selectedSelectSiteNavigationMenuType.innerText;
				}

				siteNavigationMenuType.value =
					selectSiteNavigationMenuTypeSelect.value;
			}
		);
	}

	var chooseSiteNavigationMenu = document.getElementById(
		'<portlet:namespace />chooseSiteNavigationMenu'
	);

	if (
		chooseSiteNavigationMenu &&
		navigationMenuName &&
		removeSiteNavigationMenu &&
		siteNavigationMenuIdInput &&
		siteNavigationMenuType
	) {
		dom.delegate(
			document.<portlet:namespace />fm,
			'change',
			'.select-navigation',
			function() {
				var siteNavigationDisabled =
					selectSiteNavigationMenuTypeSelect.disabled;

				Liferay.Util.toggleDisabled(
					chooseSiteNavigationMenu,
					siteNavigationDisabled
				);
				Liferay.Util.toggleDisabled(
					selectSiteNavigationMenuTypeSelect,
					!siteNavigationDisabled
				);

				navigationMenuName.innerText = '';
				siteNavigationMenuIdInput.value = 0;
				siteNavigationMenuType.value = -1;

				removeSiteNavigationMenu.classList.add('hide');

				<portlet:namespace/>resetPreview();
			}
		);
	}
</aui:script>