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
User user2 = (User)request.getAttribute("view_user.jsp-user");

Contact contact2 = user2.getContact();

boolean incompleteProfile = false;

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(User.class.getName(), user2.getUserId());

if (assetTags.isEmpty()) {
	incompleteProfile = true;
}

if (Validator.isNull(user2.getComments())) {
	incompleteProfile = true;
}
%>

<c:if test="<%= showComments && Validator.isNotNull(user2.getComments()) %>">
	<div class="field-group lfr-user-comments section" data-sectionId="comments" data-title="<%= LanguageUtil.get(request, "introduction") %>">
		<liferay-ui:icon
			icon="pencil"
			markupView="lexicon"
		/>

		<h3><liferay-ui:message key="introduction" />:</h3>

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
	<div class="field-group lfr-user-phones section" data-sectionId="phoneNumbers" data-title="<%= LanguageUtil.get(request, "phone-numbers") %>">
		<liferay-ui:icon
			icon="pencil"
			markupView="lexicon"
		/>

		<h3><liferay-ui:message key="phones" />:</h3>

		<ul class="property-list">

			<%
			for (Phone phone : phones) {
			%>

				<li class="<%= phone.isPrimary() ? "primary" : "" %>">
					<span class="property-type"><%= LanguageUtil.get(request, phone.getType().getName()) %></span>
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
	<div class="field-group lfr-user-email-addresses section" data-sectionId="additionalEmailAddresses" data-title="<%= LanguageUtil.get(request, "additional-email-addresses") %>">
		<liferay-ui:icon
			icon="pencil"
			markupView="lexicon"
		/>

		<h3><liferay-ui:message key="additional-email-addresses" />:</h3>

		<ul class="property-list">

			<%
			for (int i = 0; i < emailAddresses.size(); i++) {
				EmailAddress emailAddress = emailAddresses.get(i);
			%>

				<li class="<%= emailAddress.isPrimary() ? "primary" : "" %>">
					<span class="property-type"><%= LanguageUtil.get(request, emailAddress.getType().getName()) %></span>
					<span class="property"><a href="mailto:<%= emailAddress.getAddress() %>"><%= emailAddress.getAddress() %></a></span>
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
	<div class="field-group section" data-sectionId="instantMessenger" data-title="<%= LanguageUtil.get(request, "instant-messenger") %>">
		<liferay-ui:icon
			icon="pencil"
			markupView="lexicon"
		/>

		<h3><liferay-ui:message key="instant-messenger" />:</h3>

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
	<div class="field-group lfr-user-addresses section" data-sectionId="addresses" data-title="<%= LanguageUtil.get(request, "addresses") %>">
		<liferay-ui:icon
			icon="pencil"
			markupView="lexicon"
		/>

		<h3><liferay-ui:message key="addresses" />:</h3>

		<ul class="property-list">

			<%
			for (Address address : addresses) {
				String mailingName = LanguageUtil.get(request, address.getType().getName());
			%>

				<li class="<%= address.isPrimary() ? "primary" : "" %>">
					<span class="property-type"><%= mailingName %></span><br />

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
	<div class="field-group lfr-user-websites section" data-sectionId="websites" data-title="<%= LanguageUtil.get(request, "websites") %>">
		<liferay-ui:icon
			icon="pencil"
			markupView="lexicon"
		/>

		<h3><liferay-ui:message key="websites" />:</h3>

		<ul class="property-list">

			<%
			for (Website website : websites) {
				website = website.toEscapedModel();
			%>

				<li class="<%= website.isPrimary() ? "primary" : "" %>">
					<span class="property-type"><%= LanguageUtil.get(request, website.getType().getName()) %></span>

					<span class="property"><a href="<%= website.getUrl() %>"><%= website.getUrl() %></a></span>
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
	<div class="field-group lfr-user-social-network section" data-sectionId="socialNetwork" data-title="<%= LanguageUtil.get(request, "social-network") %>">
		<liferay-ui:icon
			icon="pencil"
			markupView="lexicon"
		/>

		<h3><liferay-ui:message key="social-network" />:</h3>

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
	<div class="field-group lfr-user-sms section" data-sectionId="sms" data-title="<%= LanguageUtil.get(request, "sms") %>">
		<liferay-ui:icon
			icon="pencil"
			markupView="lexicon"
		/>

		<h3><liferay-ui:message key="sms" />:</h3>

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