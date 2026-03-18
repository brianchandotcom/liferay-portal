/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FeatureIndicator} from 'frontend-js-components-web';
import React from 'react';

import EnterpriseLink from './EnterpriseLink';

export default function EnterpriseProductMenuBanner() {
	return (
		<div className="p-3">
			<div className="bg-white border p-2 rounded-lg">
				<div className="p-1 pb-2">
					<FeatureIndicator className="m-0" type="enterprise" />

					<div className="mt-2 text-2 text-secondary">
						<p className="mb-1 text-3 text-dark text-weight-semi-bold">
							{Liferay.Language.get('get-more-with-enterprise')}
						</p>

						<p>
							{Liferay.Language.get(
								'share-your-email-and-we-ll-show-you-what-you-can-unlock-with-our-enterprise-subscription'
							)}
						</p>

						<EnterpriseLink className="btn btn-primary btn-sm w-100" />
					</div>
				</div>
			</div>
		</div>
	);
}
