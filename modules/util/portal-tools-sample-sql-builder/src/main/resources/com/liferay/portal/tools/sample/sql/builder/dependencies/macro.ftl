<#setting number_format = "computer">

<#macro insertAssetEntry
	_entry
	_categoryAndTag = false
	_ctCollectionId = ""
>
	<#if (_ctCollectionId?has_content)>
		<#local ctCollectionAssetEntryModel = dataFactory.newAssetEntryModel(_entry)>

		${dataFactory.toCTCollectionInsertSQL(ctCollectionAssetEntryModel, _ctCollectionId)}

		<#if _categoryAndTag>
			<#local ctCollectionAssetCategoryIds = dataFactory.getAssetCategoryIds(ctCollectionAssetEntryModel)>

			<#list ctCollectionAssetCategoryIds as ctCollectionAssetCategoryId>
				<#local ctCollectionAssetEntryAssetCategoryRelId = dataFactory.getCounterNext()>

			insert into AssetEntryAssetCategoryRel values (0, ${_ctCollectionId}, ${ctCollectionAssetEntryAssetCategoryRelId}, ${ctCollectionAssetEntryModel.companyId}, ${ctCollectionAssetEntryModel.entryId}, ${ctCollectionAssetCategoryId}, 0);
			</#list>

			<#local ctCollectionAssetTagIds = dataFactory.getAssetTagIds(ctCollectionAssetEntryModel)>

			<#list ctCollectionAssetTagIds as ctCollectionAssetTagId>
				${dataFactory.toInsertSQL("AssetEntries_AssetTags", ctCollectionAssetEntryModel.companyId, ctCollectionAssetEntryModel.entryId, ctCollectionAssetTagId)}
			</#list>
		</#if>

	<#else>

		<#local assetEntryModel = dataFactory.newAssetEntryModel(_entry)>

		${dataFactory.toInsertSQL(assetEntryModel)}

		<#if _categoryAndTag>
			<#local assetCategoryIds = dataFactory.getAssetCategoryIds(assetEntryModel)>

			<#list assetCategoryIds as assetCategoryId>
				<#local assetEntryAssetCategoryRelId = dataFactory.getCounterNext()>

			insert into AssetEntryAssetCategoryRel values (0, 0, ${assetEntryAssetCategoryRelId}, ${assetEntryModel.companyId}, ${assetEntryModel.entryId}, ${assetCategoryId}, 0);
			</#list>

			<#local assetTagIds = dataFactory.getAssetTagIds(assetEntryModel)>

			<#list assetTagIds as assetTagId>
				${dataFactory.toInsertSQL("AssetEntries_AssetTags", assetEntryModel.companyId, assetEntryModel.entryId, assetTagId)}
			</#list>
		</#if>
	</#if>
</#macro>

<#macro insertContentLayout
	_layoutModel
	_fragmentEntryModel
	_journalArticleModel
