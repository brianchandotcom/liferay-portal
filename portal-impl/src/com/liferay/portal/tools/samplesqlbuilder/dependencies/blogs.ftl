<#if (maxBlogsEntryCount > 0)>
	<#list 1..maxBlogsEntryCount as blogsEntryCount>
		<#assign blogsEntry = dataFactory.newBlogsEntry(groupId, blogsEntryCount)>

		insert into BlogsEntry values ('${blogsEntry.uuid}', ${blogsEntry.entryId}, ${blogsEntry.groupId}, ${blogsEntry.companyId}, ${blogsEntry.userId}, '${blogsEntry.userName}', '${dataFactory.getDateString(blogsEntry.createDate)}', '${dataFactory.getDateString(blogsEntry.modifiedDate)}', '${blogsEntry.title}', '${blogsEntry.urlTitle}', '${blogsEntry.description}', '${blogsEntry.content}', '${dataFactory.getDateString(blogsEntry.displayDate)}', ${blogsEntry.allowPingbacks?string}, ${blogsEntry.allowTrackbacks?string}, '${blogsEntry.trackbacks}', ${blogsEntry.smallImage?string}, ${blogsEntry.smallImageId}, '${blogsEntry.smallImageURL}', ${blogsEntry.status}, ${blogsEntry.statusByUserId}, '${blogsEntry.statusByUserName}', '${dataFactory.getDateString(blogsEntry.statusDate)}');

		<@insertResourcePermissions
			_entry = blogsEntry
		/>

		<@insertAssetEntry
			_entry = blogsEntry
			_currentIndex = blogsEntryCount
		/>

		<#assign mbThreadId = counter.get()>
		<#assign mbRootMessageId = counter.get()>

		<@insertMBDiscussion
			_classNameId = dataFactory.blogsEntryClassNameId
			_classPK = blogsEntry.entryId
			_groupId = groupId
			_maxCommentCount = maxBlogsEntryCommentCount
			_mbRootMessageId = mbRootMessageId
			_mbThreadId = mbThreadId
		/>

		<@insertSubscription
			_entry = blogsEntry
		/>

		<@insertSocialActivity
			_entry = blogsEntry
		/>

		${writerBlogsCSV.write(blogsEntry.entryId + "," + blogsEntry.urlTitle + "," + mbThreadId + "," + mbRootMessageId + "\n")}
	</#list>
</#if>