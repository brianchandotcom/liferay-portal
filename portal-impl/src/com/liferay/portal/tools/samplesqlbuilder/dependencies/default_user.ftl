<#-- Default user -->

${sampleSQLBuilder.insertUser(null, null, dataFactory.defaultUser)}

<#-- Guest user -->

<#assign user = dataFactory.guestUser>

<#assign userGroup = dataFactory.addGroup(counter.get(), dataFactory.userClassNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName, false)>

${sampleSQLBuilder.insertGroup(userGroup, [])}

<#assign groupIds = [dataFactory.guestGroup.groupId]>
<#assign roleIds = [dataFactory.administratorRole.roleId]>

${sampleSQLBuilder.insertUser(groupIds, roleIds, user)}

<#-- Sample user -->

<#assign user = dataFactory.sampleUser>

<#assign sampleUserId = user.userId>

<#assign userGroup = dataFactory.addGroup(counter.get(), dataFactory.userClassNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName, false)>

${sampleSQLBuilder.insertGroup(userGroup, [dataFactory.addLayout(1, "Home", "/home", "", "33,")])}

<#assign groupIds = 1..maxGroupCount>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

${sampleSQLBuilder.insertUser(groupIds, roleIds, user)}

<#list groupIds as groupId>
	<#assign blogsStatsUser = dataFactory.addBlogsStatsUser(groupId, user.userId)>

	insert into BlogsStatsUser (statsUserId, groupId, companyId, userId) values (${counter.get()}, ${blogsStatsUser.groupId}, ${companyId}, ${blogsStatsUser.userId});

	<#assign mbStatsUser = dataFactory.addMBStatsUser(groupId, user.userId)>

	insert into MBStatsUser (statsUserId, groupId, userId) values (${counter.get()}, ${mbStatsUser.groupId}, ${mbStatsUser.userId});
</#list>