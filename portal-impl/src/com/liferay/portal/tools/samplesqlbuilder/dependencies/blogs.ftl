<#if (maxBlogsEntryCount > 0)>
	<#list 1..maxBlogsEntryCount as blogsEntryCount>
		<#assign blogsEntry = dataFactory.newBlogsEntry(groupId, blogsEntryCount)>

		insert into BlogsEntry values ('${blogsEntry.uuid}', ${blogsEntry.entryId}, ${blogsEntry.groupId}, ${blogsEntry.companyId}, ${blogsEntry.userId}, '${blogsEntry.userName}', '${dataFactory.getDateString(blogsEntry.createDate)}', '${dataFactory.getDateString(blogsEntry.modifiedDate)}', '${blogsEntry.title}', '${blogsEntry.urlTitle}', '${blogsEntry.description}', '${blogsEntry.content}', '${dataFactory.getDateString(blogsEntry.displayDate)}', ${blogsEntry.allowPingbacks?string}, ${blogsEntry.allowTrackbacks?string}, '${blogsEntry.trackbacks}', ${blogsEntry.smallImage?string}, ${blogsEntry.smallImageId}, '${blogsEntry.smallImageURL}', ${blogsEntry.status}, ${blogsEntry.statusByUserId}, '${blogsEntry.statusByUserName}', '${dataFactory.getDateString(blogsEntry.statusDate)}');

		<#assign assetEntry = dataFactory.newAssetEntry(blogsEntry)>

		insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${dataFactory.getDateString(assetEntry.createDate)}', '${dataFactory.getDateString(assetEntry.modifiedDate)}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, '${dataFactory.getDateString(assetEntry.startDate)}', '${dataFactory.getDateString(assetEntry.endDate)}', '${dataFactory.getDateString(assetEntry.publishDate)}', '${dataFactory.getDateString(assetEntry.expirationDate)}', '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});

		<#assign mbThreadId = counter.get()>
		<#assign mbRootMessageId = counter.get()>

		${sampleSQLBuilder.insertMBDiscussion(groupId, dataFactory.blogsEntryClassNameId, blogsEntry.entryId, mbThreadId, mbRootMessageId, maxBlogsEntryCommentCount)}

		${writerBlogsCSV.write(blogsEntry.entryId + "," + blogsEntry.urlTitle + "," + mbThreadId + "," + mbRootMessageId + "\n")}
	</#list>
</#if>