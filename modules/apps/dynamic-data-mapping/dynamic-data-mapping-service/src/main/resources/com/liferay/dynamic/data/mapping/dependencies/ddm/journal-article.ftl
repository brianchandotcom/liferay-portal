<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && validator.isNull(fieldRawValue)>
	<#assign fieldRawValue = predefinedValue />
</#if>

<#assign
	fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)

	assetTitle = ""
/>

<#if validator.isNotNull(fieldRawValue)>
	<#assign
		fieldJournalJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)

		journalArticle = fetchLatestArticle(fieldJournalJSONObject)
	/>

	<#if validator.isNotNull(journalArticle)>
		<#assign selectedAssetTitle = journalArticle.getTitle(requestedLocale) />
	</#if>
</#if>

<#assign assetBrowserAuthToken = authTokenUtil.getToken(request, themeDisplay.getPlid(), "com_liferay_asset_browser_web_portlet_AssetBrowserPortlet") />

<#assign data = data + {
	"assetBrowserAuthToken": assetBrowserAuthToken
}>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	required=required
>
	<div class="form-group">
		<div class="hide" id="${portletNamespace}${namespacedFieldName}SelectContainer"></div>

		<@liferay_aui.input
			helpMessage=escape(fieldStructure.tip)
			inlineField=true
			label=escape(label)
			name="${namespacedFieldName}Title"
			readonly="readonly"
			type="text"
			value=selectedAssetTitle
		/>

		<@liferay_aui.input
			name=namespacedFieldName
			type="hidden"
			value=fieldRawValue
		/>

		<@liferay_aui.input
			name=namespacedFieldName
			type="hidden"
			value=fieldRawValue
		>
			<#if required>
				<@liferay_aui.validator name="required" />
			</#if>
		</@>

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