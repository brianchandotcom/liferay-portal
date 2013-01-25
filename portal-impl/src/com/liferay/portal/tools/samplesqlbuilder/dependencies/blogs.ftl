<#if (maxBlogsEntryCount > 0)>
	<#list 1..maxBlogsEntryCount as blogsEntryCount>
		<#assign blogsEntry = dataFactory.addBlogsEntry(groupId, blogsEntryCount)>

		insert into BlogsEntry values ('${blogsEntry.uuid}', ${blogsEntry.entryId}, ${blogsEntry.groupId}, ${blogsEntry.companyId}, ${blogsEntry.userId}, '${blogsEntry.userName}', '${blogsEntry.createDate?datetime}', '${blogsEntry.modifiedDate?datetime}', '${blogsEntry.title}', '${blogsEntry.urlTitle}', '${blogsEntry.description}', '${blogsEntry.content}', '${blogsEntry.displayDate?datetime}', ${blogsEntry.allowPingbacks?string}, ${blogsEntry.allowTrackbacks?string}, '${blogsEntry.trackbacks}', ${blogsEntry.smallImage?string}, ${blogsEntry.smallImageId}, '${blogsEntry.smallImageURL}', ${blogsEntry.status}, ${blogsEntry.statusByUserId}, '${blogsEntry.statusByUserName}', ${blogsEntry.statusDate!'null'});

		<#assign mbRootMessage = dataFactory.addMBMessage(blogsEntry)>

		<@insertMBDiscussion _entry = blogsEntry _maxCommentCount = maxBlogsEntryCommentCount _mbRootMessage = mbRootMessage/>

		${writerBlogsCSV.write(blogsEntry.entryId + "," + blogsEntry.urlTitle + "," + mbRootMessage.threadId + "," + mbRootMessage.messageId + "\n")}
	</#list>
</#if>