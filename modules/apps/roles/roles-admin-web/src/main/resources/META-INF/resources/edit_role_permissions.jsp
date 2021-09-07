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
String cmd = ParamUtil.getString(request, Constants.CMD);

String tabs2 = "roles";
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNull(redirect)) {
	redirect = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/edit_role_permissions.jsp"
	).setCMD(
		Constants.VIEW
	).setBackURL(
		backURL
	).setTabs1(
		roleDisplayContext.getEditRolePermissionsTabs1()
	).setTabs2(
		tabs2
	).setParameter(
		"accountRoleGroupScope", roleDisplayContext.isAccountRoleGroupScope()
	).setParameter(
		"roleId", role.getRoleId()
	).setParameter(
		"tabs3", tabs3
	).buildString();
}

request.setAttribute("edit_role_permissions.jsp-role", role);

request.setAttribute("edit_role_permissions.jsp-portletResource", portletResource);

if (!portletName.equals(PortletKeys.SERVER_ADMIN)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);

	renderResponse.setTitle(role.getTitle(locale));
}
%>

<liferay-ui:success key="permissionDeleted" message="the-permission-was-deleted" />
<liferay-ui:success key="permissionsUpdated" message="the-role-permissions-were-updated" />

<c:if test="<%= GetterUtil.getBoolean(request.getAttribute(RolesAdminWebKeys.SHOW_NAV_TABS), true) %>">
	<liferay-util:include page="/edit_role_tabs.jsp" servletContext="<%= application %>" />
</c:if>

<clay:container-fluid
	cssClass="container-form-lg"
	id='<%= liferayPortletResponse.getNamespace() + "permissionContainer" %>'
>
	<clay:row>
		<c:if test="<%= !portletName.equals(PortletKeys.SERVER_ADMIN) %>">
			<clay:col
				md="3"
			>
				<%@ include file="/edit_role_permissions_navigation.jspf" %>
			</clay:col>
		</c:if>

		<clay:col
			cssClass="lfr-permission-content-container"
			id='<%= liferayPortletResponse.getNamespace() + "permissionContentContainer" %>'
			md="<%= portletName.equals(PortletKeys.SERVER_ADMIN) ? String.valueOf(12) : String.valueOf(9) %>"
		>
			<c:choose>
				<c:when test="<%= cmd.equals(Constants.VIEW) %>">
					<liferay-util:include page="/edit_role_permissions_summary.jsp" servletContext="<%= application %>" />

					<c:if test="<%= portletName.equals(PortletKeys.SERVER_ADMIN) %>">
						<br />

						<aui:button href="<%= redirect %>" type="cancel" />
					</c:if>
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/edit_role_permissions_form.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />selectOrganization(
		organizationId,
		groupId,
		name,
		type,
		target
	) {
		<portlet:namespace />selectGroup(groupId, name, target);
	}
</aui:script>

