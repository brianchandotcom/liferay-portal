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

<#include "init.ftl" />

<#assign tabs1 = ParamUtil.getString(request, "tabs1", "bundles") />

<#assign orderByCol = ParamUtil.getString(request, "orderByCol", "bundleId") />
<#assign orderByType = ParamUtil.getString(request, "orderByType", "asc") />

<#assign portletURL = renderResponse.createRenderURL() />

<#assign void = portletURL.setParameter("mvcPath", "/view.ftl") />
<#assign void = portletURL.setParameter("tabs1", tabs1) />

<@liferay_ui["error"] key="java.lang.IllegalArgumentException">
	<#assign error = SessionErrors.get(portletRequest, key) />

	${LanguageUtil.get(pageContext, error.getMessage())}
</@>
<@liferay_ui["error"] key="org.osgi.framework.BundleException">
	<#assign error = SessionErrors.get(portletRequest, key) />

	${LanguageUtil.get(pageContext, error.getMessage())}
</@>

<@liferay_ui["tabs"] names="bundles,install-bundle" url=(portletURL.toString()) />

<@portlet["actionURL"] var="editBundleURL">
	<@portlet["param"] name="mvcPath" value="/edit_bundle.ftl" />
</@>

<@aui["form"] action=(editBundleURL) enctype="multipart/form-data" method="post" name="fm">
	<@aui["input"] name="redirect" type="hidden" value=(currentURL) />
	<@aui["input"] name=(Constants.CMD) type="hidden" />
	<@aui["input"] name="bundleId" type="hidden" />
</@>

