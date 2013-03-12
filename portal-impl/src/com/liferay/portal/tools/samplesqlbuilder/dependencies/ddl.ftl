<#setting number_format = "0">

<#if (maxDDLRecordSetCount > 0)>
	<#assign ddmStructure = dataFactory.newDDLDDMStructure(groupId)>

	<#assign createDate = dataFactory.getDateString(ddmStructure.createDate)>

	insert into DDMStructure values ('${ddmStructure.uuid}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '${ddmStructure.userName}', '${dataFactory.getDateString(ddmStructure.createDate)}', '${dataFactory.getDateString(ddmStructure.modifiedDate)}', ${ddmStructure.parentStructureId}, ${ddmStructure.classNameId}, '${ddmStructure.structureKey}', '${ddmStructure.name}', '${ddmStructure.description}', '${ddmStructure.xsd}', '${ddmStructure.storageType}', ${ddmStructure.type});

	<#list 1..maxDDLRecordSetCount as ddlRecordSetCount>
		<#assign ddlLayoutName = "dynamic_data_list_display_" + ddlRecordSetCount>
		<#assign ddlPortletId = "169_INSTANCE_TEST" + ddlRecordSetCount>

		<#assign ddlDisplayLayout = dataFactory.newLayout(groupId, ddlLayoutName, "", ddlPortletId)>

		${sampleSQLBuilder.insertLayout(ddlDisplayLayout)}

		<#assign ddlRecordSet = dataFactory.newDDLRecordSet(ddmStructure.groupId, ddmStructure.companyId, ddmStructure.userId, ddmStructure.structureId)>

		insert into DDLRecordSet values ('${portalUUIDUtil.generate()}', ${ddlRecordSet.recordSetId}, ${ddlRecordSet.groupId}, ${ddlRecordSet.companyId}, ${ddlRecordSet.userId}, '', '${createDate}', '${createDate}', ${ddlRecordSet.DDMStructureId}, 'Test DDL Record Set ${ddlRecordSetCount}', '<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales="en_US" default-locale="en_US"><Name language-id="en_US">Test DDL Record Set  ${ddlRecordSetCount}</Name></root>', '', 20, 0);

		<#if (maxDDLRecordCount > 0)>
			<#list 1..maxDDLRecordCount as ddlRecordCount>
				<#assign ddlRecord = dataFactory.newDDLRecord(ddlRecordSet.groupId, ddlRecordSet.companyId, ddlRecordSet.userId, ddlRecordSet.recordSetId)>

				${sampleSQLBuilder.insertDDLRecord(ddlRecord, ddlRecordSet, ddlRecordCount)}

				${writerDynamicDataListsCSV.write(ddlLayoutName + "," + ddlPortletId + "," + ddlRecordSet.recordSetId + "," + ddlRecord.recordId + "\n")}
			</#list>
		</#if>

		<#assign preferences = "<portlet-preferences><preference><name>recordSetId</name><value>" + ddlRecordSet.recordSetId +"</value></preference><preference><name>displayDDMTemplateId</name><value></value></preference><preference><name>editable</name><value>true</value></preference></portlet-preferences>">

		<#assign portletPreferences = dataFactory.newPortletPreferences(0, ddlDisplayLayout.plid, ddlPortletId, preferences)>

		insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');

		${sampleSQLBuilder.insertResourcePermission("169", ddlDisplayLayout.plid + "_LAYOUT_" + ddlPortletId)}
	</#list>
</#if>