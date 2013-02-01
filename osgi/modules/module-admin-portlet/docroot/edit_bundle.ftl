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

<#assign redirect = ParamUtil.getString(request, "redirect") />

<#assign bundleId = ParamUtil.getLong(request, "bundleId") />

<#assign bundle = bundleContext.getBundle(bundleId) />

<#assign headers = moduleUtil.getHeaders(bundle, themeDisplay.getLanguageId()) />

<#assign bundleName = headers[FrameworkConstants.BUNDLE_NAME]!bundle.getSymbolicName() />
<#assign bundleDescription = headers[FrameworkConstants.BUNDLE_DESCRIPTION]! />
<#assign fragmentHost = headers[FrameworkConstants.FRAGMENT_HOST]! />

<#if (fragmentHost?has_content)>
	<#assign bundleName = bundleName + " (" + LanguageUtil.get(pageContext, "fragment") + ")" />
</#if>

<#assign bundleUpdateLocation = headers[FrameworkConstants.BUNDLE_UPDATELOCATION]!bundle.getLocation() />

<@liferay_ui["header"] backURL=(redirect) title=(bundleName) />

<@portlet["actionURL"] var="editBundleURL">
	<@portlet["param"] name="mvcPath" value="/edit_bundle.ftl" />
</@>

<@aui["form"] action=(editBundleURL) enctype="multipart/form-data" method="post" name="fm">
	<@aui["input"] name="redirect" type="hidden" value=(currentURL) />
	<@aui["input"] name="bundleId" type="hidden" value=(bundleId?string) />

	<@aui["layout"]>

		<@aui["column"] columnWidth=(75) cssClass="lfr-asset-column lfr-asset-column-details" first=(true)>
			<@liferay_ui["panel-container"] extended=(false) persistState=(true)>

				<@aui["field-wrapper"] label="symbolic-name">
					${bundle.getSymbolicName()}
				</@>

				<@aui["field-wrapper"] label="bundle-id">
					${bundleId}
				</@>

				<#if (fragmentHost?has_content)>
					<@aui["field-wrapper"] label="host">
						${fragmentHost}
					</@>
				</#if>

				<#if (bundleDescription?has_content)>
					<@aui["field-wrapper"] label="description">
						${bundleDescription}
					</@>
				</#if>

				<#if (bundleUpdateLocation?has_content)>
					<@aui["field-wrapper"] label="location">
						${bundleUpdateLocation}
					</@>
				</#if>

				<@aui["field-wrapper"] label="state">
					<span class="state-${bundleId}">
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
			</@>
		</@>

		<@aui["column"] columnWidth=(25) cssClass="lfr-asset-column lfr-asset-column-actions" last=(true)>
			<div class="lfr-asset-summary">
				<@liferay_ui["icon"]
					cssClass="lfr-asset-avatar"
					image='../file_system/large/compressed'
					message=(bundleName)
				/>

				<div class="lfr-asset-name">
					<h4>${bundleName}</h4>
				</div>
			</div>

			<#assign expandedView = true />

			<#include "bundle_action.ftl" />
		</@>

	</@>

	<br />

	<div class="tabs">
		<@liferay_ui["tabs"] names="headers,services-registered,services-in-use" refresh=(false)>
			<@liferay_ui["section"]>
				<@liferay_ui["search-container"]>

					<#assign headerKeys = headers?keys />

					<@liferay_ui["search-container-results"]
						results=(headerKeys?sort)
						total=(headerKeys?size)
					/>

					<@liferay_ui["search-container-row"]
						className="java.lang.String"
						modelVar="headerKey"
					>

						<@liferay_ui["search-container-column-text"]
							name="header"
							valign="top"
							value=(headerKey)
						/>

						<@liferay_ui["search-container-column-text"]
							name="value"
						>
							<div class="container">
								<code>

									<#if (headerKey == FrameworkConstants.BUNDLE_DOCURL)>
										<a href="${headers[headerKey]}" target="_new">${headers[headerKey]}</a>
									<#elseif (headerKey == FrameworkConstants.IMPORT_PACKAGE)>
										${moduleUtil.processImports(pageContext, bundle)}
									<#else>
										<#assign parsedHeader = OSGiHeader.parseHeader(headers[headerKey]) />

										<#list parsedHeader.entrySet() as entry>
											<#assign key = entry.key />
											<#assign value = entry.value />

											<#assign outputValue = key />

											<#list value.entrySet() as attribute>
												<#assign aKey = attribute.key />
												<#assign aValue = attribute.value />

												<#assign outputValue = key + ";${aKey}=\"${aValue}\"" />
											</#list>

											${outputValue}

											<#if (entry_has_next)>
												<br />
											</#if>
										</#list>
									</#if>

								</code>
							</div>
						</@>

					</@>

					<@liferay_ui["search-iterator"] paginate=(false) />
				</@>
			</@>
			<@liferay_ui["section"]>
				<@liferay_ui["search-container"] emptyResultsMessage="no-services-are-registered">

					<#assign serviceReferences = moduleUtil.getRegisteredServices(bundle) />

					<@liferay_ui["search-container-results"]
						results=(serviceReferences)
						total=(serviceReferences?size)
					/>

					<@liferay_ui["search-container-row"]
						className="org.osgi.framework.ServiceReference"
						escapedModel=(false)
						modelVar="serviceReference"
					>

						<@liferay_ui["search-container-column-text"]
							name="service"
							valign="top"
						>

							<#assign objectClass = serviceReference.getProperty(FrameworkConstants.OBJECTCLASS) />

							<strong>
								<#if (objectClass?is_sequence)>
									<#list objectClass as oc>
										${oc?string}<#if (oc_has_next)><br /></#if>
									</#list>
								<#else>
									${objectClass?string}
								</#if>
							</strong>

							<br /><br />

							<#list serviceReference.getPropertyKeys() as key>
								<#assign value = serviceReference.getProperty(key) />

								<#if (key != FrameworkConstants.OBJECTCLASS)>
									<em>${key?string}:</em>

									<code>
										<#if (value?is_sequence)>
											<#list value as curValue>
												${curValue?string}<#if (curValue_has_next)>,<br /></#if>
											</#list>
										<#elseif (value?is_date)>
											${value?datetime?iso(themeDisplay.getTimeZone())}
										<#else>
											${value?string}
										</#if>
									</code>

									<#if (key_has_next)><br /></#if>
								</#if>
							</#list>

						</@>
					</@>

					<@liferay_ui["search-iterator"] paginate=(false) />
				</@>
			</@>
			<@liferay_ui["section"]>
				<@liferay_ui["search-container"] emptyResultsMessage="no-services-are-in-use">

					<#assign serviceReferences = moduleUtil.getServicesInUse(bundle) />

					<@liferay_ui["search-container-results"]
						results=(serviceReferences)
						total=(serviceReferences?size)
					/>

					<@liferay_ui["search-container-row"]
						className="org.osgi.framework.ServiceReference"
						escapedModel=(false)
						modelVar="serviceReference"
					>

						<@liferay_ui["search-container-column-text"]
							name="service"
							valign="top"
						>

							<#assign objectClass = serviceReference.getProperty(FrameworkConstants.OBJECTCLASS) />

							<strong>
								<#if (objectClass?is_sequence)>
									<#list objectClass as oc>
										${oc?string}<#if (oc_has_next)><br /></#if>
									</#list>
								<#else>
									${objectClass?string}
								</#if>
							</strong>

							<br /><br />

							<#list serviceReference.getPropertyKeys() as key>
								<#assign value = serviceReference.getProperty(key) />

								<#if (key != FrameworkConstants.OBJECTCLASS)>
									<em>${key?string}:</em>

									<code>
										<#if (value?is_sequence)>
											<#list value as curValue>
												${curValue?string}<#if (curValue_has_next)>,<br /></#if>
											</#list>
										<#elseif (value?is_date)>
											${value?datetime?iso(themeDisplay.getTimeZone())}
										<#else>
											${value?string}
										</#if>
									</code>

									<#if (key_has_next)><br /></#if>
								</#if>
							</#list>

						</@>
					</@>

					<@liferay_ui["search-iterator"] paginate=(false) />
				</@>
			</@>
		</@>
	</div>

	<#if (bundleId != 0)>
		<@aui["fieldset"]>
			<@aui["field-wrapper"] label="how-will-you-be-updating-the-bundle">
				<@aui["input"] checked=(true) inlineLabel="left" label="upload" name=(Constants.CMD) onClick=(renderResponse.getNamespace() + "switchType();") type="radio" value="update-from-upload" />
				<@aui["input"] inlineLabel="left" label="from-a-remote-location" name=(Constants.CMD) onClick=(renderResponse.getNamespace() + "switchType();") type="radio" value="update-from-remote-location" />
			</@>

			<@aui["input"] label="select-a-bundle-to-upload" name="importBundle" size="50" type="file" />

			<@aui["input"] cssClass="aui-helper-hidden" label="specify-a-url-for-a-remote-bundle" name="location" size="50" type="text" />
		</@>
	</#if>

	<@aui["button-row"]>
		<#if (bundleId != 0)>
			<@aui["button"] type="submit" value="update" />
		</#if>

		<@aui["button"] onClick=(redirect) type="cancel" />
	</@>
</@>

<#include "script.ftl" />