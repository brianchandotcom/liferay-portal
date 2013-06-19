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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "any-website");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

PortletPreferences preferences = renderRequest.getPreferences();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portlet_configuration/edit_sharing");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

String widgetURL = PortalUtil.getWidgetURL(portlet, themeDisplay);
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="sharing" />
</liferay-util:include>

<liferay-ui:tabs
	names="any-website,facebook,opensocial-gadget,netvibes,friends"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<portlet:actionURL var="editSharingURL">
	<portlet:param name="struts_action" value="/portlet_configuration/edit_sharing" />
</portlet:actionURL>

<aui:form action="<%= editSharingURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.SAVE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test='<%= tabs2.equals("any-website") %>'>

				<%
				boolean widgetShowAddAppLink = GetterUtil.getBoolean(preferences.getValue("lfrWidgetShowAddAppLink", null), PropsValues.THEME_PORTLET_SHARING_DEFAULT);
				%>

				<div class="alert alert-info">
					<liferay-ui:message key="share-this-application-on-any-website" />
				</div>

				<liferay-util:buffer var="textAreaContent">
<script src="<%= themeDisplay.getPortalURL() %><%= themeDisplay.getPathContext() %>/html/js/liferay/widget.js" type="text/javascript"></script>
<script type="text/javascript">
	Liferay.Widget({ url: '<%= widgetURL %>'});
</script></liferay-util:buffer>

				<aui:input cssClass="lfr-textarea-container" label="" name="example" onClick="Liferay.Util.selectAndCopy(this);" type="textarea" value="<%= textAreaContent %>" />

				<aui:input label='<%= LanguageUtil.format(pageContext, "allow-users-to-add-x-to-any-website", portletDisplay.getTitle()) %>' name="widgetShowAddAppLink" type="checkbox" value="<%= widgetShowAddAppLink %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("facebook") %>'>

				<%
				String facebookAPIKey = GetterUtil.getString(preferences.getValue("lfrFacebookApiKey", null));
				String facebookCanvasPageURL = GetterUtil.getString(preferences.getValue("lfrFacebookCanvasPageUrl", null));
				boolean facebookShowAddAppLink = GetterUtil.getBoolean(preferences.getValue("lfrFacebookShowAddAppLink", null), true);

				String callbackURL = widgetURL;

				if (portlet.getFacebookIntegration().equals(PortletConstants.FACEBOOK_INTEGRATION_FBML)) {
					callbackURL = PortalUtil.getFacebookURL(portlet, facebookCanvasPageURL, themeDisplay);
				}
				%>

				<div class="alert alert-info">
					<aui:a href="http://www.facebook.com/developers/editapp.php?new" target="_blank"><liferay-ui:message key="get-the-api-key-and-canvas-page-url-from-facebook" /></aui:a>
				</div>

				<aui:input cssClass="lfr-input-text-container" label="api-key" name="facebookAPIKey" value="<%= HtmlUtil.toInputSafe(facebookAPIKey) %>" />

				<aui:input cssClass="lfr-input-text-container flexible" label="canvas-page-url" name="facebookCanvasPageURL" prefix="http://apps.facebook.com/" suffix="/" value="<%= HtmlUtil.toInputSafe(facebookCanvasPageURL) %>" />

				<c:if test="<%= Validator.isNotNull(facebookCanvasPageURL) %>">
					<br />

					<div class="alert alert-info">
						<liferay-ui:message key="copy-the-callback-url-and-specify-it-in-facebook" />

						<c:choose>
							<c:when test="<%= portlet.getFacebookIntegration().equals(PortletConstants.FACEBOOK_INTEGRATION_FBML) %>">
								<liferay-ui:message key="this-application-is-exposed-to-facebook-via-fbml" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-application-is-exposed-to-facebook-via-an-iframe" />
							</c:otherwise>
						</c:choose>
					</div>

					<label><liferay-ui:message key="callback-url" /></label>

					<liferay-ui:input-resource url="<%= callbackURL %>" />

					<aui:input label='<%= LanguageUtil.format(pageContext, "allow-users-to-add-x-to-facebook", portletDisplay.getTitle()) %>' name="facebookShowAddAppLink" type="checkbox" value="<%= facebookShowAddAppLink %>" />
				</c:if>
			</c:when>
			<c:when test='<%= tabs2.equals("opensocial-gadget") %>'>

				<%
				boolean iGoogleShowAddAppLink = PrefsParamUtil.getBoolean(preferences, request, "lfrIgoogleShowAddAppLink");
				%>

				<div class="alert alert-info">
					<liferay-ui:message key="use-the-opensocial-gadget-url-to-create-an-opensocial-gadget" />
				</div>

				<label><liferay-ui:message key="opensocial-gadget-url" /></label>

				<liferay-ui:input-resource url="<%= PortalUtil.getGoogleGadgetURL(portlet, themeDisplay) %>" />

				<aui:input label='<%= LanguageUtil.format(pageContext, "allow-users-to-add-x-to-igoogle", portletDisplay.getTitle()) %>' name="iGoogleShowAddAppLink" type="checkbox" value="<%= iGoogleShowAddAppLink %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("netvibes") %>'>

				<%
				boolean netvibesShowAddAppLink = PrefsParamUtil.getBoolean(preferences, request, "lfrNetvibesShowAddAppLink");
				%>

				<div class="alert alert-info">
					<liferay-ui:message key="use-the-netvibes-widget-url-to-create-a-netvibes-widget" />
				</div>

				<label><liferay-ui:message key="netvibes-widget-url" /></label>

				<liferay-ui:input-resource url="<%= PortalUtil.getNetvibesURL(portlet, themeDisplay) %>" />

				<aui:input label='<%= LanguageUtil.format(pageContext, "allow-users-to-add-x-to-netvibes-pages", portletDisplay.getTitle()) %>' name="netvibesShowAddAppLink" type="checkbox" value="<%= netvibesShowAddAppLink %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("friends") %>'>

				<%
				boolean appShowShareWithFriendsLink = GetterUtil.getBoolean(preferences.getValue("lfrAppShowShareWithFriendsLink", null));
				%>

				<aui:input label='<%= LanguageUtil.format(pageContext, "allow-users-to-share-x-with-friends", portletDisplay.getTitle()) %>' name="appShowShareWithFriendsLink" type="checkbox" value="<%= appShowShareWithFriendsLink %>" />
			</c:when>
		</c:choose>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs2), currentURL);
%>