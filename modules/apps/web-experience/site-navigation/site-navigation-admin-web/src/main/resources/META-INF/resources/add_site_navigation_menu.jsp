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
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "add-new-menu"));
%>

<portlet:actionURL name="/navigation_menu/add_site_navigation_menu" var="editSitaNavigationMenuURL">
	<portlet:param name="mvcPath" value="/add_site_navigation_menu.jsp" />
	<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
</portlet:actionURL>

<aui:form action="<%= editSitaNavigationMenuURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="selectedItemType" type="hidden" value="" />

	<aui:model-context model="<%= SiteNavigationMenu.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" label="name" name="name" placeholder="name" />
		</aui:fieldset>

		<aui:fieldset>
			<div class="row" id="<portlet:namespace/>siteNavigationMenuItemTypes">

				<%
				for (SiteNavigationMenuItemType siteNavigationMenuItemType : siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemTypes()) {
				%>

					<div class="col-md-2 item-type" data-type="<%= siteNavigationMenuItemType.getType() %>">
						<div class="card card-type-asset">
							<div class="aspect-ratio card-item-first">
								<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid">
									<liferay-ui:icon
										icon="<%= siteNavigationMenuItemType.getIcon() %>"
										markupView="lexicon"
									/>
								</div>
							</div>

							<div class="card-body">
								<div class="card-row">
									<div class="flex-col flex-col-expand">
										<div class="card-title text-center text-truncate">
											<%= siteNavigationMenuItemType.getLabel(locale) %>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

				<%
				}
				%>

			</div>

			<div class="text-center">
				<div>
					<liferay-ui:message key="this-menu-is-empty" />
				</div>

				<div>
					<liferay-ui:message key="please-select-a-menu-item-to-continue" />
				</div>
			</div>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	A.one('#<portlet:namespace/>siteNavigationMenuItemTypes').delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			document.getElementById('<portlet:namespace/>selectedItemType').value = currentTarget.attr('data-type');

			submitForm(document.<portlet:namespace/>fm);
		},
		'.item-type'
	);
</aui:script>