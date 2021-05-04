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
Group group = themeDisplay.getSiteGroup();

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

<c:if test="<%= publicLayoutSetPrototype != null %>">
	<aui:fieldset label="public-site-template">
		<c:choose>
			<c:when test="<%= publicLayoutSetPrototypeLinkEnabled %>">
				<liferay-ui:message arguments="<%= HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)) %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

				<aui:field-wrapper label="site-template-settings">
					<aui:input disabled="<%= true %>" label="active" name="siteTemplateActive" type="checkbox" value="<%= publicLayoutSetPrototype.isActive() %>" />
					<aui:input disabled="<%= true %>" label="site-template-allows-modifications" name="siteTemplateAllowsModifications" type="checkbox" value='<%= GetterUtil.getBoolean(publicLayoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true) %>' />
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<liferay-ui:message arguments='<%= new Object[] {"<em>" + HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)) + "</em>"} %>' key="these-pages-are-linked-to-site-template-x-without-propagation-of-changes" translateArguments="<%= false %>" />
			</c:otherwise>
		</c:choose>
	</aui:fieldset>
</c:if>

<c:if test="<%= privateLayoutSetPrototype != null %>">
	<aui:fieldset label="private-site-template">
		<c:choose>
			<c:when test="<%= privateLayoutSetPrototypeLinkEnabled %>">
				<liferay-ui:message arguments="<%= HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)) %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

				<aui:field-wrapper label="site-template-settings">
					<aui:input disabled="<%= true %>" label="active" name="siteTemplateActive" type="checkbox" value="<%= privateLayoutSetPrototype.isActive() %>" />
					<aui:input disabled="<%= true %>" label="site-template-allows-modifications" name="siteTemplateAllowsModifications" type="checkbox" value='<%= GetterUtil.getBoolean(privateLayoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true) %>' />
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<liferay-ui:message arguments='<%= new Object[] {"<em>" + HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)) + "</em>"} %>' key="these-pages-are-linked-to-site-template-x-without-propagation-of-changes" translateArguments="<%= false %>" />
			</c:otherwise>
		</c:choose>
	</aui:fieldset>
</c:if>