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
public class Permissions {

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

	private static class PermissionsJSONParser<T> extends BaseJSONParser<Permissions> {

		@Override
		protected Permissions createDTO() {
			return new Permissions();
		}

		@Override
		protected Permissions[] createDTOArray(int size) {
			return new Permissions[size];
		}

		@Override
		protected void setField(Permissions permissions, String jsonParserFieldName, Object jsonParserFieldValue) {
			if (Objects.equals(jsonParserFieldName, "actionIds")) {
				if (jsonParserFieldValue != null) {
					permissions.setActionIds((String[])jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				if (jsonParserFieldValue != null) {
					permissions.setRoleName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException("Unsupported field name " + jsonParserFieldName);
			}
		}
	}
}