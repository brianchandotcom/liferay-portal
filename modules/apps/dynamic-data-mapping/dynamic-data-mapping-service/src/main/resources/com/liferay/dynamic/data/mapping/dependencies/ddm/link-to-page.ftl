<#include "../init.ftl">

<#assign
	layoutLocalService = serviceLocator.findService("com.liferay.portal.kernel.service.LayoutLocalService")
	layoutService = serviceLocator.findService("com.liferay.portal.kernel.service.LayoutService")
/>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
>
	<#assign
		selectedLayoutName = ""

		fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)
	/>

	<#if validator.isNotNull(fieldRawValue)>
		<#assign
			fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)

			selectedLayoutGroupId = getterUtil.getLong(fieldLayoutJSONObject.get("groupId"))
		/>

		<#if selectedLayoutGroupId <= 0>
			<#assign selectedLayoutGroupId = scopeGroupId />
		</#if>

		<#assign
			selectedLayoutLayoutId = getterUtil.getLong(fieldLayoutJSONObject.get("layoutId"))

			selectedLayout = layoutLocalService.fetchLayout(selectedLayoutGroupId, fieldLayoutJSONObject.getBoolean("privateLayout"), selectedLayoutLayoutId)!""
		/>

		<#if validator.isNotNull(selectedLayout)>
			<#assign selectedLayoutName = selectedLayout.getName(requestedLocale) />
		</#if>
	</#if>

	<div class="form-group">
		<@liferay_aui.input
			dir=requestedLanguageDir
			helpMessage=escape(fieldStructure.tip)
			label=escape(label)
			name="${namespacedFieldName}LayoutName"
			readonly="readonly"
			required=required
			type="text"
			value=selectedLayoutName
		/>

		<@liferay_aui.input
			name=namespacedFieldName
			type="hidden"
			value=fieldRawValue
		/>

		<div class="button-holder">
			<@liferay_aui.button
				cssClass="select-button"
				id="${namespacedFieldName}SelectButton"
				value="select"
			/>

			<@liferay_aui.button
				cssClass="clear-button ${(fieldRawValue?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}ClearButton"
				value="clear"
			/>
		</div>
	</div>

	${fieldStructure.children}
</@>