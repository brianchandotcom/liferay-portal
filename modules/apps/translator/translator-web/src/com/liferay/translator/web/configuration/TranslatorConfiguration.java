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

/**
 * @author Raymond Augé
 * @author Peter Fellwock
 */
@Meta.OCD(
	id = "com.liferay.translator.web", localization = "content.Language"
)
public interface TranslatorConfiguration {

	public static final String TRANSLATOR_TRANSLATION =
		"TRANSLATOR_TRANSLATION";

	@Meta.AD(
		id = "translator.default.languages", deflt = "en_es", required = false
	)
	public String getTranslatorDefaultLanguages();

	@Meta.AD(
		id = "translator.languages", deflt = "ar,bg,ca,cs,da,de,el,en,es,et,fi,fr,hi_IN,ht,hu,in,it,iw,ja,ko,lt,lv,mww,nb,nl,pl,pt_PT,ro,ru,sk,sl,sv,th,tr,uk,vi,zh_CN,zh_TW",
		required = false
	)
	public String getTranslatorLanguages();

}