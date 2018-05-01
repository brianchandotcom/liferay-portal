<#include "../init.ftl">

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<#if hasFieldValue || showEmptyFieldLabel>
		<label>
			<@liferay_ui.message key=escape(label) />
		</label>
	</#if>

	<#if hasFieldValue>
		<#if !disabled>
			<@liferay_aui.input
				name=namespacedFieldName
				type="hidden"
				value=fieldValue
			/>
		</#if>

		<#assign
			fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)

			layoutLocalService = serviceLocator.findService("com.liferay.portal.kernel.service.LayoutLocalService")
		/>

		<#if fieldLayoutJSONObject.getLong("groupId") gt 0>
			<#assign fieldLayoutGroupId = fieldLayoutJSONObject.getLong("groupId") />
		<#else>
			<#assign fieldLayoutGroupId = scopeGroupId />
		</#if>

		<#assign fieldLayout = layoutLocalService.fetchLayout(fieldLayoutGroupId, fieldLayoutJSONObject.getBoolean("privateLayout"), fieldLayoutJSONObject.getLong("layoutId"))!"" />

		<#if validator.isNotNull(fieldLayout)>
			<a href="${fieldLayout.getRegularURL(request)}">${escape(fieldLayout.getName(requestedLocale))}</a>
		</#if>
	</#if>

	${fieldStructure.children}
</div>