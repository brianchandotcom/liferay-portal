package ${package}.${template};

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import ${modelClassPackage}.${modelClassName};

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

/**
 * @author ${author}
 */
@Component(immediate = true, service = ModelListener.class)
public class ${className}ModelListener extends BaseModelListener<${modelClassName}> {

	@Override
	public void onBeforeCreate(${modelClassName} model) throws ModelListenerException {
		model.setTitle(model.getTitle() + " modified");

		_log.log(LogService.LOG_INFO, "Modified title on ${modelClassName}");
	}

	@Reference
	private LogService _log;

}