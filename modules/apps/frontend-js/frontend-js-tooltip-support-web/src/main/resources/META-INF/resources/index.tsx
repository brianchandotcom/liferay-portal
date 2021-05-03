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

import ClayTooltip from '@clayui/tooltip';
import {render, useTimeout} from '@liferay/frontend-js-react-web';

// Hack to get around frontend-js-web not being in TS
// @ts-ignore

import {ALIGN_POSITIONS as POSITIONS, align, delegate} from 'frontend-js-web';
import React, {
	useEffect,
	useLayoutEffect,
	useReducer,
	useRef,
	useState,
} from 'react';

import reducer, {STATES} from './reducer';

const ALIGN_POSITIONS = [
	'top-right',
	'top',
	'top-left',
	'bottom-right',
	'bottom',
	'bottom-left',
	'left',
	'right',
] as const;

const SELECTOR_TOOLTIP = '.tooltip[role="tooltip"]';
const SELECTOR_TRIGGER = `
	.lfr-portal-tooltip,
	.manage-collaborators-dialog .lexicon-icon[data-title]:not(.lfr-portal-tooltip),
	.manage-collaborators-dialog .lexicon-icon[title]:not(.lfr-portal-tooltip),
	.manage-collaborators-dialog [data-restore-title],
	.management-bar [data-title]:not(.lfr-portal-tooltip),
	.management-bar [title]:not(.lfr-portal-tooltip),
	.management-bar [data-restore-title],
	.preview-toolbar-container [data-title]:not(.lfr-portal-tooltip),
	.preview-toolbar-container [title]:not(.lfr-portal-tooltip),
	.preview-tooltbar-containter [data-restore-title],
	.progress-container[data-title],
	.redirect-entries [data-title]:not(.lfr-portal-tooltip),
	.source-editor__fixed-text__help[data-title],
	.upper-tbar [data-title]:not(.lfr-portal-tooltip),
	.upper-tbar [title]:not(.lfr-portal-tooltip),
	.upper-tbar [data-restore-title]
`;

const TRIGGER_HIDE_EVENTS = [
	'mouseout',
	'mouseup',
	'MSPointerUp',
	'pointerup',
	'touchend',
];
const TRIGGER_SHOW_EVENTS = [
	'mouseover',
	'mouseup',
	'MSPointerDown',
	'pointerdown',
	'touchstart',
];

const DEFAULT_TOOLTIP_CONTAINER_ID = 'tooltipContainer';

const getDefaultTooltipContainer = () => {
	let container = document.getElementById(DEFAULT_TOOLTIP_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_TOOLTIP_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
};

const TooltipProvider = () => {
	const delay = useTimeout();

	const [state, dispatch] = useReducer(reducer, {current: STATES.IDLE});
	const tooltipRef = useRef(null);
	const [alignment, setAlignment] = useState(0);

	useEffect(() => {
		let dispose;

		if (state.current === STATES.WAIT_SHOW) {
			dispose = delay(() => dispatch({type: 'showDelayCompleted'}), 500);
		}
		else if (state.current === STATES.WAIT_HIDE) {
			dispose = delay(() => dispatch({type: 'hideDelayCompleted'}), 100);
		}
		else if (state.current === STATES.WAIT_RESHOW) {
			dispose = delay(() => dispatch({type: 'showDelayCompleted'}), 100);
		}

		return dispose;
	}, [delay, state]);

	const saveTitle = (element: HTMLElement) => {
		if (element) {
			const title = element.getAttribute('title');

			if (title) {
				element.setAttribute('data-restore-title', title);
				element.removeAttribute('title');
			}
			else if (element.tagName === 'svg') {
				const titleTag = element.querySelector('title');

				if (titleTag) {
					element.setAttribute(
						'data-restore-title',
						titleTag.innerHTML
					);

					titleTag.remove();
				}
			}
		}
	};

	const restoreTitle = (element?: HTMLElement) => {
		if (element) {
			const title = element.getAttribute('data-restore-title');

			if (title) {
				if (element.tagName === 'svg') {
					const titleTag = document.createElement('title');

					titleTag.innerHTML = title;

					element.appendChild(titleTag);
				}
				else {
					element.setAttribute('title', title);
				}

				element.removeAttribute('data-restore-title');
			}
		}
	};

	useEffect(() => {
		const TRIGGER_SHOW_HANDLES = TRIGGER_SHOW_EVENTS.map((eventName) => {
			return delegate(
				document.body,
				eventName,
				SELECTOR_TRIGGER,
				(event: MouseEvent & {delegateTarget: HTMLElement}) => {
					saveTitle(event.delegateTarget);

					dispatch({target: event.delegateTarget, type: 'show'});
				}
			);
		});

		const TRIGGER_HIDE_HANDLES = TRIGGER_HIDE_EVENTS.map((eventName) => {
			return delegate(document.body, eventName, SELECTOR_TRIGGER, () => {
				dispatch({type: 'hide'});

				restoreTitle(state.target);
			});
		});

		const TOOLTIP_ENTER = delegate(
			document.body,
			'mouseover',
			SELECTOR_TOOLTIP,
			() => dispatch({target: state.target, type: 'show'})
		);

		const TOOLTIP_LEAVE = delegate(
			document.body,
			'mouseout',
			SELECTOR_TOOLTIP,
			() => dispatch({type: 'hide'})
		);

		return () => {
			[
				TOOLTIP_ENTER,
				TOOLTIP_LEAVE,
				...TRIGGER_HIDE_HANDLES,
				...TRIGGER_SHOW_HANDLES,
			].forEach((handle) => handle.dispose());
		};
	}, [state]);

	useLayoutEffect(() => {
		if (state.target && tooltipRef.current) {
			setAlignment(
				align(tooltipRef.current, state.target, POSITIONS.BottomCenter)
			);
		}
	}, [state.target]);

	return state.target ? (
		<ClayTooltip
			alignPosition={ALIGN_POSITIONS[alignment]}
			ref={tooltipRef}
			show={state.current.show}
		>
			<div
				dangerouslySetInnerHTML={{
					__html:
						state.target.title ||
						state.target.dataset.restoreTitle ||
						state.target.dataset.title ||
						'',
				}}
			/>
		</ClayTooltip>
	) : null;
};

export default () => {
	render(<TooltipProvider />, {}, getDefaultTooltipContainer());
};
