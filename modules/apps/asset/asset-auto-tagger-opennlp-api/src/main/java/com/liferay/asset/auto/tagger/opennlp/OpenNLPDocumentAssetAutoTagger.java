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

package com.liferay.asset.auto.tagger.opennlp;

import java.util.Collection;
import java.util.Locale;

/**
 * Models a OpenNLP Document Asset Auto Tagger.
 *
 * @author     Cristina González
 * @deprecated As of Mueller (7.2.x)
 * @review
 */
@Deprecated
public interface OpenNLPDocumentAssetAutoTagger {

	/**
	 * Returns a list of tag names from the OpenNLP API.
	 *
	 * @param  companyId the company ID
	 * @param  content the text to be tagged.
	 * @param  locale the text's locale.
	 * @param  mimeType the mime type of the text.
	 * @return a list of tag names.
	 * @review
	 */
	public Collection<String> getTagNames(
			long companyId, String content, Locale locale, String mimeType)
		throws Exception;

	/**
	 * Returns a list of tag names from the OpenNLP API.
	 *
	 * @param  content the text to be tagged.
	 * @param  mimeType the mime type of the text.
	 * @return a list of tag names.
	 * @review
	 */
	public Collection<String> getTagNames(
			long companyId, String content, String mimeType)
		throws Exception;

}