<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User user2 = (User)request.getAttribute("view_user.jsp-user");

Contact contact2 = user2.getContact();

boolean incompleteProfile = false;

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(User.class.getName(), user2.getUserId());

if (assetTags.isEmpty() || Validator.isNull(user2.getComments())) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showComments && Validator.isNotNull(user2.getComments()) %>">
	<div class="lfr-field-group lfr-user-comments section" data-title="<%= LanguageUtil.get(request, "introduction") %>">

		<%
		PortletURL editCommentsURL = PortletURLFactoryUtil.create(request, PortletKeys.MY_ACCOUNT, embeddedPersonalApplicationLayout, PortletRequest.RENDER_PHASE);
		%>

		<clay:link
			borderless="<%= true %>"
			cssClass="edit-button lfr-portal-tooltip"
			displayType="secondary"
			href="<%= editCommentsURL.toString() %>"
			icon="pencil"
			monospaced="<%= true %>"
			small="<%= true %>"
			title='<%= LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "information"), false) %>'
			type="button"
		/>

		<span class="h3"><liferay-ui:message key="introduction" />:</span>

		<ul class="property-list">
			<li>
				<span class="property"><%= HtmlUtil.replaceNewLine(user2.getComments()) %></span>
			</li>
		</ul>
	</div>
</c:if>

<%
List<Phone> phones = PhoneServiceUtil.getPhones(Contact.class.getName(), contact2.getContactId());

