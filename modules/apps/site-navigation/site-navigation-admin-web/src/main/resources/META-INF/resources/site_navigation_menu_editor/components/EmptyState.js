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

import ClayButton from '@clayui/button';
import React from 'react';

import {AddItemDropDown} from './AddItemDropdown';

export function EmptyState() {
	return (
		<div className="taglib-empty-result-message text-center">
			<div className="taglib-empty-state" />

			<h1 className="taglib-empty-result-message-title">
				{Liferay.Language.get('no-element-yet')}
			</h1>

			<p className="taglib-empty-result-message-description">
				{Liferay.Language.get(
					'fortunately-it-is-very-easy-to-add-new-ones'
				)}
			</p>

			<div className="taglib-empty-result-message-actionDropdownItems">
				<AddItemDropDown
					trigger={
						<ClayButton displayType="secondary" small>
							{Liferay.Language.get('new')}
						</ClayButton>
					}
				/>
			</div>
		</div>
	);
}
