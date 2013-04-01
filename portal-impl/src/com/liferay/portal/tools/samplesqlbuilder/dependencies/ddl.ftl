<#if (maxDDLRecordSetCount > 0)>
	<#assign ddmStructure = dataFactory.newDDLDDMStructure(groupId)>

	insert into DDMStructure values ('${ddmStructure.uuid}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '${ddmStructure.userName}', '${dataFactory.getDateString(ddmStructure.createDate)}', '${dataFactory.getDateString(ddmStructure.modifiedDate)}', ${ddmStructure.parentStructureId}, ${ddmStructure.classNameId}, '${ddmStructure.structureKey}', '${ddmStructure.name}', '${ddmStructure.description}', '${ddmStructure.xsd}', '${ddmStructure.storageType}', ${ddmStructure.type});

	<#list 1..maxDDLRecordSetCount as ddlRecordSetCount>
		<#assign layoutName = "dynamic_data_list_display_" + ddlRecordSetCount>
		<#assign portletId = "169_INSTANCE_TEST" + ddlRecordSetCount>

		<#assign layout = dataFactory.newLayout(groupId, layoutName, "", portletId)>

		<@insertLayout _layout = layout />

		<#assign ddlRecordSet = dataFactory.newDDLRecordSet(ddmStructure, ddlRecordSetCount)>

		insert into DDLRecordSet values ('${ddlRecordSet.uuid}', ${ddlRecordSet.recordSetId}, ${ddlRecordSet.groupId}, ${ddlRecordSet.companyId}, ${ddlRecordSet.userId}, '${ddlRecordSet.userName}', '${dataFactory.getDateString(ddlRecordSet.createDate)}', '${dataFactory.getDateString(ddlRecordSet.modifiedDate)}', ${ddlRecordSet.DDMStructureId}, '${ddlRecordSet.recordSetKey}', '${ddlRecordSet.name}', '${ddlRecordSet.description}', ${ddlRecordSet.minDisplayRows}, ${ddlRecordSet.scope});

		<@insertDDMStructureLink _entry = ddlRecordSet />

		<#if (maxDDLRecordCount > 0)>
			<#list 1..maxDDLRecordCount as ddlRecordCount>
				<#assign ddlRecord = dataFactory.newDDLRecord(ddlRecordSet)>

				${sampleSQLBuilder.insertDDLRecord(ddlRecord, ddlRecordCount, ddmStructure.structureId)}

				${writerDynamicDataListsCSV.write(layoutName + "," + portletId + "," + ddlRecordSet.recordSetId + "," + ddlRecord.recordId + "\n")}
			</#list>
		</#if>

		<@insertPortletPreferences _entry = ddlRecordSet _plid = layout.plid _portletId = portletId />
	</#list>
</#if>