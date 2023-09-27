/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ExperienceSelector} from '@liferay/layout-js-components-web';
import React, {useEffect, useState} from 'react';

export default function ExperiencePicker({
	segmentsExperiences,
	selectedSegmentsExperience,
}) {
	const [showSelector, setShowSelector] = useState(true);

	useEffect(() => {
		if (Liferay.FeatureFlags['LPS-186558']) {
			Liferay.on('SimulationMenu:closeSimulationPanel', () =>
				setShowSelector(true)
			);

			Liferay.on('SimulationMenu:openSimulationPanel', () =>
				setShowSelector(false)
			);
		}

		if (Liferay.FeatureFlags['LPS-187284']) {
			Liferay.on('PageAuditMenu:closePageAuditPanel', () =>
				setShowSelector(true)
			);

			Liferay.on('PageAuditMenu:openPageAuditPanel', () =>
				setShowSelector(false)
			);
		}
	}, []);

	return showSelector ? (
		<ExperienceSelector
			className="mb-0"
			displayType="dark"
			segmentsExperiences={segmentsExperiences}
			selectedSegmentsExperience={selectedSegmentsExperience}
		/>
	) : null;
}
