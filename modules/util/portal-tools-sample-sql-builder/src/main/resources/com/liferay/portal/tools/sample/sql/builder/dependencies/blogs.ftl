<#assign
	blogsEntryModels = dataFactory.newBlogsEntryModels(groupId)

	userNotificationDeliveryModel = dataFactory.newUserNotificationDeliveryModel("com_liferay_comment_web_portlet_CommentPortlet")
/>

${dataFactory.toInsertSQL(userNotificationDeliveryModel)}

<#list blogsEntryModels as blogsEntryModel>
	${dataFactory.toInsertSQL(blogsEntryModel)}

	<#assign friendlyURLEntryModel = dataFactory.newFriendlyURLEntryModel(blogsEntryModel) />

	${dataFactory.toInsertSQL(friendlyURLEntryModel)}

	${dataFactory.toInsertSQL(dataFactory.newFriendlyURLEntryLocalizationModel(friendlyURLEntryModel, blogsEntryModel))}

	${dataFactory.toInsertSQL(dataFactory.newFriendlyURLEntryMapping(friendlyURLEntryModel))}

	${dataFactory.toInsertSQL(dataFactory.newMBDiscussionAssetEntryModel(blogsEntryModel))}

	<@insertAssetEntry
		_categoryAndTag=true
		_entry=blogsEntryModel
	/>

	<#assign
		mbThreadId = dataFactory.getCounterNext()
		mbRootMessageId = dataFactory.getCounterNext()
	/>

	<@insertMBDiscussion
		_classNameId=dataFactory.blogsEntryClassNameId
		_classPK=blogsEntryModel.entryId
		_groupId=groupId
		_maxCommentCount=dataFactory.maxBlogsEntryCommentCount
		_mbRootMessageId=mbRootMessageId
		_mbThreadId=mbThreadId
	/>

	${dataFactory.toInsertSQL(dataFactory.newSubscriptionModel(blogsEntryModel))}

	${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(blogsEntryModel))}

	${dataFactory.getCSVWriter("blog").write(blogsEntryModel.entryId + "," + blogsEntryModel.urlTitle + "," + mbThreadId + "," + mbRootMessageId + "\n")}
</#list>