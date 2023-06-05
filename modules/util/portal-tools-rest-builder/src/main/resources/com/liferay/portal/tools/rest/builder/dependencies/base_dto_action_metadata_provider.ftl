package ${configYAML.apiPackagePath}.internal.dto.${escapedVersion}.action.metadata;

import ${configYAML.apiPackagePath}.internal.resource.${escapedVersion}.${schemaName}ResourceImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.vulcan.action.ActionInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ${configYAML.author}
 * @generated
 */
public abstract class Base${schemaName}DTOActionMetadataProvider {
	<#assign
		actionPropertyNames = ["delete", "get", "replace", "update"]
		javaMethodSignatures = freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName)
	/>

	public Base${schemaName}DTOActionMetadataProvider() {
		<#list actionPropertyNames as actionPropertyName>
			_actionInfoMap.put("${actionPropertyName}", new ActionInfo(get${actionPropertyName?cap_first}ActionKey(), ${schemaName}ResourceImpl.class, get${actionPropertyName?cap_first}ResourceMethodName()));
		</#list>
	}

	public final ActionInfo getActionInfo(String actionName) {
		return _actionInfoMap.get(actionName);
	}

	public final Set<String> getActionNames() {
		return _actionInfoMap.keySet();
	}

	public abstract String getPermissionName();

	<#list actionPropertyNames as actionPropertyName>
		<#assign actionKey = freeMarkerTool.getActionKey(actionPropertyName)!"" />

		protected String get${actionPropertyName?cap_first}ActionKey() {
			return ActionKeys.${actionKey!};
		}

		<#assign actionResourceName = freeMarkerTool.getActionResourceName(javaMethodSignatures, actionPropertyName)!"" />

		<#if actionResourceName?has_content>
			protected String get${actionPropertyName?cap_first}ResourceMethodName() {
				return "${actionResourceName!}";
			}
		<#else>
			protected abstract String get${actionPropertyName?cap_first}ResourceMethodName();
		</#if>
	</#list>

	protected final void registerActionInfo(String actionName, ActionInfo actionInfo) {
		_actionInfoMap.put(actionName, actionInfo);
	}

	private final Map<String, ActionInfo> _actionInfoMap = new HashMap<>();
}