<#if (maxDDLRecordSetCount > 0)>
	<#assign ddmStructure = dataFactory.newDDLDDMStructure(groupId)>

	insert into DDMStructure values ('${ddmStructure.uuid}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '${ddmStructure.userName}', '${dataFactory.getDateString(ddmStructure.createDate)}', '${dataFactory.getDateString(ddmStructure.modifiedDate)}', ${ddmStructure.parentStructureId}, ${ddmStructure.classNameId}, '${ddmStructure.structureKey}', '${ddmStructure.name}', '${ddmStructure.description}', '${ddmStructure.xsd}', '${ddmStructure.storageType}', ${ddmStructure.type});

	<@insertResourcePermissions
		_entry = ddmStructure
	/>

	<#list 1..maxDDLRecordSetCount as ddlRecordSetCount>
		<#assign layoutName = "dynamic_data_list_display_" + ddlRecordSetCount>
		<#assign portletId = "169_INSTANCE_TEST" + ddlRecordSetCount>

		<#assign layout = dataFactory.newLayout(groupId, layoutName, "", portletId)>

		<@insertLayout
			_layout = layout
		/>

		<#assign ddlRecordSet = dataFactory.newDDLRecordSet(ddmStructure, ddlRecordSetCount)>

		insert into DDLRecordSet values ('${ddlRecordSet.uuid}', ${ddlRecordSet.recordSetId}, ${ddlRecordSet.groupId}, ${ddlRecordSet.companyId}, ${ddlRecordSet.userId}, '${ddlRecordSet.userName}', '${dataFactory.getDateString(ddlRecordSet.createDate)}', '${dataFactory.getDateString(ddlRecordSet.modifiedDate)}', ${ddlRecordSet.DDMStructureId}, '${ddlRecordSet.recordSetKey}', '${ddlRecordSet.name}', '${ddlRecordSet.description}', ${ddlRecordSet.minDisplayRows}, ${ddlRecordSet.scope});

		<@insertDDMStructureLink
			_entry = ddlRecordSet
		/>

		<@insertResourcePermissions
			_entry = ddlRecordSet
		/>

		<#if (maxDDLRecordCount > 0)>
			<#list 1..maxDDLRecordCount as ddlRecordCount>
				<#assign ddlRecord = dataFactory.newDDLRecord(ddlRecordSet)>

				insert into DDLRecord values ('${ddlRecord.uuid}', ${ddlRecord.recordId}, ${ddlRecord.groupId}, ${ddlRecord.companyId}, ${ddlRecord.userId}, '${ddlRecord.userName}', ${ddlRecord.versionUserId}, '${ddlRecord.versionUserName}', '${dataFactory.getDateString(ddlRecord.createDate)}', '${dataFactory.getDateString(ddlRecord.modifiedDate)}', ${ddlRecord.DDMStorageId}, ${ddlRecord.recordSetId}, '${ddlRecord.version}', ${ddlRecord.displayIndex});

				<#assign ddlRecordVersion = dataFactory.newDDLRecordVersion(ddlRecord)>

				insert into DDLRecordVersion values (${ddlRecordVersion.recordVersionId}, ${ddlRecordVersion.groupId}, ${ddlRecordVersion.companyId}, ${ddlRecordVersion.userId}, '${ddlRecordVersion.userName}', '${dataFactory.getDateString(ddlRecordVersion.createDate)}', ${ddlRecordVersion.DDMStorageId}, ${ddlRecordVersion.recordSetId}, ${ddlRecordVersion.recordId}, '${ddlRecordVersion.version}', ${ddlRecordVersion.displayIndex}, ${ddlRecordVersion.status}, ${ddlRecordVersion.statusByUserId}, '${ddlRecordVersion.statusByUserName}', '${dataFactory.getDateString(ddlRecordVersion.statusDate)}');

				<@insertDDMContent
					_currentIndex = ddlRecordCount
					_ddmStorageLinkId = counter.get()
					_ddmStructureId = ddmStructure.structureId
					_entry = ddlRecord
				/>

				${writerDynamicDataListsCSV.write(ddlRecord.groupId + "," + layoutName + "," + portletId + "," + ddlRecordSet.recordSetId + "," + ddlRecord.recordId + "\n")}
			</#list>
		</#if>

		<#assign portletPreferences = dataFactory.newPortletPreferences(layout.plid, portletId, ddlRecordSet)>

		<@insertPortletPreferences
			_portletPreferences = portletPreferences
		/>

		<#assign portletPreferencesList = dataFactory.newPortletPreferences(layout.plid)>

		<#list portletPreferencesList as portletPreferences>
			<@insertPortletPreferences
				_portletPreferences = portletPreferences
			/>
		</#list>
	</#list>
</#if>