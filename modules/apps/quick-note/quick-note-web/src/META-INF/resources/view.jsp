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

<div id="<portlet:namespace />pad" style="background: <%= HtmlUtil.escapeAttribute(color) %>;">
	<c:if test="<%= portletDisplay.isShowConfigurationIcon() %>">
		<table width="100%">
		<tr>
			<td width="60%">
				<div class="portlet-title-default">&nbsp;</div>
			</td>
			<td>
				<c:if test="<%= portletDisplay.isShowCloseIcon() %>">
					<liferay-ui:icon
						cssClass="close-note"
						iconCssClass="icon-remove"
						message="close"
						url="<%= portletDisplay.getURLClose() %>"
					/>
				</c:if>

				<span class="note-color yellow"></span>
				<span class="green note-color"></span>
				<span class="blue note-color"></span>
				<span class="note-color red"></span>
			</td>
		</tr>
		</table>
	</c:if>

	<div class="note-content" id="<portlet:namespace />note"><%= StringUtil.replace(HtmlUtil.escape(data), "&lt;br /&gt;", "<br />") %></div>
</div>

<script src="/o/metal-components-1.0.0/soyutils.js"></script>

<aui:script require="crystal-modal/src/Modal,metal/src/soy/SoyComponent">
	var Modal = crystalModalSrcModal;
	var SoyComponent = metalSrcSoySoyComponent;

	//new Modal({
	$('body').modal({
		header: SoyComponent.sanitizeHtml('<h4 class="modal-title">Modal header</h4>'),
		body: 'One fine body...',
		footer: SoyComponent.sanitizeHtml('<button type="button" class="btn btn-primary">OK</button>')
	});
	//}).render();
</aui:script>