/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

import {sub} from '../../utils/utils';

export default function EmptyPlaceholder() {
	return (
		<div className="c-mb-0 c-p-4 empty-contributors rounded">
			<ClayEmptyState
				description={Liferay.Language.get('empty-conditions-message')}
				imgSrc={`${themeDisplay.getPathThemeImages()}/states/empty_state.gif`}
				title={sub(Liferay.Language.get('no-x-yet'), [
					Liferay.Language.get('conditions'),
				])}
			></ClayEmptyState>
		</div>
	);
}
