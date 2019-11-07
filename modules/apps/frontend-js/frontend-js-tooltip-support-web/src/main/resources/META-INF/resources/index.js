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
import {render, useTimeout} from 'frontend-js-react-web';
import dom from 'metal-dom';
import {Align} from 'metal-position';
import React, {
	useEffect,
	useReducer,
	useRef,
	useState,
	useLayoutEffect
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
	'right'
];

const SELECTOR_TOOLTIP = '.tooltip[role="tooltip"]';
const SELECTOR_TRIGGER = `
	.lfr-portal-tooltip,
	.manage-collaborators-dialog .lexicon-icon[data-title]:not(.lfr-portal-tooltip),
	.manage-collaborators-dialog .lexicon-icon[title]:not(.lfr-portal-tooltip),
	.management-bar [data-title]:not(.lfr-portal-tooltip),
	.management-bar [title]:not(.lfr-portal-tooltip),
	.preview-toolbar-container [data-title]:not(.lfr-portal-tooltip),
	.preview-toolbar-container [title]:not(.lfr-portal-tooltip),
	.progress-container[data-title],
	.source-editor__fixed-text__help[data-title],
	.taglib-discussion [data-title]:not(.lfr-portal-tooltip),
	.taglib-discussion [title]:not(.lfr-portal-tooltip):not([title=""]),
	.upper-tbar [data-title]:not(.lfr-portal-tooltip),
	.upper-tbar [title]:not(.lfr-portal-tooltip)
`;

const TRIGGER_HIDE_EVENTS = [
	'mouseleave',
	'mouseup',
	'MSPointerUp',
	'touchend'
];
const TRIGGER_SHOW_EVENTS = [
	'mouseenter',
	'mouseup',
	'MSPointerDown',
	'touchstart'
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
		} else if (state.current === STATES.WAIT_HIDE) {
			dispose = delay(() => dispatch({type: 'hideDelayCompleted'}), 100);
		} else if (state.current === STATES.WAIT_RESHOW) {
			dispose = delay(() => dispatch({type: 'showDelayCompleted'}), 100);
		}

		return dispose;
	}, [delay, state]);

	useEffect(() => {
		const TRIGGER_SHOW_HANDLES = TRIGGER_SHOW_EVENTS.map(eventName => {
			return dom.delegate(
				document.body,
				eventName,
				SELECTOR_TRIGGER,
				event => dispatch({target: event.delegateTarget, type: 'show'})
			);
		});

		const TRIGGER_HIDE_HANDLES = TRIGGER_HIDE_EVENTS.map(eventName => {
			return dom.delegate(
				document.body,
				eventName,
				SELECTOR_TRIGGER,
				() => dispatch({type: 'hide'})
			);
		});

		const TOOLTIP_ENTER = dom.delegate(
			document.body,
			'mouseenter',
			SELECTOR_TOOLTIP,
			() => dispatch({target: state.target, type: 'show'})
		);

		const TOOLTIP_LEAVE = dom.delegate(
			document.body,
			'mouseleave',
			SELECTOR_TOOLTIP,
			() => dispatch({type: 'hide'})
		);

		return () => {
			[
				TOOLTIP_ENTER,
				TOOLTIP_LEAVE,
				...TRIGGER_HIDE_HANDLES,
				...TRIGGER_SHOW_HANDLES
			].forEach(handle => handle.dispose());
		};
	}, [state]);

	useLayoutEffect(() => {
		if (state.target && tooltipRef.current) {
			setAlignment(
				Align.align(
					tooltipRef.current,
					state.target,
					Align.BottomCenter
				)
			);
		}
	}, [state]);

	return state.target ? (
		<ClayTooltip
			alignPosition={ALIGN_POSITIONS[alignment]}
			ref={tooltipRef}
			show={state.current.show}
		>
			<div
				dangerouslySetInnerHTML={{
					__html: state.target.title || state.target.dataset.title
				}}
			/>
		</ClayTooltip>
	) : null;
};

export default () => {
	render(() => <TooltipProvider />, {}, getDefaultTooltipContainer());
};
