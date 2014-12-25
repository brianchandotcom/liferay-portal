<#assign blogsEntryModels = dataFactory.newBlogsEntryModels(groupId)>

<#list blogsEntryModels as blogsEntryModel>
	insert into BlogsEntry values ('${blogsEntryModel.uuid}', ${blogsEntryModel.entryId}, ${blogsEntryModel.groupId}, ${blogsEntryModel.companyId}, ${blogsEntryModel.userId}, '${blogsEntryModel.userName}', '${dataFactory.getDateString(blogsEntryModel.createDate)}', '${dataFactory.getDateString(blogsEntryModel.modifiedDate)}', '${blogsEntryModel.title}', '${blogsEntryModel.subtitle}', '${blogsEntryModel.urlTitle}', '${blogsEntryModel.description}', '${blogsEntryModel.content}', '${dataFactory.getDateString(blogsEntryModel.displayDate)}', ${blogsEntryModel.allowPingbacks?string}, ${blogsEntryModel.allowTrackbacks?string}, '${blogsEntryModel.trackbacks}', ${blogsEntryModel.coverImageFileEntryId}, '${blogsEntryModel.coverImageURL}', ${blogsEntryModel.smallImage?string}, ${blogsEntryModel.smallImageFileEntryId}, ${blogsEntryModel.smallImageId}, '${blogsEntryModel.smallImageURL}', ${blogsEntryModel.status}, ${blogsEntryModel.statusByUserId}, '${blogsEntryModel.statusByUserName}', '${dataFactory.getDateString(blogsEntryModel.statusDate)}');

	<@insertResourcePermissions
		_entry = blogsEntryModel
	/>

	<@insertAssetEntry
		_entry = blogsEntryModel
		_categoryAndTag = true
	/>

	<#assign mbThreadId = dataFactory.getCounterNext()>
	<#assign mbRootMessageId = dataFactory.getCounterNext()>

	<@insertMBDiscussion
		_classNameId = dataFactory.blogsEntryClassNameId
		_classPK = blogsEntryModel.entryId
		_groupId = groupId
		_maxCommentCount = dataFactory.maxBlogsEntryCommentCount
		_mbRootMessageId = mbRootMessageId
		_mbThreadId = mbThreadId
	/>

	<@insertSubscription
		_entry = blogsEntryModel
	/>

	<@insertSocialActivity
		_entry = blogsEntryModel
	/>

	${blogCSVWriter.write(blogsEntryModel.entryId + "," + blogsEntryModel.urlTitle + "," + mbThreadId + "," + mbRootMessageId + "\n")}
</#list>