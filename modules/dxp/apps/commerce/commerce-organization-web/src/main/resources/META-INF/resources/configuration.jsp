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

String wrapperId = liferayPortletResponse.getNamespace() + "autocomplete-wrapper";
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

			<div id="<%= wrapperId %>"></div>
		</div>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"rootOrganizationId", (rootOrganization == null) ? 0 : rootOrganization.getOrganizationId()
				).put(
					"rootOrganizationName", (rootOrganization == null) ? "" : rootOrganization.getName()
				).put(
					"wrapperId", wrapperId
				).build()
			%>'
			module="js/configuration"
		/>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>