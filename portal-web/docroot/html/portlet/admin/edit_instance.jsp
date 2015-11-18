<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

Company selCompany = (Company)request.getAttribute(WebKeys.SEL_COMPANY);

long companyId = BeanParamUtil.getLong(selCompany, request, "companyId");

VirtualHost virtualHost = null;

try {
	virtualHost = VirtualHostLocalServiceUtil.getVirtualHost(companyId, 0);
}
catch (Exception e) {
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	escapeXml="<%= false %>"
	localizeTitle="<%= (selCompany == null) %>"
	title='<%= (selCompany == null) ? "new-portal-instance" : HtmlUtil.escape(selCompany.getName()) %>'
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

	<aui:model-context bean="<%= selCompany %>" model="<%= Company.class %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= selCompany != null %>">
				<aui:input name="id" type="resource" value="<%= String.valueOf(companyId) %>" />

				<aui:input name="web-id" type="resource" value="<%= selCompany.getWebId() %>" />
			</c:when>
			<c:otherwise>
				<aui:input name="webId" />
			</c:otherwise>
		</c:choose>

		<aui:input bean="<%= virtualHost %>" fieldParam="virtualHostname" label="virtual-host" model="<%= VirtualHost.class %>" name="hostname" />

		<aui:input label="mail-domain" name="mx" />

		<c:if test="<%= showShardSelector %>">
			<c:choose>
				<c:when test="<%= selCompany != null %>">
					<%= selCompany.getShardName() %>
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

		<aui:input disabled="<%= (selCompany != null) && (selCompany.getCompanyId() == PortalInstances.getDefaultCompanyId()) %>" name="active" type="checkbox" value="<%= (selCompany != null) ? selCompany.isActive() : true %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCompany() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (selCompany == null) ? Constants.ADD : Constants.UPDATE %>";

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>