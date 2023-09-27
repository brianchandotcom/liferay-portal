/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.theme.builder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class ThemeBuilderArgs {

	public File getDiffsDir() {
		return _diffsDir;
	}

	public String getName() {
		return _name;
	}

	public File getOutputDir() {
		return _outputDir;
	}

	public File getParentDir() {
		return _parentDir;
	}

	public String getParentName() {
		return _parentName;
	}

	public String getTemplateExtension() {
		return _templateExtension;
	}

	public int getThumbnailHeight() {
		return _thumbnailHeight;
	}

	public int getThumbnailWidth() {
		return _thumbnailWidth;
	}

	public File getUnstyledDir() {
		return _unstyledDir;
	}

	public void setDiffsDir(File diffsDir) {
		_diffsDir = diffsDir;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOutputDir(File outputDir) {
		_outputDir = outputDir;
	}

	public void setParentDir(File parentDir) {
		_parentDir = parentDir;
	}

	public void setParentName(String parentName) {
		_parentName = parentName;
	}

	public void setTemplateExtension(String templateExtension) {
		_templateExtension = templateExtension;
	}

	public void setThumbnailHeight(int thumbnailHeight) {
		_thumbnailHeight = thumbnailHeight;
	}

	public void setThumbnailWidth(int thumbnailWidth) {
		_thumbnailWidth = thumbnailWidth;
	}

	public void setUnstyledDir(File unstyledDir) {
		_unstyledDir = unstyledDir;
	}

	protected boolean isHelp() {
		return _help;
	}

	protected static final String DEFAULT_NAME;

	protected static final String DEFAULT_TEMPLATE_EXTENSION = "ftl";

	protected static final int DEFAULT_THUMBNAIL_HEIGHT = 120;

	protected static final int DEFAULT_THUMBNAIL_WIDTH = 160;

	static {
		File userDir = new File(System.getProperty("user.dir"));

		DEFAULT_NAME = userDir.getName();
	}

	@Parameter(
		converter = FileConverter.class,
		description = "The directory that contains the files to copy over the parent theme.",
		names = {"-d", "--diffs-dir"}
	)
	private File _diffsDir;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		description = "The name of the new theme.", names = {"-n", "--name"}
	)
	private String _name = DEFAULT_NAME;

	@Parameter(
		converter = FileConverter.class,
		description = "The directory where to build the theme.",
		names = {"-o", "--output-dir"}, required = true
	)
	private File _outputDir;

	@Parameter(
		converter = FileConverter.class,
		description = "The directory or the JAR file of the parent theme.",
		names = {"-p", "--parent-path"}
	)
	private File _parentDir;

	@Parameter(
		description = "The name of the parent theme.",
		names = {"-m", "--parent-name"}
	)
	private String _parentName;

	@Parameter(
		description = "The extension of the template files, usually \"ftl\" or \"vm\".",
		names = {"-t", "--template-extension"}
	)
	private String _templateExtension = DEFAULT_TEMPLATE_EXTENSION;

	@Parameter(
		description = "The height for the generated thumbnail.",
		names = "--thumbnail-height"
	)
	private int _thumbnailHeight = DEFAULT_THUMBNAIL_HEIGHT;

	@Parameter(
		description = "The width for the generated thumbnail.",
		names = "--thumbnail-width"
	)
	private int _thumbnailWidth = DEFAULT_THUMBNAIL_WIDTH;

	@Parameter(
		converter = FileConverter.class,
		description = "The directory or the JAR file of Liferay Frontend Theme Unstyled.",
		names = {"-u", "--unstyled-path"}
	)
	private File _unstyledDir;

}