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

import {closest} from 'metal-dom';
import PropTypes from 'prop-types';
import {useEffect} from 'react';

export default function FragmentContentClickFilter({element}) {
	useEffect(() => {
		if (!element) {
			return;
		}

		const handleFragmentEntryLinkContentClick = event => {
			const closestElement = closest(event.target, '[href]');

			if (
				closestElement &&
				!('data-lfr-page-editor-href-enabled' in element.dataset)
			) {
				event.preventDefault();
			}
		};

		element.addEventListener(
			'click',
			handleFragmentEntryLinkContentClick,
			true
		);

		return () => {
			element.removeEventListener(
				'click',
				handleFragmentEntryLinkContentClick,
				true
			);
		};
	});

	return null;
}

FragmentContentClickFilter.propTypes = {
	element: PropTypes.instanceOf(HTMLElement)
};
