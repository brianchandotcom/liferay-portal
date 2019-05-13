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

package com.liferay.portal.search.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.web.internal.portlet.shared.search.NullPortletURL;

import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author André de Oliveira
 */
public class PortletURLFactoryImpl implements PortletURLFactory {

	public PortletURLFactoryImpl(
		PortletRequest portletRequest, MimeResponse mimeResponse) {

		_portletRequest = portletRequest;
		_mimeResponse = mimeResponse;
	}

	@Override
	public PortletURL getPortletURL() throws PortletException {
		return new NullPortletURL() {

			@Override
			public void setParameter(String name, String value) {
				setParameter(name, new String[] {value});
			}

			@Override
			public void setParameter(String name, String[] values) {
				String[] oldValues = _nullPortletURLParameterMap.get(name);

				if (oldValues == null) {
					_nullPortletURLParameterMap.put(name, values);
				}
				else {
					String[] newValues = ArrayUtil.append(oldValues, values);

					_nullPortletURLParameterMap.put(name, newValues);
				}
			}

			@Override
			public String toString() {
				_nullPortletURLParameterMap.forEach(
					(paramName, paramValues) -> {
						_setURL(paramName, paramValues);
					});

				return _url;
			}

			final class NullPortletURLParameterMap
				extends AbstractMap<String, String[]> {

				@Override
				public Set<Entry<String, String[]>> entrySet() {
					if (_entrySet == null) {
						_entrySet = new LinkedHashSet<>();
					}

					return _entrySet;
				}

				@Override
				public String[] put(String key, String[] value) {
					Set<Map.Entry<String, String[]>> entrySet = entrySet();

					for (Map.Entry<String, String[]> entry : entrySet) {
						String entryKey = entry.getKey();

						if (entryKey.equals(key)) {
							String[] oldValues = entry.getValue();

							entry.setValue(value);

							return oldValues;
						}
					}

					entrySet.add(new SimpleEntry<>(key, value));

					return null;
				}

				private Set<Map.Entry<String, String[]>> _entrySet;

			}

			private String _getCurrentCompleteURL() {
				PortletRequest portletRequest = _getPortletRequest();

				return (String)portletRequest.getAttribute(
					"CURRENT_COMPLETE_URL");
			}

			private void _setURL(String paramName, String[] paramValues) {
				String currentCompleteURL = _getCurrentCompleteURL();

				StringBundler urlSB = null;

				for (String paramValue : paramValues) {
					if (!currentCompleteURL.contains(paramValue)) {
						urlSB = new StringBundler(5);

						urlSB.append(currentCompleteURL);
						urlSB.append(StringPool.AMPERSAND);
						urlSB.append(paramName);
						urlSB.append(StringPool.EQUAL);
						urlSB.append(paramValue);
					}
					else {
						urlSB = new StringBundler(1);

						urlSB.append(currentCompleteURL);
					}
				}

				_url = urlSB.toString();
			}

			private final Map<String, String[]> _nullPortletURLParameterMap =
				new NullPortletURLParameterMap();
			private String _url;

		};
	}

	private PortletRequest _getPortletRequest() {
		return _portletRequest;
	}

	private final MimeResponse _mimeResponse;
	private final PortletRequest _portletRequest;

}