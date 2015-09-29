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

<#assign configurationModel = Request["configurationModel"] />
<#assign ddmFormHTML = Request["DYNAMIC_DATA_MAPPING_FORM_HTML"] />

<#assign redirectURL = renderResponse.createRenderURL() />

<@portlet["actionURL"] name="bindConfiguration" varImpl="bindConfigActionURL"/>
<@portlet["actionURL"] name="deleteConfiguration" varImpl="deleteConfigActionURL"/>

<#assign void = portletDisplay.setShowBackIcon(true) />
<#assign void = portletDisplay.setURLBack(redirectURL) />
<#assign void = renderResponse.setTitle(configurationModel.getName()) />

<div class="container-fluid-1280">
	<@aui["form"] action="${bindConfigActionURL}" method="post" name="fm">
		<@aui["input"] name="redirect" type="hidden" value="${redirectURL}" />
		<@aui["input"] name="pid" type="hidden" value="${configurationModel.getID()}" />
		<@aui["input"] name="factoryPid" type="hidden" value="${configurationModel.getFactoryPid()}" />

		<div class="lfr-ddm-container" id="lfr-ddm-container">
			${ddmFormHTML}
		</div>

		<@aui["button-row"]>
			<#if configurationModel.getConfiguration()??>
				<@aui["button"] value="update" type="submit" />

				<#assign deleteAttributesOnClickValue = renderResponse.getNamespace() + "deleteConfig();">

				<@aui["button"] onClick=deleteAttributesOnClickValue value="delete" type="button" />
			<#else>
				<@aui["button"] value="save" type="submit" />
			</#if>

			<@aui["button"] href="${redirectURL}" type="cancel" />
		</@>
	</@>
</div>

<@aui["script"]>
	function <@portlet["namespace"] />setDDMFieldNamespaceAndSubmit(actionURL) {
		if (actionURL) {
			document.<@portlet["namespace"] />fm.action = actionURL;
		}

		submitForm(document.<@portlet["namespace"] />fm);
	}

	function <@portlet["namespace"] />deleteConfig() {
		var actionURL = "${deleteConfigActionURL?js_string}";

		<@portlet["namespace"] />setDDMFieldNamespaceAndSubmit(actionURL);
	}
</@>