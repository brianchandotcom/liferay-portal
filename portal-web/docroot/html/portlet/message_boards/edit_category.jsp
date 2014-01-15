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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId", MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

String defaultDisplayStyle = MBCategoryConstants.DEFAULT_DISPLAY_STYLE;

if ((category == null) && (parentCategoryId > 0)) {
	MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

	defaultDisplayStyle = parentCategory.getDisplayStyle();
}

String displayStyle = BeanParamUtil.getString(category, request, "displayStyle", defaultDisplayStyle);

MBMailingList mailingList = null;

try {
	if (categoryId > 0) {
		mailingList = MBMailingListLocalServiceUtil.getCategoryMailingList(scopeGroupId, categoryId);
	}
}
catch (NoSuchMailingListException nsmle) {
}

if ((category == null) && (mailingList == null)) {
	try {
		if (parentCategoryId > 0) {
			mailingList = MBMailingListLocalServiceUtil.getCategoryMailingList(scopeGroupId, parentCategoryId);
		}
	}
	catch (NoSuchMailingListException nsmle) {
	}
}

if (category != null) {
	MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
	}
}
else {
	if (parentCategoryId > 0) {
		MBUtil.addPortletBreadcrumbEntries(parentCategoryId, request, renderResponse);
	}

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-category[message-board]"), currentURL);
	}
}
%>

<liferay-util:include page="/html/portlet/message_boards/top_links.jsp" />

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= (category == null) %>"
	title='<%= (category == null) ? "add-category[message-board]" : LanguageUtil.format(pageContext, "edit-x", category.getName(), false) %>'
/>

<portlet:actionURL var="editCategoryURL">
	<portlet:param name="struts_action" value="/message_boards/edit_category" />
</portlet:actionURL>

<aui:form action="<%= editCategoryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCategory();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

	<liferay-ui:error exception="<%= CaptchaMaxChallengesException.class %>" message="maximum-number-of-captcha-attempts-exceeded" />
	<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
	<liferay-ui:error exception="<%= CategoryNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= MailingListEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
	<liferay-ui:error exception="<%= MailingListInServerNameException.class %>" message="please-enter-a-valid-incoming-server-name" />
	<liferay-ui:error exception="<%= MailingListInUserNameException.class %>" message="please-enter-a-valid-incoming-user-name" />
	<liferay-ui:error exception="<%= MailingListOutEmailAddressException.class %>" message="please-enter-a-valid-outgoing-email-address" />
	<liferay-ui:error exception="<%= MailingListOutServerNameException.class %>" message="please-enter-a-valid-outgoing-server-name" />
	<liferay-ui:error exception="<%= MailingListOutUserNameException.class %>" message="please-enter-a-valid-outgoing-user-name" />

	<aui:model-context bean="<%= category %>" model="<%= MBCategory.class %>" />

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
				<aui:field-wrapper label="parent-category[message-board]">
					<liferay-ui:input-resource id="parentCategoryName" url="<%= parentCategoryName %>" />
				</aui:field-wrapper>
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

		<liferay-ui:custom-attributes-available className="<%= MBCategory.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= MBCategory.class.getName() %>"
				classPK="<%= (category != null) ? category.getCategoryId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= category == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= MBCategory.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<br />

		<liferay-ui:panel-container extended="<%= true %>" id="messageBoardsCategoryPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="messageBoardsMailingListPanel" persistState="<%= true %>" title="mailing-list">

				<aui:model-context bean="<%= mailingList %>" model="<%= MBMailingList.class %>" />

				<aui:input fieldParam="mailingListActive" name="active" />

				<aui:input label="allow-anonymous-emails" name="allowAnonymous" />

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
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>

	<br />

	<c:if test="<%= (category == null) && PropsValues.CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY %>">
		<portlet:resourceURL var="captchaURL">
			<portlet:param name="struts_action" value="/message_boards/captcha" />
		</portlet:resourceURL>

		<liferay-ui:captcha url="<%= captchaURL %>" />
	</c:if>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCategory() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (category == null) ? Constants.ADD : Constants.UPDATE %>";

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.toggleBoxes('<portlet:namespace />mailingListActiveCheckbox', '<portlet:namespace />mailingListSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />outCustomCheckbox', '<portlet:namespace />outCustomSettings');
</aui:script>