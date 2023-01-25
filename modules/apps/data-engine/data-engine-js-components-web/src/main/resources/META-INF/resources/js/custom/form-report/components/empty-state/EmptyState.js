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

export default function EmptyState({
	description = Liferay.Language.get(
		'the-report-will-show-up-once-the-first-entry-is-submitted'
	),
	title = Liferay.Language.get('there-are-no-entries'),
}) {
	return (
		<ClayEmptyState
			description={description}
			imgSrc={`${themeDisplay.getPathThemeImages()}/states/empty_state.gif`}
			title={title}
		/>
	);
}
