package ${package}.${className}GlobalStartupAction;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;

/**
 * @author ${author}
 */
public class ${className}StartupAction extends SimpleAction {
	
	@Override
	public void run(String[] arg0) throws ActionException {
		System.out.println("## My custom Startup Action");
	}

}