package ${packagePath}.uad.exporter;

import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(immediate = true,service = UADExporter.class)
public class ${entity.name}UADExporter extends Base${entity.name}UADExporter {
}