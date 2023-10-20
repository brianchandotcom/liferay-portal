/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.unit;

import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;

/**
 * @author Shuyang Zhou
 */
public class BatchEngineUnitMetaInfo {

	public static BatchEngineUnitMetaInfo readFrom(Deserializer deserializer) {
		boolean advanced = deserializer.readBoolean();
		long companyId = deserializer.readLong();
		String featureFlag = deserializer.readString();
		boolean multiCompany = deserializer.readBoolean();

		int pathCount = deserializer.readInt();

		String[] paths = new String[pathCount];

		for (int i = 0; i < pathCount; i++) {
			paths[i] = deserializer.readString();
		}

		return new BatchEngineUnitMetaInfo(
			advanced, companyId, featureFlag, multiCompany, paths);
	}

	public BatchEngineUnitMetaInfo(
		boolean advanced, long companyId, String featureFlag,
		boolean multiCompany, String[] paths) {

		_advanced = advanced;
		_companyId = companyId;
		_featureFlag = featureFlag;
		_multiCompany = multiCompany;
		_paths = paths;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getFeatureFlag() {
		return _featureFlag;
	}

	public String[] getPaths() {
		return _paths;
	}

	public boolean isAdvanced() {
		return _advanced;
	}

	public boolean isMultiCompany() {
		return _multiCompany;
	}

	public void writeTo(Serializer serializer) {
		serializer.writeBoolean(_advanced);
		serializer.writeLong(_companyId);
		serializer.writeString(_featureFlag);
		serializer.writeBoolean(_multiCompany);

		serializer.writeInt(_paths.length);

		for (String path : _paths) {
			serializer.writeString(path);
		}
	}

	private final boolean _advanced;
	private final long _companyId;
	private final String _featureFlag;
	private final boolean _multiCompany;
	private final String[] _paths;

}