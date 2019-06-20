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

package com.liferay.message.boards.uad.anonymizer;

import com.liferay.message.boards.exception.RequiredMessageException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = UADAnonymizer.class)
public class MBMessageUADAnonymizer extends BaseMBMessageUADAnonymizer {

	@Override
	public Map<Class, String> getExceptionMessageMap(Locale locale) {
		Map<Class, String> exceptionMessageMap = new HashMap<>();

		exceptionMessageMap.put(
			RequiredMessageException.class,
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					locale, BaseMBMessageUADAnonymizer.class),
				"root-messages-with-multiple-replies-cannot-be-deleted.-" +
					"delete-the-thread-instead"));

		return exceptionMessageMap;
	}

}