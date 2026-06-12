/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.criteria;

import java.util.Collections;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AudienceCriteriaType {

	public AudienceCriteriaType(
		List<AudienceCriteria> audienceCriterias, String label) {

		_audienceCriterias = Collections.unmodifiableList(audienceCriterias);
		_label = label;
	}

	public List<AudienceCriteria> getAudienceCriterias() {
		return _audienceCriterias;
	}

	public String getLabel() {
		return _label;
	}

	private final List<AudienceCriteria> _audienceCriterias;
	private final String _label;

}