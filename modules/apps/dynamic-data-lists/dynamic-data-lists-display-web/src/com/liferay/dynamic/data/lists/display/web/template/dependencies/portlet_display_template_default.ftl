<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />
<#assign portlet = taglibLiferayHash["/META-INF/liferay-portlet.tld"] />

<#assign backURL = paramUtil.getString(request, "backURL") />
<#assign recordId = paramUtil.getLong(request, "recordId") />

<#if recordId == 0>
	<@liferay_ui["search-container"]
		total=total
	>

		<@liferay_ui["search-container-results"]
			results=entries
		/>

	  	<#assign ddmFormFields = displayRendererHelper.getDDMFormFields(recordSet) />

		<@liferay_ui["search-container-row"]
			className="com.liferay.portlet.dynamicdatalists.model.DDLRecord"
			modelVar="record"
		>

		    <@portlet["renderURL"] varImpl="viewRecordURL">
		        <@portlet["param"] name="backURL" value="${currentURL}" />
		        <@portlet["param"] name="recordId" value="${record.recordId}" />
			</@>

		    <#list ddmFormFields as ddmFormField>
				<#assign label = ddmFormField.label />

				<@liferay_ui["search-container-column-text"]
					name=label.getString(locale)
					href="${viewRecordURL}"
				>

				    ${displayRendererHelper.renderRecordField(record, ddmFormField, locale)}
				</@>
			</#list>
		</@>

		<@liferay_ui["search-iterator"] />
	</@>
<#else>
	<@liferay_ui["header"]
		backURL="${backURL}"
		title="view"
	/>

	${displayRendererHelper.renderRecord(renderRequest, renderResponse)}
</#if>