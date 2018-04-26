package ${packagePath}.uad.exporter;

import ${packagePath}.uad.constants.${portletShortName}UADConstants;

import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 * @generated
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}},
	service = UADExporter.class
)
public class ${entity.name}UADExporter extends Base${entity.name}UADExporter {
}