<aui:script use="aui-loading-mask-deprecated,aui-parse-content,aui-toggler,autocomplete-base,autocomplete-filters">
	var AParseContent = A.Plugin.ParseContent;

	var permissionNavigationDataContainer = A.one(
		'#<portlet:namespace />permissionNavigationDataContainer'
	);

	var togglerDelegate;

	function createLiveSearch() {
		var instance = this;

		var PermissionNavigationSearch = A.Component.create({
			AUGMENTS: [A.AutoCompleteBase],

			EXTENDS: A.Base,

			NAME: 'searchpermissioNnavigation',

			prototype: {
				initializer: function () {
					var instance = this;

					instance._bindUIACBase();
					instance._syncUIACBase();
				},
			},
		});

		var getItems = function () {
			var results = [];

			permissionNavigationItems.each((item, index, collection) => {
				results.push({
					data: item.text().trim(),
					node: item,
				});
			});

			return results;
		};

		var getNoResultsNode = function () {
			if (!noResultsNode) {
				noResultsNode = A.Node.create(
					'<div class="alert"><liferay-ui:message key="there-are-no-results" /></div>'
				);
			}

			return noResultsNode;
		};

		var permissionNavigationItems = permissionNavigationDataContainer.all(
			'.permission-navigation-item-container'
		);

		var permissionNavigationSectionsNode = permissionNavigationDataContainer.all(
			'.permission-navigation-section'
		);

		var noResultsNode;

		var permissionNavigationSearch = new PermissionNavigationSearch({
			inputNode: '#<portlet:namespace />permissionNavigationSearch',
			minQueryLength: 0,
			nodes: '.permission-navigation-item-container',
			resultFilters: 'subWordMatch',
			resultTextLocator: 'data',
			source: getItems(),
		});

		permissionNavigationSearch.on('query', (event) => {
			if (event.query) {
				togglerDelegate.expandAll();
			}
			else {
				togglerDelegate.collapseAll();
			}
		});

		permissionNavigationSearch.on('results', (event) => {
			permissionNavigationItems.each((item, index, collection) => {
				item.addClass('hide');
			});

			event.results.forEach((item, index) => {
				item.raw.node.removeClass('hide');
			});

			var foundVisibleSection;

			permissionNavigationSectionsNode.each((item, index, collection) => {
				var action = 'addClass';

				var visibleItem = item.one(
					'.permission-navigation-item-container:not(.hide)'
				);

				if (visibleItem) {
					action = 'removeClass';

					foundVisibleSection = true;
				}

				item[action]('hide');
			});

			var noResultsNode = getNoResultsNode();

			if (foundVisibleSection) {
				noResultsNode.remove();
			}
			else {
				permissionNavigationDataContainer.appendChild(noResultsNode);
			}
		});
	}

	var originalSelectedValues = [];

	function processNavigationLinks() {
		var permissionContainerNode = A.one(
			'#<portlet:namespace />permissionContainer'
		);

		var permissionContentContainerNode = permissionContainerNode.one(
			'#<portlet:namespace />permissionContentContainer'
		);

		permissionContainerNode.delegate(
			'click',
			(event) => {
				event.preventDefault();

				var href = event.currentTarget.attr('data-resource-href');

				href = Liferay.Util.addParams('p_p_isolated=true', href);

				permissionContentContainerNode.plug(A.LoadingMask);

				permissionContentContainerNode.loadingmask.show();

				permissionContentContainerNode.unplug(AParseContent);

				Liferay.Util.fetch(href)
					.then((response) => {
						if (response.status === 401) {
							window.location.reload();
						}
						else if (response.ok) {
							return response.text();
						}
						else {
							throw new Error(
								'<liferay-ui:message key="sorry,-we-were-not-able-to-access-the-server" />'
							);
						}
					})
					.then((response) => {
						permissionContentContainerNode.loadingmask.hide();

						permissionContentContainerNode.unplug(A.LoadingMask);

						permissionContentContainerNode.plug(AParseContent);

						permissionContentContainerNode.empty();

						permissionContentContainerNode.setContent(response);

						var checkedNodes = permissionContentContainerNode.all(
							':checked'
						);

						originalSelectedValues = checkedNodes.val();

						A.all('.permission-navigation-link').removeClass('active');

						event.currentTarget.addClass('active');
					})
					.catch((error) => {
						permissionContentContainerNode.loadingmask.hide();

						permissionContentContainerNode.unplug(A.LoadingMask);

						Liferay.Util.openToast({
							message: error.message,
							type: 'warning',
						});
					});
			},
			'.permission-navigation-link'
		);
	}

	function processTargetCheckboxes() {
		var permissionContainerNode = A.one(
			'#<portlet:namespace />permissionContainer'
		);

		permissionContainerNode.delegate(
			'change',
			(event) => {
				var unselectedTargetsNode = permissionContainerNode.one(
					'#<portlet:namespace />unselectedTargets'
				);

				var unselectedTargets = unselectedTargetsNode.val().split(',');

				var form = A.one(document.<portlet:namespace />fm);

				form.all('input[type=checkbox]').each((item, index) => {
					var checkbox = A.one(item);

					var value = checkbox.val();

					if (checkbox.get('checked')) {
						var unselectedTargetIndex = unselectedTargets.indexOf(
							value
						);

						if (unselectedTargetIndex != -1) {
							unselectedTargets.splice(unselectedTargetIndex, 1);
						}
					}
					else if (originalSelectedValues.indexOf(value) != -1) {
						unselectedTargets.push(value);
					}
				});

				unselectedTargetsNode.val(unselectedTargets.join(','));
			},
			':checkbox'
		);
	}

	A.on('domready', (event) => {
		togglerDelegate = new A.TogglerDelegate({
			container: <portlet:namespace />permissionNavigationDataContainer,
			content: '.permission-navigation-item-content',
			header: '.permission-navigation-item-header',
		});

		createLiveSearch();
		processNavigationLinks();
		processTargetCheckboxes();
	});
</aui:script>

<aui:script>
	function <portlet:namespace />updateActions() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				redirect: '<%= HtmlUtil.escapeJS(redirect) %>',
				selectedTargets: Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				),
				unselectedTargets: Liferay.Util.listUncheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				),
			},
		});
	}
</aui:script>