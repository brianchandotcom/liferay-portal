<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationDisplayContext commerceOrganizationDisplayContext = (CommerceOrganizationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<%
request.setAttribute("configuration.jsp-configurationRenderURL", configurationRenderURL);
request.setAttribute("configuration.jsp-redirect", redirect);

Organization rootOrganization = commerceOrganizationDisplayContext.getRootOrganization();
%>

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<div class="form-group">
			<label><liferay-ui:message key="root-organization" /></label>

			<div>
				<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>

				<react:component
					module="js/configuration"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"apiUrl", "/o/headless-admin-user/v1.0/organizations?flatten=true"
						).put(
							"initialLabel", (rootOrganization == null) ? "" : rootOrganization.getName()
						).put(
							"initialValue", (rootOrganization == null) ? 0 : rootOrganization.getOrganizationId()
						).put(
							"inputName", liferayPortletResponse.getNamespace() + "preferences--rootOrganizationId--"
						).put(
							"itemsKey", "id"
						).put(
							"itemsLabel", "name"
						).put(
							"required", false
						).build()
					%>'
				/>
			</div>
		</div>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>