<#setting number_format = "0">

<#if (maxDDLRecordSetCount > 0)>
	<#assign ddmStructure = dataFactory.addDDMStructureForDDL(groupId)>

	insert into DDMStructure values ('${ddmStructure.uuid}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '${ddmStructure.userName}', '${ddmStructure.createDate?datetime}', '${ddmStructure.modifiedDate?datetime}', ${ddmStructure.parentStructureId}, ${ddmStructure.classNameId}, '${ddmStructure.structureKey}', '${ddmStructure.name}', '${ddmStructure.description}', '${ddmStructure.xsd}', '${ddmStructure.storageType}', ${ddmStructure.type});

	<#assign ddmStructureId = ddmStructure.structureId>

	<#list 1..maxDDLRecordSetCount as ddlRecordSetCount>
		<#assign ddlFriendlyURL = "dynamic_data_list_display_" + ddlRecordSetCount>
		<#assign ddlPortletId = "169_INSTANCE_TEST" + ddlRecordSetCount>

		<#assign ddlDisplayLayout = dataFactory.addLayout(groupId,  "dynamic_data_list_display_" + ddlRecordSetCount, "", ddlPortletId)>

		<#assign publicLayouts = publicLayouts + [ddlDisplayLayout]>

		<#assign ddlRecordSet = dataFactory.addDDLRecordSet(ddmStructure, ddlRecordSetCount)>

		insert into DDLRecordSet values ('${ddlRecordSet.uuid}', ${ddlRecordSet.recordSetId}, ${ddlRecordSet.groupId}, ${ddlRecordSet.companyId}, ${ddlRecordSet.userId}, '${ddlRecordSet.userName}', '${ddlRecordSet.createDate?datetime}', '${ddlRecordSet.modifiedDate?datetime}', ${ddlRecordSet.DDMStructureId}, '${ddlRecordSet.recordSetKey}', '${ddlRecordSet.name}', '${ddlRecordSet.description}', ${ddlRecordSet.minDisplayRows}, ${ddlRecordSet.scope});

		<#if (maxDDLRecordCount > 0)>
			<#list 1..maxDDLRecordCount as ddlRecordCount>
				<#assign ddlRecord = dataFactory.addDDLRecord(ddlRecordSet)>

				${sampleSQLBuilder.insertDDLRecord(ddlRecord, ddlRecordCount, ddmStructureId)}

				${writerDynamicDataListsCSV.write(ddlFriendlyURL + "," + ddlPortletId + "," + ddlRecordSet.recordSetId + "," + ddlRecord.recordId + "\n")}
			</#list>
		</#if>

		<#assign portletPreferences = dataFactory.addPortletPreferences(ddlRecordSet, ddlDisplayLayout.plid, ddlPortletId)>

		insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');

		${sampleSQLBuilder.insertResourcePermission("169", ddlDisplayLayout.plid + "_LAYOUT_" + ddlPortletId)}
	</#list>
</#if>