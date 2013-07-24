<#assign groupIds = dataFactory.getNewUserGroupIds(groupModel.groupId)>
<#assign roleIds = [dataFactory.administratorRoleModel.roleId, dataFactory.powerUserRoleModel.roleId, dataFactory.userRoleModel.roleId]>

<#if (dataFactory.maxUserCount > 0)>
	<#list 1..dataFactory.maxUserCount as userCount>
		<#assign userModel = dataFactory.newUserModel(userCount)>

		<#assign userGroupModel = dataFactory.newGroupModel(userModel)>

		<#assign layoutModel = dataFactory.newLayoutModel(userGroupModel.groupId, "home", "", "33,")>

		<@insertLayout
			_layoutModel = layoutModel
		/>

		<@insertGroup
			_groupModel = userGroupModel
			_publicPageCount = 1
		/>

		<@insertUser
			_groupIds = groupIds
			_roleIds = roleIds
			_userModel = userModel
		/>
	</#list>
</#if>