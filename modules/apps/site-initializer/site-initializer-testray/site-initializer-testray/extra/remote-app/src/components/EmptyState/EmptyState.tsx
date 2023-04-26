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
import React, {ReactNode} from 'react';

import i18n from '../../i18n';
import {Liferay} from '../../services/liferay';

export const States = {
	BLANK: '',

	/**
	 * When the filters or search results return zero results.
	 */

	EMPTY_SEARCH: `${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`,

	/**
	 * When there are no elements in the data set at a certain level
	 */

	EMPTY_STATE: `${Liferay.ThemeDisplay.getPathThemeImages()}/states/empty_state.gif`,

	/**
	 * When there no permission to access the module
	 */

	NO_ACCESS: `${Liferay.ThemeDisplay.getPathThemeImages()}/app_builder/illustration_locker.svg`,

	NOT_FOUND:
		'https://www.liferay.com/documents/10182/501717/404-Illustration-v2.svg',

	/**
	 * The user has emptied the dataset for a good cause.
	 * For example, all the notifications have been addressed, resulting in a clean state.
	 */
	SUCCESS: `${Liferay.ThemeDisplay.getPathThemeImages()}/states/success_state.gif`,
};

export type EmptyStateProps = {
	children?: ReactNode;
	description?: string;
	imgSrc?: string;
	title?: string;
	type?: keyof typeof States;
};

const EmptyState: React.FC<EmptyStateProps> = ({
	children,
	description,
	imgSrc,
	title,
	type,
}) => (
	<ClayEmptyState
		description={
			description || i18n.translate('sorry-there-are-no-results-found')
		}
		imgSrc={imgSrc ?? (type ? States[type] : States.EMPTY_STATE)}
		title={title || i18n.translate('no-results-found')}
	>
		{children}
	</ClayEmptyState>
);

export default EmptyState;
