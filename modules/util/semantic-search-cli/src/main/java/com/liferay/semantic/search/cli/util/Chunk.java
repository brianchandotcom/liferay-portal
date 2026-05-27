/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.util;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.List;
import java.util.UUID;

/**
 * One unit of indexable content — a section (or sliding-window slice
 * of a section) of a Markdown document. The chunk_id field is the
 * stable opaque identifier used as the Qdrant point ID after a
 * UUID5 transform.
 *
 * @author JR Houn
 */
public class Chunk {

	public static final UUID NAMESPACE = UUID.fromString(
		"6f1f0d6c-1c4a-4f6e-9b1a-7c0b8d9e0f11");

	public Chunk(
		String chunkId, String relPath, List<String> headingPath, String text) {

		_chunkId = chunkId;
		_relPath = relPath;
		_headingPath = headingPath;
		_text = text;
	}

	public String chunkId() {
		return _chunkId;
	}

	public List<String> headingPath() {
		return _headingPath;
	}

	public UUID pointId() {
		return _uuid5(NAMESPACE, _chunkId);
	}

	public String relPath() {
		return _relPath;
	}

	public String text() {
		return _text;
	}

	private byte[] _toBytes(UUID uuid) {
		byte[] bytes = new byte[16];

		long msb = uuid.getMostSignificantBits();

		long lsb = uuid.getLeastSignificantBits();

		for (int i = 0; i < 8; i++) {
			bytes[i] = (byte)(msb >>> (8 * (7 - i)));
		}

		for (int i = 0; i < 8; i++) {
			bytes[8 + i] = (byte)(lsb >>> (8 * (7 - i)));
		}

		return bytes;
	}

	private UUID _uuid5(UUID namespace, String name) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

			messageDigest.update(_toBytes(namespace));
			messageDigest.update(name.getBytes(StandardCharsets.UTF_8));

			byte[] hash = messageDigest.digest();

			hash[6] &= 0x0f;
			hash[6] |= 0x50;
			hash[8] &= 0x3f;
			hash[8] |= 0x80;

			long msb = 0;

			long lsb = 0;

			for (int i = 0; i < 8; i++) {
				msb = (msb << 8) | (hash[i] & 0xff);
			}

			for (int i = 8; i < 16; i++) {
				lsb = (lsb << 8) | (hash[i] & 0xff);
			}

			return new UUID(msb, lsb);
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new RuntimeException(noSuchAlgorithmException);
		}
	}

	private final String _chunkId;
	private final List<String> _headingPath;
	private final String _relPath;
	private final String _text;

}