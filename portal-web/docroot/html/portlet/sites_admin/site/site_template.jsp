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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute("site.group");

LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(group.getGroupId());

LayoutSetPrototype layoutSetPrototype = null;

boolean layoutSetPrototypeLinkEnabled = false;

if (Validator.isNotNull(layoutSet.getLayoutSetPrototypeUuid())) {
	layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSet.getLayoutSetPrototypeUuid(), company.getCompanyId());

	layoutSetPrototypeLinkEnabled = layoutSet.isLayoutSetPrototypeLinkEnabled();
}
%>

<liferay-ui:error-marker key="errorSection" value="siteTempate" />

<h3><liferay-ui:message key="site-template" /></h3>

<c:if test="<%= (layoutSetPrototype == null) %>">
	<div class="alert alert-info">
		<liferay-ui:message key="this-site-is-not-related-to-a-site-template" />
	</div>
</c:if>

<aui:fieldset>
	<c:if test="<%= layoutSetPrototype != null %>">
		<aui:fieldset label="site-template">
			<c:choose>
				<c:when test="<%= layoutSetPrototypeLinkEnabled %>">

					<%
					boolean layoutsUpdateable = GetterUtil.getBoolean(layoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true);
					%>

					<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(layoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

					<aui:field-wrapper label="site-template-settings">
						<aui:input disabled="<%= true %>" name="active" type="checkbox" value="<%= layoutSetPrototype.isActive() %>" />
						<aui:input disabled="<%= true %>" name="site-template-allows-modifications" type="checkbox" value="<%= layoutsUpdateable %>" />
					</aui:field-wrapper>
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(layoutSetPrototype.getName(locale))} %>" key="this-site-was-cloned-from-site-template-x" translateArguments="<%= false %>" />
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</c:if>
</aui:fieldset>