>
	${dataFactory.toInsertSQL(_layoutModel)}

	${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(_layoutModel))}

	<#local fragmentEntryLinkModel = dataFactory.newFragmentEntryLinkModel(_layoutModel, _fragmentEntryModel)>

	${dataFactory.toInsertSQL(fragmentEntryLinkModel)}

	<#local journalContentPortletPreferencesModel = dataFactory.newJournalContentPortletPreferencesModel(fragmentEntryLinkModel)>

	${dataFactory.toInsertSQL(journalContentPortletPreferencesModel)}

	${dataFactory.toInsertSQL(dataFactory.newJournalContentPortletPreferenceValueModel(journalContentPortletPreferencesModel, _journalArticleModel))}

	<#local layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(_layoutModel)>

	${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

	<#local layoutPageTemplateStructureRelModel = dataFactory.newLayoutPageTemplateStructureRelModel(_layoutModel, layoutPageTemplateStructureModel, fragmentEntryLinkModel)>

	${dataFactory.toInsertSQL(layoutPageTemplateStructureRelModel)}
</#macro>

<#macro insertContentPageLayout
	_fragmentEntryLinkModels
	_layoutModels
	_templateFileName
>
	<#list _fragmentEntryLinkModels as fragmentEntryLinkModel>
		${dataFactory.toInsertSQL(fragmentEntryLinkModel)}
	</#list>

	<#list _layoutModels as layoutModel>
		${dataFactory.toInsertSQL(layoutModel)}

		${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(layoutModel))}

		<#local layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(layoutModel)>

		${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

		<#local layoutPageTemplateStructureRelModel = dataFactory.newLayoutPageTemplateStructureRelModel(layoutModel, layoutPageTemplateStructureModel, _fragmentEntryLinkModels, _templateFileName)>

		${dataFactory.toInsertSQL(layoutPageTemplateStructureRelModel)}
	</#list>
</#macro>

<#macro insertDDMContent
	_ddmStorageLinkId
	_ddmStructureId
	_entry
	_ddmStructureVersionId = 0
	_currentIndex = -1
>
	<#if _currentIndex = -1>
		<#local ddmStorageLinkModel = dataFactory.newDDMStorageLinkModel(_entry, _ddmStorageLinkId, _ddmStructureId)>

		<#local ddmFieldModels = dataFactory.newDDMFieldModels(_entry, ddmStorageLinkModel)>

		<#local ddmFieldAttributeModels = dataFactory.newDDMFieldAttributeModels(_entry, ddmFieldModels, ddmStorageLinkModel)>
	<#else>
		<#local ddmStorageLinkModel = dataFactory.newDDMStorageLinkModel(_entry, _ddmStorageLinkId, _ddmStructureId, _ddmStructureVersionId)>

		<#local ddmFieldModels = dataFactory.newDDMFieldModels(_currentIndex, _entry, ddmStorageLinkModel)>

		<#local ddmFieldAttributeModels = dataFactory.newDDMFieldAttributeModels(_currentIndex, _entry, ddmFieldModels, ddmStorageLinkModel)>
	</#if>

	<#list ddmFieldModels as ddmFieldModel>
		${dataFactory.toInsertSQL(ddmFieldModel)}
	</#list>

	<#list ddmFieldAttributeModels as ddmFieldAttributeModel>
		${dataFactory.toInsertSQL(ddmFieldAttributeModel)}
	</#list>

	${dataFactory.toInsertSQL(ddmStorageLinkModel)}
</#macro>

<#macro insertDDMStructure
	_ddmStructureModel
	_ddmStructureLayoutModel
	_ddmStructureVersionModel
	_ctCollectionId=""
>
	<#if (_ctCollectionId?has_content)>
		${dataFactory.toCTCollectionInsertSQL(_ddmStructureModel, _ctCollectionId)}

		${dataFactory.toCTCollectionInsertSQL(_ddmStructureLayoutModel, _ctCollectionId)}

		${dataFactory.toCTCollectionInsertSQL(_ddmStructureVersionModel, _ctCollectionId)}
	<#else>
		${dataFactory.toInsertSQL(_ddmStructureModel)}

		${dataFactory.toInsertSQL(_ddmStructureLayoutModel)}

		${dataFactory.toInsertSQL(_ddmStructureVersionModel)}
	</#if>
</#macro>

<#macro insertDLFolder
	_ddmStructureId
	_dlFolderDepth
	_groupModel
	_parentDLFolderId
>
	<#if _dlFolderDepth <= dataFactory.maxDLFolderDepth>
		<#local dlFolderModels = dataFactory.newDLFolderModels(_groupModel.groupId, _parentDLFolderId, _dlFolderDepth)>

		<#list dlFolderModels as dlFolderModel>
			${dataFactory.toInsertSQL(dlFolderModel)}

			<@insertAssetEntry _entry = dlFolderModel />

			<#local dlFileEntryModels = dataFactory.newDlFileEntryModels(dlFolderModel)>

			<#list dlFileEntryModels as dlFileEntryModel>
				${dataFactory.toInsertSQL(dlFileEntryModel)}

				<#local dlFileVersionModel = dataFactory.newDLFileVersionModel(dlFileEntryModel)>

				${dataFactory.toInsertSQL(dlFileVersionModel)}

				<@insertAssetEntry _entry = dlFileEntryModel />

				<#local ddmStorageLinkId = dataFactory.getCounterNext()>

				<@insertDDMContent
					_ddmStorageLinkId = ddmStorageLinkId
					_ddmStructureId = _ddmStructureId
					_entry = dlFileEntryModel
				/>

				<@insertMBDiscussion
					_classNameId = dataFactory.DLFileEntryClassNameId
					_classPK = dlFileEntryModel.fileEntryId
					_groupId = dlFileEntryModel.groupId
					_maxCommentCount = 0
					_mbRootMessageId = dataFactory.getCounterNext()
					_mbThreadId = dataFactory.getCounterNext()
				/>

				${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(dlFileEntryModel))}

				<#local dlFileEntryMetadataModel = dataFactory.newDLFileEntryMetadataModel(ddmStorageLinkId, _ddmStructureId, dlFileVersionModel)>

				${dataFactory.toInsertSQL(dlFileEntryMetadataModel)}

				${dataFactory.toInsertSQL(dataFactory.newDDMStructureLinkModel(dlFileEntryMetadataModel))}

				${csvFileWriter.write("documentLibrary", virtualHostModel.hostname + "," + _groupModel.friendlyURL + "," + dlFileEntryModel.uuid + "," + dlFolderModel.folderId + "," + dlFileEntryModel.name + "," + dlFileEntryModel.fileEntryId + "," + dlFileEntryModel.fileName + "," + _groupModel.groupId + "\n")}
			</#list>

			<#local groupModel = _groupModel>

			<@insertDLFolder
				_ddmStructureId = _ddmStructureId
				_dlFolderDepth = _dlFolderDepth + 1
				_groupModel = groupModel
				_parentDLFolderId = dlFolderModel.folderId
			/>
		</#list>
	</#if>
