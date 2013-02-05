<#if (maxUserCount > 0)>
	<#assign groupIds = dataFactory.addUserToGroupIds(groupId)>
	<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

	<#list 1..maxUserCount as userCount>
		<#assign user = dataFactory.addUser(userCount)>

		<@insertUser _user = user _roleIds = roleIds _groupIds = groupIds/>
	</#list>
</#if>