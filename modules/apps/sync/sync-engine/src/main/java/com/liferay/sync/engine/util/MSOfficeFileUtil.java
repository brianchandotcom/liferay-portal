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

package com.liferay.sync.engine.util;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hpsf.HPSFPropertiesOnlyDocument;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;

/**
 * @author Shinn Lok
 */
public class MSOfficeFileUtil {

	public static Date getLastSavedDate(Path filePath) {
		NPOIFSFileSystem npoifsFileSystem = null;

		try {
			npoifsFileSystem = new NPOIFSFileSystem(filePath.toFile());

			HPSFPropertiesOnlyDocument hpsfPropertiesOnlyDocument =
				new HPSFPropertiesOnlyDocument(npoifsFileSystem);

			SummaryInformation summaryInformation =
				hpsfPropertiesOnlyDocument.getSummaryInformation();

			return summaryInformation.getLastSaveDateTime();
		}
		catch (Exception e) {
			return null;
		}
		finally {
			if (npoifsFileSystem != null) {
				try {
					npoifsFileSystem.close();
				}
				catch (Exception e) {
					return null;
				}
			}
		}
	}

	public static boolean isLegacyExcelFile(Path filePath) {
		String extension = FilenameUtils.getExtension(filePath.toString());

		if (extension == null) {
			return false;
		}

		extension = extension.toLowerCase();

		if (extension.equals("xls") && !Files.isDirectory(filePath)) {
			return true;
		}

		return false;
	}

	public static boolean isTempCreatedFile(Path filePath) {
		String fileName = String.valueOf(filePath.getFileName());

		if ((fileName.startsWith("~$") ||
			 ((fileName.startsWith("~") || fileName.startsWith("ppt") ||
			   fileName.startsWith("pub")) &&
			  fileName.endsWith(".tmp"))) &&
			!Files.isDirectory(filePath)) {

			return true;
		}

		Matcher matcher = _tempCreatedFilePattern.matcher(
			String.valueOf(filePath.getFileName()));

		if (matcher.matches() && !Files.isDirectory(filePath)) {
			return true;
		}

		return false;
	}

	public static boolean isTempRenamedFile(
		Path sourceFilePath, Path targetFilePath) {

		String extension = FilenameUtils.getExtension(
			sourceFilePath.toString());

		if (extension.equals("pub")) {
			String fileName = String.valueOf(targetFilePath.getFileName());

			if (fileName.startsWith("pub") && fileName.endsWith(".tmp")) {
				return true;
			}
		}
		else if (hasExtension(extension, _excelExtensions) ||
				 hasExtension(extension, _powerpointExtensions)) {

			Matcher matcher = _tempRenamedFilePattern.matcher(
				String.valueOf(targetFilePath.getFileName()));

			if (matcher.matches()) {
				return true;
			}
		}
		else if (hasExtension(extension, _wordExtensions)) {
			String fileName = String.valueOf(targetFilePath.getFileName());

			if (fileName.startsWith("~WR") && fileName.endsWith(".tmp")) {
				return true;
			}
		}

		return false;
	}

	protected static boolean hasExtension(
		String extension, Set<String> extensions) {

		if ((extension != null) &&
			extensions.contains(extension.toLowerCase())) {

			return true;
		}

		return false;
	}

	private static final Set<String> _excelExtensions = new HashSet<>(
		Arrays.asList(
			"csv", "xla", "xlam", "xls", "xlsb", "xlsm", "xlsx", "xlt", "xltm",
			"xltx"));
	private static final Set<String> _powerpointExtensions = new HashSet<>(
		Arrays.asList(
			"pot", "potm", "potx", "ppa", "ppam", "pps", "ppsm", "ppsx", "ppt",
			"pptm", "pptx"));
	private static final Pattern _tempCreatedFilePattern = Pattern.compile(
		"[0-9A-F]{6,8}\\.tmp");
	private static final Pattern _tempRenamedFilePattern = Pattern.compile(
		"[0-9A-F]{6,8}(\\.tmp)?");
	private static final Set<String> _wordExtensions = new HashSet<>(
		Arrays.asList("doc", "docb", "docm", "docx", "dot", "dotm", "dotx"));

}