//Complete ALL open tasks from all users

import com.liferay.petra.string.*
import com.liferay.portal.kernel.dao.orm.*
import com.liferay.portal.kernel.model.*
import com.liferay.portal.kernel.service.*
import com.liferay.portal.kernel.test.util.*
import com.liferay.portal.kernel.util.*
import com.liferay.portal.kernel.workflow.*
import com.liferay.blogs.service.*
import java.util.*

companyId = com.liferay.portal.kernel.util.PortalUtil.getCompanyId(actionRequest)
List<User> users = com.liferay.portal.kernel.service.UserLocalServiceUtil.getUsers(0,50);
List<User> activeUsers = new ArrayList<User>();

for (User user : users){
	if (user.getEmailAddress().contains("user")) {
		activeUsers.add(user);
	}
}

userCount = 0;

for (User activeUser : activeUsers) {
	activeUserId = activeUser.getUserId();

	List<WorkflowTask> workflowTasks = new ArrayList<>()
	workflowTasks.addAll(WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, activeUserId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null))

	for (WorkflowTask workflowTask : workflowTasks) {
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, activeUserId, workflowTask.getWorkflowTaskId(), Constants.APPROVE, StringPool.BLANK, null);
	}
}