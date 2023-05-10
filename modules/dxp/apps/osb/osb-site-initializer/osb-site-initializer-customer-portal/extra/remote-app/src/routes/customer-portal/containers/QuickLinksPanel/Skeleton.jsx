/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
