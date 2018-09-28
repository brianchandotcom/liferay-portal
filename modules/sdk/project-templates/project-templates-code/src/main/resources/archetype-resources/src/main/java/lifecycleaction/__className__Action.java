package ${package}.${template};

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

/**
 * @author${author}
 */
@Component(
	immediate = true, property = "key=login.events.pre",
	service = LifecycleAction.class
)
public class ${className}Action implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {

		_log.log(
			LogService.LOG_INFO,
			"${className}Action login.events.pre=" + lifecycleEvent);
	}

	@Reference
	private LogService _log;

}