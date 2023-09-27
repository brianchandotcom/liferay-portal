/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

/**
 * @author Jorge Ferrer
 */
public class InfoItemDetails {

	public InfoItemDetails(
		InfoItemClassDetails infoItemClassDetails,
		InfoItemReference infoItemReference) {

		_infoItemClassDetails = infoItemClassDetails;
		_infoItemReference = infoItemReference;
	}

	public String getClassName() {
		return _infoItemClassDetails.getClassName();
	}

	public InfoItemClassDetails getInfoItemClassDetails() {
		return _infoItemClassDetails;
	}

	public InfoItemReference getInfoItemReference() {
		return _infoItemReference;
	}

	private final InfoItemClassDetails _infoItemClassDetails;
	private final InfoItemReference _infoItemReference;

}