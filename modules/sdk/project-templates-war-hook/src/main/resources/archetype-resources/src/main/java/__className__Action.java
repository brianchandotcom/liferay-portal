package ${package}.${className}LoginPreAction;

import com.liferay.portal.kernel.events.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ${author}
 */
public class ${className}LoginPreAction extends Action {
	
	@Override
    public void run(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("## My custom login action");
    }
	
}