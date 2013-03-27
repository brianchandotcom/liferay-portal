<#setting number_format = "0">

<#if (maxDDLRecordSetCount > 0)>
	<#assign ddmStructure = dataFactory.newDDLDDMStructure(groupId)>

	<#assign createDate = dataFactory.getDateString(ddmStructure.createDate)>

	insert into DDMStructure values ('${ddmStructure.uuid}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '${ddmStructure.userName}', '${dataFactory.getDateString(ddmStructure.createDate)}', '${dataFactory.getDateString(ddmStructure.modifiedDate)}', ${ddmStructure.parentStructureId}, ${ddmStructure.classNameId}, '${ddmStructure.structureKey}', '${ddmStructure.name}', '${ddmStructure.description}', '${ddmStructure.xsd}', '${ddmStructure.storageType}', ${ddmStructure.type});

	<#list 1..maxDDLRecordSetCount as ddlRecordSetCount>
		<#assign layoutName = "dynamic_data_list_display_" + ddlRecordSetCount>
		<#assign portletId = "169_INSTANCE_TEST" + ddlRecordSetCount>

		<#assign layout = dataFactory.newLayout(groupId, layoutName, "", portletId)>

		${sampleSQLBuilder.insertLayout(layout)}

		<#assign ddlRecordSet = dataFactory.newDDLRecordSet(ddmStructure.groupId, ddmStructure.companyId, ddmStructure.userId, ddmStructure.structureId)>

		insert into DDLRecordSet values ('${portalUUIDUtil.generate()}', ${ddlRecordSet.recordSetId}, ${ddlRecordSet.groupId}, ${ddlRecordSet.companyId}, ${ddlRecordSet.userId}, '', '${createDate}', '${createDate}', ${ddlRecordSet.DDMStructureId}, 'Test DDL Record Set ${ddlRecordSetCount}', '<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales="en_US" default-locale="en_US"><Name language-id="en_US">Test DDL Record Set  ${ddlRecordSetCount}</Name></root>', '', 20, 0);

		<#assign ddmStructureLink = dataFactory.newDDMStructureLink(ddlRecordSet)>

		insert into DDMStructureLink values (${ddmStructureLink.structureLinkId},${ ddmStructureLink.classNameId}, ${ddmStructureLink.classPK}, ${ddmStructureLink.structureId});

		<#if (maxDDLRecordCount > 0)>
			<#list 1..maxDDLRecordCount as ddlRecordCount>
				<#assign ddlRecord = dataFactory.newDDLRecord(ddlRecordSet.groupId, ddlRecordSet.companyId, ddlRecordSet.userId, ddlRecordSet.recordSetId)>

				${sampleSQLBuilder.insertDDLRecord(ddlRecord, ddlRecordSet, ddlRecordCount)}

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