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

<%@ include file="/html/taglib/aui/nav_item/init.jsp" %>

<%@ page import="javax.servlet.jsp.tagext.BodyContent" %>

<%
BodyContent bodyContent = (BodyContent)request.getAttribute("aui:nav-item:bodyContent");

String bodyContentString = StringPool.BLANK;

if (bodyContent != null) {
	bodyContentString = bodyContent.getString();
}
%>

<c:if test="<%= !dropdown || Validator.isNotNull(bodyContentString.trim()) %>">
	<li class="<%= cssClass %><%= selected ? " active" : StringPool.BLANK %>" id="<%= id %>" role="presentation" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
		<c:if test="<%= Validator.isNotNull(iconClass) || Validator.isNotNull(label) %>">
			<c:if test="<%= Validator.isNotNull(href) %>">
				<c:choose>
					<c:when test="<%= Validator.isNull(ariaLabel) %>">
						<a aria-labelledby="<%= id %>" class="<%= anchorCssClass %>" <%= AUIUtil.buildData(anchorData) %> href="<%= href %>" id="<%= anchorId %>" role="<%= Validator.isNull(ariaRole) ? "menuitem" : ariaRole %>" title="<liferay-ui:message key="<%= title %>" />">
					</c:when>
					<c:otherwise>
						<a aria-label="<%= ariaLabel %>" class="<%= anchorCssClass %>" <%= AUIUtil.buildData(anchorData) %> href="<%= href %>" id="<%= anchorId %>" role="<%= Validator.isNull(ariaRole) ? "menuitem" : ariaRole %>" title="<liferay-ui:message key="<%= title %>" />">
					</c:otherwise>
				</c:choose>

				<c:if test="<%= useDialog %>">
					<aui:script>
						Liferay.delegateClick('<%= anchorId %>', Liferay.Util.openInDialog);
					</aui:script>
				</c:if>
			</c:if>
					<c:if test="<%= Validator.isNotNull(iconClass) %>">
						<i class="<%= iconClass %>"></i>
					</c:if>

					<span class="nav-item-label">
						<liferay-ui:message key="<%= label %>" />
					</span>

					<c:if test="<%= dropdown %>">
						<i class="icon-caret-down"></i>
					</c:if>
			<c:if test="<%= Validator.isNotNull(href) %>">
				</a>
			</c:if>
		</c:if>

		<c:if test="<%= dropdown %>">
			<aui:script use="aui-base,event-move,event-outside">
				A.Event.defineOutside('touchend');

				var container = A.one('#<%= id %>');

				container.one('a').on(
					'gesturemovestart',
					function(event) {
						var currentTarget = event.currentTarget;

						currentTarget.once(
							'gesturemoveend',
							function(event) {
								var eventOutside = event._event.type + 'outside';

								container.toggleClass('open');

								<c:if test="<%= !toggle %>">
									var menuOpen = container.hasClass('open');

									var handle = Liferay.Data['<%= id %>Handle'];

										if (menuOpen && !handle) {
											handle = currentTarget.on(
												eventOutside,
												function(event) {
													if (!event.target.ancestor('#<%= id %>')) {
														Liferay.Data['<%= id %>Handle'] = null;

														handle.detach();

														container.removeClass('open');
													}
												}
											);
										}
										else if (handle) {
											handle.detach();

											handle = null;
										}

									Liferay.Data['<%= id %>Handle'] = handle;
								</c:if>
							}
						);
					}
				);
			</aui:script>

			<c:if test="<%= wrapDropDownMenu %>">
				<ul class='dropdown-menu <%= LanguageUtil.get(locale, "lang.dir").equals("rtl") ? "pull-right" : StringPool.BLANK %>'>
			</c:if>
		</c:if>

		<c:if test="<%= Validator.isNotNull(bodyContentString) %>">
			<%= bodyContentString %>
		</c:if>

		<c:if test="<%= dropdown && wrapDropDownMenu %>">
			</ul>
		</c:if>
	</li>
</c:if>