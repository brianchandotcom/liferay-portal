package ${entity.UADPackagePath}.uad.anonymizer;

import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(service = UADAnonymizer.class)
public class ${entity.name}UADAnonymizer extends Base${entity.name}UADAnonymizer {
}