/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet.taglib.util;

import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class OutputData implements Mergeable<OutputData> {

	public void addData(String outputKey, String webKey, StringBundler sb) {
		DataKey dataKey = new DataKey(outputKey, webKey);

		StringBundler mergedSB = _dataMap.get(dataKey);

		if (mergedSB == null) {
			_dataMap.put(dataKey, sb);
		}
		else {
			mergedSB.append(sb);
		}
	}

	public boolean addOutputKey(String outputKey) {
		return _outputKeySet.add(outputKey);
	}

	public StringBundler getMergedData(String webKey) {
		StringBundler mergedSB = null;

		for (Map.Entry<DataKey, StringBundler> entry : _dataMap.entrySet()) {
			DataKey dataKey = entry.getKey();

			if (dataKey._webKey.equals(webKey)) {
				if (mergedSB == null) {
					mergedSB = entry.getValue();
				}
				else {
					mergedSB.append(entry.getValue());
				}
			}
		}

		return mergedSB;
	}

	public OutputData merge(OutputData outputData) {
		// Don't merge null or myself
		if ((outputData != null) && (outputData != this)) {

			// Iterate through given OutputData
			for (Map.Entry<DataKey, StringBundler> entry :
				outputData._dataMap.entrySet()) {

				DataKey dataKey = entry.getKey();
				String outputKey = dataKey._outputKey;

				StringBundler sb = entry.getValue();

				// Don't merge if outputKey exist in my unique key set.
				if (!_outputKeySet.contains(outputKey)) {

					// Merge data
					StringBundler mergedSB = _dataMap.get(dataKey);

					if (mergedSB == null) {
						_dataMap.put(dataKey, sb);
					}
					else {
						mergedSB.append(sb);
					}

					// If outputKey is unique for the given key set,
					// we should honor it.
					if (outputData._outputKeySet.contains(outputKey)) {
						_outputKeySet.add(outputKey);
					}
				}
			}
		}

		return this;
	}

	private Map<DataKey, StringBundler> _dataMap =
		new HashMap<DataKey, StringBundler>();

	private Set<String> _outputKeySet = new HashSet<String>();

	private class DataKey {

		public DataKey(String outputKey, String webKey) {
			if (outputKey == null) {
				_outputKey = StringPool.BLANK;
			}
			else {
				_outputKey = outputKey;
			}

			_webKey = webKey;
		}

		@Override
		public boolean equals(Object obj) {
			DataKey dataKey = (DataKey)obj;

			if (_outputKey.equals(dataKey._outputKey) &&
				_webKey.equals(dataKey._webKey)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return _outputKey.hashCode() * 11 + _webKey.hashCode();
		}

		private String _outputKey;
		private String _webKey;

	}

}