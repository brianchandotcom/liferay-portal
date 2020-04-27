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

import {useEffect, useState} from 'react';

const removeCollapseHeight = (collapseElementRef) => {
	if (collapseElementRef && collapseElementRef.current) {
		collapseElementRef.current.style.removeProperty('height');
	}
};

const setCollapseHeight = (collapseElementRef) => {
	if (collapseElementRef && collapseElementRef.current) {
		const height = Array.prototype.slice
			.call(collapseElementRef.current.children)
			.reduce((acc, child) => acc + child.clientHeight, 0);

		collapseElementRef.current.setAttribute('style', `height: ${height}px`);
	}
};

export default (visible, setVisible, contentRef) => {
	const [transitioning, setTransitioning] = useState(false);

	useEffect(() => {
		if (transitioning) {
			setCollapseHeight(contentRef);
			if (visible) {
				removeCollapseHeight(contentRef);
			}
		}
	}, [contentRef, transitioning, visible]);

	const handleTransitionEnd = (event) => {
		if (event.target === contentRef.current && transitioning && !visible) {
			setVisible(true);
			setTransitioning(false);
			removeCollapseHeight(contentRef);
		}
		else if (event.target === contentRef.current) {
			setVisible(false);
			setTransitioning(false);
		}
	};

	const startTransition = (event) => {
		event.preventDefault();

		if (visible && !transitioning) {
			setCollapseHeight(contentRef);
		}

		if (!transitioning) {
			setTransitioning(true);
		}
	};

	return [transitioning, handleTransitionEnd, startTransition];
};
