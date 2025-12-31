/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {replaceTokens} from '@liferay/frontend-data-set-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import {openCMSModal} from '../../../common/utils/openCMSModal';
import FilePreviewerModalContent from '../../modal/FilePreviewerModalContent';

interface ActionItem {
	data: {id: string};
	href?: string;
}

export default function AssetVersionRenderer({
	actions,
	itemData,
	value,
}: {
	actions: ActionItem[];
	itemData: any;
	value: string;
}) {
	let formattedHref: string;

	if (!itemData.file?.thumbnailURL) {
		const selectedAction = actions.find(
			({data}) => data?.id === 'view-content'
		);

		if (!selectedAction?.href) {
			return value ? <>{value}</> : null;
		}

		formattedHref = replaceTokens(selectedAction.href, itemData);
	}

	const title = sub(
		Liferay.Language.get('x-version-x'),
		itemData.title,
		`${sub(
			Liferay.Language.get('version-x'),
			itemData.systemProperties.version.number
		)}`
	);

	return (
		<div className="table-list-title">
			<ClayLink
				className="text-decoration-underline"
				data-senna-off
				href="#"
				onClick={() => {
					if (itemData.file?.thumbnailURL) {
						openCMSModal({
							contentComponent: () =>
								FilePreviewerModalContent({
									file: itemData.file,
									headerName: title,
								}),
							size: 'full-screen',
						});
					}
					else {
						openCMSModal({
							size: 'full-screen',
							title,
							url: formattedHref,
						});
					}
				}}
				role="button"
			>
				{value}
			</ClayLink>
		</div>
	);
}
