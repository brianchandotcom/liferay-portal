<#assign user = dataFactory.defaultUser>

${sampleSQLBuilder.insertUser(user, [], [])}

<#assign user = dataFactory.guestUser>
<#assign roleIds = [dataFactory.administratorRole.roleId]>
<#assign groupIds = [dataFactory.guestGroup.groupId]>

${sampleSQLBuilder.insertUser(user, roleIds, groupIds)}

<#assign user = dataFactory.sampleUser>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>
<#assign groupIds = 1..maxGroupCount>

${sampleSQLBuilder.insertUser(user, roleIds, groupIds)}

<#list 1..maxGroupCount as groupId>
	<#assign userId = dataFactory.sampleUser.userId>

	<#assign blogsStatsUser = dataFactory.addBlogsStatsUser(groupId, userId)>

	insert into BlogsStatsUser values (${blogsStatsUser.statsUserId}, ${blogsStatsUser.groupId}, ${blogsStatsUser.companyId}, ${blogsStatsUser.userId}, ${blogsStatsUser.entryCount}, '${blogsStatsUser.lastPostDate?datetime}', ${blogsStatsUser.ratingsTotalEntries}, ${blogsStatsUser.ratingsTotalScore}, ${blogsStatsUser.ratingsAverageScore});

	<#assign mbStatsUser = dataFactory.addMBStatsUser(groupId, userId)>

	insert into MBStatsUser values (${mbStatsUser.statsUserId}, ${mbStatsUser.groupId}, ${mbStatsUser.userId}, ${mbStatsUser.messageCount}, '${mbStatsUser.lastPostDate?datetime}');
</#list>