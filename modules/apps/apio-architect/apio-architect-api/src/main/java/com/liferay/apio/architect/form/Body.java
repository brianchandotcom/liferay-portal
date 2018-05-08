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

package com.liferay.apio.architect.form;

import com.liferay.apio.architect.file.BinaryFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Instances of this interface represent the current HTTP request body.
 *
 * @author Alejandro Hernández
 * @review
 */
public interface Body {

	/**
	 * Creates a new {@code Body} from two lambdas.
	 *
	 * @param  valueFunction the function used to obtain values
	 * @param  valueListFunction the function used to obtain lists of values
	 * @return the body instance
	 * @review
	 */
	public static Body create(
		Function<String, Optional<String>> valueFunction,
		Function<String, Optional<List<String>>> valueListFunction) {

		return new Body() {

			@Override
			public Optional<List<String>> getValueListOptional(String key) {
				return valueListFunction.apply(key);
			}

			@Override
			public Optional<String> getValueOptional(String key) {
				return valueFunction.apply(key);
			}

		};
	}

	/**
	 * Creates a new {@code Body} from four lambdas.
	 *
	 * @param  valueFunction the function used to obtain values
	 * @param  valueListFunction the function used to obtain lists of values
	 * @param  fileListFunction the function used to obtain lists of files
	 * @param  fileFunction the function used to obtain files
	 * @return the body instance
	 * @review
	 */
	public static Body create(
		Function<String, Optional<String>> valueFunction,
		Function<String, Optional<List<String>>> valueListFunction,
		Function<String, Optional<List<BinaryFile>>> fileListFunction,
		Function<String, Optional<BinaryFile>> fileFunction) {

		return new Body() {

			@Override
			public Optional<List<BinaryFile>> getFileListOptional(String key) {
				return fileListFunction.apply(key);
			}

			@Override
			public Optional<BinaryFile> getFileOptional(String key) {
				return fileFunction.apply(key);
			}

			@Override
			public Optional<List<String>> getValueListOptional(String key) {
				return valueListFunction.apply(key);
			}

			@Override
			public Optional<String> getValueOptional(String key) {
				return valueFunction.apply(key);
			}

		};
	}

	/**
	 * Returns a list of files from the body, if present. Returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @param  key the key for extracting the list of files
	 * @return the list, if present; {@code Optional#empty()} otherwise
	 * @review
	 */
	public default Optional<List<BinaryFile>> getFileListOptional(String key) {
		return Optional.empty();
	}

	/**
	 * Returns a binary file from the body, if present. Returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @param  key the key for extracting the file
	 * @return the file, if present; {@code Optional#empty()} otherwise
	 * @review
	 */
	public default Optional<BinaryFile> getFileOptional(String key) {
		return Optional.empty();
	}

	/**
	 * Returns a list of values from the body, if present. Returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @param  key the key for extracting the list of values
	 * @return the list, if present; {@code Optional#empty()} otherwise
	 * @review
	 */
	public default Optional<List<String>> getValueListOptional(String key) {
		return Optional.empty();
	}

	/**
	 * Returns a value from the body, if present. Returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @param  key the key for extracting the value
	 * @return the value, if present; {@code Optional#empty()} otherwise
	 * @review
	 */
	public Optional<String> getValueOptional(String key);

}