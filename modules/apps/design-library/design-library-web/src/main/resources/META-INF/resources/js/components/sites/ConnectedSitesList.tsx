/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClaySticker from '@clayui/sticker';
import React from 'react';

import {Site} from '../../types';

export default function ConnectedSitesList({
	connectedSites,
	disconnectSite,
	listLabelId,
}: {
	connectedSites: Site[];
	disconnectSite: (site: Site) => Promise<void>;
	listLabelId: string;
}) {
	if (!connectedSites.length) {
		return (
			<div className="text-center">
				<h2 className="font-weight-semi-bold text-4">
					{Liferay.Language.get('no-sites-are-connected-yet')}
				</h2>

				<p className="text-3">
					{Liferay.Language.get(
						'connect-sites-to-this-design-library'
					)}
				</p>
			</div>
		);
	}

	return (
		<>
			<label className="c-mb-2 c-mt-n2 d-block" id={listLabelId}>
				{Liferay.Language.get('connected-sites')}
			</label>

			<ul aria-labelledby={listLabelId} className="list-unstyled mb-0">
				{connectedSites.map((site) => {
					return (
						<li
							className="align-items-center c-py-2 d-flex font-weight-semi-bold justify-content-between text-3"
							key={site.id}
						>
							<div className="align-items-center d-flex">
								<ClaySticker
									className="c-mr-2"
									displayType="secondary"
									shape="circle"
									size="sm"
								>
									<ClaySticker.Image alt="" src={site.logo} />
								</ClaySticker>

								{site.descriptiveName}
							</div>

							<div className="align-items-center d-flex">
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'disconnect'
									)}
									borderless
									className="align-self-end"
									displayType="secondary"
									monospaced
									onClick={() => disconnectSite(site)}
									size="xs"
									symbol="times-circle"
								/>
							</div>
						</li>
					);
				})}
			</ul>
		</>
	);
}
