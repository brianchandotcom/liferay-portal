<#assign groupIds = dataFactory.getNewUserGroupIds(group.groupId)>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

<#if (maxUserCount > 0)>
	<#list 1..maxUserCount as userCount>
		<#assign user = dataFactory.newUser(userCount)>

		<#assign userGroup = dataFactory.newGroup(user)>

		<#assign layout = dataFactory.newLayout(userGroup.groupId, "home", "", "33,")>

		<@insertLayout _layout = layout />

		<@insertGroup _group = userGroup _publicPageCount = 1 />

		${sampleSQLBuilder.insertUser(groupIds, roleIds, user)}
	</#list>
</#if>