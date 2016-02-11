<#include "../init.ftl">

<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.kernel.service.LayoutLocalService")>
<#assign layoutService = serviceLocator.findService("com.liferay.portal.kernel.service.LayoutService")>

<@liferay_aui["field-wrapper"] data=data>
	<#assign selectedPlid = 0>

	<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

	<#if (validator.isNotNull(fieldRawValue))>
		<#assign fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

		<#assign selectedLayoutGroupId = getterUtil.getLong(fieldLayoutJSONObject.get("groupId"))>

		<#if (selectedLayoutGroupId <= 0)>
			<#assign selectedLayoutGroupId = scopeGroupId>
		</#if>

		<#assign selectedLayoutLayoutId = getterUtil.getLong(fieldLayoutJSONObject.get("layoutId"))>

		<#assign selectedLayout = layoutLocalService.fetchLayout(selectedLayoutGroupId, fieldLayoutJSONObject.getBoolean("privateLayout"), selectedLayoutLayoutId)!"">

		<#if (validator.isNotNull(selectedLayout))>
			<#assign selectedPlid = selectedLayout.getPlid()>
		</#if>
	</#if>

	<div class="form-group">
		
		<@liferay_aui.input cssClass='link-to-page-value' dir=requestedLanguageDir helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName required=required type="text" value=fieldValue>
			
		</@liferay_aui.input>
		
		<@liferay_aui["button-row"]>
			<@liferay_aui.button
				cssClass="select-button"
				id="${namespacedFieldName}SelectButton"
				value="link-to-page"
			/>

			<@liferay_aui.button
				cssClass="clear-button ${(fieldRawValue?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}ClearButton"
				value="clear"
			/>
		</@>
		
	</div>

	${fieldStructure.children}
</@>

