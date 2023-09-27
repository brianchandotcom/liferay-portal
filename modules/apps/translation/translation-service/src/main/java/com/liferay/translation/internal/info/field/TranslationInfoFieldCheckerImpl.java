/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.info.field;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = TranslationInfoFieldChecker.class)
public class TranslationInfoFieldCheckerImpl
	implements TranslationInfoFieldChecker {

	@Override
	public boolean isTranslatable(InfoField infoField) {
		if (!infoField.isLocalizable()) {
			return false;
		}

		if (Objects.equals(
				infoField.getInfoFieldType(), HTMLInfoFieldType.INSTANCE) ||
			Objects.equals(
				infoField.getInfoFieldType(), NumberInfoFieldType.INSTANCE) ||
			Objects.equals(
				infoField.getInfoFieldType(), TextInfoFieldType.INSTANCE)) {

			return true;
		}

		return false;
	}

}