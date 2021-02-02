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

package com.liferay.headless.admin.content.internal.dto.v1_0.util;

import com.liferay.headless.admin.content.dto.v1_0.Status;
import com.liferay.headless.admin.content.dto.v1_0.Version;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Luis Miguel Barcos
 */
public class VersionUtil {

	public static Version toVersion(
		long groupId, int statusCode, double version) {

		String statusLabel = WorkflowConstants.getStatusLabel(statusCode);

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			groupId);

		Stream<Locale> localesStream = availableLocales.stream();

		return new Version() {
			{
				number = version;
				status = new Status() {
					{
						code = statusCode;
						label = statusLabel;
						label_i18n = localesStream.collect(
							Collectors.toMap(
								LocaleUtil::toBCP47LanguageId,
								locale -> LanguageUtil.get(
									locale, statusLabel)));
					}
				};
			}
		};
	}

}