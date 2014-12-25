<#assign mbCategoryModels = dataFactory.newMBCategoryModels(groupId)>

<#list mbCategoryModels as mbCategoryModel>
	insert into MBCategory values ('${mbCategoryModel.uuid}', ${mbCategoryModel.categoryId}, ${mbCategoryModel.groupId}, ${mbCategoryModel.companyId}, ${mbCategoryModel.userId}, '${mbCategoryModel.userName}', '${dataFactory.getDateString(mbCategoryModel.createDate)}', '${dataFactory.getDateString(mbCategoryModel.modifiedDate)}', ${mbCategoryModel.parentCategoryId}, '${mbCategoryModel.name}', '${mbCategoryModel.description}', '${mbCategoryModel.displayStyle}', ${mbCategoryModel.threadCount}, ${mbCategoryModel.messageCount}, '${dataFactory.getDateString(mbCategoryModel.lastPostDate)}', ${mbCategoryModel.status}, ${mbCategoryModel.statusByUserId}, '${mbCategoryModel.statusByUserName}', '${dataFactory.getDateString(mbCategoryModel.statusDate)}');

	<@insertResourcePermissions
		_entry = mbCategoryModel
	/>

	<#assign mbMailingListModel = dataFactory.newMBMailingListModel(mbCategoryModel)>

	insert into MBMailingList values ('${mbMailingListModel.uuid}', ${mbMailingListModel.mailingListId}, ${mbMailingListModel.groupId}, ${mbMailingListModel.companyId}, ${mbMailingListModel.userId}, '${mbMailingListModel.userName}', '${dataFactory.getDateString(mbMailingListModel.createDate)}', '${dataFactory.getDateString(mbMailingListModel.modifiedDate)}', ${mbMailingListModel.categoryId}, '${mbMailingListModel.emailAddress}', '${mbMailingListModel.inProtocol}', '${mbMailingListModel.inServerName}', ${mbMailingListModel.inServerPort}, ${mbMailingListModel.inUseSSL?string}, '${mbMailingListModel.inUserName}', '${mbMailingListModel.inPassword}', ${mbMailingListModel.inReadInterval}, '${mbMailingListModel.outEmailAddress}', ${mbMailingListModel.outCustom?string}, '${mbMailingListModel.outServerName}', ${mbMailingListModel.outServerPort}, ${mbMailingListModel.outUseSSL?string}, '${mbMailingListModel.outUserName}', '${mbMailingListModel.outPassword}', ${mbMailingListModel.allowAnonymous?string}, ${mbMailingListModel.active?string});

	<#assign mbThreadModels = dataFactory.newMBThreadModels(mbCategoryModel)>

	<#list mbThreadModels as mbThreadModel>
		insert into MBThread values ('${mbThreadModel.uuid}', ${mbThreadModel.threadId}, ${mbThreadModel.groupId}, ${mbThreadModel.companyId}, ${mbThreadModel.userId}, '${mbThreadModel.userName}', '${dataFactory.getDateString(mbThreadModel.createDate)}', '${dataFactory.getDateString(mbThreadModel.modifiedDate)}', ${mbThreadModel.categoryId}, ${mbThreadModel.rootMessageId}, ${mbThreadModel.rootMessageUserId}, ${mbThreadModel.messageCount}, ${mbThreadModel.viewCount}, ${mbThreadModel.lastPostByUserId}, '${dataFactory.getDateString(mbThreadModel.lastPostDate)}', ${mbThreadModel.priority}, ${mbThreadModel.question?string}, ${mbThreadModel.status}, ${mbThreadModel.statusByUserId}, '${mbThreadModel.statusByUserName}', '${dataFactory.getDateString(mbThreadModel.statusDate)}');

		<@insertSubscription
			_entry = mbThreadModel
		/>

		<@insertAssetEntry
			_entry = mbThreadModel
		/>

		<#assign mbThreadFlagModel = dataFactory.newMBThreadFlagModel(mbThreadModel)>

		insert into MBThreadFlag values ('${mbThreadFlagModel.uuid}', ${mbThreadFlagModel.threadFlagId}, ${mbThreadFlagModel.groupId}, ${mbThreadFlagModel.companyId}, ${mbThreadFlagModel.userId}, '${mbThreadFlagModel.userName}', '${dataFactory.getDateString(mbThreadFlagModel.createDate)}', '${dataFactory.getDateString(mbThreadFlagModel.modifiedDate)}', ${mbThreadFlagModel.threadId});

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