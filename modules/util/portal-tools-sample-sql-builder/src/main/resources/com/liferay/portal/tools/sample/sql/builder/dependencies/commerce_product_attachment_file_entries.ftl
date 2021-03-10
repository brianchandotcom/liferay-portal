<#list dataFactory.getSequence(dataFactory.maxCPDefinitionAttachmentTypeImageCount) as cpDefinitionAttachmentTypeImageCount>
	<#assign
		cpAttachmentFileEntryModel = dataFactory.newCPAttachmentFileEntryModel(commerceCatalogGroupModel.groupId, cpDefinitionModel.CPDefinitionId, cpDefinitionAttachmentTypeImageCount, 0)

		dlFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, cpDefinitionDLFolderModel.folderId, "Commerce Product Definition Attachment Folder " + cpAttachmentFileEntryModel.CPAttachmentFileEntryId)

		dlFileEntryModel = dataFactory.newDlFileEntryModel(dlFolderModel, "TestFile" + cpAttachmentFileEntryModel.CPAttachmentFileEntryId, "jpeg", "image/jpeg", cpAttachmentFileEntryModel.fileEntryId)

		dlFileVersionModel = dataFactory.newDLFileVersionModel(dlFileEntryModel)
	/>

	${dataFactory.toInsertSQL(cpAttachmentFileEntryModel)}

	${dataFactory.toInsertSQL(dlFolderModel)}

	${dataFactory.toInsertSQL(dlFileEntryModel)}

	${dataFactory.toInsertSQL(dlFileVersionModel)}

	<@insertAssetEntry _entry=dlFileEntryModel />
</#list>

<#list dataFactory.getSequence(dataFactory.maxCPDefinitionAttachmentTypePDFCount) as cpDefinitionAttachmentTypePDFCount>
	<#assign
		cpAttachmentFileEntryModel = dataFactory.newCPAttachmentFileEntryModel(commerceCatalogGroupModel.groupId, cpDefinitionModel.CPDefinitionId, cpDefinitionAttachmentTypePDFCount, 1)

		dlFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, cpDefinitionDLFolderModel.folderId, "Commerce PDF Folder" + cpAttachmentFileEntryModel.CPAttachmentFileEntryId)

		dlFileEntryModel = dataFactory.newDlFileEntryModel(dlFolderModel, "TestFile" + cpAttachmentFileEntryModel.CPAttachmentFileEntryId, "pdf", "application/pdf", cpAttachmentFileEntryModel.fileEntryId)

		dlFileVersionModel = dataFactory.newDLFileVersionModel(dlFileEntryModel)
	/>

	${dataFactory.toInsertSQL(cpAttachmentFileEntryModel)}

	${dataFactory.toInsertSQL(dlFolderModel)}

	${dataFactory.toInsertSQL(dlFileEntryModel)}

	${dataFactory.toInsertSQL(dlFileVersionModel)}

	<@insertAssetEntry _entry=dlFileEntryModel />
</#list>