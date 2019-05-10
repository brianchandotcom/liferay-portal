/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.checker.composite;

import com.liferay.multi.factor.authentication.checker.MFAChecker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.List;
import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public abstract class BaseCompositeMFAChecker implements MFAChecker {

	public BaseCompositeMFAChecker(List<MFAChecker> mfaCheckers) {
		this.mfaCheckers = mfaCheckers;
	}

	@Override
	public String getLabel(Locale locale) {
		if (mfaCheckers.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(mfaCheckers.size() * 2 - 1);

		for (MFAChecker mfaChecker : mfaCheckers) {
			if (sb.length() > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(mfaChecker.getLabel(locale));
		}

		return sb.toString();
	}

	public List<MFAChecker> getMFACheckers() {
		return mfaCheckers;
	}

	@Override
	public String getName() {
		if (mfaCheckers.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(mfaCheckers.size() * 2 - 1);

		for (MFAChecker mfaChecker : mfaCheckers) {
			if (sb.length() > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(mfaChecker.getName());
		}

		return sb.toString();
	}

	@Override
	public boolean isEnabled() {
		for (MFAChecker mfaChecker : mfaCheckers) {
			if (!mfaChecker.isEnabled()) {
				return false;
			}
		}

		return true;
	}

	protected List<MFAChecker> mfaCheckers;

}