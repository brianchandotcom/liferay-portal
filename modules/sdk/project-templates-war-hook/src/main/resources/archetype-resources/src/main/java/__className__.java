package ${package}.${className};

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author ${author}
 */
public class ${className} extends SimpleAction {
	
	@Override
	public void run(String[] arg0) throws ActionException {
		System.out.println("Currently logged in as: " + System.getProperty("user.name"));
	}
	
}