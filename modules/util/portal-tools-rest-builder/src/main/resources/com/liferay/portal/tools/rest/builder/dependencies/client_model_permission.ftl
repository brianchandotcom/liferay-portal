package ${configYAML.apiPackagePath}.client.permission;

import ${configYAML.apiPackagePath}.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
* @author ${configYAML.author}
* @generated
*/
@Generated("")
public class ModelPermission {

	public String[] getActionIds() {
		return _actionIds;
	}

	public String getRoleName() {
		return _roleName;
	}

	public void setActionIds(String[] actionIds) {
		this._actionIds = actionIds;
	}

	public void setRoleName(String roleName) {
		this._roleName = roleName;
	}

	private String[] _actionIds;
	private String _roleName;

	private static class ModelPermissionJSONParser<T> extends BaseJSONParser<ModelPermission> {

		@Override
		protected ModelPermission createDTO() {
			return new ModelPermission();
		}

		@Override
		protected ModelPermission[] createDTOArray(int size) {
			return new ModelPermission[size];
		}

		@Override
		protected void setField(ModelPermission modelPermission, String jsonParserFieldName, Object jsonParserFieldValue) {
			if (Objects.equals(jsonParserFieldName, "actionIds")) {
				if (jsonParserFieldValue != null) {
					modelPermission.setActionIds((String[])jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				if (jsonParserFieldValue != null) {
					modelPermission.setRoleName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException("Unsupported field name " + jsonParserFieldName);
			}
		}
	}
}