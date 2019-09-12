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

<%@ include file="/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBRequestHelper mbRequestHelper = new MBRequestHelper(request);

MBCategory category = mbRequestHelper.getCategory();

long categoryId = MBUtil.getCategoryId(request, category);

long parentCategoryId = mbRequestHelper.getParentCategoryId();

String defaultDisplayStyle = MBCategoryConstants.DEFAULT_DISPLAY_STYLE;

if ((category == null) && (parentCategoryId > 0)) {
	MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

	defaultDisplayStyle = parentCategory.getDisplayStyle();
}

String displayStyle = BeanParamUtil.getString(category, request, "displayStyle", defaultDisplayStyle);

MBMailingList mailingList = null;

if (categoryId > 0) {
	mailingList = MBMailingListLocalServiceUtil.fetchCategoryMailingList(scopeGroupId, categoryId);
}

if ((category == null) && (mailingList == null)) {
	if (parentCategoryId > 0) {
		mailingList = MBMailingListLocalServiceUtil.fetchCategoryMailingList(scopeGroupId, parentCategoryId);
	}
}

if (category != null) {
	MBBreadcrumbUtil.addPortletBreadcrumbEntries(category, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
	}
}
else {
	MBBreadcrumbUtil.addPortletBreadcrumbEntries(parentCategoryId, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-category[message-board]"), currentURL);
	}
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

MBHomeDisplayContext mbHomeDisplayContext = mbDisplayContextProvider.getMBHomeDisplayContext(request, response);

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(LanguageUtil.get(request, mbHomeDisplayContext.getTitle()));
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<c:if test="<%= !portletTitleBasedNavigation %>">
		<h3><%= LanguageUtil.get(request, mbHomeDisplayContext.getTitle()) %></h3>
	</c:if>

	<portlet:actionURL name="/message_boards/edit_category" var="editCategoryURL">
		<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_category" />
	</portlet:actionURL>

	<aui:form action="<%= editCategoryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCategory();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
		<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

		<liferay-ui:error exception="<%= CaptchaConfigurationException.class %>" message="a-captcha-error-occurred-please-contact-an-administrator" />
		<liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
		<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
		<liferay-ui:error exception="<%= CategoryNameException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= MailingListEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
		<liferay-ui:error exception="<%= MailingListInServerNameException.class %>" message="please-enter-a-valid-incoming-server-name" />
		<liferay-ui:error exception="<%= MailingListInUserNameException.class %>" message="please-enter-a-valid-incoming-user-name" />
		<liferay-ui:error exception="<%= MailingListOutEmailAddressException.class %>" message="please-enter-a-valid-outgoing-email-address" />
		<liferay-ui:error exception="<%= MailingListOutServerNameException.class %>" message="please-enter-a-valid-outgoing-server-name" />
		<liferay-ui:error exception="<%= MailingListOutUserNameException.class %>" message="please-enter-a-valid-outgoing-user-name" />

		<aui:model-context bean="<%= category %>" model="<%= MBCategory.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<c:if test="<%= parentCategoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>">

					<%
					String parentCategoryName = StringPool.BLANK;

					try {
						MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

						parentCategoryName = parentCategory.getName();
					}
					catch (NoSuchCategoryException nsce) {
					}
					%>

					<c:if test="<%= category != null %>">
						<aui:input label="parent-category[message-board]" name="parentCategoryName" type="resource" value="<%= parentCategoryName %>" />
					</c:if>
				</c:if>

				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

				<aui:input name="description" />

				<aui:select name="displayStyle">

					<%
					for (int i = 0; i < MBCategoryConstants.DISPLAY_STYLES.length; i++) {
					%>

						<aui:option label="<%= MBCategoryConstants.DISPLAY_STYLES[i] %>" selected="<%= displayStyle.equals(MBCategoryConstants.DISPLAY_STYLES[i]) %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="mailing-list">
				<aui:model-context bean="<%= mailingList %>" model="<%= MBMailingList.class %>" />

				<aui:input label="active" name="mailingListActive" type="toggle-switch" value='<%= ParamUtil.getBoolean(request, "mailingListActive", BeanPropertiesUtil.getBoolean(mailingList, "active")) %>' />

				<aui:input label="allow-anonymous-emails" name="allowAnonymous" type="toggle-switch" value='<%= BeanParamUtil.getBoolean(mailingList, request, "allowAnonymous") %>' />

				<div id="<portlet:namespace />mailingListSettings">
					<aui:input name="emailAddress" />

					<br />

					<aui:fieldset label="incoming">

						<%
						String protocol = BeanParamUtil.getString(mailingList, request, "inProtocol", "pop3");
						%>

						<aui:field-wrapper label="protocol">
							<aui:input checked='<%= protocol.startsWith("pop3") %>' label="pop" name="inProtocol" type="radio" value="pop3" />
							<aui:input checked='<%= protocol.startsWith("imap") %>' label="imap" name="inProtocol" type="radio" value="imap" />
						</aui:field-wrapper>

						<aui:input label="server-name" name="inServerName" />

						<aui:input label="server-port" name="inServerPort" value="110" />

						<aui:input label="use-a-secure-network-connection" name="inUseSSL" />

						<aui:input label="user-name" name="inUserName" />

						<aui:input label="password" name="inPassword" />

						<aui:input label="read-interval-minutes" name="inReadInterval" value="5" />
					</aui:fieldset>

					<aui:fieldset label="outgoing">
						<aui:input label="email-address" name="outEmailAddress" />

						<aui:input label="use-custom-outgoing-server" name="outCustom" />

						<div id="<portlet:namespace />outCustomSettings">
							<aui:input label="server-name" name="outServerName" />

							<aui:input label="server-port" name="outServerPort" value="25" />

							<aui:input label="use-a-secure-network-connection" name="outUseSSL" />

							<aui:input label="user-name" name="outUserName" />

							<aui:input label="password" name="outPassword" />
						</div>
					</aui:fieldset>
				</div>

				<c:if test="<%= (category == null) && captchaConfiguration.messageBoardsEditCategoryCaptchaEnabled() %>">
					<portlet:resourceURL id="/message_boards/captcha" var="captchaURL" />

					<liferay-captcha:captcha
						url="<%= captchaURL %>"
					/>
				</c:if>
			</aui:fieldset>

			<liferay-expando:custom-attributes-available
				className="<%= MBCategory.class.getName() %>"
			>
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
					<liferay-expando:custom-attribute-list
						className="<%= MBCategory.class.getName() %>"
						classPK="<%= (category != null) ? category.getCategoryId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</liferay-expando:custom-attributes-available>

			<c:if test="<%= category == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= MBCategory.class.getName() %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />saveCategory() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (category == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.toggleBoxes(
		'<portlet:namespace />mailingListActive',
		'<portlet:namespace />mailingListSettings'
	);
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />outCustom',
		'<portlet:namespace />outCustomSettings'
	);
</aui:script>