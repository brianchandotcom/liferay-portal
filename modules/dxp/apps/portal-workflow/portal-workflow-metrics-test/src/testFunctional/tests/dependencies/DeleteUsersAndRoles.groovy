//Delete users and roles created

import com.liferay.portal.kernel.dao.orm.*
import com.liferay.portal.kernel.model.*
import com.liferay.portal.kernel.service.*
import com.liferay.portal.kernel.test.util.*
import com.liferay.portal.kernel.util.*
import com.liferay.portal.kernel.workflow.*
import java.util.*

companyId = com.liferay.portal.kernel.util.PortalUtil.getCompanyId(actionRequest)
List<User> users = com.liferay.portal.kernel.service.UserLocalServiceUtil.getUsers(0,100);
List<Role> roles = com.liferay.portal.kernel.service.RoleLocalServiceUtil.getRoles(companyId)

//Delete Users
for (User user : users){
	if (user.getEmailAddress().contains("user")) {
		com.liferay.portal.kernel.service.UserLocalServiceUtil.deleteUser(user.getUserId())
	}
}

//Delete Roles
for (Role role : roles){
	if (role.getName().contains("Role")) {
		com.liferay.portal.kernel.service.RoleLocalServiceUtil.deleteRole(role.getRoleId())
	}
}