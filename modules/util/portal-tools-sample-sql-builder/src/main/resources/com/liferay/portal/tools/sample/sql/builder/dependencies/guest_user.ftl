<#-- Guest user -->

<#assign guestUserModel = dataFactory.newGuestUserModel() />

<@insertGroup _groupModel = dataFactory.newGroupModel(guestUserModel) />

<#assign
	groupIds = [guestGroupModel.groupId]
	roleIds = [dataFactory.guestRoleModel.roleId]
/>

<@insertUser
	_groupIds = groupIds
	_roleIds = roleIds
	_userModel = guestUserModel
/>

<#-- Default admin user -->

<#assign defaultAdminUserModel = dataFactory.newDefaultAdminUserModel() />

<@insertGroup _groupModel = dataFactory.newGroupModel(defaultAdminUserModel) />

<#assign
	groupIds = [guestGroupModel.groupId]
	roleIds = [dataFactory.administratorRoleModel.roleId]
/>

<@insertUser
	_groupIds = groupIds
	_roleIds = roleIds
	_userModel = defaultAdminUserModel
/>