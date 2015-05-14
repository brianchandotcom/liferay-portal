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
String htmlAttributes =
	"alt=" + iFrameDisplayContext.getAlt() + "\n" +
	"border=" + iFrameDisplayContext.getBorder() + "\n" +
	"bordercolor=" + iFrameDisplayContext.getBordercolor() + "\n" +
	"frameborder=" + iFrameDisplayContext.getFrameborder() + "\n" +
	"hspace=" + iFrameDisplayContext.getHspace() + "\n" +
	"longdesc=" + iFrameDisplayContext.getLongdesc() + "\n" +
	"scrolling=" + iFrameDisplayContext.getScrolling() + "\n" +
	"title=" + iFrameDisplayContext.getTitle() + "\n" +
	"vspace=" + iFrameDisplayContext.getVspace() + "\n";
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:panel-container extended="<%= true %>" id="iframeSettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="iframeGeneralPanel" persistState="<%= true %>" title="general">
			<aui:fieldset>

				<%
				WindowState windowState = liferayPortletRequest.getWindowState();
				%>

				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" cssClass="lfr-input-text-container" label="source-url" name="preferences--src--" prefix="<%= iFrameDisplayContext.isRelative() ? StringPool.TRIPLE_PERIOD : StringPool.BLANK %>" type="text" value="<%= iFrameDisplayContext.getSrc() %>" />

				<aui:input label="relative-to-context-path" name="preferences--relative--" type="checkbox" value="<%= iFrameDisplayContext.isRelative() %>" />
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="iframeAuthenticationPanel" persistState="<%= true %>" title="authenticate">
			<aui:fieldset>
				<aui:input label="authenticate" name="preferences--auth--" type="checkbox" value="<%= iFrameDisplayContext.isAuth() %>" />

				<div id="<portlet:namespace />authenticationOptions">
					<div class="alert alert-info" id="<portlet:namespace />currentLoginMsg">
						<c:choose>
							<c:when test="<%= IFrameUtil.isPasswordTokenEnabled(renderRequest) %>">
								<liferay-ui:message key="you-may-use-the-tokens-email-address-screen-name-userid-and-password" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="you-may-use-the-tokens-email-address-screen-name-userid" />
							</c:otherwise>
						</c:choose>
					</div>

					<aui:select label="authentication-type" name="preferences--authType--" value="<%= iFrameDisplayContext.getAuthType() %>">
						<aui:option label="basic" />
						<aui:option label="form" />
					</aui:select>

					<div id="<portlet:namespace />formAuthOptions">
						<aui:select name="preferences--formMethod--" value="<%= iFrameDisplayContext.getFormMethod() %>">
							<aui:option label="get" />
							<aui:option label="post" />
						</aui:select>

						<aui:field-wrapper label="user-name">
							<table class="lfr-table">
							<tr>
								<td>
									<aui:input cssClass="lfr-input-text-container" label="field-name" name="preferences--userNameField--" type="text" value="<%= iFrameDisplayContext.getUserNameField() %>" />
								</td>
								<td>
									<aui:input cssClass="lfr-input-text-container" label="value" name="preferences--formUserName--" type="text" value="<%= iFrameDisplayContext.getFormUserName() %>" />
								</td>
							</tr>
							</table>
						</aui:field-wrapper>

						<aui:field-wrapper name="password">
							<table class="lfr-table">
							<tr>
								<td>
									<aui:input cssClass="lfr-input-text-container" label="field-name" name="preferences--passwordField--" type="text" value="<%= iFrameDisplayContext.getPasswordField() %>" />
								</td>
								<td>
									<aui:input cssClass="lfr-input-text-container" label="value" name="preferences--formPassword--" type="text" value="<%= iFrameDisplayContext.getFormPassword() %>" />
								</td>
							</tr>
							</table>

							<aui:input cssClass="lfr-input-text-container" name="preferences--hiddenVariables--" type="text" value="<%= iFrameDisplayContext.getHiddenVariables() %>" />
						</aui:field-wrapper>
					</div>

					<div id="<portlet:namespace />basicAuthOptions">
						<aui:input cssClass="lfr-input-text-container" label="user-name" name="preferences--basicUserName--" type="text" value="<%= iFrameDisplayContext.getBasicUserName() %>" />

						<aui:input cssClass="lfr-input-text-container" label="password" name="preferences--basicPassword--" type="text" value="<%= iFrameDisplayContext.getBasicPassword() %>" />
					</div>
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="iframeDisplaySettingsPanel" persistState="<%= true %>" title="display-settings">
			<aui:fieldset>
				<aui:input label="resize-automatically" name="preferences--resizeAutomatically--" type="checkbox" value="<%= iFrameDisplayContext.isResizeAutomatically() %>" />

				<div id="<portlet:namespace />displaySettings">
					<aui:input name="preferences--heightMaximized--" type="text" value="<%= iFrameDisplayContext.getHeightMaximized() %>">
						<aui:validator name="digits" />
						<aui:validator name="required" />
					</aui:input>

					<aui:input name="preferences--heightNormal--" type="text" value="<%= iFrameDisplayContext.getHeightNormal() %>">
						<aui:validator name="digits" />
						<aui:validator name="required" />
					</aui:input>

					<aui:input name="preferences--width--" type="text" value="<%= iFrameDisplayContext.getWidth() %>" />
				</div>

				<aui:input cssClass="lfr-textarea-container" name="preferences--htmlAttributes--" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();" type="textarea" value="<%= htmlAttributes %>" wrap="soft" />
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />auth', '<portlet:namespace />authenticationOptions');
	Liferay.Util.toggleBoxes('<portlet:namespace />resizeAutomatically', '<portlet:namespace />displaySettings', true);
	Liferay.Util.toggleSelectBox('<portlet:namespace />authType', 'form', '<portlet:namespace />formAuthOptions');
	Liferay.Util.toggleSelectBox('<portlet:namespace />authType', 'basic', '<portlet:namespace />basicAuthOptions');
</aui:script>