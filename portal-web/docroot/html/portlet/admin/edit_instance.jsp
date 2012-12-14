<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Company company2 = (Company)request.getAttribute(WebKeys.SEL_COMPANY);

long companyId = BeanParamUtil.getLong(company2, request, "companyId");

VirtualHost virtualHost = null;

try {
	virtualHost = VirtualHostLocalServiceUtil.getVirtualHost(companyId, 0);
}
catch (Exception e) {
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= (company2 == null) %>"
	title='<%= (company2 == null) ? "new-portal-instance" : company2.getName() %>'
/>

<portlet:actionURL var="editInstanceURL">
	<portlet:param name="struts_action" value="/admin/edit_instance" />
</portlet:actionURL>

<aui:form action="<%= editInstanceURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCompany();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="companyId" type="hidden" value="<%= companyId %>" />

	<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />
	<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />
	<liferay-ui:error exception="<%= CompanyWebIdException.class %>" message="please-enter-a-valid-web-id" />

	<aui:model-context bean="<%= company2 %>" model="<%= Company.class %>" />

	<aui:fieldset>
		<c:if test="<%= company2 != null %>">
			<aui:field-wrapper label="id">
				<%= companyId %>
			</aui:field-wrapper>

			<aui:field-wrapper label="web-id">
				<%= company2.getWebId() %>
			</aui:field-wrapper>
		</c:if>

		<c:if test="<%= company2 == null %>">
			<aui:input name="webId" />
		</c:if>

		<aui:input bean="<%= virtualHost %>" fieldParam="virtualHostname" label="virtual-host" model="<%= VirtualHost.class %>" name="hostname" />

		<aui:input label="mail-domain" name="mx" />

		<c:if test="<%= showShardSelector %>">
			<c:choose>
				<c:when test="<%= company2 != null %>">
					<%= company2.getShardName() %>
				</c:when>
				<c:otherwise>
					<aui:select name="shardName">

						<%
						for (String shardName : ShardUtil.getAvailableShardNames()) {
						%>

							<aui:option label="<%= shardName %>" selected="<%= shardName.equals(PropsValues.SHARD_DEFAULT_NAME) %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>
		</c:if>

		<aui:input name="maxUsers" />

		<aui:input disabled="<%= (company2 != null) && (company2.getCompanyId() == PortalInstances.getDefaultCompanyId()) %>" name="active" type="checkbox" value="<%= (company2 != null) ? company2.isActive() : true %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCompany() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (company2 == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>