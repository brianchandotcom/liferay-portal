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
import {ALIGN_POSITIONS, align} from 'frontend-js-web';
import {PropTypes} from 'prop-types';
import React, {useEffect, useState} from 'react';

import PopoverBase from './PopoverBase.es';

const ALIGNMENTS_MAP = {
	bottom: ALIGN_POSITIONS.Bottom,
	'bottom-left': ALIGN_POSITIONS.BottomLeft,
	'bottom-right': ALIGN_POSITIONS.BottomRight,
	left: ALIGN_POSITIONS.Left,
	right: ALIGN_POSITIONS.Right,
	top: ALIGN_POSITIONS.Top,
	'top-left': ALIGN_POSITIONS.TopLeft,
	'top-right': ALIGN_POSITIONS.TopRight,
};

const POSITIONS = [
	'top',
	'top',
	'right',
	'bottom',
	'bottom',
	'bottom',
	'left',
	'top',
];

const getAlignPosition = (source, target, suggestedPosition) => {
	if (!suggestedPosition) {
		suggestedPosition = 'top';
	}

	const position = align(source, target, ALIGNMENTS_MAP[suggestedPosition]);

	return POSITIONS[position];
};

const Popover = ({
	alignElement,
	className,
	content,
	footer,
	forwardRef,
	showArrow,
	suggestedPosition,
	title,
	visible,
}) => {
	const [state, setState] = useState({position: null, width: 240});
	const {position, width} = state;

	useEffect(() => {
		if (forwardRef && forwardRef.current) {
			const width = forwardRef.current.offsetWidth;

			setState({width});
		}
	}, [forwardRef]);

	useEffect(() => {
		if (forwardRef && forwardRef.current && visible) {
			setState({
				position: getAlignPosition(
					forwardRef.current,
					alignElement,
					suggestedPosition
				),
			});
		}
	}, [alignElement, forwardRef, suggestedPosition, visible]);

	const withoutContent = !content;

	return (
		<PopoverBase
			className={classNames(className, {
				'no-content': withoutContent,
				'popover-large': width > 600,
			})}
			forwardRef={forwardRef}
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
	visible: PropTypes.bool,
};

export default React.forwardRef((props, ref) => (
	<Popover {...props} forwardRef={ref} />
));
