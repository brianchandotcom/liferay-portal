<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSetPrototype privateLayoutSetPrototype = null;
boolean privateLayoutSetPrototypeLinkEnabled = true;

LayoutSetPrototype publicLayoutSetPrototype = null;
boolean publicLayoutSetPrototypeLinkEnabled = true;

OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

Organization organization = organizationScreenNavigationDisplayContext.getOrganization();

Group organizationGroup = organization.getGroup();

boolean site = organizationGroup.isSite();

if (site) {
	LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(organizationGroup.getGroupId(), true);

	if (privateLayoutSet != null) {
		privateLayoutSetPrototypeLinkEnabled = privateLayoutSet.isLayoutSetPrototypeLinkEnabled();

		String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
		}
	}

	LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(organizationGroup.getGroupId(), false);

	if (publicLayoutSet != null) {
		publicLayoutSetPrototypeLinkEnabled = publicLayoutSet.isLayoutSetPrototypeLinkEnabled();

		String layoutSetPrototypeUuid = publicLayoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
		}
	}
}
%>

<clay:sheet-section>
	<c:choose>
		<c:when test="<%= (publicLayoutSetPrototype == null) && (privateLayoutSetPrototype == null) %>">
			<p class="sheet-text"><liferay-ui:message key="by-clicking-this-toggle-you-could-create-a-public-and-or-private-site-for-your-organization" /></p>

			<aui:input inlineLabel="right" label="create-site" labelCssClass="simple-toggle-switch" name="site" type="toggle-switch" value="<%= site %>" />
		</c:when>
		<c:otherwise>
			<aui:input label="create-site" name="site" type="hidden" value="<%= site %>" />
		</c:otherwise>
	</c:choose>

	<c:if test="<%= site %>">
		<aui:input inlineField="<%= true %>" name="siteId" type="resource" value="<%= String.valueOf(organizationGroup.getGroupId()) %>" />

		<div class="form-group">
			<liferay-ui:icon
				iconCssClass="icon-cog"
				label="<%= true %>"
				message="manage-site"
				url='<%=
					PortletURLBuilder.create(
						PortletProviderUtil.getPortletURL(request, organizationGroup, Group.class.getName(), PortletProvider.Action.EDIT)
					).setParameter(
						"viewOrganizationsRedirect", currentURL
					).buildString()
				%>'
			/>
		</div>
	</c:if>
</clay:sheet-section>

<%
boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
%>

<div id="<portlet:namespace />siteTemplates">
	<clay:sheet-section>
		<h3 class="sheet-subtitle">
			<c:choose>
				<c:when test="<%= organizationGroup.isPrivateLayoutsEnabled() %>">
					<liferay-ui:message key="public-pages" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="pages" />
				</c:otherwise>
			</c:choose>
		</h3>

		<c:choose>
			<c:when test="<%= (publicLayoutSetPrototype == null) && (organization.getPublicLayoutsPageCount() == 0) && !layoutSetPrototypes.isEmpty() %>">
				<clay:alert
					displayType="warning"
					message="this-action-cannot-be-undone"
				/>

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
							<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= true %>" />
						</div>
					</c:when>
					<c:otherwise>
						<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<c:choose>
						<c:when test="<%= organization.getPublicLayoutsPageCount() > 0 %>">
							<liferay-ui:icon
								iconCssClass="icon-search"
								label="<%= true %>"
								message='<%= organizationGroup.isPrivateLayoutsEnabled() ? "open-public-pages" : "open-pages" %>'
								method="get"
								target="_blank"
								url="<%= organizationGroup.getDisplayURL(themeDisplay, false) %>"
							/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="<%= organizationGroup.isPrivateLayoutsEnabled() %>">
									<liferay-ui:message key="this-organization-does-not-have-any-public-pages" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="this-organization-does-not-have-any-pages" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="form-group">
					<c:choose>
						<c:when test="<%= (publicLayoutSetPrototype != null) && !organizationGroup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
							<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)), false) %>' name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
						</c:when>
						<c:when test="<%= publicLayoutSetPrototype != null %>">
							<liferay-ui:message arguments="<%= HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)) %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

							<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
						</c:when>
					</c:choose>
				</div>
			</c:otherwise>
		</c:choose>
	</clay:sheet-section>

	<c:if test="<%= organizationGroup.isPrivateLayoutsEnabled() %>">
		<clay:sheet-section>
			<h3 class="sheet-subtitle"><liferay-ui:message key="private-pages" /></h3>

			<c:choose>
				<c:when test="<%= (privateLayoutSetPrototype == null) && (organization.getPrivateLayoutsPageCount() == 0) && !layoutSetPrototypes.isEmpty() %>">
					<clay:alert
						displayType="warning"
						message="this-action-cannot-be-undone"
					/>

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
								<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= true %>" />
							</div>
						</c:when>
						<c:otherwise>
							<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="form-group">
						<c:choose>
							<c:when test="<%= organization.getPrivateLayoutsPageCount() > 0 %>">
								<liferay-ui:icon
									iconCssClass="icon-search"
									label="<%= true %>"
									message="open-private-pages"
									method="get"
									target="_blank"
									url="<%= organizationGroup.getDisplayURL(themeDisplay, true) %>"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-organization-does-not-have-any-private-pages" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<c:choose>
							<c:when test="<%= (privateLayoutSetPrototype != null) && !organizationGroup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
								<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)), false) %>' name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
							</c:when>
							<c:when test="<%= privateLayoutSetPrototype != null %>">
								<liferay-ui:message arguments="<%= HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)) %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

								<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
							</c:when>
						</c:choose>
					</div>
				</c:otherwise>
			</c:choose>
		</clay:sheet-section>
	</c:if>

	<clay:sheet-footer>
		<aui:button primary="<%= true %>" type="submit" />

		<aui:button href="<%= organizationScreenNavigationDisplayContext.getBackURL() %>" type="cancel" />
	</clay:sheet-footer>
</div>

<c:if test="<%= !site %>">
	<aui:script>
		function <portlet:namespace />isVisible(currentValue, value) {
			return currentValue != '';
		}

		Liferay.Util.toggleBoxes(
			'<portlet:namespace />site',
			'<portlet:namespace />siteTemplates'
		);

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
</c:if>