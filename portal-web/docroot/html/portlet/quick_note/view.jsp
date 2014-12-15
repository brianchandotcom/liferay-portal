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

<%@ include file="/html/portlet/quick_note/init.jsp" %>

<div id="<portlet:namespace />pad" style="background: <%= HtmlUtil.escapeAttribute(color) %>;">
	<c:if test="<%= portletDisplay.isShowConfigurationIcon() %>">
		<table width="100%">
		<tr>
			<td width="60%">
				<div class="portlet-title-default">&nbsp;</div>
			</td>
			<td>
				<c:if test="<%= portletDisplay.isShowCloseIcon() %>">
					<a border="0" class="close-note" href="<%= portletDisplay.getURLClose() %>"><img alt="<liferay-ui:message key="close" />" src="<%= themeDisplay.getPathThemeImages() %>/portlet/close.png" /></a>
				</c:if>

				<span class="note-color yellow"></span>
				<span class="note-color green"></span>
				<span class="note-color blue"></span>
				<span class="note-color red"></span>
			</td>
		</tr>
		</table>
	</c:if>

	<div id="<portlet:namespace />note"><%= StringUtil.replace(HtmlUtil.escape(data), "&lt;br /&gt;", "<br />") %></div>
</div>

<c:if test="<%= portletDisplay.isShowConfigurationIcon() %>">
	<aui:script use="aui-editable-deprecated,aui-io-request">
		var quickNotePad = A.one('#<portlet:namespace />pad');

		if (quickNotePad) {
			quickNotePad.all('.note-color').on(
				'click',
				function(event) {
					var box = event.currentTarget;

					var bgColor = box.getStyle('backgroundColor');

					quickNotePad.setStyle('backgroundColor', bgColor);

					A.io.request(
						'<%= themeDisplay.getPathMain() %>/quick_note/save',
						{
							data: {
								color: bgColor,
								p_auth: Liferay.authToken,
								p_l_id: '<%= plid %>',
								portletId: '<%= portletDisplay.getId() %>'
							}
						}
					);
				}
			);
		}

		new A.Editable(
			{
				inputType: 'textarea',
				node: '#<portlet:namespace />note',
				on: {
					contentTextChange: function(event) {
						var instance = this;

						if (!event.initial) {
							var newValue = event.newVal.replace(/\n/gi, '<br />');

							event.newVal = instance._toText(event.newVal);

							A.io.request(
								'<%= themeDisplay.getPathMain() %>/quick_note/save',
								{
									data: {
										data: newValue,
										p_auth: '<%= AuthTokenUtil.getToken(request) %>',
										p_l_id: '<%= plid %>',
										portletId: '<%= portletDisplay.getId() %>'
									}
								}
							);
						}
					}
				}
			}
		);
	</aui:script>
</c:if>