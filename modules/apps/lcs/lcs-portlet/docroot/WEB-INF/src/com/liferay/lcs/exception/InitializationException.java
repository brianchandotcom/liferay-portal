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

package com.liferay.lcs.exception;

/**
 * @author Ivica Cardic
 */
public class InitializationException extends RuntimeException {

	public InitializationException(String message) {
		super(message);
	}

	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}

	public static class FileSystemAccessException
		extends InitializationException {

		public FileSystemAccessException(Exception e) {
			super(e);
		}

		public FileSystemAccessException(String path, Exception e) {
			super("Unable to access path " + path, e);
		}

	}

	public static class KeyStoreException extends InitializationException {

		public KeyStoreException(Exception e) {
			super("Unable to access key store", e);
		}

	}

}