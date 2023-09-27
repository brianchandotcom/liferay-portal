<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User selUser = PortalUtil.getSelectedUser(request);

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

boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
%>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="profile" /></h3>

	<c:choose>
		<c:when test="<%= PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED && hasGroupUpdatePermission && ((selUser == null) || ((publicLayoutSetPrototype == null) && (selUser.getPublicLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
			<aui:select label="" name="publicLayoutSetPrototypeId">
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
			<aui:field-wrapper>
				<c:choose>
					<c:when test="<%= selUser != null %>">
						<c:choose>
							<c:when test="<%= selUser.getPublicLayoutsPageCount() > 0 %>">

								<%
								Group selUserGroup = selUser.getGroup();
								%>

								<clay:link
									aria-label='<%= LanguageUtil.get(request, "go-to-profile-pages") + StringPool.SPACE + LanguageUtil.get(request, "opens-new-window") %>'
									decoration="underline"
									displayType="primary"
									href="<%= selUserGroup.getDisplayURL(themeDisplay, false) %>"
									iconAfter="shortcut"
									label='<%= LanguageUtil.get(request, "my-profile") %>'
									target="_blank"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-user-does-not-have-any-public-pages" />
							</c:otherwise>
						</c:choose>

						<c:if test="<%= PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED && (publicLayoutSetPrototype != null) %>">

							<%
							String publicLayoutSetPrototypeName = HtmlUtil.escape(publicLayoutSetPrototype.getName(locale));
							%>

							<c:choose>
								<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
									<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", publicLayoutSetPrototypeName, false) %>' name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message arguments="<%= publicLayoutSetPrototypeName %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

									<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:when>
				</c:choose>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>
</clay:sheet-section>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="dashboard" /></h3>

	<c:choose>
		<c:when test="<%= PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED && hasGroupUpdatePermission && ((selUser == null) || ((privateLayoutSetPrototype == null) && (selUser.getPrivateLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
			<aui:select label="" name="privateLayoutSetPrototypeId">
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
			<aui:field-wrapper>
				<c:choose>
					<c:when test="<%= selUser != null %>">
						<c:choose>
							<c:when test="<%= selUser.getPrivateLayoutsPageCount() > 0 %>">

								<%
								Group selUserGroup = selUser.getGroup();
								%>

								<clay:link
									aria-label='<%= LanguageUtil.get(request, "go-to-dashboard-pages") + StringPool.SPACE + LanguageUtil.get(request, "opens-new-window") %>'
									decoration="underline"
									displayType="primary"
									href="<%= selUserGroup.getDisplayURL(themeDisplay, true) %>"
									iconAfter="shortcut"
									label='<%= LanguageUtil.get(request, "my-dashboard") %>'
									target="_blank"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-user-does-not-have-any-private-pages" />
							</c:otherwise>
						</c:choose>

						<c:if test="<%= PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED && (privateLayoutSetPrototype != null) %>">

							<%
							String privateLayoutSetPrototypeName = HtmlUtil.escape(privateLayoutSetPrototype.getName(locale));
							%>

							<c:choose>
								<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
									<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", privateLayoutSetPrototypeName, false) %>' name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message arguments="<%= privateLayoutSetPrototypeName %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

									<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:when>
				</c:choose>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>
</clay:sheet-section>

<%
if ((selUser == null) && layoutSetPrototypes.isEmpty()) {
	request.setAttribute(WebKeys.FORM_NAVIGATOR_SECTION_SHOW + "pages", Boolean.FALSE);
}
%>

<aui:script>
	function <portlet:namespace />isVisible(currentValue, value) {
		return currentValue != '';
	}

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />publicLayoutSetPrototypeId',
		<portlet:namespace />isVisible,
		'<portlet:namespace />publicLayoutSetPrototypeIdOptions'
	);
	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />privateLayoutSetPrototypeId',
		<portlet:namespace />isVisible,
		'<portlet:namespace />privateLayoutSetPrototypeIdOptions'
	);
</aui:script>