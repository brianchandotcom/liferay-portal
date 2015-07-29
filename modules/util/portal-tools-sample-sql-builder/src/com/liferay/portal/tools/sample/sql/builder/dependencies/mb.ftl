<#assign mbCategoryModels = dataFactory.newMBCategoryModels(groupId)>

<#list mbCategoryModels as mbCategoryModel>
	insert into MBCategory values ('${mbCategoryModel.uuid}', ${mbCategoryModel.categoryId}, ${mbCategoryModel.groupId}, ${mbCategoryModel.userId}, '${mbCategoryModel.userName}', '${dataFactory.getDateString(mbCategoryModel.createDate)}', '${dataFactory.getDateString(mbCategoryModel.modifiedDate)}', ${mbCategoryModel.parentCategoryId}, '${mbCategoryModel.name}', '${mbCategoryModel.description}', '${mbCategoryModel.displayStyle}', ${mbCategoryModel.threadCount}, ${mbCategoryModel.messageCount}, '${dataFactory.getDateString(mbCategoryModel.lastPostDate)}', '${dataFactory.getDateString(mbCategoryModel.lastPublishDate)}', ${mbCategoryModel.status}, ${mbCategoryModel.statusByUserId}, '${mbCategoryModel.statusByUserName}', '${dataFactory.getDateString(mbCategoryModel.statusDate)}', ${mbCategoryModel.companyId});

	<@insertResourcePermissions
		_entry = mbCategoryModel
	/>

	<#assign mbMailingListModel = dataFactory.newMBMailingListModel(mbCategoryModel)>

	insert into MBMailingList values ('${mbMailingListModel.uuid}', ${mbMailingListModel.mailingListId}, ${mbMailingListModel.groupId}, ${mbMailingListModel.userId}, '${mbMailingListModel.userName}', '${dataFactory.getDateString(mbMailingListModel.createDate)}', '${dataFactory.getDateString(mbMailingListModel.modifiedDate)}', ${mbMailingListModel.categoryId}, '${mbMailingListModel.emailAddress}', '${mbMailingListModel.inProtocol}', '${mbMailingListModel.inServerName}', ${mbMailingListModel.inServerPort}, ${mbMailingListModel.inUseSSL?string}, '${mbMailingListModel.inUserName}', '${mbMailingListModel.inPassword}', ${mbMailingListModel.inReadInterval}, '${mbMailingListModel.outEmailAddress}', ${mbMailingListModel.outCustom?string}, '${mbMailingListModel.outServerName}', ${mbMailingListModel.outServerPort}, ${mbMailingListModel.outUseSSL?string}, '${mbMailingListModel.outUserName}', '${mbMailingListModel.outPassword}', ${mbMailingListModel.allowAnonymous?string}, ${mbMailingListModel.active?string}, ${mbMailingListModel.companyId});

	<#assign mbThreadModels = dataFactory.newMBThreadModels(mbCategoryModel)>

	<#list mbThreadModels as mbThreadModel>
		insert into MBThread values ('${mbThreadModel.uuid}', ${mbThreadModel.threadId}, ${mbThreadModel.groupId}, ${mbThreadModel.userId}, '${mbThreadModel.userName}', '${dataFactory.getDateString(mbThreadModel.createDate)}', '${dataFactory.getDateString(mbThreadModel.modifiedDate)}', ${mbThreadModel.categoryId}, ${mbThreadModel.rootMessageId}, ${mbThreadModel.rootMessageUserId}, ${mbThreadModel.messageCount}, ${mbThreadModel.viewCount}, ${mbThreadModel.lastPostByUserId}, '${dataFactory.getDateString(mbThreadModel.lastPostDate)}', ${mbThreadModel.priority}, ${mbThreadModel.question?string}, '${dataFactory.getDateString(mbThreadModel.lastPublishDate)}', ${mbThreadModel.status}, ${mbThreadModel.statusByUserId}, '${mbThreadModel.statusByUserName}', '${dataFactory.getDateString(mbThreadModel.statusDate)}', ${mbThreadModel.companyId});

		<@insertSubscription
			_entry = mbThreadModel
		/>

		<@insertAssetEntry
			_entry = mbThreadModel
		/>

		<#assign mbThreadFlagModel = dataFactory.newMBThreadFlagModel(mbThreadModel)>

		insert into MBThreadFlag values ('${mbThreadFlagModel.uuid}', ${mbThreadFlagModel.threadFlagId}, ${mbThreadFlagModel.groupId}, ${mbThreadFlagModel.userId}, '${mbThreadFlagModel.userName}', '${dataFactory.getDateString(mbThreadFlagModel.createDate)}', '${dataFactory.getDateString(mbThreadFlagModel.modifiedDate)}', ${mbThreadFlagModel.threadId}, '${dataFactory.getDateString(mbThreadFlagModel.lastPublishDate)}', ${mbThreadFlagModel.companyId});

		<#assign mbMessageModels = dataFactory.newMBMessageModels(mbThreadModel)>

		<#list mbMessageModels as mbMessageModel>
			<@insertMBMessage
				_mbMessageModel = mbMessageModel
			/>

			<@insertResourcePermissions
				_entry = mbMessageModel
			/>

			<@insertSocialActivity
				_entry = mbMessageModel
			/>
		</#list>

		${messageBoardCSVWriter.write(mbCategoryModel.categoryId + "," + mbThreadModel.threadId + "," + mbThreadModel.rootMessageId + "\n")}
	</#list>
</#list>