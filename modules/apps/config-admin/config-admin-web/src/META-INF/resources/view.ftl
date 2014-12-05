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

<#assign modelIterator = Request["modelIterator"] />

<@liferay_ui["search-container"]
	emptyResultsMessage="no-configurations-were-found"
	total=modelIterator.getTotal()
>

	<@liferay_ui["search-container-results"]
		results=modelIterator.getResults(searchContainer.getStart(), searchContainer.getEnd())
	/>

	<@liferay_ui["search-container-row"]
		className="com.liferay.osgi.config.admin.model.ConfigurationModel"
		keyProperty="ID"
		modelVar="model"
	>

		<@portlet["renderURL"] varImpl="editURL">
			<@portlet["param"] name="mvcPath" value="/edit_configuration.ftl" />
			<@portlet["param"] name="pid" value="${model.getID()}" />
			<@portlet["param"] name="factoryPid" value="${model.getFactoryPid()}" />
		</@>

		<@liferay_ui["search-container-column-text"]
			name="name"
		>
			<#if model.isFactory()>
			<#else>
				<@aui["a"] href=(editURL.toString())>${model.getName()}</@>
			</#if>

			<#if model??>
				<br/>

				${model.getID()}
			</#if>
		</@>

		<@liferay_ui["search-container-column-text"]
			align="center"
			name="status"
		>
			<#if model.isFactory()>
				<@liferay_ui["icon"]
					image="add"
					message="factory"
				/>
			<#elseif model.getConfiguration()??>
				<@liferay_ui["icon"]
					image="checked"
					message="active"
				/>
			<#else>
				<@liferay_ui["icon"]
					image="unchecked"
					message="not-active"
				/>
			</#if>
		</@>

		<@liferay_ui["search-container-column-text"]
			align="right"
			name=""
		>
			<@liferay_ui["icon-menu"]>
				<#if model.isFactory()>
				<#else>
					<@liferay_ui["icon"]
						image="edit"
						label=true
						message="edit"
						method="post"
						url="${editURL}"
					/>

					<#if model.getConfiguration()??>
						<@portlet["actionURL"] name="deleteConfiguration" varImpl="deleteConfigActionURL">
							<@portlet["param"] name="factoryPid" value="${model.getFactoryPid()}" />
							<@portlet["param"] name="pid" value="${model.getID()}" />
						</@>

						<@liferay_ui["icon"]
							image="delete"
							label=true
							message="delete"
							method="post"
							url="${deleteConfigActionURL}"
						/>
					</#if>
				</#if>
			</@>
		</@>
	</@>

	<@liferay_ui["search-iterator"] />
</@>