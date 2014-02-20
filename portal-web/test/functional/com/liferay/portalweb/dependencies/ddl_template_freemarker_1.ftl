<#assign DDLRecordLocalService = serviceLocator.findService("com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalService")>

<#assign records = DDLRecordLocalService.getRecords(reserved_record_set_id)>

<#if records?has_content>
	<#list records as cur_record>

	    <div class="ddl-record">${cur_record.getFieldValue("Name", locale)}</div>

	</#list>
</#if>