/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';

export default function Header({
	handleClickInfo,
	item,
}: {
	handleClickInfo: () => void;
	item: ItemData;
}) {
	const headerName = item.embedded?.title || item.embedded.file.name;

	const file = item.embedded.file;

	return (
		<div className="autofit-row autofit-row-center">
			<div className="autofit-col autofit-col-expand">
				<div className="text-truncate">{headerName}</div>
			</div>

			<div className="align-items-center c-gap-2 d-flex">
				<ClayButtonWithIcon
					borderless
					displayType="secondary"
					onClick={handleClickInfo}
					symbol="info-circle-open"
				/>

				{file && (
					<div className="autofit-col pr-3">
						<ClayLink
							button
							displayType="primary"
							href={file.link.href}
							small
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol="download" />
							</span>

							{Liferay.Language.get('download')}
						</ClayLink>
					</div>
				)}
			</div>
		</div>
	);
}
