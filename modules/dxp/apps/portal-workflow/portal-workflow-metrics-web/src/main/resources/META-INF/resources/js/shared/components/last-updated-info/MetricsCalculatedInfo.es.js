/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import {sub} from '../../util/lang.es';
import moment from '../../util/moment.es';

export default function MetricsCalculatedInfo({dateModified}) {
	const date = dateModified
		? moment(dateModified).format(Liferay.Language.get('mmm-dd-lt'))
		: null;

	const infoLabel = sub(Liferay.Language.get('sla-metrics-calculated'), [
		date,
	]);

	return (
		date && (
			<div className="updated-info-container">
				<ClayLayout.ContainerFluid className="mt-3">
					<span className="metrics-calculated-info">{infoLabel}</span>
				</ClayLayout.ContainerFluid>
			</div>
		)
	);
}
