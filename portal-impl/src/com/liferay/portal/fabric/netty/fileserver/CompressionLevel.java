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

package com.liferay.portal.fabric.netty.fileserver;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;

/**
 * @author Shuyang Zhou
 */
public enum CompressionLevel {

	BEST_COMPRESSION(Deflater.BEST_COMPRESSION),
	BEST_SPEED(Deflater.BEST_SPEED),
	DEFAULT_COMPRESSION(Deflater.DEFAULT_COMPRESSION), LEVEL2(2), LEVEL3(3),
	LEVEL4(4), LEVEL5(5), LEVEL6(6), LEVEL7(7), LEVEL8(8),
	NO_COMPRESSION(Deflater.NO_COMPRESSION);

	public static CompressionLevel getCompressionLevel(int level) {
		CompressionLevel compressionLevel = _compressionLevels.get(level);

		if (compressionLevel == null) {
			throw new IllegalArgumentException(
				"Unknown compression level " + level +
					", acceptable value range is [-1, 9]");
		}

		return compressionLevel;
	}

	public int getLevel() {
		return _level;
	}

	private CompressionLevel(int level) {
		_level = level;
	}

	private static Map<Integer, CompressionLevel> _compressionLevels =
		new HashMap<Integer, CompressionLevel>();

	static {
		for (CompressionLevel compressionLevel : EnumSet.allOf(
				CompressionLevel.class)) {

			_compressionLevels.put(compressionLevel._level, compressionLevel);
		}
	}

	private final int _level;

}