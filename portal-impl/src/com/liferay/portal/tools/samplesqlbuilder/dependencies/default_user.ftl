<#-- Default user -->

<@insertUser _user = dataFactory.defaultUser />

<#-- Guest user -->

<#assign user = dataFactory.guestUser>

<@insertGroup _group = dataFactory.newGroup(user) _publicPageCount = 0 />

<#assign groupIds = [dataFactory.guestGroup.groupId]>
<#assign roleIds = [dataFactory.administratorRole.roleId]>

<@insertUser _user = user _groupIds = groupIds _roleIds = roleIds />

<#-- Sample user -->

<#assign user = dataFactory.sampleUser>

<#assign sampleUserId = user.userId>

<#assign userGroup = dataFactory.newGroup(user)>

<#assign layout = dataFactory.newLayout(userGroup.groupId, "home", "", "33,")>

<@insertLayout _layout = layout />

<@insertGroup _group = userGroup _publicPageCount = 1 />

<#assign groupIds = 1..maxGroupCount>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

<@insertUser _user = user _groupIds = groupIds _roleIds = roleIds />

<#list groupIds as groupId>
	<#assign blogsStatsUser = dataFactory.newBlogsStatsUser(groupId)>

	insert into BlogsStatsUser values (${blogsStatsUser.statsUserId}, ${blogsStatsUser.groupId}, ${blogsStatsUser.companyId}, ${blogsStatsUser.userId}, ${blogsStatsUser.entryCount}, '${dataFactory.getDateString(blogsStatsUser.lastPostDate)}', ${blogsStatsUser.ratingsTotalEntries}, ${blogsStatsUser.ratingsTotalScore}, ${blogsStatsUser.ratingsAverageScore});

	<#assign mbStatsUser = dataFactory.newMBStatsUser(groupId)>

	insert into MBStatsUser values (${mbStatsUser.statsUserId}, ${mbStatsUser.groupId}, ${mbStatsUser.userId}, ${mbStatsUser.messageCount}, '${dataFactory.getDateString(mbStatsUser.lastPostDate)}');
</#list>