if (phones.isEmpty()) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showPhones && !phones.isEmpty() %>">
	<div class="lfr-field-group lfr-user-phones section" data-title="<%= LanguageUtil.get(request, "phone-numbers") %>">

		<%
		PortletURL editPhonesURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(request, PortletKeys.MY_ACCOUNT, embeddedPersonalApplicationLayout, PortletRequest.RENDER_PHASE)
		).setParameter(
			"screenNavigationCategoryKey", "contact"
		).setParameter(
			"screenNavigationEntryKey", "contact-information"
		).buildPortletURL();
		%>

		<clay:link
			borderless="<%= true %>"
			cssClass="edit-button lfr-portal-tooltip"
			displayType="secondary"
			href="<%= editPhonesURL.toString() %>"
			icon="pencil"
			monospaced="<%= true %>"
			small="<%= true %>"
			title='<%= LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "contact-information"), false) %>'
			type="button"
		/>

		<span class="h3"><liferay-ui:message key="phones" />:</span>

		<ul class="property-list">

			<%
			for (Phone phone : phones) {
				ListType listType = phone.getListType();
			%>

				<li class="<%= phone.isPrimary() ? "primary" : "" %>">
					<span class="property-type"><liferay-ui:message key="<%= listType.getName() %>" /></span>
					<span class="property"><%= HtmlUtil.escape(phone.getNumber()) %> <%= phone.getExtension() %></span>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</c:if>

<%
List<EmailAddress> emailAddresses = EmailAddressServiceUtil.getEmailAddresses(Contact.class.getName(), contact2.getContactId());

if (emailAddresses.isEmpty()) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showAdditionalEmailAddresses && !emailAddresses.isEmpty() %>">
	<div class="lfr-field-group lfr-user-email-addresses section" data-title="<%= LanguageUtil.get(request, "additional-email-addresses") %>">

		<%
		PortletURL editAdditionalEmailAddressesURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(request, PortletKeys.MY_ACCOUNT, embeddedPersonalApplicationLayout, PortletRequest.RENDER_PHASE)
		).setParameter(
			"screenNavigationCategoryKey", "contact"
		).setParameter(
			"screenNavigationEntryKey", "contact-information"
		).buildPortletURL();
		%>

		<clay:link
			borderless="<%= true %>"
			cssClass="edit-button lfr-portal-tooltip"
			displayType="secondary"
			href="<%= editAdditionalEmailAddressesURL.toString() %>"
			icon="pencil"
			monospaced="<%= true %>"
			small="<%= true %>"
			title='<%= LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "contact-information"), false) %>'
			type="button"
		/>

		<span class="h3"><liferay-ui:message key="additional-email-addresses" />:</span>

		<ul class="property-list">

			<%
			for (int i = 0; i < emailAddresses.size(); i++) {
				EmailAddress emailAddress = emailAddresses.get(i);

				ListType listType = emailAddress.getListType();
			%>

				<li class="<%= emailAddress.isPrimary() ? "primary" : "" %>">
					<span class="property-type"><liferay-ui:message key="<%= listType.getName() %>" /></span>
					<span class="property"><a class="text-decoration-underline" href="mailto:<%= emailAddress.getAddress() %>"><%= emailAddress.getAddress() %></a></span>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</c:if>

<%
String jabberSn = contact2.getJabberSn();
String skypeSn = contact2.getSkypeSn();

if (Validator.isNull(jabberSn) && Validator.isNull(skypeSn)) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showInstantMessenger && (Validator.isNotNull(jabberSn) || Validator.isNotNull(skypeSn)) %>">
	<div class="lfr-field-group section" data-title="<%= LanguageUtil.get(request, "instant-messenger") %>">
		<clay:link
			borderless="<%= true %>"
			cssClass="edit-button lfr-portal-tooltip"
			displayType="secondary"
			href='<%=
				PortletURLBuilder.create(
					PortletURLFactoryUtil.create(request, PortletKeys.MY_ACCOUNT, embeddedPersonalApplicationLayout, PortletRequest.RENDER_PHASE)
				).setParameter(
					"screenNavigationCategoryKey", "contact"
				).setParameter(
					"screenNavigationEntryKey", "contact-information"
				).buildString()
			%>'
			icon="pencil"
			monospaced="<%= true %>"
			small="<%= true %>"
			title='<%= LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "contact-information"), false) %>'
			type="button"
		/>

		<span class="h3"><liferay-ui:message key="instant-messenger" />:</span>

		<ul class="property-list">
			<c:if test="<%= Validator.isNotNull(jabberSn) %>">
				<li>
					<span class="property-type"><liferay-ui:message key="jabber" /></span>

					<span class="property"><%= HtmlUtil.escape(jabberSn) %></span>
				</li>
			</c:if>

			<c:if test="<%= Validator.isNotNull(skypeSn) %>">
				<li>
					<span class="property-type"><liferay-ui:message key="skype" /></span>

					<span class="property"><%= HtmlUtil.escape(skypeSn) %></span>
				</li>
			</c:if>
		</ul>
	</div>
</c:if>

<%
List<Address> addresses = AddressServiceUtil.getAddresses(Contact.class.getName(), contact2.getContactId());

if (addresses.isEmpty()) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showAddresses && !addresses.isEmpty() %>">
	<div class="lfr-field-group lfr-user-addresses section" data-title="<%= LanguageUtil.get(request, "addresses") %>">

		<%
		PortletURL editAddressesURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(request, PortletKeys.MY_ACCOUNT, embeddedPersonalApplicationLayout, PortletRequest.RENDER_PHASE)
		).setParameter(
			"screenNavigationCategoryKey", "contact"
		).buildPortletURL();
		%>

		<clay:link
			borderless="<%= true %>"
			cssClass="edit-button lfr-portal-tooltip"
			displayType="secondary"
			href="<%= editAddressesURL.toString() %>"
			icon="pencil"
			monospaced="<%= true %>"
			small="<%= true %>"
			title='<%= LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "addresses"), false) %>'
			type="button"
		/>

		<span class="h3"><liferay-ui:message key="addresses" />:</span>

		<ul class="property-list">

			<%
			for (Address address : addresses) {
				ListType listType = address.getListType();
			%>

				<li class="<%= address.isPrimary() ? "primary" : "" %>">
					<span class="property-type"><liferay-ui:message key="<%= listType.getName() %>" /></span><br />

					<liferay-text-localizer:address-display
						address="<%= address %>"
					/>

					<c:if test="<%= address.isMailing() %>">(<liferay-ui:message key="mailing" />)</c:if>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</c:if>

<%
List<Website> websites = WebsiteServiceUtil.getWebsites(Contact.class.getName(), contact2.getContactId());

if (websites.isEmpty()) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showWebsites && !websites.isEmpty() %>">
	<div class="lfr-field-group lfr-user-websites section" data-title="<%= LanguageUtil.get(request, "websites") %>">

		<%
		PortletURL editWebsitesURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(request, PortletKeys.MY_ACCOUNT, embeddedPersonalApplicationLayout, PortletRequest.RENDER_PHASE)
		).setParameter(
			"screenNavigationCategoryKey", "contact"
		).setParameter(
			"screenNavigationEntryKey", "contact-information"
		).buildPortletURL();
		%>

		<clay:link
			borderless="<%= true %>"
			cssClass="edit-button lfr-portal-tooltip"
			displayType="secondary"
			href="<%= editWebsitesURL.toString() %>"
			icon="pencil"
			monospaced="<%= true %>"
			small="<%= true %>"
			title='<%= LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "contact-information"), false) %>'
			type="button"
		/>

		<span class="h3"><liferay-ui:message key="websites" />:</span>

		<ul class="property-list">

			<%
			for (Website website : websites) {
				website = website.toEscapedModel();
			%>

				<li class="<%= website.isPrimary() ? "primary" : "" %>">
					<span class="property-type"><liferay-ui:message key="<%= website.getListType().getName() %>" /></span>

					<span class="property"><a class="text-decoration-underline" href="<%= website.getUrl() %>"><%= website.getUrl() %></a></span>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</c:if>

<%
String facebook = contact2.getFacebookSn();
String twitter = contact2.getTwitterSn();

if (Validator.isNull(facebook) && Validator.isNull(twitter)) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showSocialNetwork && (Validator.isNotNull(facebook) || Validator.isNotNull(twitter)) %>">
	<div class="lfr-field-group lfr-user-social-network section" data-title="<%= LanguageUtil.get(request, "social-network") %>">

		<%
		PortletURL editSocialNetworkURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(request, PortletKeys.MY_ACCOUNT, embeddedPersonalApplicationLayout, PortletRequest.RENDER_PHASE)
		).setParameter(
			"screenNavigationCategoryKey", "contact"
		).setParameter(
			"screenNavigationEntryKey", "contact-information"
		).buildPortletURL();
		%>

		<clay:link
			borderless="<%= true %>"
			cssClass="edit-button lfr-portal-tooltip"
			displayType="secondary"
			href="<%= editSocialNetworkURL.toString() %>"
			icon="pencil"
			monospaced="<%= true %>"
			small="<%= true %>"
			title='<%= LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "contact-information"), false) %>'
			type="button"
		/>

		<span class="h3"><liferay-ui:message key="social-network" />:</span>

		<ul class="property-list">
			<c:if test="<%= Validator.isNotNull(facebook) %>">
				<li>
					<span class="property-type"><liferay-ui:message key="facebook" /></span>

					<span class="property"><%= HtmlUtil.escape(facebook) %></span>
				</li>
			</c:if>

			<c:if test="<%= Validator.isNotNull(twitter) %>">
				<li>
					<span class="property-type"><liferay-ui:message key="twitter" /></span>

					<span class="property"><%= HtmlUtil.escape(twitter) %></span>
				</li>
			</c:if>
		</ul>
	</div>
