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

import {AOP} from 'frontend-js-web';
import {Drag, DragDrop} from 'metal-drag-drop';
import {Position} from 'metal-position';

import {FRAGMENTS_EDITOR_DRAGGING_CLASS} from './constants';

let _getRegionInterceptor = null;

/**
 * Stops intercepting metal-position getRegion calls
 * @private
 * @review
 * @see {_interceptGetRegion}
 */
function _stopInterceptGetRegion() {
	if (_getRegionInterceptor) {
		_getRegionInterceptor.detach();

		_getRegionInterceptor = null;
	}
}

/**
 * Uses metal-aop to intercept metal-position getRegion calls
 * and take into account bootstrap's negative margins.
 * @private
 * @review
 * @see {_stopInterceptGetRegion}
 */
function _interceptGetRegion() {
	_getRegionInterceptor =
		_getRegionInterceptor ||
		AOP.after(
			target => {
				let newReturnVal;

				if (
					target instanceof HTMLElement &&
					target.classList.contains('col') &&
					target.parentElement &&
					target.parentElement.classList.contains('row')
				) {
					const currentRetVal = {...AOP.currentRetVal};

					const parentComputedStyle = window.getComputedStyle(
						target.parentElement
					);

					const negativeLeftMargin = Math.min(
						parseInt(parentComputedStyle.marginLeft, 10) || 0,
						0
					);
					const negativeRightMargin = Math.min(
						parseInt(parentComputedStyle.marginRight, 10) || 0,
						0
					);

					currentRetVal.width =
						currentRetVal.width +
						negativeLeftMargin +
						negativeRightMargin;
					currentRetVal.left -= negativeLeftMargin;
					currentRetVal.right += negativeRightMargin;

					newReturnVal = AOP.alterReturn(currentRetVal);
				}

				return newReturnVal;
			},
			Position,
			'getRegion'
		);
}

/**
 * @param {object} dragDropOptions
 * @review
 */
function initializeDragDrop(dragDropOptions) {
	const dragDrop = new DragDrop({
		autoScroll: true,
		dragPlaceholder: Drag.Placeholder.CLONE,
		draggingClass: FRAGMENTS_EDITOR_DRAGGING_CLASS,
		scrollContainers: '.fragment-entry-link-list-wrapper',
		...dragDropOptions
	});

	dragDrop.on('dispose', _stopInterceptGetRegion);
	dragDrop.on(Drag.Events.START, _interceptGetRegion);
	dragDrop.on(DragDrop.Events.END, _stopInterceptGetRegion);

	return dragDrop;
}

export {initializeDragDrop};
