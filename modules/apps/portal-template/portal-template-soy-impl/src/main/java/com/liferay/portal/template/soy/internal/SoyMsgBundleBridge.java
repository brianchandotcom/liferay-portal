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

package com.liferay.portal.template.soy.internal;

import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.msgs.restricted.SoyMsg;
import com.google.template.soy.msgs.restricted.SoyMsgPart;
import com.google.template.soy.msgs.restricted.SoyMsgPlaceholderPart;
import com.google.template.soy.msgs.restricted.SoyMsgRawTextPart;

import com.ibm.icu.util.ULocale;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Bruno Basto
 */
public class SoyMsgBundleBridge extends SoyMsgBundle {

	public SoyMsgBundleBridge(
		SoyMsgBundle soyMsgBundle, Locale locale,
		ResourceBundle resourceBundle) {

		_languageId = LanguageUtil.getLanguageId(locale);

		_rtl = soyMsgBundle.isRtl();

		for (SoyMsg soyMsg : soyMsgBundle) {
			SoyMsg.Builder builder = SoyMsg.builder();

			builder.setLocaleString(_languageId);
			builder.setIsPlrselMsg(false);
			builder.setParts(_getLocalizedMessageParts(resourceBundle, soyMsg));

			_soyMsgMap.put(soyMsg.getId(), builder.build());
		}

		_uLocale = soyMsgBundle.getLocale();
	}

	@Override
	public ULocale getLocale() {
		return _uLocale;
	}

	@Override
	public String getLocaleString() {
		return _languageId;
	}

	@Override
	public SoyMsg getMsg(long messageId) {
		return _soyMsgMap.get(messageId);
	}

	@Override
	public int getNumMsgs() {
		return _soyMsgMap.size();
	}

	@Override
	public boolean isRtl() {
		return _rtl;
	}

	@Override
	public Iterator<SoyMsg> iterator() {
		Collection<SoyMsg> values = _soyMsgMap.values();

		return values.iterator();
	}

	private static List<SoyMsgPart> _getLocalizedMessageParts(
		ResourceBundle resourceBundle, SoyMsg soyMsg) {

		List<SoyMsgPart> soyMsgParts = soyMsg.getParts();

		StringBundler sb = new StringBundler(soyMsgParts.size());

		LinkedList<SoyMsgPart> placeholderParts = new LinkedList<>();

		List<String> placeholderStrings = new ArrayList<>();

		Iterator<SoyMsgPart> iterator = soyMsgParts.iterator();

		while (iterator.hasNext()) {
			SoyMsgPart soyMsgPart = iterator.next();

			if (soyMsgPart instanceof SoyMsgPlaceholderPart) {
				placeholderParts.add(soyMsgPart);
				placeholderStrings.add(_PLACEHOLDER);

				sb.append(CharPool.LOWER_CASE_X);
			}
			else {
				SoyMsgRawTextPart soyMsgRawTextPart =
					(SoyMsgRawTextPart)soyMsgPart;

				sb.append(soyMsgRawTextPart.getRawText());
			}
		}

		String localizedText = LanguageUtil.format(
			resourceBundle, sb.toString(), placeholderStrings.toArray());

		List<SoyMsgPart> localizedSoyMsgParts = new ArrayList<>();

		String[] localizedTextParts = StringUtil.split(
			localizedText, _PLACEHOLDER);

		for (String localizedTextPart : localizedTextParts) {
			localizedSoyMsgParts.add(SoyMsgRawTextPart.of(localizedTextPart));

			if (!placeholderParts.isEmpty()) {
				localizedSoyMsgParts.add(placeholderParts.poll());
			}
		}

		return localizedSoyMsgParts;
	}

	private static final String _PLACEHOLDER = "__SOY_MSG_PLACEHOLDER__";

	private final String _languageId;
	private final boolean _rtl;
	private final Map<Long, SoyMsg> _soyMsgMap = new HashMap<>();
	private final ULocale _uLocale;

}