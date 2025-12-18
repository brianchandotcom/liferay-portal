/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {sub} from 'frontend-js-web';
import React from 'react';

const BulkActionTaskResultRenderer = ({
	itemData,
}: {
	itemData: {
		executionStatus: {key: string};
		numberOfFailedItems: number;
		numberOfSuccessfulItems: number;
	};
}) => {
	const {executionStatus, numberOfFailedItems, numberOfSuccessfulItems} =
		itemData;

	return executionStatus.key === 'initial' ||
		executionStatus.key === 'started' ? (
		<span className="inline-item">
			<ClayIcon className="text-secondary" fontSize={16} symbol="time" />

			<span className="ml-1">{Liferay.Language.get('processing')}</span>
		</span>
	) : (
		<span className="inline-item text-nowrap">
			{numberOfFailedItems > 0 && numberOfSuccessfulItems > 0 && (
				<span>
					<ClayIcon
						className="text-success"
						fontSize={16}
						symbol="check-circle-full"
					/>

					<span className="ml-1">
						{sub(Liferay.Language.get('x-successful'), [
							numberOfSuccessfulItems,
						])}
					</span>

					<ClayIcon
						className="ml-2 text-danger"
						fontSize={16}
						symbol="times-circle-full"
					/>

					<span className="ml-1">
						{sub(Liferay.Language.get('x-failed'), [
							numberOfFailedItems,
						])}
					</span>
				</span>
			)}

			{numberOfFailedItems === 0 && numberOfSuccessfulItems > 0 && (
				<span>
					<ClayIcon
						className="text-success"
						fontSize={16}
						symbol="check-circle-full"
					/>

					<span className="ml-1">
						{Liferay.Language.get('all-successful')}
					</span>
				</span>
			)}

			{numberOfFailedItems > 0 && numberOfSuccessfulItems === 0 && (
				<span>
					<ClayIcon
						className="text-danger"
						fontSize={16}
						symbol="times-circle-full"
					/>

					<span className="ml-1">
						{Liferay.Language.get('all-failed')}
					</span>
				</span>
			)}
		</span>
	);
};

export default BulkActionTaskResultRenderer;
