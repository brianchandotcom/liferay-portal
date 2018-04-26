package ${packagePath}.uad.exporter;

import ${apiPackagePath}.model.${entity.name};
import ${apiPackagePath}.service.${entity.name}LocalService;
import ${packagePath}.uad.constants.${portletShortName}UADConstants;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.user.associated.data.exporter.DynamicQueryUADExporter;
import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
 * @generated
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}},
	service = UADExporter.class
)
public class ${entity.name}UADExporter extends DynamicQueryUADExporter<${entity.name}> {

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _${entity.varName}LocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return ${portletShortName}UADConstants.USER_ID_FIELD_NAMES_${entity.constantName};
	}

	@Override
	protected String toXmlString(${entity.name} ${entity.varName}) {
		StringBundler sb = new StringBundler(${entity.UADEntityColumns?size * 3 + 4});

		sb.append("<model><model-name>");
		sb.append("${apiPackagePath}.model.${entity.name}");
		sb.append("</model-name>");

		<#list entity.UADEntityColumns as entityColumn>
			<#if !stringUtil.equals(entityColumn.type, "Blob") || !entityColumn.lazy>
				sb.append("<column><column-name>${entityColumn.name}</column-name><column-value><![CDATA[");
				sb.append(${entity.varName}.get${entityColumn.methodName}());
				sb.append("]]></column-value></column>");
			</#if>
		</#list>

		sb.append("</model>");

		return sb.toString();
	}

	@Reference
	private ${entity.name}LocalService _${entity.varName}LocalService;

}