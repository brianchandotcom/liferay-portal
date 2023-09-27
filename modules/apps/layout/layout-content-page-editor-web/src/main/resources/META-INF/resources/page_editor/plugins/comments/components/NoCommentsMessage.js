/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

import {config} from '../../../app/config/index';

export default function NoCommentsMessage() {
	return (
		<div className="p-4 text-center">
			<ClayEmptyState
				description={Liferay.Language.get(
					'select-a-fragment-to-add-a-comment'
				)}
				imgSrc={`${config.imagesPath}/no_comments.svg`}
				small
				title={Liferay.Language.get('there-are-no-comments-yet')}
			/>
		</div>
	);
}
