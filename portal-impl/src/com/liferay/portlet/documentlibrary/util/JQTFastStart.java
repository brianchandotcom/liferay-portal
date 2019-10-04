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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class updates an MP4 to be a "fast start" movie. This allows the MP4 to
 * be more quickly read by a client player without needing to completely
 * download the file. It is achieved by moving the movie's MOOV atom to the
 * front of the file. This code is based on the public domain code of <a
 * href="http://www.wired-space.de/media/JQTFaststart.java">JQTFaststat</a>.
 *
 * @author Juan González
 */
public class JQTFastStart {

	public static void convert(File inputFile, File outputFile)
		throws IOException {

		_jqtFastStart.doConvert(inputFile, outputFile);
	}

	protected void doConvert(File inputFile, File outputFile)
		throws IOException {

		validate(inputFile, outputFile);

		RandomAccessFile randomAccessInputFile = null;
		RandomAccessFile randomAccessOutputFile = null;

		try {
			randomAccessInputFile = new RandomAccessFile(inputFile, "r");

			Atom atom = null;
			Atom ftypAtom = null;

			boolean fastStart = false;
			boolean ftypFound = false;
			boolean mdatFound = false;

			while (randomAccessInputFile.getFilePointer() <
						randomAccessInputFile.length()) {

				atom = new Atom(randomAccessInputFile);

				if (!atom.isTopLevelAtom()) {
					throw new IOException(
						"Non top level atom was found " + atom.getType());
				}

				if (ftypFound && !mdatFound && atom.isMOOV()) {
					fastStart = true;

					break;
				}

				if (atom.isFTYP()) {
					ftypAtom = atom;

					ftypAtom.fillBuffer(randomAccessInputFile);

					ftypFound = true;
				}
				else if (atom.isMDAT()) {
					mdatFound = true;

					randomAccessInputFile.skipBytes((int)atom.getSize());
				}
				else {
					randomAccessInputFile.skipBytes((int)atom.getSize());
				}
			}

			if (fastStart) {
				if (_log.isInfoEnabled()) {
					_log.info("The movie is already a fast start MP4");
				}

				FileUtil.move(inputFile, outputFile);

				return;
			}

			if (!atom.isMOOV()) {
				throw new IOException("Last atom was not of type MOOV");
			}

			randomAccessInputFile.seek(atom.getOffset());

			Atom moovAtom = atom;

			moovAtom.fillBuffer(randomAccessInputFile);

			if (moovAtom.hasCompressedMoovAtom()) {
				throw new IOException("Compressed MOOV atoms are unsupported");
			}

			moovAtom.patchAtom();

			randomAccessInputFile.seek(
				ftypAtom.getOffset() + ftypAtom.getSize());

			randomAccessOutputFile = new RandomAccessFile(outputFile, "rw");

			randomAccessOutputFile.setLength(0);

			randomAccessOutputFile.write(ftypAtom.getBuffer());
			randomAccessOutputFile.write(moovAtom.getBuffer());

			byte[] buffer = new byte[1024 * 1024];

			while ((randomAccessInputFile.getFilePointer() + buffer.length) <
						moovAtom.getOffset()) {

				int read = randomAccessInputFile.read(buffer);

				randomAccessOutputFile.write(buffer, 0, read);
			}

			int bufferSize =
				(int)
					(moovAtom.getOffset() -
						randomAccessInputFile.getFilePointer());

			buffer = new byte[bufferSize];

			randomAccessInputFile.readFully(buffer);

			randomAccessOutputFile.write(buffer);
		}
		finally {
			if (randomAccessInputFile != null) {
				randomAccessInputFile.close();
			}

			if (randomAccessOutputFile != null) {
				randomAccessOutputFile.close();
			}
		}
	}

	protected void validate(File inputFile, File outputFile)
		throws IOException {

		if (!inputFile.exists() || !inputFile.canRead()) {
			throw new IOException("Input file cannot be read " + inputFile);
		}

		if (outputFile.exists()) {
			throw new IOException("Output file alread exists " + outputFile);
		}

		String inputFileAbsolutePath = inputFile.getAbsolutePath();

		if (inputFileAbsolutePath.equals(outputFile.getAbsolutePath())) {
			throw new IOException(
				"Input file and output file cannot be the same " + inputFile);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JQTFastStart.class);

	private static final JQTFastStart _jqtFastStart = new JQTFastStart();

}