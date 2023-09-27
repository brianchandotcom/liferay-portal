/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.content.targeting.upgrade.internal.upgrade.v1_0_0.util;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.normalizer.Normalizer;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "rule.converter.key=CustomFieldRule",
	service = RuleConverter.class
)
public class CustomFieldRuleConverter implements RuleConverter {

	@Override
	public void convert(
		long companyId, Criteria criteria, String typeSettings) {

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(typeSettings);

			String attributeName = jsonObject.getString("attributeName");
			String value = jsonObject.getString("value");

			if (Validator.isNull(attributeName) || Validator.isNull(value)) {
				return;
			}

			ExpandoTable expandoTable =
				_expandoTableLocalService.getDefaultTable(
					companyId, User.class.getName());

			ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
				expandoTable.getTableId(), attributeName);

			if (expandoColumn == null) {
				return;
			}

			String fieldName = _encodeName(expandoColumn);

			_userSegmentsCriteriaContributor.contribute(
				criteria,
				StringBundler.concat(
					"(customField/", fieldName, " eq '", value, "')"),
				Criteria.Conjunction.AND);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to convert custom field rule with type settings " +
					typeSettings,
				exception);
		}
	}

	private String _encodeName(ExpandoColumn expandoColumn) {
		return StringBundler.concat(
			StringPool.UNDERLINE, expandoColumn.getColumnId(),
			StringPool.UNDERLINE,
			Normalizer.normalizeIdentifier(expandoColumn.getName()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomFieldRuleConverter.class);

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(target = "(segments.criteria.contributor.key=user)")
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}