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

package com.liferay.translator.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(category = "other")
@Meta.OCD(
	id = "com.liferay.translator.web.configuration.TranslatorConfiguration",
	localization = "content/Language", name = "translator.configuration.name"
)
public interface TranslatorConfiguration {

	public static final String TRANSLATOR_TRANSLATION =
		"TRANSLATOR_TRANSLATION";

	@Meta.AD(
		deflt = "ar|bg|ca|cs|da|de|el|en|es|et|fi|fr|hi_IN|ht|hu|in|it|iw|ja|ko|lt|lv|mww|nb|nl|pl|pt_PT|ro|ru|sk|sl|sv|th|tr|uk|vi|zh_CN|zh_TW",
		id = "language.ids", required = false
	)
	public String[] languageIds();

	@Meta.AD(deflt = "en_es", id = "translation.id", required = false)
	public String translationId();

}