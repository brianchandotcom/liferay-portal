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

package com.liferay.portal.crypto.hash.generation.context.salt;

/**
 * @author Carlos Sierra Andrés
 */
public class SaltCommand {

	/**
	 * A command to HashProcessor to use a new salt generated from provided DefaultSizeSaltProvider
	 *
	 * @return a default size salt
	 */
	public static final SaltCommand generateDefaultSizeSalt() {
		return new SaltCommand();
	}

	/**
	 * A command to HashProcessor to use a new salt generated from provided VariableSizeSaltProvider
	 *
	 * @param size the size of generated salt
	 * @return A variable size salt
	 */
	public static final SaltCommand generateVariableSizeSalt(int size) {
		return new VariableSizeSaltCommand(size);
	}

	public static final class VariableSizeSaltCommand extends SaltCommand {

		public int getSaltSize() {
			return _saltSize;
		}

		private VariableSizeSaltCommand(int saltSize) {
			_saltSize = saltSize;
		}

		private final int _saltSize;

	}

	private SaltCommand() {
	}

}