</c:if>

<%
if (Validator.isNull(contact2.getSmsSn())) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showSMS && Validator.isNotNull(contact2.getSmsSn()) %>">
	<div class="lfr-field-group lfr-user-sms section" data-title="<%= LanguageUtil.get(request, "sms") %>">

		<%
		PortletURL editSmsURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(request, PortletKeys.MY_ACCOUNT, embeddedPersonalApplicationLayout, PortletRequest.RENDER_PHASE)
		).setParameter(
			"screenNavigationCategoryKey", "contact"
		).setParameter(
			"screenNavigationEntryKey", "contact-information"
		).buildPortletURL();
		%>

		<clay:link
			borderless="<%= true %>"
			cssClass="edit-button lfr-portal-tooltip"
			displayType="secondary"
			href="<%= editSmsURL.toString() %>"
			icon="pencil"
			monospaced="<%= true %>"
			small="<%= true %>"
			title='<%= LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "contact-information"), false) %>'
			type="button"
		/>

		<span class="h3"><liferay-ui:message key="sms" />:</span>

		<ul class="property-list">
			<li class="property">
				<%= HtmlUtil.escape(contact2.getSmsSn()) %>
			</li>
		</ul>
	</div>
</c:if>

<c:if test="<%= incompleteProfile && showCompleteYourProfile && (themeDisplay.getUserId() == user2.getUserId()) %>">
	<%@ include file="/user/complete_your_profile.jspf" %>
</c:if>