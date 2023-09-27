/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {Skeleton} from '../../../../common/components';
import {storage} from '../../../../common/services/liferay/storage';
import {STORAGE_KEYS} from '../../../../common/utils/constants';

const QuickLinksSkeleton = () => {
	const [expandedPanel, setExpandedPanel] = useState(true);

	useEffect(() => {
		const quickLinksExpandedStorage = storage.getItem(
			STORAGE_KEYS.quickLinksExpanded
		);

		if (quickLinksExpandedStorage) {
			setExpandedPanel(JSON.parse(quickLinksExpandedStorage));
		}
	}, []);

	return (
		<div
			className={classNames(
				'cp-link-body p-4 quick-links-container rounded',
				{
					'position-absolute': !expandedPanel,
				}
			)}
		>
			<Skeleton className="mb-4" height={20} width={107} />

			{expandedPanel && (
				<>
					<div className="bg-white cp-link-body my-3 p-3 rounded-lg">
						<Skeleton className="mb-2" height={24} width={150} />

						<Skeleton className="mb-1" height={16} width={240} />

						<Skeleton height={16} width={180} />
					</div>

					<div className="bg-white cp-link-body my-3 p-3 rounded-lg">
						<Skeleton className="mb-2" height={24} width={150} />

						<Skeleton className="mb-1" height={16} width={240} />

						<Skeleton height={16} width={180} />
					</div>

					<div className="bg-white cp-link-body my-3 p-3 rounded-lg">
						<Skeleton className="mb-2" height={24} width={150} />

						<Skeleton className="mb-1" height={16} width={240} />

						<Skeleton height={16} width={180} />
					</div>
				</>
			)}
		</div>
	);
};

export default QuickLinksSkeleton;
