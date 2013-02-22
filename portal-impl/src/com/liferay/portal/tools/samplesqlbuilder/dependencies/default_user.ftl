<#assign contact = dataFactory.addContact("", "")>
<#assign user = dataFactory.addUser(true, "")>

${sampleSQLBuilder.insertUser(contact, null, null, user)}

<#assign contact = dataFactory.addContact("Test", "Test")>
<#assign user = dataFactory.addUser(false, "test")>

<#assign userGroup = dataFactory.addGroup(counter.get(), dataFactory.userClassNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName, false)>

${sampleSQLBuilder.insertGroup(userGroup, [])}

<#assign groupIds = [dataFactory.guestGroup.groupId]>
<#assign roleIds = [dataFactory.administratorRole.roleId]>

${sampleSQLBuilder.insertUser(contact, groupIds, roleIds, user)}

<#assign contact = dataFactory.addContact("test0", "test0")>
<#assign user = dataFactory.addUser(false, "test0")>
<#assign sampleUserId = user.userId>

<#assign userGroup = dataFactory.addGroup(counter.get(), dataFactory.userClassNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName, false)>

${sampleSQLBuilder.insertGroup(userGroup, [dataFactory.addLayout(1, "Home", "/home", "", "33,")])}

<#assign groupIds = 1..maxGroupCount>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

${sampleSQLBuilder.insertUser(contact, groupIds, roleIds, user)}

<#list groupIds as groupId>
	<#assign blogsStatsUser = dataFactory.addBlogsStatsUser(groupId, user.userId)>

	insert into BlogsStatsUser (statsUserId, groupId, companyId, userId) values (${counter.get()}, ${blogsStatsUser.groupId}, ${companyId}, ${blogsStatsUser.userId});

	<#assign mbStatsUser = dataFactory.addMBStatsUser(groupId, user.userId)>

	insert into MBStatsUser (statsUserId, groupId, userId) values (${counter.get()}, ${mbStatsUser.groupId}, ${mbStatsUser.userId});
</#list>