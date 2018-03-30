package ${packagePath}.uad.display;

import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import ${packagePath}.uad.entity.${entity.name}UADEntity;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
 * @generated
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}},
	service = UADEntityDisplay.class
)
public class ${entity.name}UADEntityDisplay implements UADEntityDisplay {

	public String getApplicationName() {
		return ${portletShortName}UADConstants.UAD_ENTITY_SET_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _${entity.varName}UADEntityDisplayHelper.getDisplayFieldNames();
	}

	@Override
	public String getEditURL(UADEntity uadEntity, LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse) throws Exception {
		${entity.name}UADEntity ${entity.varName}UADEntity = (${entity.name}UADEntity)uadEntity;

		return _${entity.varName}UADEntityDisplayHelper.get${entity.name}EditURL(${entity.varName}UADEntity.get${entity.name}(), liferayPortletRequest, liferayPortletResponse);
	}

	public String getKey() {
		return ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName};
	}

	@Override
	public Map<String, Object> getUADEntityNonanonymizableFieldValues(UADEntity uadEntity) {
		${entity.name}UADEntity ${entity.varName}UADEntity = (${entity.name}UADEntity)uadEntity;

		return _${entity.varName}UADEntityDisplayHelper.getUADEntityNonanonymizableFieldValues(${entity.varName}UADEntity.get${entity.name}());
	}

	@Override
	public String getUADEntityTypeDescription() {
		return "${entity.UADEntityTypeDescription}";
	}

	@Override
	public String getUADEntityTypeName() {
		return "${entity.name}";
	}

	@Reference
	private ${entity.name}UADEntityDisplayHelper _${entity.varName}UADEntityDisplayHelper;

	@Reference(
		target = "(model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName} + ")"
	)
	private UADEntityAnonymizer _uadEntityAnonymizer;

}