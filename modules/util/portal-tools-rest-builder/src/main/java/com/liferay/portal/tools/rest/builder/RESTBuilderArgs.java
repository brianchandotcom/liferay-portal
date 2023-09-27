/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public Boolean isForcePredictableOperationId() {
		return _forcePredictableOperationId;
	}

	public void setCopyrightFile(File copyrightFile) {
		_copyrightFile = copyrightFile;
	}

	public void setForceClientVersionDescription(
		Boolean forceClientVersionDescription) {

		_forceClientVersionDescription = forceClientVersionDescription;
	}

	public void setForcePredictableOperationId(
		Boolean forcePredictableOperationId) {

		_forcePredictableOperationId = forcePredictableOperationId;
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
		arity = 1, converter = BooleanConverter.class,
		description = "Updates the operation ID.",
		names = {"-o", "--force-predictable-operation-id"}
	)
	private Boolean _forcePredictableOperationId;

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