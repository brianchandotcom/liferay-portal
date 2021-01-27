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

package com.liferay.dispatch.talend.web.internal.process;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Base64;

/**
 * @author Matija Petanjek
 */
public class TalendProcessOutputParser {

	public static final String KEY_ERROR = "error";

	public static final String KEY_EXIT_CODE = "exitCode";

	public static final String KEY_OUTPUT = "output";

	public TalendProcessOutputParser(String result) throws JSONException {
		JSONObject resultJSONObject = JSONFactoryUtil.createJSONObject(result);

		_error = resultJSONObject.getString(KEY_ERROR);
		_exitCode = resultJSONObject.getInt(KEY_EXIT_CODE);
		_output = resultJSONObject.getString(KEY_OUTPUT);
	}

	public byte[] getError() {
		return Base64.decode(_error);
	}

	public int getExitCode() {
		return _exitCode;
	}

	public byte[] getOutput() {
		return Base64.decode(_output);
	}

	public boolean hasException() {
		if (_exitCode == 0) {
			return false;
		}

		return true;
	}

	private final String _error;
	private int _exitCode;
	private final String _output;

}