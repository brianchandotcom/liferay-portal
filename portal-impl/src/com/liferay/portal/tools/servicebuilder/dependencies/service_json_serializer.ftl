package ${packagePath}.service.http;

import ${packagePath}.model.${entity.name};

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;
import java.util.List;

/**
 * @author ${author}
 * @generated
 */
public class ${entity.name}JSONSerializer {

	public static JSONObject toJSONObject(${entity.name} model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		<#list entity.regularColList as column>
			<#if column.type == "Date">
				Date ${column.name} = model.get${column.methodName}();

				String ${column.name}JSON = StringPool.BLANK;

				if (${column.name} != null) {
					${column.name}JSON = String.valueOf(${column.name}.getTime());
				}

				jsonObject.put("${column.name}", ${column.name}JSON);
			<#else>
				jsonObject.put("${column.name}", model.get${column.methodName}());
			</#if>
		</#list>

		return jsonObject;
	}

	public static JSONArray toJSONArray(${packagePath}.model.${entity.name}[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name} model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(${packagePath}.model.${entity.name}[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name}[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(List<${packagePath}.model.${entity.name}> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name} model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

}