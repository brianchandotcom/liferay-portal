package ${packagePath}.uad.entity;

import ${apiPackagePath}.model.${entity.name};
import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.associated.data.entity.BaseUADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.exception.UADEntityFieldNameException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ${author}
 * @generated
 */
public class ${entity.name}UADEntity extends BaseUADEntity {

	public ${entity.name}UADEntity(long userId, String uadEntityId, ${entity.name} ${entity.varName}) {
		super(userId, uadEntityId, ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName});

		_${entity.varName} = ${entity.varName};
	}

	public ${entity.name} get${entity.name}() {
		return _${entity.varName};
	}

	<#if entity.UADNonanonymizableEntityColumns?has_content>
		@Override
		public Map<String, Object> getEntityNonanonymizableFieldValues() {
			Map<String, Object> entityNonanonymizableFieldValues = new HashMap<String, Object>();

			<#list entity.UADNonanonymizableEntityColumns as uadNonanonymizableEntityColumn>
				entityNonanonymizableFieldValues.put("${uadNonanonymizableEntityColumn.name}", _${entity.varName}.get${textFormatter.format(uadNonanonymizableEntityColumn.name, 6)}());
			</#list>

			return entityNonanonymizableFieldValues;
		}
	</#if>

	private final ${entity.name} _${entity.varName};

}