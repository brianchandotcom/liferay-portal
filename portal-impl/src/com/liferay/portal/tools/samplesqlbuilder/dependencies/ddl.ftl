<#setting number_format = "0">

<#if (maxDDLRecordSetCount > 0)>
	<#assign ddmStructure = dataFactory.newDDLDDMStructure(groupId)>

	insert into DDMStructure values ('${ddmStructure.uuid}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '${ddmStructure.userName}', '${dataFactory.getDateString(ddmStructure.createDate)}', '${dataFactory.getDateString(ddmStructure.modifiedDate)}', ${ddmStructure.parentStructureId}, ${ddmStructure.classNameId}, '${ddmStructure.structureKey}', '${ddmStructure.name}', '${ddmStructure.description}', '${ddmStructure.xsd}', '${ddmStructure.storageType}', ${ddmStructure.type});

	<#list 1..maxDDLRecordSetCount as ddlRecordSetCount>
		<#assign layoutName = "dynamic_data_list_display_" + ddlRecordSetCount>
		<#assign portletId = "169_INSTANCE_TEST" + ddlRecordSetCount>

		<#assign layout = dataFactory.newLayout(groupId, layoutName, "", portletId)>

		${sampleSQLBuilder.insertLayout(layout)}

		<#assign ddlRecordSet = dataFactory.newDDLRecordSet(ddmStructure, ddlRecordSetCount)>

		insert into DDLRecordSet values ('${ddlRecordSet.uuid}', ${ddlRecordSet.recordSetId}, ${ddlRecordSet.groupId}, ${ddlRecordSet.companyId}, ${ddlRecordSet.userId}, '${ddlRecordSet.userName}', '${dataFactory.getDateString(ddlRecordSet.createDate)}', '${dataFactory.getDateString(ddlRecordSet.modifiedDate)}', ${ddlRecordSet.DDMStructureId}, '${ddlRecordSet.recordSetKey}', '${ddlRecordSet.name}', '${ddlRecordSet.description}', ${ddlRecordSet.minDisplayRows}, ${ddlRecordSet.scope});

		<#assign ddmStructureLink = dataFactory.newDDMStructureLink(ddlRecordSet)>

		insert into DDMStructureLink values (${ddmStructureLink.structureLinkId},${ ddmStructureLink.classNameId}, ${ddmStructureLink.classPK}, ${ddmStructureLink.structureId});

		<#if (maxDDLRecordCount > 0)>
			<#list 1..maxDDLRecordCount as ddlRecordCount>
				<#assign ddlRecord = dataFactory.newDDLRecord(ddlRecordSet)>

				${sampleSQLBuilder.insertDDLRecord(ddlRecord, ddlRecordCount, ddmStructure.structureId)}

				${writerDynamicDataListsCSV.write(layoutName + "," + portletId + "," + ddlRecordSet.recordSetId + "," + ddlRecord.recordId + "\n")}
			</#list>
		</#if>

		<#assign portletPreferencesList = dataFactory.newPortletPreferences(layout.plid, portletId, ddlRecordSet)>

		<#list portletPreferencesList as portletPreferences>
			insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');

			<#assign primKey = dataFactory.getPortletPermissionPrimaryKey(layout.plid, portletPreferences.portletId)>

			${sampleSQLBuilder.insertResourcePermission(portletPreferences.portletId, primKey)}
		</#list>
	</#list>
</#if>