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

package com.liferay.portal.tools.rest.builder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.BooleanConverter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

/**
 * @author Peter Shin
 */
public class RESTBuilderArgs {

	public static final String REST_CONFIG_DIR_NAME = ".";

	public File getCopyrightFile() {
		return _copyrightFile;
	}

	public File getRESTConfigDir() {
		return _restConfigDir;
	}

	public Boolean isForceClientVersionDescription() {
		return _forceClientVersionDescription;
	}

	public void setCopyrightFile(File copyrightFile) {
		_copyrightFile = copyrightFile;
	}

	public void setForceClientVersionDescription(
		Boolean forceClientVersionDescription) {

		_forceClientVersionDescription = forceClientVersionDescription;
	}

	public void setRESTConfigDir(File restConfigDir) {
		_restConfigDir = restConfigDir;
	}

	protected boolean isHelp() {
		return _help;
	}

	@Parameter(
		converter = FileConverter.class,
		description = "The copyright.txt file which contains the copyright for the source code.",
		names = {"-c", "--copyright-file"}
	)
	private File _copyrightFile;

	@Parameter(
		arity = 1, converter = BooleanConverter.class,
		description = "Updates client version with bnd version information.",
		names = {"-f", "--force-client-version-description"}
	)
	private Boolean _forceClientVersionDescription;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		converter = FileConverter.class,
		description = "The directory that contains the REST configuration files.",
		names = {"-r", "--rest-config-dir"}
	)
	private File _restConfigDir = new File(REST_CONFIG_DIR_NAME);

}