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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute("site.group");
Group liveGroup = (Group)request.getAttribute("site.liveGroup");
LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("site.layoutSetPrototype");
boolean showPrototypes = GetterUtil.getBoolean(request.getAttribute("site.showPrototypes"));

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSet privateLayoutSet = null;
LayoutSetPrototype privateLayoutSetPrototype = null;
boolean privateLayoutSetPrototypeLinkEnabled = true;

LayoutSet publicLayoutSet = null;
LayoutSetPrototype publicLayoutSetPrototype = null;
boolean publicLayoutSetPrototypeLinkEnabled = true;

if (showPrototypes && (group != null)) {
	try {
		LayoutLocalServiceUtil.getLayouts(liveGroup.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroup.getGroupId(), true);

		privateLayoutSetPrototypeLinkEnabled = privateLayoutSet.isLayoutSetPrototypeLinkEnabled();

		String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(layoutSetPrototypeUuid);
		}
	}
	catch (Exception e) {
	}

	try {
		LayoutLocalServiceUtil.getLayouts(liveGroup.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroup.getGroupId(), false);

		publicLayoutSetPrototypeLinkEnabled = publicLayoutSet.isLayoutSetPrototypeLinkEnabled();

		String layoutSetPrototypeUuid = publicLayoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(layoutSetPrototypeUuid);
		}
	}
	catch (Exception e) {
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="details" />

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= RequiredGroupException.class %>" message="old-group-name-is-a-required-system-group" />

<aui:fieldset>
	<c:choose>
		<c:when test="<%= (liveGroup != null) && PortalUtil.isSystemGroup(liveGroup.getName()) %>">
			<aui:input name="name" type="hidden" />
		</c:when>
		<c:when test="<%= (liveGroup != null) && liveGroup.isOrganization() %>">
			<aui:field-wrapper helpMessage="the-name-of-this-site-cannot-be-edited-because-it-belongs-to-an-organization" label="name">
				<%= liveGroup.getDescriptiveName(locale) %>
			</aui:field-wrapper>
		</c:when>
		<c:otherwise>
			<aui:input name="name" />
		</c:otherwise>
	</c:choose>

	<aui:input name="description" />

	<aui:select label="membership-type" name="type">
		<aui:option label="open" value="<%= GroupConstants.TYPE_SITE_OPEN %>" />
		<aui:option label="restricted" value="<%= GroupConstants.TYPE_SITE_RESTRICTED %>" />
		<aui:option label="private" value="<%= GroupConstants.TYPE_SITE_PRIVATE %>" />
	</aui:select>

	<aui:input inlineLabel="left" name="active" value="<%= true %>" />

	<c:if test="<%= liveGroup != null %>">
		<aui:field-wrapper label="site-id">
			<%= liveGroup.getGroupId() %>
		</aui:field-wrapper>
	</c:if>
</aui:fieldset>

<aui:fieldset>
	<c:choose>
		<c:when test="<%= showPrototypes && ((group != null) || (!layoutSetPrototypes.isEmpty() && (layoutSetPrototype == null))) %>">
			<liferay-ui:panel-container extended="<%= false %>">
				<liferay-ui:panel defaultState='<%= ((group != null) && (group.getPublicLayoutsPageCount() > 0)) ? "open" : "closed" %>' collapsible="<%= true %>" title="public-pages">
					<c:choose>
						<c:when test="<%= ((group == null) || (group.getPublicLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
							<aui:select helpMessage="site-templates-with-an-incompatible-application-adapter-are-disabled" label="site-template" name="publicLayoutSetPrototypeId">
								<aui:option label="none" selected="<%= true %>" value="" />

								<%
								for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
									UnicodeProperties settingsProperties = curLayoutSetPrototype.getSettingsProperties();

									String servletContextName = settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK);
								%>

									<aui:option data-servletContextName="<%= servletContextName %>" value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(user.getLanguageId())) %></aui:option>

								<%
								}
								%>

							</aui:select>

							<c:choose>
								<c:when test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE) %>">
									<div class="aui-helper-hidden" id="<portlet:namespace />publicLayoutSetPrototypeIdOptions">
										<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
									</div>
								</c:when>
								<c:otherwise>
									<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="<%= (group != null) && (group.getPublicLayoutsPageCount() > 0) %>">
									<liferay-portlet:actionURL var="publicPagesURL" portletName="<%= PortletKeys.SITE_REDIRECTOR %>">
										<portlet:param name="struts_action" value="/my_sites/view" />
										<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
										<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
									</liferay-portlet:actionURL>

									<liferay-ui:icon
										image="view"
										label="<%= true %>"
										message="open-public-pages"
										method="get"
										target="_blank"
										url="<%= publicPagesURL.toString() %>"
									/>

									<c:choose>
										<c:when test="<%= (publicLayoutSetPrototype != null) && !liveGroup.isStaged() && PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE) %>">
											<aui:input label='<%= LanguageUtil.format(pageContext, "enable-propagation-of-changes-from-the-site-template-x", publicLayoutSetPrototype.getName(user.getLanguageId())) %>' name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
										</c:when>
										<c:when test="<%= publicLayoutSetPrototype != null %>">
											<liferay-ui:message arguments="<%= new Object[] {publicLayoutSetPrototype.getName(locale)} %>" key="these-pages-are-linked-to-site-template-x" />

											<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
										</c:when>
									</c:choose>
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="this-site-does-not-have-any-public-pages" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</liferay-ui:panel>
				<liferay-ui:panel defaultState='<%= ((group != null) && (group.getPrivateLayoutsPageCount() > 0)) ? "open" : "closed" %>' collapsible="<%= true %>" title="private-pages">
					<c:choose>
						<c:when test="<%= ((group == null) || (group.getPrivateLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
							<aui:select helpMessage="site-templates-with-an-incompatible-application-adapter-are-disabled" label="site-template" name="privateLayoutSetPrototypeId">
								<aui:option label="none" selected="<%= true %>" value="" />

								<%
								for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
									UnicodeProperties settingsProperties = curLayoutSetPrototype.getSettingsProperties();

									String servletContextName = settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK);
								%>

									<aui:option data-servletContextName="<%= servletContextName %>" value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(user.getLanguageId())) %></aui:option>

								<%
								}
								%>

							</aui:select>

							<c:choose>
								<c:when test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE) %>">
									<div class="aui-helper-hidden" id="<portlet:namespace />privateLayoutSetPrototypeIdOptions">
										<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help"  label="enable-propagation-of-changes-from-the-site-template" name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
									</div>
								</c:when>
								<c:otherwise>
									<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="<%= (group != null) && (group.getPrivateLayoutsPageCount() > 0) %>">
									<liferay-portlet:actionURL var="privatePagesURL" portletName="<%= PortletKeys.SITE_REDIRECTOR %>">
										<portlet:param name="struts_action" value="/my_sites/view" />
										<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
										<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
									</liferay-portlet:actionURL>

									<liferay-ui:icon
										image="view"
										label="<%= true %>"
										message="open-private-pages"
										method="get"
										target="_blank"
										url="<%= privatePagesURL.toString() %>"
									/>

									<c:choose>
										<c:when test="<%= (privateLayoutSetPrototype != null) && !liveGroup.isStaged() && PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE) %>">
											<aui:input label='<%= LanguageUtil.format(pageContext, "enable-propagation-of-changes-from-the-site-template-x", privateLayoutSetPrototype.getName(user.getLanguageId())) %>' name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
										</c:when>
										<c:when test="<%= privateLayoutSetPrototype != null %>">
											<liferay-ui:message arguments="<%= new Object[] {privateLayoutSetPrototype.getName(locale)} %>" key="these-pages-are-linked-to-site-template-x" />

											<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
										</c:when>
									</c:choose>
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="this-site-does-not-have-any-private-pages" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</liferay-ui:panel>
			</liferay-ui:panel-container>

			<%
			Set<String> servletContextNames = CustomJspRegistryUtil.getServletContextNames();
			%>

			<c:if test="<%= servletContextNames.size() > 0 %>">
				<aui:fieldset label="configuration">

					<%
					String customJspServletContextName = StringPool.BLANK;

					if (group != null) {
						UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

						customJspServletContextName = GetterUtil.getString(typeSettingsProperties.get("customJspServletContextName"));
					}
					%>

					<aui:select helpMessage='<%= LanguageUtil.format(pageContext, "application-adapter-help", "http://www.liferay.com/community/wiki/-/wiki/Main/Application+Adapters") %>' label="application-adapter" name="customJspServletContextName">
						<aui:option label="none" value="" />

						<%
						for (String servletContextName : servletContextNames) {
						%>

							<aui:option selected="<%= customJspServletContextName.equals(servletContextName) %>" value="<%= servletContextName %>"><%= CustomJspRegistryUtil.getDisplayName(servletContextName) %></aui:option>

						<%
						}
						%>

					</aui:select>
				</aui:fieldset>
			</c:if>
		</c:when>
		<c:when test="<%= layoutSetPrototype != null %>">
			<aui:fieldset label="pages">
				<aui:input name="layoutSetPrototypeId" type="hidden" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

				<aui:field-wrapper label="copy-as">
					<aui:input checked="<%= true %>" helpMessage='<%= LanguageUtil.format(pageContext, "select-this-to-copy-the-pages-of-the-site-template-x-as-public-pages-for-this-site", layoutSetPrototype.getName(user.getLanguageId())) %>' label="public-pages" name="layoutSetVisibility" type="radio" value="0" />
					<aui:input helpMessage='<%= LanguageUtil.format(pageContext, "select-this-to-copy-the-pages-of-the-site-template-x-as-private-pages-for-this-site", layoutSetPrototype.getName(user.getLanguageId())) %>' label="private-pages" name="layoutSetVisibility" type="radio" value="1" />
				</aui:field-wrapper>

				<c:choose>
					<c:when test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE) %>">
						<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="layoutSetPrototypeLinkEnabled" type="checkbox" value="<%= true %>" />
					</c:when>
					<c:otherwise>
						<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
					</c:otherwise>
				</c:choose>
			</aui:fieldset>
		</c:when>
	</c:choose>
</aui:fieldset>

<aui:script>
	function <portlet:namespace />testVisibility(currentValue, value) {
		return currentValue != '';
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />publicLayoutSetPrototypeId', <portlet:namespace />testVisibility, '<portlet:namespace />publicLayoutSetPrototypeIdOptions');
	Liferay.Util.toggleSelectBox('<portlet:namespace />privateLayoutSetPrototypeId', <portlet:namespace />testVisibility, '<portlet:namespace />privateLayoutSetPrototypeIdOptions');
</aui:script>