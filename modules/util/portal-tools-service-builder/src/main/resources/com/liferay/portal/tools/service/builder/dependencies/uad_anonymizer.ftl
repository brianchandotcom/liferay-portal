package ${packagePath}.uad.anonymizer;

import ${packagePath}.uad.constants.${portletShortName}UADConstants;

import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}},
	service = UADAnonymizer.class
)
public class ${entity.name}UADAnonymizer extends Base${entity.name}UADAnonymizer {
}