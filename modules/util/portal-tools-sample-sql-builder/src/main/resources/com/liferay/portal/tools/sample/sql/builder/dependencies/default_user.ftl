<#-- Default user -->

<@insertUser
	_userModel = dataFactory.defaultUserModel
/>

<#-- Guest user -->

<#assign userModel = dataFactory.guestUserModel>

<@insertGroup
	_groupModel = dataFactory.newGroupModel(userModel)
	_publicPageCount = 0
/>

<#assign groupIds = [dataFactory.guestGroupModel.groupId]>
<#assign roleIds = [dataFactory.administratorRoleModel.roleId]>

<@insertUser
	_groupIds = groupIds
	_roleIds = roleIds
	_userModel = userModel
/>

<#-- Sample user -->

<#assign userModel = dataFactory.sampleUserModel>

<#assign sampleUserId = userModel.userId>

<#assign userGroupModel = dataFactory.newGroupModel(userModel)>

<#assign layoutModel = dataFactory.newLayoutModel(userGroupModel.groupId, "home", "", "")>

<@insertLayout
	_layoutModel = layoutModel
/>

<@insertGroup
	_groupModel = userGroupModel
	_publicPageCount = 1
/>

<#assign groupIds = dataFactory.getSequence(dataFactory.maxGroupCount)>
<#assign roleIds = [dataFactory.administratorRoleModel.roleId, dataFactory.powerUserRoleModel.roleId, dataFactory.userRoleModel.roleId]>

<@insertUser
	_groupIds = groupIds
	_roleIds = roleIds
	_userModel = userModel
/>

<#list groupIds as groupId>
	<#assign blogsStatsUserModel = dataFactory.newBlogsStatsUserModel(groupId)>

	insert into BlogsStatsUser values (${blogsStatsUserModel.statsUserId}, ${blogsStatsUserModel.groupId}, ${blogsStatsUserModel.companyId}, ${blogsStatsUserModel.userId}, ${blogsStatsUserModel.entryCount}, '${dataFactory.getDateString(blogsStatsUserModel.lastPostDate)}', ${blogsStatsUserModel.ratingsTotalEntries}, ${blogsStatsUserModel.ratingsTotalScore}, ${blogsStatsUserModel.ratingsAverageScore});

	<#assign mbStatsUserModel = dataFactory.newMBStatsUserModel(groupId)>

	insert into MBStatsUser values (${mbStatsUserModel.statsUserId}, ${mbStatsUserModel.groupId}, ${mbStatsUserModel.userId}, ${mbStatsUserModel.messageCount}, '${dataFactory.getDateString(mbStatsUserModel.lastPostDate)}');
</#list>