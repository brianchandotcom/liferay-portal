<#setting number_format = "0">

${docmentLibraryCsvWriter.write("###" + companyId + "\n")}

<#if (maxRootFolderCount > 0)>
	<#assign ddmStructure = dataFactory.addDDMStructure(groupId, companyId, firstUserId, dataFactory.dlFileEntryClassName.classNameId)>
	insert into DDMStructure values ('${portalUUIDUtil.generate()}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${ddmStructure.classNameId}, 'HttpHeaders', '<?xml version="1.0" encoding="UTF-8"?><root available-locales="en_US" default-locale="en_US"><Name language-id="en_US">HttpHeaders</Name></root>', '<?xml version="1.0" encoding="UTF-8"?><root available-locales="en_US" default-locale="en_US"><Description language-id="en_US">HttpHeaders</Description></root>', '<?xml version="1.0"?><root><dynamic-element dataType="string" name="CONTENT_ENCODING" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_ENCODING]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LANGUAGE" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LANGUAGE]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LENGTH" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LENGTH]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LOCATION" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LOCATION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_DISPOSITION" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_DISPOSITION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_MD5" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_MD5]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_TYPE" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_TYPE]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="LAST_MODIFIED" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.LAST_MODIFIED]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="LOCATION" type="text"><meta-data><entry name="label"><![CDATA[metadata.HttpHeaders.LOCATION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element></root>', 'xml');

	<#list 1..maxRootFolderCount as rootFolderCount>
		<#assign rootFolder = dataFactory.addDLFolder(groupId, companyId, firstUserId, 0, "Test Root Folder " + rootFolderCount, "This is a test root folder " + rootFolderCount + ".")>
		insert into DLFolder values ('${portalUUIDUtil.generate()}', ${rootFolder.folderId}, ${rootFolder.groupId}, ${rootFolder.companyId}, ${rootFolder.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${rootFolder.repositoryId}, 0, 0, '${rootFolder.name}', '${rootFolder.description}', null, 0, 0);

		${sampleSQLBuilder.insertSecurity("com.liferay.portlet.documentlibrary.model.DLFolder", rootFolder.folderId)}

		<#if !leafFolderOnly>
			${sampleSQLBuilder.insertDLFiles(rootFolder, ddmStructure, docmentLibraryCsvWriter)}
		</#if>

		<#if (maxFolderDepth > 1)>
			<#assign lastLevelFolderIds = []>
			<#assign lastLevelTotalFolderCount = 0>

			<#list 2..maxFolderDepth as folderDepth>
				<#if (folderDepth == 2)>
					<#assign parentFolderIds = [rootFolder.folderId]>
					<#assign lastLevelTotalFolderCount = 1>
				<#else>
					<#assign parentFolderIds = lastLevelFolderIds>
					<#assign lastLevelFolderIds = []>
				</#if>

				<#assign currentLevelMaxFolderCount = lastLevelTotalFolderCount * maxFolderFanOutCount>
				<#assign lastLevelTotalFolderCount = currentLevelMaxFolderCount>

				<#if (currentLevelMaxFolderCount > 0)>
					<#list 1..currentLevelMaxFolderCount as folderCount>
						<#assign parentFolderIdIndex = ((folderCount - 1) / maxFolderFanOutCount) ? int>

						<#assign subFolder = dataFactory.addDLFolder(groupId, companyId, firstUserId, parentFolderIds[parentFolderIdIndex],"Test Sub Flolder " + folderCount, "This is a test sub folder " + folderCount + ".")>
						insert into DLFolder values ('${portalUUIDUtil.generate()}', ${subFolder.folderId}, ${subFolder.groupId}, ${subFolder.companyId}, ${subFolder.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${subFolder.repositoryId}, 0, ${subFolder.parentFolderId}, '${subFolder.name}', '${subFolder.description}', null, 0, 0);

						${sampleSQLBuilder.insertSecurity("com.liferay.portlet.documentlibrary.model.DLFolder", subFolder.folderId)}

						update DLFolder set modifiedDate=CURRENT_TIMESTAMP, lastPostDate=CURRENT_TIMESTAMP where folderId='${subFolder.parentFolderId}';

						<#assign lastLevelFolderIds = lastLevelFolderIds + [subFolder.folderId]>

						<#if (!leafFolderOnly || (folderDepth == maxFolderDepth))>
							${sampleSQLBuilder.insertDLFiles(subFolder, ddmStructure, docmentLibraryCsvWriter)}
						</#if>
					</#list>
				</#if>
			</#list>
		</#if>
	</#list>
</#if>