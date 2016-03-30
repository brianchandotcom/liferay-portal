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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;

/**
 * Implements the logic for CMIS repository vendor and version detection.
 *
 * @author Iván Zaera
 */
public class CMISRepositoryDetector {

	/**
	 * Creates a detector for the given CMIS repository. The detection routines
	 * are run once and cached inside the object for future reference.
	 *
	 * @param repositoryInfo the repository description
	 */
	public CMISRepositoryDetector(RepositoryInfo repositoryInfo) {
		_detectVendor(repositoryInfo);
	}

	public boolean isNuxeo() {
		return _nuxeo;
	}

	public boolean isNuxeo5_4() {
		return _nuxeo5_4;
	}

	public boolean isNuxeo5_5OrHigher() {
		return _nuxeo5_5OrHigher;
	}

	public boolean isNuxeo5_8OrHigher() {
		return _nuxeo5_8OrHigher;
	}

	/**
	 * Detects the version number for the Nuxeo repository.
	 *
	 * @param repositoryInfo the repository description
	 */
	protected void detectNuxeo(RepositoryInfo repositoryInfo) {
		String productVersion = repositoryInfo.getProductVersion();

		String[] versionParts = StringUtil.split(
			productVersion, StringPool.PERIOD);

		int major = GetterUtil.getInteger(versionParts[0]);
		int minor = GetterUtil.getInteger(versionParts[1]);

		if (major > 5) {
			_nuxeo5_8OrHigher = true;
			_nuxeo5_5OrHigher = true;
		}
		else if (major == 5) {
			if (minor >= 8) {
				_nuxeo5_8OrHigher = true;
			}

			if (minor >= 5) {
				_nuxeo5_5OrHigher = true;
			}

			if (minor == 4) {
				_nuxeo5_4 = true;
			}
		}
	}

	/**
	 * Detects the vendor's name for the CMIS repository.
	 *
	 * @param repositoryInfo the repository description
	 */
	private void _detectVendor(RepositoryInfo repositoryInfo) {
		String productName = repositoryInfo.getProductName();

		if (productName.contains(_NUXEO_ID)) {
			_nuxeo = true;

			detectNuxeo(repositoryInfo);
		}
	}

	private static final String _NUXEO_ID = "Nuxeo";

	private boolean _nuxeo;
	private boolean _nuxeo5_4;
	private boolean _nuxeo5_5OrHigher;
	private boolean _nuxeo5_8OrHigher;

}