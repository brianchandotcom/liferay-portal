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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Bruno Basto
 */
public class SoyMsgBundleBridge extends SoyMsgBundle {

	public SoyMsgBundleBridge(
		Iterable<SoyMsg> messages, Locale locale,
		ResourceBundle resourceBundle) {

		_messages = messages;
		_locale = locale;
		_resourceBundle = resourceBundle;
	}

	@Override
	public String getLocaleString() {
		return LanguageUtil.getLanguageId(_locale);
	}

	@Override
	public SoyMsg getMsg(long messageId) {
		SoyMsg soyMsg = _getMsg(messageId);

		SoyMsg.Builder builder = SoyMsg.builder();

		builder.setLocaleString(getLocaleString());
		builder.setIsPlrselMsg(false);
		builder.setParts(_getLocalizedMessageParts(soyMsg));

		return builder.build();
	}

	@Override
	public int getNumMsgs() {
		int count = 0;

		Iterator<SoyMsg> iterator = _messages.iterator();

		while (iterator.hasNext()) {
			count++;
		}

		return count;
	}

	@Override
	public Iterator<SoyMsg> iterator() {
		return _messages.iterator();
	}

	private List<SoyMsgPart> _getLocalizedMessageParts(SoyMsg soyMsg) {
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
			_resourceBundle, sb.toString(), placeholderStrings.toArray());

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

	private SoyMsg _getMsg(long messageId) {
		Iterator<SoyMsg> iterator = _messages.iterator();

		while (iterator.hasNext()) {
			SoyMsg soyMsg = iterator.next();

			if (messageId == soyMsg.getId()) {
				return soyMsg;
			}
		}

		return null;
	}

	private static final String _PLACEHOLDER = "__SOY_MSG_PLACEHOLDER__";

	private final Locale _locale;
	private final Iterable<SoyMsg> _messages;
	private final ResourceBundle _resourceBundle;

}