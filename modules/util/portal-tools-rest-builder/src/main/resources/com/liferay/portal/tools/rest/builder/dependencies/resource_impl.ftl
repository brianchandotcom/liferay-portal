package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author ${configYAML.author}
 */
@Component(
	scope = ServiceScope.PROTOTYPE,
	service = AopService.class
)
public class ${schemaName}ResourceImpl extends Base${schemaName}ResourceImpl {
}