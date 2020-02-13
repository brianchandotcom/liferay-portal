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
import {useIsMounted} from 'frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useState} from 'react';
import ReactDOM from 'react-dom';

import {useSelector} from '../../store/index';

export const EDITABLE_DECORATION_CLASS_NAMES = {
	active: 'page-editor__editable-decoration-mask-rect--active',
	highlighted: 'page-editor__editable-decoration-mask-rect--highlighted',
	hovered: 'page-editor__editable-decoration-mask-rect--hovered',
	mapped: 'page-editor__editable-decoration-mask-rect--mapped',
	translated: 'page-editor__editable-decoration-mask-rect--translated'
};

function EditableDecorationMask({classNames: elementsClassNames, elements}) {
	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const layoutData = useSelector(state => state.layoutData);

	const isMounted = useIsMounted();

	const [rects, setRects] = useState({});
	const [showMask, setShowMask] = useState(false);

	const hideMask = useCallback(() => {
		setShowMask(false);
	}, []);

	const computeRects = useCallback(() => {
		const newRects = {};

		Object.entries(elements).forEach(([editableId, element]) => {
			const rect = element.getBoundingClientRect();

			if (!(rect.bottom < 0 || rect.top > window.innerHeight)) {
				newRects[editableId] = {
					height: rect.height,
					width: rect.width,
					x: rect.x,
					y: rect.y
				};
			}
		});

		setRects(newRects);
		setShowMask(true);
	}, [elements]);

	const requestComputeRects = useMemo(() => {
		const debouncedComputeRects = debounce(() => {
			if (isMounted()) {
				computeRects();
			}
		}, 100);

		return () => {
			hideMask();
			debouncedComputeRects();
		};
	}, [computeRects, hideMask, isMounted]);

	// - Initial render
	// - LayoutData changed
	// - Any editable content changed
	useEffect(() => {
		requestComputeRects();
	}, [fragmentEntryLinks, layoutData, requestComputeRects]);

	// - Any editable added or removed
	useEffect(() => {
		requestComputeRects();
	}, [elements, requestComputeRects]);

	// - Window resize
	// - Window scroll
	useEffect(() => {
		window.addEventListener('resize', requestComputeRects);
		window.addEventListener('scroll', requestComputeRects);

		return () => {
			window.removeEventListener('resize', requestComputeRects);
			window.removeEventListener('scroll', requestComputeRects);
		};
	}, [requestComputeRects]);

	// - Editing item
	// useEffect(() => {
	// 	let id = null;

	// 	const tick = () => {
	// 		computeRects();
	// 		id = setTimeout(tick, 50);
	// 	};

	// 	if (editingItemId) {
	// 		tick();
	// 	}

	// 	return () => {
	// 		clearTimeout(id);
	// 	};
	// }, [computeRects, editingItemId]);

	// - Page Editor sidebar
	useEffect(() => {
		const sidebarElement = document.querySelector(
			'.page-editor__sidebar__content'
		);

		const handleTransitionEnd = event => {
			if (event.target === sidebarElement) {
				requestComputeRects();
			}
		};

		const handleTransitionStart = event => {
			if (event.target === sidebarElement) {
				hideMask();
			}
		};

		sidebarElement.addEventListener('transitionend', handleTransitionEnd);

		sidebarElement.addEventListener(
			'transitionstart',
			handleTransitionStart
		);

		return () => {
			sidebarElement.removeEventListener(
				'transitionend',
				handleTransitionEnd
			);

			sidebarElement.removeEventListener(
				'transitionstart',
				handleTransitionStart
			);
		};
	}, [hideMask, requestComputeRects]);

	// - Product menu
	useEffect(() => {
		const sideNavigation = Liferay.SideNavigation.instance(
			document.querySelector('.product-menu-toggle')
		);

		let sideNavigationListeners = [];

		if (sideNavigation) {
			sideNavigationListeners = [
				sideNavigation.on('open.lexicon.sidenav', requestComputeRects),
				sideNavigation.on('openStart.lexicon.sidenav', hideMask),
				sideNavigation.on(
					'closed.lexicon.sidenav',
					requestComputeRects
				),
				sideNavigation.on('closedStart.lexicon.sidenav', hideMask)
			];
		}

		return () => {
			sideNavigationListeners.forEach(listener =>
				listener.removeListener()
			);
		};
	}, [hideMask, requestComputeRects]);

	return (
		showMask &&
		ReactDOM.createPortal(
			<svg
				className="page-editor__editable-decoration-mask"
				height={window.innerHeight}
				style={{
					left: 0,
					pointerEvents: 'none',
					position: 'fixed',
					top: 0
				}}
				viewBox={`0 0 ${window.innerWidth} ${window.innerHeight}`}
				width={window.innerWidth}
				xmlns="http://www.w3.org/2000/svg"
				xmlnsXlink="http://www.w3.org/1999/xlink"
			>
				{Object.entries(rects).map(([editableId, rect]) => (
					<rect
						className={classNames(
							'page-editor__editable-decoration-mask-rect',
							elementsClassNames[editableId] || ''
						)}
						height={rect.height}
						key={editableId}
						width={rect.width}
						x={rect.x}
						y={rect.y}
					/>
				))}
			</svg>,
			document.body
		)
	);
}

EditableDecorationMask.propTypes = {
	classNames: PropTypes.objectOf(PropTypes.string),
	elements: PropTypes.objectOf(PropTypes.instanceOf(HTMLElement))
};

export default React.memo(EditableDecorationMask);
