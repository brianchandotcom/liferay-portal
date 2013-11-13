/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis;

import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;

import java.util.regex.Pattern;

/**
 * This class implements the logic for CMIS repository vendor and version
 * detection.
 * @author Ivan Zaera
 */
public class CMISRepositoryDetector {

	/**
	 * Create a detector for the given repository. The detection routines are
	 * run once and cached inside the object for future reference.
	 * @param repositoryInfo the repository description
	 */
	public CMISRepositoryDetector(RepositoryInfo repositoryInfo) {
		_detectVendor(repositoryInfo);
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
	 * Detects version number for a Nuxeo repository
	 * @param repositoryInfo the repository description
	 */
	private void _detectNuxeo(RepositoryInfo repositoryInfo) {
		String productVersion = repositoryInfo.getProductVersion();
		String[] versionParts = productVersion.split(Pattern.quote("."));
		int major = _safeParseInt(versionParts[0]);
		int minor = _safeParseInt(versionParts[1]);

		if (major>5) {
			_nuxeo5_8OrHigher = true;
			_nuxeo5_5OrHigher = true;
		}
		else if (major==5) {
			if(minor>=8) {
				_nuxeo5_8OrHigher = true;
			}
			if(minor>=5) {
				_nuxeo5_5OrHigher = true;
			}
			if(minor==4) {
				_nuxeo5_4 = true;
			}
		}
	}

	/**
	 * Detects vendor for a CMIS repository
	 * @param repositoryInfo the repository description
	 */
	private void _detectVendor(RepositoryInfo repositoryInfo) {
		String productName = repositoryInfo.getProductName();

		if( productName.contains(_NUXEO_ID) ) {
			_nuxeo = true;
			_detectNuxeo(repositoryInfo);
		}
	}

	/**
	 * Parses a number like {@link Integer#parseInt} but does not throw any
	 * exception if it fails (instead returns 0)
	 * @param number the number to parse as a String
	 * @return the parsed number of 0 if it is not a number
	 */
	private int _safeParseInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private static final String _NUXEO_ID = "Nuxeo";

	private boolean _nuxeo;
	private boolean _nuxeo5_4;
	private boolean _nuxeo5_5OrHigher;
	private boolean _nuxeo5_8OrHigher;

}