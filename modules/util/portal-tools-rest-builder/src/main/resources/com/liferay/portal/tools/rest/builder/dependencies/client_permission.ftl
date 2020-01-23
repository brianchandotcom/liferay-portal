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
public class Permission {

	public static Permission toDTO(String json) {
		PermissionJSONParser<Permission> permissionJSONParser = new PermissionJSONParser();

		return permissionJSONParser.parseToDTO(json);
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (_actionIds != null) {
			sb.append("\"actionIds\": [");

			for (int i = 0; i < _actionIds.length; i++) {
				sb.append("\"");
				sb.append(_actionIds[i]);
				sb.append("\"");

				if ((i + 1) < _actionIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (_roleName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleName\": \"");
			sb.append(_roleName);
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private String[] _actionIds;
	private String _roleName;

	private static class PermissionJSONParser<T> extends BaseJSONParser<Permission> {

		@Override
		protected Permission createDTO() {
			return new Permission();
		}

		@Override
		protected Permission[] createDTOArray(int size) {
			return new Permission[size];
		}

		@Override
		protected void setField(Permission permission, String jsonParserFieldName, Object jsonParserFieldValue) {
			if (Objects.equals(jsonParserFieldName, "actionIds")) {
				if (jsonParserFieldValue != null) {
					permission.setActionIds((String[])jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				if (jsonParserFieldValue != null) {
					permission.setRoleName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException("Unsupported field name " + jsonParserFieldName);
			}
		}
	}

}