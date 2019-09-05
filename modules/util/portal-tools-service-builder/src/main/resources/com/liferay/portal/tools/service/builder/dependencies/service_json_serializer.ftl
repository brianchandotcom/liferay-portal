package ${packagePath}.service.http;

import ${apiPackagePath}.model.${entity.name};

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * @author ${author}
 * @generated
 */
public class ${entity.name}JSONSerializer {

	public static JSONObject toJSONObject(${entity.name} model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		<#list entity.regularEntityColumns as entityColumn>
			<#if stringUtil.equals(entityColumn.type, "Date")>
				Date ${entityColumn.name} = model.get${entityColumn.methodName}();

				String ${entityColumn.name}JSON = "";

				if (${entityColumn.name} != null) {
					${entityColumn.name}JSON = String.valueOf(${entityColumn.name}.getTime());
				}

				jsonObject.put("${entityColumn.name}", ${entityColumn.name}JSON);
			<#else>
				jsonObject.put("${entityColumn.name}", model.get${entityColumn.methodName}());
			</#if>
		</#list>

		return jsonObject;
	}

	public static JSONArray toJSONArray(${apiPackagePath}.model.${entity.name}[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name} model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(${apiPackagePath}.model.${entity.name}[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name}[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(List<${apiPackagePath}.model.${entity.name}> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name} model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

}