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

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class GitUtil {

	public static class ExecutionResult {
	
		public int getExitValue() {
			return _exitValue;
		}
	
		public String getStandardError() {
			return _standardError;
		}
	
		public String getStandardOut() {
			return _standardOut;
		}
	
		protected ExecutionResult(
			int exitValue, String standardError, String standardOut) {
	
			_exitValue = exitValue;
			_standardError = standardError;
	
			if (standardOut.endsWith("\nFinished executing Bash commands.")) {
				_standardOut = standardOut.substring(
					0,
					standardOut.indexOf("\nFinished executing Bash commands."));
			}
			else {
				_standardOut = standardOut;
			}
		}
	
		private final int _exitValue;
		private final String _standardError;
		private final String _standardOut;
	
	}
}
