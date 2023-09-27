/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.snapshot;

import com.liferay.info.item.InfoItemFieldValues;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class TranslationSnapshot {

	public TranslationSnapshot(
		InfoItemFieldValues infoItemFieldValues, Locale sourceLocale,
		Locale targetLocale) {

		_infoItemFieldValues = infoItemFieldValues;
		_sourceLocale = sourceLocale;
		_targetLocale = targetLocale;
	}

	public InfoItemFieldValues getInfoItemFieldValues() {
		return _infoItemFieldValues;
	}

	public Locale getSourceLocale() {
		return _sourceLocale;
	}

	public Locale getTargetLocale() {
		return _targetLocale;
	}

	private final InfoItemFieldValues _infoItemFieldValues;
	private final Locale _sourceLocale;
	private final Locale _targetLocale;

}