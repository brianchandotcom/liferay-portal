package ${packagePath}.uad.display;

import ${apiPackagePath}.model.${entity.name};

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	service = ${entity.name}UADEntityDisplayHelper.class
)
public class ${entity.name}UADEntityDisplayHelper {

	/**
	 * Implement get${entity.name}EditURL() to enable editing ${entity.names} from the GDPR UI.
	 *
	 * <p>
	 * Editing ${entity.names} in the GDPR UI depends on generating valid edit URLs. Implement get${entity.name}EditURL() such that it returns a valid edit URL for the specified ${entity.name}.
	 * </p>
	 *
	 */
	public String get${entity.name}EditURL(${entity.name} ${entity.varName}, LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse) {
		return StringPool.BLANK;
	}

}