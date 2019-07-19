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

package com.liferay.asset.info.display.contributor.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.info.display.contributor.field.ExpandoInfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Pavel Savinov
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 *             com.liferay.info.display.field.ExpandoInfoDisplayFieldProvider
 */
@Deprecated
public class ExpandoInfoDisplayContributorFieldUtil {

	public static List<InfoDisplayContributorField>
		getInfoDisplayContributorFields(String className) {

		List<InfoDisplayContributorField> infoDisplayContributorFields =
			new ArrayList<>();

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			CompanyThreadLocal.getCompanyId(), className, 0L);

		Enumeration<String> attributeNames = expandoBridge.getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();

			infoDisplayContributorFields.add(
				new ExpandoInfoDisplayContributorField(
					attributeName, expandoBridge));
		}

		return infoDisplayContributorFields;
	}

}