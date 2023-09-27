/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure.collection;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class EmptyCollectionOptions {

	public static EmptyCollectionOptions of(JSONObject jsonObject) {
		if ((jsonObject == null) || (jsonObject.length() == 0)) {
			return null;
		}

		return new EmptyCollectionOptions() {
			{
				setDisplayMessage(
					() -> {
						if (jsonObject.has("displayMessage")) {
							return jsonObject.getBoolean("displayMessage");
						}

						return null;
					});
				setMessage(
					() -> {
						if (jsonObject.has("message")) {
							return JSONUtil.toStringMap(
								jsonObject.getJSONObject("message"));
						}

						return null;
					});
			}
		};
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EmptyCollectionOptions)) {
			return false;
		}

		EmptyCollectionOptions emptyCollectionOptions =
			(EmptyCollectionOptions)object;

		if (!Objects.equals(
				_displayMessage, emptyCollectionOptions._displayMessage) ||
			!Objects.equals(_message, emptyCollectionOptions._message)) {

			return false;
		}

		return super.equals(object);
	}

	public Map<String, String> getMessage() {
		return _message;
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public Boolean isDisplayMessage() {
		return _displayMessage;
	}

	public void setDisplayMessage(Boolean displayMessage) {
		_displayMessage = displayMessage;
	}

	public void setDisplayMessage(
		UnsafeSupplier<Boolean, Exception> displayMessageUnsafeSupplier) {

		try {
			_displayMessage = displayMessageUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setMessage(Map<String, String> message) {
		_message = message;
	}

	public void setMessage(
		UnsafeSupplier<Map<String, String>, Exception> messageUnsafeSupplier) {

		try {
			_message = messageUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public JSONObject toJSONObject() {
		if ((_displayMessage == null) &&
			((_message == null) || _message.isEmpty())) {

			return null;
		}

		return JSONUtil.put(
			"displayMessage", isDisplayMessage()
		).put(
			"message",
			() -> {
				if ((_message == null) || _message.isEmpty()) {
					return null;
				}

				return JSONFactoryUtil.createJSONObject(_message);
			}
		);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject();

		if (jsonObject == null) {
			return null;
		}

		return jsonObject.toString();
	}

	private Boolean _displayMessage;
	private Map<String, String> _message = new HashMap<>();

}