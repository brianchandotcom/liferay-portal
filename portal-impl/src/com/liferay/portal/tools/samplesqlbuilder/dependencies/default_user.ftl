<#-- Default user -->

${sampleSQLBuilder.insertUser(null, null, dataFactory.defaultUser)}

<#-- Guest user -->

<#assign user = dataFactory.guestUser>

<#assign userGroup = dataFactory.newGroup(user)>

${sampleSQLBuilder.insertGroup(userGroup, [])}

<#assign groupIds = [dataFactory.guestGroup.groupId]>
<#assign roleIds = [dataFactory.administratorRole.roleId]>

${sampleSQLBuilder.insertUser(groupIds, roleIds, user)}

<#-- Sample user -->

<#assign user = dataFactory.sampleUser>

<#assign sampleUserId = user.userId>

<#assign userGroup = dataFactory.newGroup(user)>

${sampleSQLBuilder.insertGroup(userGroup, [dataFactory.newLayout(userGroup.groupId, "home", "", "33,")])}

<#assign groupIds = 1..maxGroupCount>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

${sampleSQLBuilder.insertUser(groupIds, roleIds, user)}

<#list groupIds as groupId>
	<#assign blogsStatsUser = dataFactory.newBlogsStatsUser(groupId, user.userId)>

	insert into BlogsStatsUser (statsUserId, groupId, companyId, userId) values (${counter.get()}, ${blogsStatsUser.groupId}, ${companyId}, ${blogsStatsUser.userId});

	<#assign mbStatsUser = dataFactory.newMBStatsUser(groupId, user.userId)>

	insert into MBStatsUser (statsUserId, groupId, userId) values (${counter.get()}, ${mbStatsUser.groupId}, ${mbStatsUser.userId});
</#list>