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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute("site.group");

LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(group.getGroupId(), true);
LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(group.getGroupId(), false);

LayoutSetPrototype privateLayoutSetPrototype = null;

boolean privateLayoutSetPrototypeLinkEnabled = false;

LayoutSetPrototype publicLayoutSetPrototype = null;

boolean publicLayoutSetPrototypeLinkEnabled = false;

if (Validator.isNotNull(privateLayoutSet.getLayoutSetPrototypeUuid())) {
	privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(privateLayoutSet.getLayoutSetPrototypeUuid(), company.getCompanyId());

	privateLayoutSetPrototypeLinkEnabled = privateLayoutSet.isLayoutSetPrototypeLinkEnabled();
}

if (Validator.isNotNull(publicLayoutSet.getLayoutSetPrototypeUuid())) {
	publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(publicLayoutSet.getLayoutSetPrototypeUuid(), company.getCompanyId());

	publicLayoutSetPrototypeLinkEnabled = publicLayoutSet.isLayoutSetPrototypeLinkEnabled();
}
%>

<liferay-ui:error-marker key="errorSection" value="siteTempate" />

<c:if test="<%= (publicLayoutSetPrototype == null) && (privateLayoutSetPrototype == null) %>">
	<div class="alert alert-info">
		<liferay-ui:message key="this-site-is-not-related-to-a-site-template" />
	</div>
</c:if>

<aui:fieldset>
	<c:if test="<%= publicLayoutSetPrototype != null %>">
		<aui:fieldset label="public-site-template">
			<c:choose>
				<c:when test="<%= publicLayoutSetPrototypeLinkEnabled %>">

					<%
					boolean layoutsUpdateable = GetterUtil.getBoolean(publicLayoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true);
					%>

					<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(publicLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" />

					<aui:field-wrapper label="site-template-settings">
						<aui:input disabled="<%= true %>" name="active" type="checkbox" value="<%= publicLayoutSetPrototype.isActive() %>" />
						<aui:input disabled="<%= true %>" name="site-template-allows-modifications" type="checkbox" value="<%= layoutsUpdateable %>" />
					</aui:field-wrapper>
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= new Object[] {publicLayoutSetPrototype.getName(locale)} %>" key="this-site-was-cloned-from-site-template-x" />
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</c:if>

	<c:if test="<%= privateLayoutSetPrototype != null %>">
		<aui:fieldset label="private-site-template">
			<c:choose>
				<c:when test="<%= privateLayoutSetPrototypeLinkEnabled %>">

					<%
					boolean layoutsUpdateable = GetterUtil.getBoolean(privateLayoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true);
					%>

					<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(privateLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" />

					<aui:field-wrapper label="site-template-settings">
						<aui:input disabled="<%= true %>" name="active" type="checkbox" value="<%= privateLayoutSetPrototype.isActive() %>" />
						<aui:input disabled="<%= true %>" name="site-template-allows-modifications" type="checkbox" value="<%= layoutsUpdateable %>" />
					</aui:field-wrapper>
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= new Object[] {privateLayoutSetPrototype.getName(locale)} %>" key="this-site-was-cloned-from-site-template-x" />
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</c:if>
</aui:fieldset>