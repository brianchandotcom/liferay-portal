/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.util.comparator;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public class ClassTypeNameComparator
	implements Comparator<ClassType>, Serializable {

	public ClassTypeNameComparator(Locale locale) {
		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(ClassType classType1, ClassType classType2) {
		return _collator.compare(classType1.getName(), classType2.getName());
	}

	private final Collator _collator;

}