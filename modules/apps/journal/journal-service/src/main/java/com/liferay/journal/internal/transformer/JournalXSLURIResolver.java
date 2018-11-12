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

package com.liferay.journal.internal.transformer;

import com.liferay.journal.internal.util.JournalHelperUtil;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.xsl.XSLURIResolver;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalXSLURIResolver implements Externalizable, XSLURIResolver {

	public JournalXSLURIResolver(
		Map<String, String> tokens, String languageId) {

		if (tokens == null) {
			throw new IllegalArgumentException("Tokens map is null");
		}

		_tokens = tokens;
		_languageId = languageId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalXSLURIResolver)) {
			return false;
		}

		JournalXSLURIResolver journalXSLURIResolver =
			(JournalXSLURIResolver)obj;

		if (Objects.equals(_languageId, journalXSLURIResolver._languageId) &&
			_tokens.equals(journalXSLURIResolver._tokens)) {

			return true;
		}

		return false;
	}

	@Override
	public String getLanguageId() {
		return _languageId;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _languageId);

		return HashUtil.hash(hashCode, _tokens);
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_languageId = objectInput.readUTF();

		if (_languageId.equals(StringPool.BLANK)) {
			_languageId = null;
		}

		_tokens = (Map<String, String>)objectInput.readObject();
	}

	@Override
	public Source resolve(String href, String base) {
		try {
			String content = null;

			Matcher matcher = _templateIdPattern.matcher(href);

			if (matcher.matches()) {
				long articleGroupId = GetterUtil.getLong(
					_tokens.get("article_group_id"));

				String templateId = matcher.group(1);

				content = JournalHelperUtil.getTemplateScript(
					articleGroupId, templateId, _tokens, _languageId);
			}
			else {
				content = HttpUtil.URLtoString(href);
			}

			return new StreamSource(new UnsyncStringReader(content));
		}
		catch (Exception e) {
			_log.error(href + " does not reference a valid template");

			return null;
		}
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (_languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(_languageId);
		}

		objectOutput.writeObject(_tokens);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalXSLURIResolver.class);

	private static final Pattern _templateIdPattern = Pattern.compile(
		".*_templateId=([0-9]+).*");

	private String _languageId;
	private Map<String, String> _tokens;

}