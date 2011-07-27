<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
boolean privateLayoutSetPrototypeInheritance = false;

LayoutSetPrototype publicLayoutSetPrototype = null;
boolean publicLayoutSetPrototypeInheritance = false;

if (Validator.isNotNull(privateLayoutSet.getLayoutSetPrototypeUuid())) {
	privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(privateLayoutSet.getLayoutSetPrototypeUuid());
	privateLayoutSetPrototypeInheritance = privateLayoutSet.getLayoutSetPrototypeLinkEnabled();
}

if (Validator.isNotNull(publicLayoutSet.getLayoutSetPrototypeUuid())) {
	publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(publicLayoutSet.getLayoutSetPrototypeUuid());
	publicLayoutSetPrototypeInheritance = publicLayoutSet.getLayoutSetPrototypeLinkEnabled();
}
%>

<aui:fieldset>
	<c:if test="<%= publicLayoutSetPrototype != null %>">
		<aui:fieldset label="public-site-template">
			<c:choose>
				<c:when test="<%= publicLayoutSetPrototypeInheritance %>">
					<%
					boolean active = false;
					boolean allowModifications = true;
					boolean allowLayoutAdditions = true;

					UnicodeProperties settings = publicLayoutSetPrototype.getSettingsProperties();

					if (Validator.isNotNull(settings.get("allowLayoutAdditions"))) {
						allowLayoutAdditions = Boolean.valueOf(settings.get("allowLayoutAdditions"));
					}

					if (Validator.isNotNull(settings.get("allowModifications"))) {
						allowModifications = Boolean.valueOf(settings.get("allowModifications"));
					}

					active = publicLayoutSetPrototype.isActive();
					%>

					<liferay-ui:message arguments="<%= new Object[] {publicLayoutSetPrototype.getName(locale)} %>" key="these-pages-are-linked-to-the-template-x" />

					<aui:field-wrapper label="site-template-settings">
						<aui:input type="checkbox" name="active" value="<%= active %>" disabled="<%= true %>" />
						<aui:input type="checkbox" disabled="<%= true %>" name="template-allows-modification" value="<%= allowModifications %>" />
						<aui:input type="checkbox" disabled="<%= true %>" name="template-allows-page-addition" value="<%= allowLayoutAdditions %>" />
					</aui:field-wrapper>
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= new Object[] {publicLayoutSetPrototype.getName(locale)} %>" key="this-site-is-cloned-from-template-x" />
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</c:if>

	<c:if test="<%= privateLayoutSetPrototype != null %>">
		<aui:fieldset label="private-site-template">
			<c:choose>
				<c:when test="<%= privateLayoutSetPrototypeInheritance %>">
					<%
					boolean active = false;
					boolean allowModifications = true;
					boolean allowLayoutAdditions = true;

					UnicodeProperties settings = privateLayoutSetPrototype.getSettingsProperties();

					if (Validator.isNotNull(settings.get("allowLayoutAdditions"))) {
						allowLayoutAdditions = Boolean.valueOf(settings.get("allowLayoutAdditions"));
					}

					if (Validator.isNotNull(settings.get("allowModifications"))) {
						allowModifications = Boolean.valueOf(settings.get("allowModifications"));
					}

					active = privateLayoutSetPrototype.isActive();
					%>

					<liferay-ui:message arguments="<%= new Object[] {privateLayoutSetPrototype.getName(locale)} %>" key="these-pages-are-linked-to-the-template-x" />

					<aui:field-wrapper label="site-template-settings">
						<aui:input type="checkbox" name="active" value="<%= active %>" disabled="<%= true %>" />
						<aui:input type="checkbox" disabled="<%= true %>" name="template-allows-modification" value="<%= allowModifications %>" />
						<aui:input type="checkbox" disabled="<%= true %>" name="template-allows-page-addition" value="<%= allowLayoutAdditions %>" />
					</aui:field-wrapper>
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= new Object[] {privateLayoutSetPrototype.getName(locale)} %>" key="this-site-is-cloned-from-template-x" />
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</c:if>
</aui:fieldset>