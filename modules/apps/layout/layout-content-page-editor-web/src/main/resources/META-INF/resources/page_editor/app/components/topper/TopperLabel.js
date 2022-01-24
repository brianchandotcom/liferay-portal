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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const TOPPER_BAR_HEIGHT = 24;

export function TopperLabel({children, isActive, itemElement}) {
	const [isInset, setIsInset] = useState(false);
	const [windowScrollPosition, setWindowScrollPosition] = useState(0);

	useEffect(() => {
		if (isActive) {
			const handleWindowScroll = () => {
				setWindowScrollPosition(window.scrollY);
			};

			window.addEventListener('scroll', handleWindowScroll);

			return () => {
				window.removeEventListener('scroll', handleWindowScroll);
			};
		}
	}, [isActive]);

	useEffect(() => {
		if (itemElement && isActive) {
			const itemTop =
				itemElement.getBoundingClientRect().top - TOPPER_BAR_HEIGHT;

			const controlMenuContainerHeight =
				document.querySelector('.control-menu-container')
					?.offsetHeight ?? 0;

			if (itemTop < controlMenuContainerHeight) {
				setIsInset(true);
			}
			else {
				setIsInset(false);
			}
		}
	}, [isActive, itemElement, windowScrollPosition]);

	return (
		<div
			className={classNames(
				'cadmin',
				'page-editor__topper__bar',
				'tbar',
				{
					'page-editor__topper__bar--inset': isInset,
				}
			)}
		>
			{children}
		</div>
	);
}

TopperLabel.propTypes = {
	isActive: PropTypes.bool,
	itemElement: PropTypes.object,
};
