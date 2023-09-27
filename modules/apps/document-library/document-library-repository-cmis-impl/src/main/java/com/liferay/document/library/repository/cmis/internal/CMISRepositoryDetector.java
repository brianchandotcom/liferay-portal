/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
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
	private void _detectNuxeo(RepositoryInfo repositoryInfo) {
		String[] versionParts = StringUtil.split(
			repositoryInfo.getProductVersion(), StringPool.PERIOD);

		int major = GetterUtil.getInteger(versionParts[0]);

		if (major > 5) {
			_nuxeo5_8OrHigher = true;
			_nuxeo5_5OrHigher = true;
		}
		else if (major == 5) {
			int minor = GetterUtil.getInteger(versionParts[1]);

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

			_detectNuxeo(repositoryInfo);
		}
	}

	private static final String _NUXEO_ID = "Nuxeo";

	private boolean _nuxeo;
	private boolean _nuxeo5_4;
	private boolean _nuxeo5_5OrHigher;
	private boolean _nuxeo5_8OrHigher;

}