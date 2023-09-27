<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	var originalSelectedValues = [];

	var permissionContainerNode = A.one(
		'#<portlet:namespace />permissionContainer'
	);

	var permissionContentContainerNode = permissionContainerNode.one(
		'#<portlet:namespace />permissionContentContainer'
	);

	window['<portlet:namespace />loadContent'] = function (href) {
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

				var checkedNodes = permissionContentContainerNode.all(':checked');

				originalSelectedValues = checkedNodes.val();

				setPortletResource(href);
			})
			.catch((error) => {
				permissionContentContainerNode.loadingmask.hide();

				permissionContentContainerNode.unplug(A.LoadingMask);

				Liferay.Util.openToast({
					message: error.message,
					type: 'warning',
				});
			});
	};

	function processNavigationLinks() {
		permissionContainerNode.delegate(
			'click',
			(event) => {
				event.preventDefault();

				var href = event.currentTarget.attr('data-resource-href');

				href = Liferay.Util.addParams('p_p_isolated=true', href);

				<portlet:namespace />loadContent(href);
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

	function setPortletResource(href) {
		const url = new URL(href);

		const cmdKey = '<portlet:namespace />cmd';
		const portletResourceKey = '<portlet:namespace />portletResource';

		const cmd = url.searchParams.get(cmdKey);
		const portletResource = url.searchParams.get(portletResourceKey);

		const currentURL = new URL(window.location.href);

		currentURL.searchParams.set(cmdKey, cmd);
		currentURL.searchParams.set(portletResourceKey, portletResource);

		const path = currentURL.toString();

		window.history.replaceState({path}, document.title, path);
	}

	A.on('domready', (event) => {
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
				selectedTargets: Liferay.Util.getCheckedCheckboxes(
					form,
					'<portlet:namespace />allRowIds'
				),
				unselectedTargets: Liferay.Util.getUncheckedCheckboxes(
					form,
					'<portlet:namespace />allRowIds'
				),
			},
		});
	}
</aui:script>