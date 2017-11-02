package ${package};

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ${author}
 */
public class ${className}LoginPostAction extends Action {

	@Override
    public void run(HttpServletRequest request, HttpServletResponse response) {
		long userId = PortalUtil.getUserId(request);

		User user = UserLocalServiceUtil.fetchUser(userId);

        System.out.println(user.getFirstName() + " has logged in.");
    }

}