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
import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

type Props = {
	description: string;
	onClick: () => void;
	title: string;
};

export const NotFound = ({description, onClick, title}: Props) => (
	<div className="a11y-panel__sidebar--body">
		<ClayEmptyState description={description} title={title}>
			<ClayButton displayType="secondary" onClick={onClick}>
				{Liferay.Language.get('back')}
			</ClayButton>
		</ClayEmptyState>
	</div>
);
