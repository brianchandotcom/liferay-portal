/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

import {sub} from '../../utils/utils';

export default function EmptyPlaceholder() {
	return (
		<div className="empty-contributors mb-0 p-4 rounded">
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
