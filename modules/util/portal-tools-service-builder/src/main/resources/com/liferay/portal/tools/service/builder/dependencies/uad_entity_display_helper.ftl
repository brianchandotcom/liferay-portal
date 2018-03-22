package ${packagePath}.uad.display;

import ${apiPackagePath}.model.${entity.name};

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
	 * Returns an ordered string array of the fields' names to be displayed.
	 * Each field name corresponds to a table column based on the order they are
	 * specified.
	 *
	 * @return the array of field names to display
	 */
	public String[] getDisplayFieldNames() {
		return new String[]{<#list entity.UADNonanonymizableEntityColumns as uadNonanonymizableEntityColumn>"${uadNonanonymizableEntityColumn.name}"<#sep>, </#sep></#list>};
	}

	/**
	 * Implement get${entity.name}EditURL() to enable editing ${entity.names} from the GDPR UI.
	 *
	 * <p>
	 * Editing ${entity.names} in the GDPR UI depends on generating valid edit URLs. Implement get${entity.name}EditURL() such that it returns a valid edit URL for the specified ${entity.name}.
	 * </p>
	 *
	 */
	public String get${entity.name}EditURL(${entity.name} ${entity.varName}, LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse) {
		return "";
	}

	@Override
	public Map<String, Object> getUADEntityNonanonymizableFieldValues(${entity.name} ${entity.varName}) {
		Map<String, Object> uadEntityNonanonymizableFieldValues = new HashMap<String, Object>();

		<#list entity.UADNonanonymizableEntityColumns as uadNonanonymizableEntityColumn>
			uadEntityNonanonymizableFieldValues.put("${uadNonanonymizableEntityColumn.name}", ${entity.varName}.get${textFormatter.format(uadNonanonymizableEntityColumn.name, 6)}());
		</#list>

		return uadEntityNonanonymizableFieldValues;
	}

}