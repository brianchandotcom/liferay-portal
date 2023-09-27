/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import FlaskIllustration from './FlaskIllustration.es';

export default function UnsupportedSegmentsExperiments() {
	return (
		<div className="align-items-center d-flex flex-column p-3">
			<FlaskIllustration />

			<h4 className="text-center text-dark">
				{Liferay.Language.get(
					'ab-test-is-available-only-for-content-pages'
				)}
			</h4>
		</div>
	);
}
