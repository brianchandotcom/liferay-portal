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

import PopoverBase from './PopoverBase.es';
import getCN from 'classnames';
import {Align} from 'metal-position';
import React, {useEffect, useRef, useState} from 'react';
import {PropTypes} from 'prop-types';

const ALIGNMENTS_MAP = {
	bottom: Align.Bottom,
	'bottom-left': Align.BottomLeft,
	'bottom-right': Align.BottomRight,
	left: Align.Left,
	right: Align.Right,
	top: Align.Top,
	'top-left': Align.TopLeft,
	'top-right': Align.TopRight
};

const POSITIONS = [
	'top',
	'top',
	'right',
	'bottom',
	'bottom',
	'bottom',
	'left',
	'top'
];

const getAlignPosition = (source, target, suggestedPosition) => {
	if (!suggestedPosition) {
		suggestedPosition = 'top';
	}

	const position = Align.align(
		source,
		target,
		ALIGNMENTS_MAP[suggestedPosition]
	);

	return POSITIONS[position];
};

const Popover = ({
	alignElement,
	className,
	content,
	footer,
	showArrow,
	suggestedPosition,
	title,
	visible
}) => {
	const [state, setState] = useState({position: null, width: 240});
	const {position, width} = state;

	const elementRef = useRef(null);

	useEffect(() => {
		const width = elementRef.current.offsetWidth;

		setState({width});
	}, [elementRef]);

	useEffect(() => {
		if (visible) {
			setState({
				position: getAlignPosition(
					elementRef.current,
					alignElement,
					suggestedPosition
				)
			});
		}
	}, [alignElement, elementRef, suggestedPosition, visible]);

	const withoutContent = !content;
	const classes = getCN(className, {
		'no-content': withoutContent,
		'popover-large': width > 600
	});

	return (
		<PopoverBase
			className={classes}
			elementRef={elementRef}
			placement={showArrow ? position : null}
			visible={visible}
		>
			{title &&
				(withoutContent ? (
					<PopoverBase.Body>{title()}</PopoverBase.Body>
				) : (
					<PopoverBase.Header>{title()}</PopoverBase.Header>
				))}

			{content && <PopoverBase.Body>{content()}</PopoverBase.Body>}

			{footer && <PopoverBase.Footer>{footer()}</PopoverBase.Footer>}
		</PopoverBase>
	);
};

Popover.propTypes = {
	alignElement: PropTypes.object,
	content: PropTypes.func,
	footer: PropTypes.func,
	showArrow: PropTypes.bool,
	suggestedPosition: PropTypes.string,
	title: PropTypes.func,
	visible: PropTypes.bool
};

export default Popover;
