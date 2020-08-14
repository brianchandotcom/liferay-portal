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

package com.liferay.translation.internal.info.field;

import com.liferay.info.field.InfoField;
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
				infoField.getInfoFieldType(), NumberInfoFieldType.INSTANCE)) {

			return true;
		}

		if (Objects.equals(
				infoField.getInfoFieldType(), TextInfoFieldType.INSTANCE)) {

			return true;
		}

		return false;
	}

}