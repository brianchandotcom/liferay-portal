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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_role_permissions.jsp");
portletURL.setParameter(Constants.CMD, Constants.VIEW);
portletURL.setParameter("tabs1", "define-permissions");
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
portletURL.setParameter("backURL", backURL);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));

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

<liferay-util:include page="/edit_role_tabs.jsp" servletContext="<%= application %>" />

<aui:container cssClass="container-fluid container-fluid-max-xl container-form-lg" id="permissionContainer">
	<aui:row>
		<c:if test="<%= !portletName.equals(PortletKeys.SERVER_ADMIN) %>">
			<aui:col width="<%= 25 %>">
				<%@ include file="/edit_role_permissions_navigation.jspf" %>
			</aui:col>
		</c:if>

		<aui:col cssClass="lfr-permission-content-container" id="permissionContentContainer" width="<%= portletName.equals(PortletKeys.SERVER_ADMIN) ? 100 : 75 %>">
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
		</aui:col>
	</aui:row>
</aui:container>

<aui:script>
	function <portlet:namespace />removeGroup(pos, target) {
		var selectedGroupIds = document.<portlet:namespace />fm[
			'<portlet:namespace />groupIds' + target
		].value.split(',');
		var selectedGroupNames = document.<portlet:namespace />fm[
			'<portlet:namespace />groupNames' + target
		].value.split('@@');

		selectedGroupIds.splice(pos, 1);
		selectedGroupNames.splice(pos, 1);

		<portlet:namespace />updateGroups(
			selectedGroupIds,
			selectedGroupNames,
			target
		);
	}

	function <portlet:namespace />selectOrganization(
		organizationId,
		groupId,
		name,
		type,
		target
	) {
		<portlet:namespace />selectGroup(groupId, name, target);
	}

	function <portlet:namespace />updateGroups(
		selectedGroupIds,
		selectedGroupNames,
		target
	) {
		document.<portlet:namespace />fm[
			'<portlet:namespace />groupIds' + target
		].value = selectedGroupIds.join(',');
		document.<portlet:namespace />fm[
			'<portlet:namespace />groupNames' + target
		].value = selectedGroupNames.join('@@');

		var nameEl = document.getElementById(
			'<portlet:namespace />groupHTML' + target
		);

		var groupsHTML = '';

		for (var i = 0; i < selectedGroupIds.length; i++) {
			var name = selectedGroupNames[i];

			groupsHTML +=
				'<span class="lfr-token"><span class="lfr-token-text">' +
				name +
				'</span><a class="icon icon-remove lfr-token-close" href="javascript:<portlet:namespace />removeGroup(' +
				i +
				", '" +
				target +
				'\' );"></a></span>';
		}

		if (groupsHTML == '') {
			groupsHTML = '<liferay-ui:message key="all-sites" />';
		}

		nameEl.innerHTML = groupsHTML;
	}
</aui:script>

<aui:script use="aui-loading-mask-deprecated,aui-parse-content,aui-toggler,autocomplete-base,autocomplete-filters,liferay-notification">
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
				initializer: function() {
					var instance = this;

					instance._bindUIACBase();
					instance._syncUIACBase();
				}
			}
		});

		var getItems = function() {
			var results = [];

			permissionNavigationItems.each(function(item, index, collection) {
				results.push({
					data: item.text().trim(),
					node: item
				});
			});

			return results;
		};

		var getNoResultsNode = function() {
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
			source: getItems()
		});

		permissionNavigationSearch.on('query', function(event) {
			if (event.query) {
				togglerDelegate.expandAll();
			} else {
				togglerDelegate.collapseAll();
			}
		});

		permissionNavigationSearch.on('results', function(event) {
			permissionNavigationItems.each(function(item, index, collection) {
				item.addClass('hide');
			});

			event.results.forEach(function(item, index) {
				item.raw.node.removeClass('hide');
			});

			var foundVisibleSection;

			permissionNavigationSectionsNode.each(function(
				item,
				index,
				collection
			) {
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
			} else {
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
			function(event) {
				event.preventDefault();

				var href = event.currentTarget.attr('data-resource-href');

				href = Liferay.Util.addParams('p_p_isolated=true', href);

				permissionContentContainerNode.plug(A.LoadingMask);

				permissionContentContainerNode.loadingmask.show();

				permissionContentContainerNode.unplug(AParseContent);

				Liferay.Util.fetch(href)
					.then(function(response) {
						if (response.status === 401) {
							window.location.reload();
						} else if (response.ok) {
							return response.text();
						} else {
							throw new Error(
								'<liferay-ui:message key="sorry,-we-were-not-able-to-access-the-server" />'
							);
						}
					})
					.then(function(response) {
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
					.catch(function(error) {
						permissionContentContainerNode.loadingmask.hide();

						permissionContentContainerNode.unplug(A.LoadingMask);

						new Liferay.Notification({
							closeable: true,
							delay: {
								hide: 0,
								show: 0
							},
							duration: 500,
							message: error.message,
							render: true,
							title: '<liferay-ui:message key="warning" />',
							type: 'warning'
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
			function(event) {
				var unselectedTargetsNode = permissionContainerNode.one(
					'#<portlet:namespace />unselectedTargets'
				);

				var unselectedTargets = unselectedTargetsNode.val().split(',');

				var form = A.one(document.<portlet:namespace />fm);

				form.all('input[type=checkbox]').each(function(item, index) {
					var checkbox = A.one(item);

					var value = checkbox.val();

					if (checkbox.get('checked')) {
						var unselectedTargetIndex = unselectedTargets.indexOf(
							value
						);

						if (unselectedTargetIndex != -1) {
							unselectedTargets.splice(unselectedTargetIndex, 1);
						}
					} else if (originalSelectedValues.indexOf(value) != -1) {
						unselectedTargets.push(value);
					}
				});

				unselectedTargetsNode.val(unselectedTargets.join(','));
			},
			':checkbox'
		);
	}

	Liferay.on('<portlet:namespace />selectGroup', function(event) {
		var selectedGroupIds = [];

		var selectedGroupIdsField =
			document.<portlet:namespace />fm[
				'<portlet:namespace />groupIds' + event.grouptarget
			].value;

		if (selectedGroupIdsField) {
			selectedGroupIds = selectedGroupIdsField.split(',');
		}

		var selectedGroupNames = [];
		var selectedGroupNamesField =
			document.<portlet:namespace />fm[
				'<portlet:namespace />groupNames' + event.grouptarget
			].value;

		if (selectedGroupNamesField) {
			selectedGroupNames = selectedGroupNamesField.split('@@');
		}

		if (selectedGroupIds.indexOf(event.entityid) == -1) {
			selectedGroupIds.push(event.entityid);
			selectedGroupNames.push(event.entityname);
		}

		<portlet:namespace />updateGroups(
			selectedGroupIds,
			selectedGroupNames,
			event.grouptarget
		);
	});

	A.on('domready', function(event) {
		togglerDelegate = new A.TogglerDelegate({
			container: <portlet:namespace />permissionNavigationDataContainer,
			content: '.permission-navigation-item-content',
			header: '.permission-navigation-item-header'
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
				redirect: '<%= HtmlUtil.escapeJS(portletURL.toString()) %>',
				selectedTargets: Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				),
				unselectedTargets: Liferay.Util.listUncheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				)
			}
		});
	}
</aui:script>