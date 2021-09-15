//Assign ALL open tasks to available users. The script assigns 1 task per user until the tasks are finished.

import com.liferay.petra.string.*
import com.liferay.portal.kernel.dao.orm.*
import com.liferay.portal.kernel.model.*
import com.liferay.portal.kernel.service.*
import com.liferay.portal.kernel.test.util.*
import com.liferay.portal.kernel.util.*
import com.liferay.portal.kernel.workflow.*
import com.liferay.blogs.service.*
import java.util.*

//Excluding from the count the existing users
companyId = com.liferay.portal.kernel.util.PortalUtil.getCompanyId(actionRequest)
mainUserId = com.liferay.portal.kernel.util.PortalUtil.getUserId(actionRequest)
List<User> users = com.liferay.portal.kernel.service.UserLocalServiceUtil.getUsers(0,100);
List<WorkflowTask> workflowTasks = new ArrayList<>()
List<User> activeUsers = new ArrayList<User>();

workflowTasks.addAll(WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(companyId, mainUserId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null))

for (User user : users){
	if (user.getEmailAddress().contains("user")) {
		activeUsers.add(user);
	}
}

userCount = 0;

for (WorkflowTask workflowTask : workflowTasks) {
	if (userCount==activeUsers.size()){
		userCount=0;
	}

	activeUser = activeUsers.get(userCount);
	userId = activeUser.getUserId();

	WorkflowTaskManagerUtil.assignWorkflowTaskToUser(companyId, mainUserId, workflowTask.getWorkflowTaskId(), userId, StringPool.BLANK, null, null);

	userCount++;

}