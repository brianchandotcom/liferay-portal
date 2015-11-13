<#--
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
-->

<#include "init.ftl">

<#assign redirectURL = renderResponse.createRenderURL() />

<#if Request["factoryConfigurationModel"]??>
	<#assign factoryConfigurationModel = Request["factoryConfigurationModel"] />

	<@liferay_ui["header"]
		backURL="${redirectURL}"
		title='${factoryConfigurationModel.getName()}'
	/>
</#if>

<#assign configurationModelIterator = Request["configurationModelIterator"] />

<div class="container-fluid-1280">
	<@liferay_ui["search-container"]
		emptyResultsMessage="no-configurations-were-found"
		total=configurationModelIterator.getTotal()
	>
		<@liferay_ui["search-container-results"]
			results=configurationModelIterator.getResults(searchContainer.getStart(), searchContainer.getEnd())
		/>

		<@liferay_ui["search-container-row"]
			className="com.liferay.configuration.admin.model.ConfigurationModel"
			keyProperty="ID"
			modelVar="configurationModel"
		>
			<@portlet["renderURL"] varImpl="editURL">
				<@portlet["param"]
					name="mvcPath"
					value="/edit_configuration.ftl"
				/>
				<@portlet["param"]
					name="factoryPid"
					value="${configurationModel.getFactoryPid()}"
				/>
				<@portlet["param"]
					name="pid"
					value="${configurationModel.getID()}"
				/>
			</@>

			<@portlet["renderURL"] varImpl="viewFactoryInstancesURL">
				<@portlet["param"]
					name="factoryPid"
					value="${configurationModel.getFactoryPid()}"
				/>
				<@portlet["param"]
					name="viewType"
					value="factoryInstances"
				/>
			</@>

			<@liferay_ui["search-container-column-text"]
				name="name"
			>
				<#if configurationModel.isFactory()>
					<@aui["a"] href=(viewFactoryInstancesURL.toString())>${configurationModel.getName()}</@>
				<#else>
					<@aui["a"] href=(editURL.toString())>${configurationModel.getName()}</@>
				</#if>

				<#if factoryConfigurationModel??>
					<br/>

					${configurationModel.getID()}
				</#if>
			</@>

			<@liferay_ui["search-container-column-text"]
				align="center"
				name="status"
			>
				<#if configurationModel.isFactory()>
					<@liferay_ui["icon"]
						cssClass="icon-plus-sign-2"
						message="factory"
					/>
				<#elseif configurationModel.getConfiguration()??>
					<@liferay_ui["icon"]
						cssClass="icon-check"
						message="active"
					/>
				<#else>
					<@liferay_ui["icon"]
						cssClass="icon-check-empty"
						message="not-active"
					/>
				</#if>
			</@>

			<@liferay_ui["search-container-column-text"]
				align="right"
				cssClass="entry-action"
				name=""
			>
				<@liferay_ui["icon-menu"]
					direction="down"
					markupView="lexicon"
					showWhenSingleIcon=true
				>
					<#if configurationModel.isFactory()>
						<@liferay_ui["icon"]
							message="view"
							method="post"
							url="${viewFactoryInstancesURL}"
						/>

						<@portlet["renderURL"] varImpl="createFactoryConfigURL">
							<@portlet["param"]
								name="mvcPath"
								value="/edit_configuration.ftl"
							/>
							<@portlet["param"]
								name="factoryPid"
								value="${configurationModel.getID()}"
							/>
						</@>

						<@liferay_ui["icon"]
							message="add"
							method="post"
							url="${createFactoryConfigURL}"
						/>
					<#else>
						<@liferay_ui["icon"]
							message="edit"
							method="post"
							url="${editURL}"
						/>

						<#if configurationModel.getConfiguration()??>
							<@portlet["actionURL"] name="deleteConfiguration" varImpl="deleteConfigActionURL">
								<@portlet["param"]
									name="factoryPid"
									value="${configurationModel.getFactoryPid()}"
								/>
								<@portlet["param"]
									name="pid"
									value="${configurationModel.getID()}"
								/>
							</@>

							<@liferay_ui["icon"]
								message="delete"
								method="post"
								url="${deleteConfigActionURL}"
							/>
						</#if>
					</#if>
				</@>
			</@>
		</@>

		<@liferay_ui["search-iterator"] markupView="lexicon" />
	</@>
</div>