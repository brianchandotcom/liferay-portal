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

package com.liferay.asset.list.web.internal.util.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.text.Collator;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class ClassNameModelResourceComparator extends OrderByComparator<Long> {

	public ClassNameModelResourceComparator(boolean ascending) {
		this(ascending, LocaleUtil.getDefault());
	}

	public ClassNameModelResourceComparator(boolean ascending, Locale locale) {
		_ascending = ascending;

		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(Long classNameId1, Long classNameId2) {
		String modelResource1 = StringPool.BLANK;
		String modelResource2 = StringPool.BLANK;

		try {
			ClassName className1 = ClassNameLocalServiceUtil.getClassName(
				classNameId1);
			ClassName className2 = ClassNameLocalServiceUtil.getClassName(
				classNameId2);

			modelResource1 = ResourceActionsUtil.getModelResource(
				_locale, className1.getValue());
			modelResource2 = ResourceActionsUtil.getModelResource(
				_locale, className2.getValue());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		int value = _collator.compare(modelResource1, modelResource2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassNameModelResourceComparator.class);

	private final boolean _ascending;
	private final Collator _collator;
	private final Locale _locale;

}