</#macro>

<#macro insertGroup
	_groupModel
>
	${dataFactory.toInsertSQL(_groupModel)}

	<#local layoutSetModels = dataFactory.newLayoutSetModels(_groupModel.groupId)>

	<#list layoutSetModels as layoutSetModel>
		${dataFactory.toInsertSQL(layoutSetModel)}
	</#list>
</#macro>

<#macro insertJournalArticle
	_journalArticleModel
	_journalDDMStructureModel
	_journalDDMTemplateModel
	_insertAssetEntry
	_ctCollectionId=""
>
	<#if (_ctCollectionId?has_content)>
		${dataFactory.toInsertSQL(_journalArticleModel)}

		${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(dataFactory.getLongValue(_ctCollectionId), _journalArticleModel, dataFactory.ctChangeTypeAddition))}

		<#local ddmFieldModels = dataFactory.newDDMFieldModels(_journalArticleModel)>

		<#list ddmFieldModels as ddmFieldModel>
			${dataFactory.toInsertSQL(ddmFieldModel)}

			${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(dataFactory.getLongValue(_ctCollectionId), ddmFieldModel, dataFactory.ctChangeTypeAddition))}
		</#list>

		<#local ddmFieldAttributeModels = dataFactory.newDDMFieldAttributeModels(_journalArticleModel, ddmFieldModels)>

		<#list ddmFieldAttributeModels as ddmFieldAttributeModel>
			${dataFactory.toInsertSQL(ddmFieldAttributeModel)}

			${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(dataFactory.getLongValue(_ctCollectionId), ddmFieldAttributeModel, dataFactory.ctChangeTypeAddition))}
		</#list>

		<#local journalArticleLocalizationModel = dataFactory.newJournalArticleLocalizationModel(_journalArticleModel)>

		${dataFactory.toInsertSQL(journalArticleLocalizationModel)}

		${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(dataFactory.getLongValue(_ctCollectionId), journalArticleLocalizationModel, dataFactory.ctChangeTypeAddition))}

		<#local ddmTemplateLinkModel = dataFactory.newDDMTemplateLinkModel(_journalArticleModel, _journalDDMTemplateModel.templateId)>

		${dataFactory.toInsertSQL(ddmTemplateLinkModel)}

		${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(dataFactory.getLongValue(_ctCollectionId), ddmTemplateLinkModel, dataFactory.ctChangeTypeAddition))}

		<#local ddmStorageLinkModel = dataFactory.newDDMStorageLinkModel(_journalArticleModel, _journalDDMStructureModel.structureId)>

		${dataFactory.toInsertSQL(ddmStorageLinkModel)}

		${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(dataFactory.getLongValue(_ctCollectionId), ddmStorageLinkModel, dataFactory.ctChangeTypeAddition))}

		<#local socialActivityModel = dataFactory.newSocialActivityModel(_journalArticleModel)>

		${dataFactory.toInsertSQL(socialActivityModel)}

		${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(dataFactory.getLongValue(_ctCollectionId), socialActivityModel, dataFactory.ctChangeTypeAddition))}

		<#if _insertAssetEntry>
			<@insertAssetEntry
				_categoryAndTag = true
				_ctCollectionId = _ctCollectionId
				_entry = dataFactory.newObjectValuePair(_journalArticleModel, journalArticleLocalizationModel)
			/>
		</#if>

	<#else>
		${dataFactory.toInsertSQL(_journalArticleModel)}

		<#local ddmFieldModels = dataFactory.newDDMFieldModels(_journalArticleModel) />

		<#list ddmFieldModels as ddmFieldModel>
			${dataFactory.toInsertSQL(ddmFieldModel)}
		</#list>

		<#local ddmFieldAttributeModels = dataFactory.newDDMFieldAttributeModels(_journalArticleModel, ddmFieldModels) />

		<#list ddmFieldAttributeModels as ddmFieldAttributeModel>
			${dataFactory.toInsertSQL(ddmFieldAttributeModel)}
		</#list>

		<#local journalArticleLocalizationModel = dataFactory.newJournalArticleLocalizationModel(_journalArticleModel)>

		${dataFactory.toInsertSQL(journalArticleLocalizationModel)}

		${dataFactory.toInsertSQL(dataFactory.newDDMTemplateLinkModel(_journalArticleModel, _journalDDMTemplateModel.templateId))}

		${dataFactory.toInsertSQL(dataFactory.newDDMStorageLinkModel(_journalArticleModel, _journalDDMStructureModel.structureId))}

		${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(_journalArticleModel))}

		<#if _insertAssetEntry>
			<@insertAssetEntry
				_categoryAndTag = true
				_entry = dataFactory.newObjectValuePair(journalArticleModel, journalArticleLocalizationModel)
			/>
		</#if>
	</#if>
