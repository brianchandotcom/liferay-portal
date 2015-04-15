<#include "../init.ftl">

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<#if hasFieldValue || showEmptyFieldLabel>
		<label>
			<@liferay_ui.message key=escape(label) />
		</label>
	</#if>

	<#if hasFieldValue>
		<#if !disabled>
			<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />
		</#if>

		<#assign fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

		<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.service.LayoutLocalService")>

		<#if (fieldLayoutJSONObject.getLong("groupId") > 0)>
			<#assign fieldLayoutGroupId = fieldLayoutJSONObject.getLong("groupId")>
		<#else>
			<#assign fieldLayoutGroupId = scopeGroupId>
		</#if>

		<#assign fieldLayout = layoutLocalService.fetchLayout(fieldLayoutGroupId, fieldLayoutJSONObject.getLong("layoutId"))!"">

		<#if (fieldLayout?? && fieldLayout != "")>
			<a href="${fieldLayout.getRegularURL(request)}">${escape(fieldLayout.getName(requestedLocale))}</a>
		</#if>
	</#if>

	${fieldStructure.children}
</div>