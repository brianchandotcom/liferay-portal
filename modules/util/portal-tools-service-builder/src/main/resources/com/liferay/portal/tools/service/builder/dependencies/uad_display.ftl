package ${packagePath}.uad.display;

import com.liferay.user.associated.data.display.UADDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(immediate = true,service = UADDisplay.class)
public class ${entity.name}UADDisplay extends Base${entity.name}UADDisplay {
}