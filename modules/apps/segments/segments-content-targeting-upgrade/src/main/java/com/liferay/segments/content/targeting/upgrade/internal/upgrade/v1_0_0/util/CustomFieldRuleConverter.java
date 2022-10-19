/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.segments.content.targeting.upgrade.internal.upgrade.v1_0_0.util;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				typeSettings);

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

	@Reference(target = "(segments.criteria.contributor.key=user)")
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}