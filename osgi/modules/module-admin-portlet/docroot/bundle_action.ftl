<#--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
-->

<#if (permissionChecker.isOmniadmin())>
	<@liferay_ui["icon-menu"] cssClass="module-actions" showExpanded=(expandedView) showWhenSingleIcon=(expandedView)>
		<#if (!expandedView)>
			<@portlet["renderURL"] var="viewURL">
				<@portlet["param"] name="mvcPath" value="/edit_bundle.ftl" />
				<@portlet["param"] name="redirect" value=(currentURL) />
				<@portlet["param"] name="bundleId" value=(bundleId?string) />
			</@>

			<@liferay_ui["icon"]
				cssClass=("view")
				image="view"
				url=(viewURL)
			/>
		</#if>

		<#if (bundleId != 0)>
			<#if (!fragmentHost?has_content)>

				<#assign taglibURL = "javascript:Liferay.ModuleAdmin.start({bundleId:" + bundleId + ", message: '" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-start-this-bundle") + "'})" />
				<#assign cssClass = renderResponse.getNamespace() + "start_" + bundleId />

				<#if (bundle.getState() == Bundle.ACTIVE)>
					<#assign cssClass = "aui-helper-hidden " + cssClass />
				</#if>

				<@liferay_ui["icon"]
					cssClass=(cssClass)
					message="start"
					src=(themeDisplay.getPathThemeImages() + "/common/add.png")
					url=(taglibURL)
				/>

				<#assign taglibURL = "javascript:Liferay.ModuleAdmin.stop({bundleId:" + bundleId + ", message: '" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-stop-this-bundle") + "'})" />
				<#assign cssClass = "aui-helper-hidden " + renderResponse.getNamespace() + "stop_" + bundleId />

				<#if (bundle.getState() == Bundle.ACTIVE)>
					<#assign cssClass = cssClass?replace("aui-helper-hidden", "") />
				</#if>

				<@liferay_ui["icon"]
					cssClass=(cssClass)
					message="stop"
					src=(themeDisplay.getPathThemeImages() + "/application/close.png")
					url=(taglibURL)
				/>
			</#if>

			<#assign taglibURL = "javascript:Liferay.ModuleAdmin.uninstall({bundleId:" + bundleId + ", message: '" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-uninstall-this-bundle") + "'})" />

			<@liferay_ui["icon"]
				cssClass=("uninstall")
				message="uninstall"
				src=(themeDisplay.getPathThemeImages() + "/common/delete.png")
				url=(taglibURL)
			/>

			<#if (bundleUpdateLocation?has_content)>
				<#assign taglibURL = "javascript:Liferay.ModuleAdmin.update({bundleId:" + bundleId + ", message: '" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-update-this-bundle") + "'})" />

				<@liferay_ui["icon"]
					cssClass=("update")
					message="update"
					src=(themeDisplay.getPathThemeImages() + "/common/undo.png")
					url=(taglibURL)
				/>
			</#if>
		</#if>
	</@>
</#if>