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
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_sharing.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

String widgetURL = PortalUtil.getWidgetURL(portlet, themeDisplay);
%>

<portlet:actionURL name="editSharing" var="editSharingURL">
	<portlet:param name="mvcPath" value="/edit_sharing.jsp" />
	<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="sharing" />
</liferay-util:include>

<div class="portlet-configuration-edit-sharing">
	<liferay-frontend:edit-form
		action="<%= editSharingURL %>"
		method="post"
		name="fm"
	>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.SAVE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
		<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

		<liferay-frontend:edit-form-body>
			<liferay-frontend:fieldset-group>
				<liferay-frontend:fieldset
					collapsed="<%= false %>"
					collapsible="<%= true %>"
					label="any-website"
				>

					<%
					boolean widgetShowAddAppLink = GetterUtil.getBoolean(portletPreferences.getValue("lfrWidgetShowAddAppLink", null), PropsValues.THEME_PORTLET_SHARING_DEFAULT);
					%>

					<div class="alert alert-info">
						<liferay-ui:message key="share-this-application-on-any-website" />
					</div>

					<liferay-util:buffer
						var="textAreaContent"
					>
						<iframe frameborder="0" height="100%" src="<%= HtmlUtil.escapeAttribute(widgetURL) %>" width="100%"></iframe>
					</liferay-util:buffer>

					<aui:field-wrapper label="code">
						<textarea class="field form-control lfr-textarea" id="<portlet:namespace />widgetScript" onClick="this.select();" readonly="true"><%= HtmlUtil.escape(textAreaContent) %></textarea>
					</aui:field-wrapper>

					<aui:input label='<%= LanguageUtil.format(request, "allow-users-to-add-x-to-any-website", HtmlUtil.escape(portletDisplay.getTitle()), false) %>' name="widgetShowAddAppLink" type="toggle-switch" value="<%= widgetShowAddAppLink %>" />
				</liferay-frontend:fieldset>

				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					label="facebook"
				>

					<%
					String facebookAPIKey = GetterUtil.getString(portletPreferences.getValue("lfrFacebookApiKey", null));
					String facebookCanvasPageURL = GetterUtil.getString(portletPreferences.getValue("lfrFacebookCanvasPageUrl", null));
					boolean facebookShowAddAppLink = GetterUtil.getBoolean(portletPreferences.getValue("lfrFacebookShowAddAppLink", null), true);

					String callbackURL = widgetURL;
					%>

					<div class="alert alert-info">
						<aui:a href="http://developers.facebook.com" target="_blank"><liferay-ui:message key="get-the-api-key-and-canvas-page-url-from-facebook" /></aui:a>
					</div>

					<aui:input cssClass="lfr-input-text-container" label="api-key" name="facebookAPIKey" value="<%= HtmlUtil.toInputSafe(facebookAPIKey) %>" />

					<aui:field-wrapper cssClass="form-group" label="canvas-page-url" name="facebookCanvasPageURLWrapper">
						<div class="form-text">http://www.liferay.com/</div>

						<aui:input cssClass="flexible lfr-input-text-container" label="" name="facebookCanvasPageURL" prefix="/" value="<%= HtmlUtil.toInputSafe(facebookCanvasPageURL) %>" />
					</aui:field-wrapper>

					<c:if test="<%= Validator.isNotNull(facebookCanvasPageURL) %>">
						<br />

						<div class="alert alert-info">
							<liferay-ui:message key="copy-the-callback-url-and-specify-it-in-facebook" />

							<liferay-ui:message key="this-application-is-exposed-to-facebook-via-an-iframe" />
						</div>

						<aui:input name="callbackURL" type="resource" value="<%= callbackURL %>" />

						<aui:input label='<%= LanguageUtil.format(request, "allow-users-to-add-x-to-facebook", HtmlUtil.escape(portletDisplay.getTitle()), false) %>' name="facebookShowAddAppLink" type="toggle-switch" value="<%= facebookShowAddAppLink %>" />
					</c:if>
				</liferay-frontend:fieldset>

				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					label="opensocial-gadget"
				>

					<%
					boolean iGoogleShowAddAppLink = PrefsParamUtil.getBoolean(portletPreferences, request, "lfrIgoogleShowAddAppLink");
					%>

					<div class="alert alert-info">
						<liferay-ui:message key="use-the-opensocial-gadget-url-to-create-an-opensocial-gadget" />
					</div>

					<aui:input name="opensocialGadgetURL" type="resource" value="<%= PortalUtil.getGoogleGadgetURL(portlet, themeDisplay) %>" />

					<aui:input label='<%= LanguageUtil.format(request, "allow-users-to-add-x-to-an-open-social-platform", HtmlUtil.escape(portletDisplay.getTitle()), false) %>' name="iGoogleShowAddAppLink" type="toggle-switch" value="<%= iGoogleShowAddAppLink %>" />
				</liferay-frontend:fieldset>

				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					label="netvibes"
				>

					<%
					boolean netvibesShowAddAppLink = PrefsParamUtil.getBoolean(portletPreferences, request, "lfrNetvibesShowAddAppLink");
					%>

					<div class="alert alert-info">
						<liferay-ui:message key="use-the-netvibes-widget-url-to-create-a-netvibes-widget" />
					</div>

					<aui:input name="netvibesWidgetURL" type="resource" value="<%= PortalUtil.getNetvibesURL(portlet, themeDisplay) %>" />

					<aui:input label='<%= LanguageUtil.format(request, "allow-users-to-add-x-to-netvibes-pages", HtmlUtil.escape(portletDisplay.getTitle()), false) %>' name="netvibesShowAddAppLink" type="toggle-switch" value="<%= netvibesShowAddAppLink %>" />
				</liferay-frontend:fieldset>
			</liferay-frontend:fieldset-group>
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<aui:button type="submit" />

			<aui:button type="cancel" />
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</div>