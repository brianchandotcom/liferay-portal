<#setting number_format = "0">

${documentLibraryCsvWriter.write("###" + companyId + "\n")}

<#if (maxDLFolderCount > 0)>
	<#assign ddmStructure = dataFactory.addDDMStructure(groupId, companyId, firstUserId, dataFactory.dlFileEntryClassName.classNameId)>
	insert into DDMStructure values ('${portalUUIDUtil.generate()}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${ddmStructure.classNameId}, 'HttpHeaders', '<?xml version="1.0" encoding="UTF-8"?><root available-locales="en_US" default-locale="en_US"><Name language-id="en_US">HttpHeaders</Name></root>', '<?xml version="1.0" encoding="UTF-8"?><root available-locales="en_US" default-locale="en_US"><Description language-id="en_US">HttpHeaders</Description></root>', '<?xml version="1.0"?><root><dynamic-element dataType="string" name="CONTENT_ENCODING" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_ENCODING]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LANGUAGE" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LANGUAGE]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LENGTH" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LENGTH]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LOCATION" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LOCATION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_DISPOSITION" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_DISPOSITION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_MD5" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_MD5]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_TYPE" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_TYPE]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="LAST_MODIFIED" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.LAST_MODIFIED]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="LOCATION" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.LOCATION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element></root>', 'xml');

	<#list 1..maxDLFolderCount as rootFolderCount>
		<#assign rootFolder = dataFactory.addDLFolder(groupId, companyId, firstUserId, 0, "Test Folder " + rootFolderCount, "This is a test dl folder " + rootFolderCount + ".")>
		${sampleSQLBuilder.insertDLFolder(rootFolder, documentLibraryCsvWriter, ddmStructure)}
		
		<#if (maxDLFolderDepth > 1)>
			<#assign lastLevelFolderIds = []>
			<#assign lastLevelTotalFolderCount = 0>

			<#list 2..maxDLFolderCount as dlFolderDepth>
				<#if (dlFolderDepth == 2)>
					<#assign parentFolderIds = [rootFolder.folderId]>
					<#assign lastLevelTotalFolderCount = 1>
				<#else>
					<#assign parentFolderIds = lastLevelFolderIds>
					<#assign lastLevelFolderIds = []>
				</#if>

				<#assign currentLevelMaxFolderCount = lastLevelTotalFolderCount * maxDLFolderCount>
				<#assign lastLevelTotalFolderCount = currentLevelMaxFolderCount>

				<#if (currentLevelMaxFolderCount > 0)>
					<#list 1..currentLevelMaxFolderCount as folderCount>
						<#assign parentFolderIdIndex = ((folderCount - 1) / maxDLFolderCount) ? int>

						<#assign subFolder = dataFactory.addDLFolder(groupId, companyId, firstUserId, parentFolderIds[parentFolderIdIndex], "Test Sub Flolder " + folderCount, "This is a test sub folder " + folderCount + ".")>
						${sampleSQLBuilder.insertDLFolder(subFolder, documentLibraryCsvWriter, ddmStructure)}

						<#assign lastLevelFolderIds = lastLevelFolderIds + [subFolder.folderId]>
					</#list>
				</#if>
			</#list>
		</#if>
	</#list>
</#if>