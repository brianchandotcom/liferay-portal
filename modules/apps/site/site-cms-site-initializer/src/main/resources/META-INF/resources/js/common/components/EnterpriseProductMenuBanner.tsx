/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';

import EnterpriseFeatureIndicator from './EnterpriseFeatureIndicator';

export default function EnterpriseProductMenuBanner() {
	return (
		<div className="p-3">
			<div className="bg-white border p-2 rounded-lg">
				<div className="p-1 pb-2">
					<EnterpriseFeatureIndicator />

					<div className="mt-3 text-2 text-secondary">
						<p className="mb-1 text-3 text-dark text-weight-semi-bold">
							{Liferay.Language.get('get-more-with-enterprise')}
						</p>

						<p>
							{Liferay.Language.get(
								'share-your-email-and-we-ll-show-you-what-you-can-unlock-with-our-enterprise-plan'
							)}
						</p>

						<ClayLink
							className="btn btn-primary btn-sm w-100"
							href="#" // TODO: Waiting for link
						>
							{Liferay.Language.get('get-enterprise-details')}
						</ClayLink>
					</div>
				</div>
			</div>
		</div>
	);
}
