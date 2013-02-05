<@insertUser _user = dataFactory.defaultUser/>

<@insertUser _user = dataFactory.guestUser _roleIds = [dataFactory.administratorRole.roleId] _groupIds = [dataFactory.guestGroup.groupId] />

<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

<@insertUser _user = dataFactory.sampleUser _roleIds = roleIds _groupIds = 1..maxGroupCount />

<#list 1..maxGroupCount as groupId>
	<#assign userId = dataFactory.sampleUser.userId>

	<#assign blogsStatsUser = dataFactory.addBlogsStatsUser(groupId, userId)>

	insert into BlogsStatsUser values (${blogsStatsUser.statsUserId}, ${blogsStatsUser.groupId}, ${blogsStatsUser.companyId}, ${blogsStatsUser.userId}, ${blogsStatsUser.entryCount}, '${blogsStatsUser.lastPostDate?datetime}', ${blogsStatsUser.ratingsTotalEntries}, ${blogsStatsUser.ratingsTotalScore}, ${blogsStatsUser.ratingsAverageScore});

	<#assign mbStatsUser = dataFactory.addMBStatsUser(groupId, userId)>

	insert into MBStatsUser values (${mbStatsUser.statsUserId}, ${mbStatsUser.groupId}, ${mbStatsUser.userId}, ${mbStatsUser.messageCount}, '${mbStatsUser.lastPostDate?datetime}');
</#list>