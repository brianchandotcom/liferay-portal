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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<div id="<portlet:namespace />devicePreviewContainer">
	<button class="close pull-right" id="closePanel" type="button">&#x00D7;</button>

	<h1><%= LanguageUtil.get(pageContext, "preview") %></h1>

	<div class="device-preview-content">
		<aui:nav cssClass="nav-list">
			<aui:nav-item cssClass="lfr-device-item row-fluid" data-device="autosize">
				<div class="span4 autosize"></div>
				<div class="span8">
					<div><%= LanguageUtil.get(pageContext, "autosize") %></div>
					<div>100%</div>
				</div>
			</aui:nav-item>

			<aui:nav-item cssClass="lfr-device-item row-fluid selected" data-device="smartphone">
				<div class="span4 smartphone"></div>
				<div class="span8">
					<div><%= LanguageUtil.get(pageContext, "smartphone") %></div>
					<div>768px</div>
				</div>
			</aui:nav-item>

			<aui:nav-item cssClass="lfr-device-item row-fluid" data-device="tablet">
				<div class="span4 tablet"></div>
				<div class="span8">
					<div><%= LanguageUtil.get(pageContext, "tablet") %></div>
					<div>1024px</div>
				</div>
			</aui:nav-item>

			<aui:nav-item cssClass="lfr-device-item row-fluid" data-device="desktop">
				<div class="span4 desktop"></div>
				<div class="span8">
					<div><%= LanguageUtil.get(pageContext, "desktop") %></div>
					<div>1280px</div>
				</div>
			</aui:nav-item>

			<aui:nav-item cssClass="lfr-device-item row-fluid" data-device="custom">
				<div><%= LanguageUtil.get(pageContext, "custom") %> (px)</div>
				<aui:input cssClass="input-mini" inlineField="<%= true %>" label="" name="width" value="200"/> x <aui:input cssClass="input-mini" inlineField="<%= true %>" label="" name="height" value="200" />
			</aui:nav-item>
		</aui:nav>
	</div>
</div>

<aui:script use="liferay-dockbar-device-preview">
	new Liferay.Dockbar.DevicePreview(
		{
			devices: {
				'autosize': {},
				'custom': {
					height: '#<portlet:namespace />height',
					width: '#<portlet:namespace />width'
				},
				'desktop': {
					height: 1050,
					width: 1300
				},
				'smartphone': {
					height: 640,
					selected: true,
					width: 400
				},
				'tablet': {
					height: 900,
					width: 760
				}
			},
			inputHeight: '#<portlet:namespace />height',
			inputWidth: '#<portlet:namespace />width',
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>