</#macro>

<#macro insertLayout
	_layoutModel
	_ctCollectionId=""
>
	<#if (_ctCollectionId?has_content)>

	${dataFactory.toCTCollectionInsertSQL(_layoutModel, _ctCollectionId)}

	${dataFactory.toCTCollectionInsertSQL(dataFactory.newLayoutFriendlyURLModel(_layoutModel), _ctCollectionId)}

	<#else>

	${dataFactory.toInsertSQL(_layoutModel)}

	${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(_layoutModel))}

	</#if>
</#macro>

<#macro insertMBDiscussion
	_classNameId
	_classPK
	_groupId
	_maxCommentCount
	_mbRootMessageId
	_mbThreadId
	_ctCollectionId=""
>
	<#if (_ctCollectionId?has_content)>
		<#local mbThreadModel = dataFactory.newMBThreadModel(_mbThreadId, _groupId, _mbRootMessageId)>

		${dataFactory.toCTCollectionInsertSQL(mbThreadModel, _ctCollectionId)}

		<#local mbRootMessageModel = dataFactory.newMBMessageModel(mbThreadModel, _classNameId, _classPK, 0)>

		<@insertMBMessage
			_ctCollectionId = _ctCollectionId
			_mbMessageModel = mbRootMessageModel
		/>

		<#local mbMessageModels = dataFactory.newMBMessageModels(mbThreadModel, _classNameId, _classPK, _maxCommentCount)>

		<#list mbMessageModels as mbMessageModel>
			<@insertMBMessage
				_ctCollectionId = _ctCollectionId
				_mbMessageModel = mbMessageModel
			/>

			${dataFactory.toCTCollectionInsertSQL(dataFactory.newSocialActivityModel(mbMessageModel), _ctCollectionId)}
		</#list>

		${dataFactory.toCTCollectionInsertSQL(dataFactory.newMBDiscussionModel(_groupId, _classNameId, _classPK, _mbThreadId), _ctCollectionId)}

	<#else>

		<#local mbThreadModel = dataFactory.newMBThreadModel(_mbThreadId, _groupId, _mbRootMessageId)>

		${dataFactory.toInsertSQL(mbThreadModel)}

		<#local mbRootMessageModel = dataFactory.newMBMessageModel(mbThreadModel, _classNameId, _classPK, 0)>

		<@insertMBMessage _mbMessageModel = mbRootMessageModel />

		<#local mbMessageModels = dataFactory.newMBMessageModels(mbThreadModel, _classNameId, _classPK, _maxCommentCount)>

		<#list mbMessageModels as mbMessageModel>
			<@insertMBMessage _mbMessageModel = mbMessageModel />

			${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(mbMessageModel))}
		</#list>

		${dataFactory.toInsertSQL(dataFactory.newMBDiscussionModel(_groupId, _classNameId, _classPK, _mbThreadId))}

	</#if>
</#macro>

<#macro insertMBMessage
	_mbMessageModel
	_ctCollectionId=""
>
	<#if (_ctCollectionId?has_content)>
		${dataFactory.toCTCollectionInsertSQL(_mbMessageModel, _ctCollectionId)}

		<@insertAssetEntry
			_ctCollectionId = _ctCollectionId
			_entry = _mbMessageModel
		/>
	<#else>
		${dataFactory.toInsertSQL(_mbMessageModel)}

		<@insertAssetEntry _entry = _mbMessageModel />
	</#if>
</#macro>

<#macro insertUser
	_userModel
	_groupIds = []
	_roleIds = []
>
	${dataFactory.toInsertSQL(_userModel)}

	${dataFactory.toInsertSQL(dataFactory.newContactModel(_userModel))}

	<#list _roleIds as roleId>
		${dataFactory.toInsertSQL("Users_Roles", 0, roleId, _userModel.userId)}
	</#list>

	<#list _groupIds as groupId>
		${dataFactory.toInsertSQL("Users_Groups", 0, groupId, _userModel.userId)}
	</#list>
</#macro>