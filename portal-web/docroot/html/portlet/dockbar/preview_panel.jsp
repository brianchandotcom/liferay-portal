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
				<div class="span4">
					<div class="device autosize"></div>
				</div>
				<div class="span8">
					<div><%= LanguageUtil.get(pageContext, "autosize") %></div>
					<div><small>100%</small></div>
				</div>
			</aui:nav-item>

			<aui:nav-item cssClass="lfr-device-item lfr-device-rotation row-fluid selected" data-device="smartphone">
				<div class="span4">
					<div class="rotation smartphone"></div>
					<div class="device smartphone"></div>
				</div>
				<div class="span8">
					<div><%= LanguageUtil.get(pageContext, "smartphone") %></div>
					<div><small>768px</small></div>
				</div>
			</aui:nav-item>

			<aui:nav-item cssClass="lfr-device-item lfr-device-rotation row-fluid" data-device="tablet">
				<div class="span4">
					<div class="rotation tablet"></div>
					<div class="device tablet"></div>
				</div>
				<div class="span8">
					<div><%= LanguageUtil.get(pageContext, "tablet") %></div>
					<div><small>1024px</small></div>
				</div>
			</aui:nav-item>

			<aui:nav-item cssClass="lfr-device-item row-fluid" data-device="desktop">
				<div class="span4">
					<div class="device desktop"></div>
				</div>
				<div class="span8">
					<div><%= LanguageUtil.get(pageContext, "desktop") %></div>
					<div><small>1280px</small></div>
				</div>
			</aui:nav-item>

			<aui:nav-item cssClass="lfr-device-item row-fluid" data-device="custom">
				<p><%= LanguageUtil.get(pageContext, "custom") %> (px)</p>
				<aui:input cssClass="input-mini" inlineField="<%= true %>" label="" name="width" value="400"/><span> X </span><aui:input cssClass="input-mini" inlineField="<%= true %>" label="" name="height" value="400" />
			</aui:nav-item>
		</aui:nav>
	</div>
	<div class="alert"><small><%= LanguageUtil.get(pageContext, "preview-may-not-be-really-accurate") %></small></div>	
</div>

<aui:script use="liferay-dockbar-device-preview">
	new Liferay.Dockbar.DevicePreview(
		{
			devices: {
				'autosize': {},
				'custom': {
					height: '#<portlet:namespace />height',
					resizable: true,
					width: '#<portlet:namespace />width'
				},
				'desktop': {
					height: 1050,
					width: 1300
				},
				'smartphone': {
					height: 640,
					preventTransition: true,
					selected: true,
					skin: 'smartphone',
					width: 400
				},
				'tablet': {
					height: 900,
					preventTransition: true,
					skin: 'tablet',
					width: 760
				}
			},
			inputHeight: '#<portlet:namespace />height',
			inputWidth: '#<portlet:namespace />width',
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>