<#if (tabs1.equals("bundles"))>

	<@liferay_ui["search-container"] orderByCol=(orderByCol) orderByType=(orderByType)>

		<#assign bundles = ListUtil.fromArray(bundleContext.getBundles()) />

		<#if (Validator.isNotNull(searchContainer.getOrderByCol()))>
			<#assign orderByCol = searchContainer.getOrderByCol() />
		</#if>

		<#if (Validator.isNotNull(searchContainer.getOrderByType()))>
			<#assign orderByType = searchContainer.getOrderByType() />
		</#if>

		<#assign propertyComparator = objectUtil("com.liferay.util.PropertyComparator", orderByCol, (orderByType?lower_case == "asc"), false) />
		<#assign bundles = ListUtil.sort(bundles, propertyComparator) />

		<#assign end = searchContainer.getEnd() />

		<#if (bundles.size() < end)>
			<#assign end = bundles.size() />
		</#if>

		<@liferay_ui["search-container-results"]
			results=(bundles.subList(searchContainer.getStart(), end))
			total=(bundles.size())
		/>

		<@liferay_ui["search-container-row"]
			className="org.osgi.framework.Bundle"
			keyProperty="bundleId"
			modelVar="bundle"
		>

			<#assign headers = moduleUtil.getHeaders(bundle, themeDisplay.getLanguageId()) />

			<#assign bundleUpdateLocation = headers[FrameworkConstants.BUNDLE_UPDATELOCATION]!bundle.getLocation() />

			<#assign fragmentHost = headers[FrameworkConstants.FRAGMENT_HOST]! />

			<#assign bundleStartLevel = moduleUtil.getBundleStartLevel(bundle) />

			<#assign bundleId = bundle.getBundleId() />

			<@liferay_portlet["renderURL"] copyCurrentRenderParameters=(false) varImpl="rowURL">
				<@portlet["param"] name="mvcPath" value="/edit_bundle.ftl" />
				<@portlet["param"] name="redirect" value=(searchContainer.getIteratorURL()?string) />
				<@portlet["param"] name="bundleId" value=(bundleId?string) />
			</@>

			<@liferay_ui["search-container-column-text"]
				href=(rowURL)
				name="bundle-id"
				orderable=true
				orderableProperty="bundleId"
				value=(bundleId?string)
			/>

			<@liferay_ui["search-container-column-text"]
				name="name"
				orderable=true
				orderableProperty="symbolicName"
			>

				<#assign bundleName = headers[FrameworkConstants.BUNDLE_NAME]!bundle.getSymbolicName() />

				<a href="${rowURL}"><strong>${bundleName}</strong></a>

				<br/>

				<em>${bundle.getSymbolicName()}</em>

			</@>

			<@liferay_ui["search-container-column-text"]
				href=(rowURL)
				name="last-modified"
				orderable=true
				orderableProperty="lastModified"
			>

				<#assign cal = Calendar.getInstance() />

				<#assign void = cal.setTimeInMillis(bundle.getLastModified()) />

				${dateFormatDateTime.format(cal.getTime())}

			</@>

			<@liferay_ui["search-container-column-text"]
				href=(rowURL)
				property="version"
			/>

			<@liferay_ui["search-container-column-text"]
				name="start-level"
			>

				<#if (fragmentHost?has_content)>
					<@liferay_ui["message"] key="fragment" />
				<#elseif (bundleId == 0)>
					<@liferay_ui["message"] key="system" />
				<#else>
					<#assign taglibOnChange = "Liferay.ModuleAdmin.setStartLevel({bundleId:" + bundleId + ", message:'" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-change-the-start-level-of-this-bundle") + "' startLevel: AUI().one(this).val()})" />

					<@aui["select"] label="" name="startLevel" onChange=(taglibOnChange)>

						<#list 1..99 as i>
							<#assign label = (i?string) />

							<#if (i == PropsValues.MODULE_FRAMEWORK_BEGINNING_START_LEVEL)>
								<#assign label = LanguageUtil.get(pageContext, "framework") />
							<#elseif (i == 1)>
								<#assign label = LanguageUtil.get(pageContext, "default") />
							</#if>

							<@aui["option"] label=(label) selected=(bundleStartLevel.getStartLevel() == i) value=(i?string) />
						</#list>

					</@>
				</#if>

			</@>

			<@liferay_ui["search-container-column-text"]
				href=(rowURL)
				name="state"
			>

				<span class='state-${bundleId}'>
					<#assign state = bundle.getState() />

					<#if (state == Bundle.ACTIVE)>
						${LanguageUtil.get(pageContext, "active")?upper_case}
					<#elseif (state == Bundle.INSTALLED)>
						${LanguageUtil.get(pageContext, "installed")?upper_case}
					<#elseif (state == Bundle.RESOLVED)>
						${LanguageUtil.get(pageContext, "resolved")?upper_case}
					<#elseif (state == Bundle.STARTING)>
						${LanguageUtil.get(pageContext, "starting")?upper_case}
					<#elseif (state == Bundle.STOPPING)>
						${LanguageUtil.get(pageContext, "stopping")?upper_case}
					<#elseif (state == Bundle.UNINSTALLED)>
						${LanguageUtil.get(pageContext, "uninstalled")?upper_case}
					</#if>
				</span>

			</@>

			<@liferay_ui["search-container-column-text"]>

				<#assign expandedView = false />

				<#include "bundle_action.ftl" />

			</@>
		</@>

		<@liferay_ui["search-iterator"] />
	</@>

<#elseif (tabs1.equals("install-bundle"))>

	<@portlet["actionURL"] var="editBundleURL">
		<@portlet["param"] name="mvcPath" value="/edit_bundle.ftl" />
	</@>

	<@aui["form"] action=(editBundleURL) enctype="multipart/form-data" method="post" name="fm">
		<@aui["input"] name="redirect" type="hidden" value=(currentURL) />

		<@aui["fieldset"]>
			<@aui["field-wrapper"] label="how-will-you-be-installing-the-bundle">
				<#assign tablibOnClick = "${renderResponse.getNamespace()}switchType();" />

				<@aui["input"] checked=(true) inlineLabel="left" label="upload" name=(Constants.CMD) onClick=(tablibOnClick) type="radio" value="install-from-upload" />
				<@aui["input"] inlineLabel="left" label="from-a-remote-location" name=(Constants.CMD) onClick=(tablibOnClick) type="radio" value="install-from-remote-location" />
			</@>

			<@aui["input"] label="select-a-bundle-to-upload" name="importBundle" size="50" type="file" />

			<@aui["input"] cssClass="aui-helper-hidden" label="specify-a-url-for-a-remote-bundle" name="location" size="50" type="text" />
		</@>

		<@aui["button-row"]>
			<@aui["button"] type="submit" />
		</@>
	</@>

</#if>

<#include "script.ftl" />