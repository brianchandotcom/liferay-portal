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

import {ClaySelectWithOption} from '@clayui/form';
import React from 'react';

// TODO: show how to colocate CSS with plugins (may use loaders)
export default function ExperienceToolbarSection({selectId}) {
	return (
		<div className="page-editor-toolbar-experience mr-2">
			<label className="mr-2" htmlFor={selectId}>
				Experience
			</label>
			<ClaySelectWithOption
				disabled={false}
				id={selectId}
				options={[
					{
						label: 'Default',
						value: '1'
					}
				]}
			/>
		</div>
	);
}
