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

package com.liferay.user.associated.data.display;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;
import java.util.Map;

/**
 * @author William Newbury
 */
public abstract class BaseUADEntityDisplay implements UADEntityDisplay {

	@Override
	public abstract String getEditURL(
			UADEntity uadEntity, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	@Override
	public String getEntityNonAnonymizableFieldValues(UADEntity uadEntity)
		throws PortalException {

		List<String> entityTypeNonAnonymizableFieldNamesList =
			getEntityTypeNonAnonymizableFieldNamesList();

		if (entityTypeNonAnonymizableFieldNamesList == null) {
			return "";
		}

		Map<String, Object> nonAnonymizableFieldValuesMap =
			uadEntity.getEntityNonAnonymizableFieldValues(
				entityTypeNonAnonymizableFieldNamesList);

		if (nonAnonymizableFieldValuesMap == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			(nonAnonymizableFieldValuesMap.size() * 4) - 1);

		for (Map.Entry<String, Object> entry :
				nonAnonymizableFieldValuesMap.entrySet()) {

			sb.append(entry.getKey());
			sb.append(StringPool.COLON);
			sb.append(StringPool.SPACE);
			sb.append(String.valueOf(entry.getValue()));
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	@Override
	public String getEntityTypeNonAnonymizableFieldNames() {
		List<String> entityTypeNonAnonymizableFieldNamesList =
			getEntityTypeNonAnonymizableFieldNamesList();

		if (entityTypeNonAnonymizableFieldNamesList == null) {
			return "";
		}

		StringBundler sb = new StringBundler(
			(entityTypeNonAnonymizableFieldNamesList.size() * 2) - 1);

		for (String field : entityTypeNonAnonymizableFieldNamesList) {
			sb.append(field);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

}