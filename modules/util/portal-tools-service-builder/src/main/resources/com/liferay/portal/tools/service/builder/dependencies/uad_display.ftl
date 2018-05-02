package ${packagePath}.uad.display;

import ${packagePath}.uad.constants.${portletShortName}UADConstants;

import com.liferay.user.associated.data.display.UADDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}},
	service = UADDisplay.class
)
public class ${entity.name}UADDisplay extends Base${entity.name}UADDisplay {
}