<#setting number_format = "0">

<#if (maxDDLRecordSetCount > 0)>
	<#assign ddmStructure = dataFactory.addDDMStructure(groupId, companyId, firstUserId, dataFactory.DDLRecordSetClassName.classNameId)>

	<#assign createDate = dataFactory.getDateString(ddmStructure.createDate)>

	insert into DDMStructure values ('${portalUUIDUtil.generate()}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '', '${createDate}', '${createDate}', 0, ${ddmStructure.classNameId}, 'Test DDM Structure', '<?xml version="1.0" encoding="UTF-8"?><root available-locales="en_US" default-locale="en_US"><Name language-id="en_US">Test DDM Structure</Name></root>', '', '<?xml version="1.0"?><root available-locales="en_US" default-locale="en_US"><dynamic-element dataType="string" name="text2102" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[Name]]></entry><entry name="showLabel"><![CDATA[true]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="tip"><![CDATA[]]></entry><entry name="width"><![CDATA[25]]></entry><entry name="fieldCssClass"><![CDATA[aui-w25]]></entry></meta-data></dynamic-element></root>', 'xml', 0);

	<#list 1..maxDDLRecordSetCount as ddlRecordSetCount>
		<#assign ddlRecordSet = dataFactory.addDDLRecordSet(ddmStructure.groupId, ddmStructure.companyId, ddmStructure.userId, ddmStructure.structureId)>

		insert into DDLRecordSet values ('${portalUUIDUtil.generate()}', ${ddlRecordSet.recordSetId}, ${ddlRecordSet.groupId}, ${ddlRecordSet.companyId}, ${ddlRecordSet.userId}, '', '${createDate}', '${createDate}', ${ddlRecordSet.DDMStructureId}, 'Test DDL Record Set ${ddlRecordSetCount}', '<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales="en_US" default-locale="en_US"><Name language-id="en_US">Test DDL Record Set</Name></root>', '', 20, 0);

		<#if (maxDDLRecordCount > 0)>
			<#list 1..maxDDLRecordCount as ddlRecordCount>
				<#assign ddlRecord = dataFactory.addDDLRecord(ddlRecordSet.groupId, ddlRecordSet.companyId, ddlRecordSet.userId, ddlRecordSet.recordSetId)>

				${sampleSQLBuilder.insertDDLRecord(ddlRecord, ddlRecordSet)}

				${writerDynamicDataListsCSV.write(ddlRecordSet.recordSetId + "," + ddlRecord.recordId + "," + dataFactory.getDateLong(ddlRecord.createDate) + "\n")}
			</#list>
		</#if>
	</#list>
</#if>