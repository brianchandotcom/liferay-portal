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

package com.liferay.digital.signature.model;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author Michael C. Han
 */
public class DSSessionKey {

	public DSSessionKey(String dsSessionKeyString) {
		List<String> dsSessionKeyTokens = StringUtil.split(
			dsSessionKeyString, CharPool.UNDERLINE);

		if (dsSessionKeyTokens.size() != 3) {
			throw new IllegalArgumentException(
				"Digital signature session key does not match format " +
					"${accountKey}_${companyId}_${userName}: " +
						dsSessionKeyString);
		}

		_accountKey = dsSessionKeyTokens.get(0);
		_companyId = GetterUtil.getLong(dsSessionKeyTokens.get(1));
		_userName = dsSessionKeyTokens.get(2);
	}

	public DSSessionKey(String accountKey, long companyId, String userName) {
		_accountKey = accountKey;
		_companyId = companyId;
		_userName = userName;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DSSessionKey)) {
			return false;
		}

		DSSessionKey dsSessionKey = (DSSessionKey)object;

		if (Objects.equals(_accountKey, dsSessionKey._accountKey) &&
			Objects.equals(_companyId, dsSessionKey._companyId) &&
			Objects.equals(_userName, dsSessionKey._userName)) {

			return true;
		}

		return false;
	}

	public String getAccountKey() {
		return _accountKey;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getUserName() {
		return _userName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_accountKey, _companyId, _userName);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append(_accountKey);
		sb.append(StringPool.UNDERLINE);
		sb.append(_companyId);
		sb.append(StringPool.UNDERLINE);
		sb.append(_userName);

		return sb.toString();
	}

	private final String _accountKey;
	private final long _companyId;
	private final String _userName;

}