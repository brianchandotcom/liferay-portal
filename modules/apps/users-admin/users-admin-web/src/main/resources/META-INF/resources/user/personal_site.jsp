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
User selUser = (User)request.getAttribute("user.selUser");

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSet privateLayoutSet = null;
LayoutSetPrototype privateLayoutSetPrototype = null;
boolean privateLayoutSetPrototypeLinkEnabled = true;

LayoutSet publicLayoutSet = null;
LayoutSetPrototype publicLayoutSetPrototype = null;
boolean publicLayoutSetPrototypeLinkEnabled = true;

boolean hasGroupUpdatePermission = true;

if (selUser != null) {
	Group userGroup = selUser.getGroup();

	hasGroupUpdatePermission = GroupPermissionUtil.contains(themeDisplay.getPermissionChecker(), userGroup.getGroupId(), ActionKeys.UPDATE);

	if (userGroup != null) {
		try {
			LayoutLocalServiceUtil.getLayouts(userGroup.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(userGroup.getGroupId(), true);

			privateLayoutSetPrototypeLinkEnabled = privateLayoutSet.isLayoutSetPrototypeLinkEnabled();

			String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
			}
		}
		catch (Exception e) {
		}

		try {
			LayoutLocalServiceUtil.getLayouts(userGroup.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(userGroup.getGroupId(), false);

			publicLayoutSetPrototypeLinkEnabled = publicLayoutSet.isLayoutSetPrototypeLinkEnabled();

			String layoutSetPrototypeUuid = publicLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
			}
		}
		catch (Exception e) {
		}
	}
}
%>

<h3><liferay-ui:message key="personal-site" /></h3>

<aui:fieldset>

	<%
	boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
	%>

	<c:choose>
		<c:when test="<%= PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED && hasGroupUpdatePermission && ((selUser == null) || ((publicLayoutSetPrototype == null) && (selUser.getPublicLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
			<aui:select label="my-profile" name="publicLayoutSetPrototypeId">
				<aui:option label="none" selected="<%= true %>" value="" />

				<%
				for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
				%>

					<aui:option label="<%= HtmlUtil.escape(layoutSetPrototype.getName(locale)) %>" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

				<%
				}
				%>

			</aui:select>

			<c:choose>
				<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
					<div class="hide" id="<portlet:namespace />publicLayoutSetPrototypeIdOptions">
						<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
					</div>
				</c:when>
				<c:otherwise>
					<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<aui:field-wrapper label="my-profile">
				<c:choose>
					<c:when test="<%= selUser != null %>">
						<c:choose>
							<c:when test="<%= selUser.getPublicLayoutsPageCount() > 0 %>">

								<%
								Group selUserGroup = selUser.getGroup();
								%>

								<liferay-ui:icon
									iconCssClass="icon-search"
									label="<%= true %>"
									message="open-pages"
									method="get"
									target="_blank"
									url="<%= selUserGroup.getDisplayURL(themeDisplay, false) %>"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-user-does-not-have-any-public-pages" />
							</c:otherwise>
						</c:choose>

						<c:if test="<%= PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED %>">
							<c:choose>
								<c:when test="<%= (publicLayoutSetPrototype != null) && hasUnlinkLayoutSetPrototypePermission %>">
									<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)), false) %>' name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
								</c:when>
								<c:when test="<%= publicLayoutSetPrototype != null %>">
									<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(privateLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

									<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
								</c:when>
							</c:choose>
						</c:if>
					</c:when>
				</c:choose>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%= PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED && hasGroupUpdatePermission && ((selUser == null) || ((privateLayoutSetPrototype == null) && (selUser.getPrivateLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
			<aui:select label="my-dashboard" name="privateLayoutSetPrototypeId">
				<aui:option label="none" selected="<%= true %>" value="" />

				<%
				for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
				%>

					<aui:option label="<%= HtmlUtil.escape(layoutSetPrototype.getName(locale)) %>" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

				<%
				}
				%>

			</aui:select>

			<c:choose>
				<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
					<div class="hide" id="<portlet:namespace />privateLayoutSetPrototypeIdOptions">
						<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
					</div>
				</c:when>
				<c:otherwise>
					<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<aui:field-wrapper label="my-dashboard">
				<c:choose>
					<c:when test="<%= selUser != null %>">
						<c:choose>
							<c:when test="<%= selUser.getPrivateLayoutsPageCount() > 0 %>">

								<%
								Group selUserGroup = selUser.getGroup();
								%>

								<liferay-ui:icon
									iconCssClass="icon-search"
									label="<%= true %>"
									message="open-pages"
									method="get"
									target="_blank"
									url="<%= selUserGroup.getDisplayURL(themeDisplay, true) %>"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-user-does-not-have-any-private-pages" />
								</c:otherwise>
						</c:choose>

						<c:if test="<%= PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED %>">
							<c:choose>
								<c:when test="<%= (privateLayoutSetPrototype != null) && hasUnlinkLayoutSetPrototypePermission %>">
									<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)), false) %>' name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
								</c:when>
								<c:when test="<%= privateLayoutSetPrototype != null %>">
									<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(privateLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

									<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
								</c:when>
							</c:choose>
						</c:if>
					</c:when>
				</c:choose>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>
</aui:fieldset>

<%
if ((selUser == null) && layoutSetPrototypes.isEmpty()) {
	request.setAttribute(WebKeys.FORM_NAVIGATOR_SECTION_SHOW + "pages", Boolean.FALSE);
}
%>

<aui:script>
	function <portlet:namespace />isVisible(currentValue, value) {
		return currentValue != '';
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />publicLayoutSetPrototypeId', <portlet:namespace />isVisible, '<portlet:namespace />publicLayoutSetPrototypeIdOptions');
	Liferay.Util.toggleSelectBox('<portlet:namespace />privateLayoutSetPrototypeId', <portlet:namespace />isVisible, '<portlet:namespace />privateLayoutSetPrototypeIdOptions');
</aui:script>