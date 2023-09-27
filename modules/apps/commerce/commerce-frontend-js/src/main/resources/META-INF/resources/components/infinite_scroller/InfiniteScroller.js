/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

function InfiniteScroller({
	children,
	customLoader: CustomLoader,
	maxHeight,
	onBottomTouched,
	scrollCompleted,
}) {
	const [scrollingAreaRendered, setScrollingAreaRendered] = useState(false);
	const infiniteLoaderRef = useRef(null);
	const [infiniteLoaderRendered, setInfiniteLoaderRendered] = useState(false);
	const scrollingAreaRef = useRef(null);

	const setScrollingArea = useCallback((node) => {
		scrollingAreaRef.current = node;
		setScrollingAreaRendered(true);
	}, []);

	const setInfiniteLoader = useCallback((node) => {
		infiniteLoaderRef.current = node;
		setInfiniteLoaderRendered(true);
	}, []);

	const setObserver = useCallback(() => {
		if (
			!scrollingAreaRef.current ||
			!infiniteLoaderRef.current ||
			!IntersectionObserver
		) {
			return;
		}

		const options = {
			root: scrollingAreaRef.current,
			rootMargin: '0px',
			threshold: 1.0,
		};

		const observer = new IntersectionObserver((entries) => {
			if (entries[0].intersectionRatio === 1) {
				onBottomTouched();
			}
		}, options);

		observer.observe(infiniteLoaderRef.current);
	}, [onBottomTouched]);

	useEffect(() => {
		if (
			scrollingAreaRendered &&
			infiniteLoaderRendered &&
			!scrollCompleted
		) {
			setObserver();
		}
	}, [
		scrollingAreaRendered,
		infiniteLoaderRendered,
		scrollCompleted,
		setObserver,
	]);

	return (
		<div
			className="inline-scroller"
			ref={setScrollingArea}
			style={maxHeight ? {maxHeight} : null}
		>
			{children}

			{!scrollCompleted &&
				(CustomLoader ? (
					<CustomLoader ref={setInfiniteLoader} />
				) : (
					<ClayLoadingIndicator ref={setInfiniteLoader} small />
				))}
		</div>
	);
}

InfiniteScroller.propTypes = {
	customLoader: PropTypes.element,
	maxHeight: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
	onBottomTouched: PropTypes.func.isRequired,
	scrollCompleted: PropTypes.bool.isRequired,
};

export default InfiniteScroller;
