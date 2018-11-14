package com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
 
public class UpgradeDDMFormParagraphFields extends UpgradeProcess {
 
	public UpgradeDDMFormParagraphFields(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select structureId, definition from DDMStructure where " +
					"classNameId = ?");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement ps3 = connection.prepareStatement(
				"select structureVersionId, definition from " +
					"DDMStructureVersion where structureId = ?");
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			ps.setLong(1, PortalUtil.getClassNameId(DDMFormInstance.class));

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString("definition");

					ps2.setString(1, updateFieldsToLocalizable(definition));

					long structureId = rs.getLong("structureId");

					ps2.setLong(2, structureId);

					ps2.addBatch();

					ps3.setLong(1, structureId);

					try (ResultSet rs2 = ps3.executeQuery()) {
						while (rs2.next()) {
							definition = rs2.getString("definition");
	
							ps4.setString(
								1, updateFieldsToLocalizable(definition));
	
							long structureVersionId = rs2.getLong(
								"structureVersionId");
	
							ps4.setLong(2, structureVersionId);
	
							ps4.addBatch();
						}
					}
				}
			}

			ps2.executeBatch();

			ps4.executeBatch();
		}
	}
 
	protected void updateFieldsToLocalizable(
		JSONArray fieldsJSONArray, JSONArray availableLanguageIds) {

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			if(fieldJSONObject.getString("type").equals("paragraph")) {
				String originalValue = fieldJSONObject.getString("text");

				Map<String, String> localizedValue =
					new HashMap<String, String>();

				for (int j = 0; j < availableLanguageIds.length(); j++) {
					localizedValue.put(
						availableLanguageIds.getString(j), originalValue);
				}

				fieldJSONObject.put("text", localizedValue);

				JSONArray nestedFieldsJSONArray = fieldJSONObject.getJSONArray(
					"nestedFields");

				if (nestedFieldsJSONArray != null) {
					updateFieldsToLocalizable(
						nestedFieldsJSONArray, availableLanguageIds);
				}
			}
		}
	}

	protected String updateFieldsToLocalizable(String definition)
		throws PortalException {

		JSONObject ddmFormJSONObject = _jsonFactory.createJSONObject(
			definition);

		JSONArray availableLanguageIds = ddmFormJSONObject.getJSONArray(
			"availableLanguageIds");

		JSONArray fieldsJSONArray = ddmFormJSONObject.getJSONArray("fields");

		updateFieldsToLocalizable(fieldsJSONArray, availableLanguageIds);

		return ddmFormJSONObject.toJSONString();
	}

	private final JSONFactory _jsonFactory;

}
