package ${configYAML.apiPackagePath}.internal.dto.${escapedVersion}.action;

import ${configYAML.apiPackagePath}.internal.dto.${escapedVersion}.action.metadata.${schemaName}DTOActionMetadataProvider;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.portal.vulcan.action.ActionInfo;
import com.liferay.portal.vulcan.action.DTOActionProvider;
import com.liferay.portal.vulcan.util.ActionUtil;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(
	<#if configYAML.liferayEnterpriseApp>enabled = false,</#if>
	property = {
		"dto.class.name=${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName}",
	},
	service = DTOActionProvider.class
)
@Generated("")
public class ${schemaName}DTOActionProvider implements DTOActionProvider {

	@Override
	public Map<String, Map<String, String>> getActions(long groupId, long primaryKey, UriInfo uriInfo, long userId) {

		Map<String, Map<String, String>> actions = new HashMap<>();

		${schemaName}DTOActionMetadataProvider ${schemaVarName}DTOActionMetadataProvider = new ${schemaName}DTOActionMetadataProvider();

		for (String actionName : ${schemaVarName}DTOActionMetadataProvider.getActionNames()) {
			ActionInfo actionInfo = ${schemaVarName}DTOActionMetadataProvider.getActionInfo(actionName);

			if (actionInfo == null || actionInfo.getActionKey() == null || actionInfo.getResourceMethodName() == null) {
				continue;
			}

			actions.put(actionName, ActionUtil.addAction(actionInfo.getActionKey(), actionInfo.getResourceClass(), primaryKey, actionInfo.getResourceMethodName(), _scopeChecker,userId, ${schemaVarName}DTOActionMetadataProvider.getPermissionName(),groupId, uriInfo));
		}

		return actions;
	}

	@Override
	public Map<String, ActionInfo> getActionInfoMap() throws Exception {
		Map<String, ActionInfo> actionInfoMap = new HashMap<>();

		${schemaName}DTOActionMetadataProvider ${schemaVarName}DTOActionMetadataProvider = new ${schemaName}DTOActionMetadataProvider();

		for (String actionName : ${schemaVarName}DTOActionMetadataProvider.getActionNames()) {
			actionInfoMap.put(actionName, ${schemaVarName}DTOActionMetadataProvider.getActionInfo(actionName));
		}

		return actionInfoMap;
	}

	@Reference
	private ScopeChecker _scopeChecker;
}