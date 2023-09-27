/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process.local;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.process.ProcessLog;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
class LocalProcessLog implements ProcessLog {

	LocalProcessLog(
		ProcessLog.Level level, String message, Throwable throwable) {

		_level = level;
		_message = message;
		_throwable = throwable;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProcessLog)) {
			return false;
		}

		ProcessLog processLog = (ProcessLog)object;

		if ((_level == processLog.getLevel()) &&
			Objects.equals(_message, processLog.getMessage()) &&
			Objects.equals(_throwable, processLog.getThrowable())) {

			return true;
		}

		return false;
	}

	@Override
	public ProcessLog.Level getLevel() {
		return _level;
	}

	@Override
	public String getMessage() {
		return _message;
	}

	@Override
	public Throwable getThrowable() {
		return _throwable;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _level);

		hash = HashUtil.hash(hash, _message);
		hash = HashUtil.hash(hash, _throwable);

		return hash;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{level=", _level, ", message=", _message, ", throwable=",
			_throwable, "}");
	}

	private final ProcessLog.Level _level;
	private final String _message;
	private final Throwable _throwable;

}