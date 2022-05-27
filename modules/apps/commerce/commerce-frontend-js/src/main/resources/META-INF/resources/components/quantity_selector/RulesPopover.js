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

import ClayPopover from '@clayui/popover';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useLayoutEffect, useRef, useState} from 'react';

export default function RulesPopover({
	alignment,
	errors,
	inputRef,
	max,
	min,
	multiple,
}) {
	const popoverRef = useRef();
	const [popoverPosition, setPopoverPosition] = useState({});

	useLayoutEffect(() => {
		const position = {
			transform: 'translateX(-50%)',
		};

		if (alignment || !inputRef?.current) {
			return setPopoverPosition({
				...position,
				alignment,
				[alignment === 'bottom'
					? 'top'
					: 'bottom']: 'calc(100% + 10px)',
				left: '50px',
			});
		}

		const {
			bottom,
			left,
			top,
			width,
		} = inputRef.current.getBoundingClientRect();
		const {
			height: popoverHeight,
		} = popoverRef.current.getBoundingClientRect();

		position.left = left + width / 2;

		const availableSpace = window.pageYOffset + top;

		const topAligned = popoverHeight > availableSpace;

		if (topAligned) {
			position.alignment = 'bottom';
			position.top = window.pageYOffset + bottom + 4;
		}
		else {
			position.alignment = 'top';
			position.top = window.pageYOffset + top - popoverHeight - 8;
		}

		setPopoverPosition(position);
	}, [alignment, inputRef]);

	const popover = (
		<ClayPopover
			alignPosition={popoverPosition.alignment}
			className="quantity-selector-popover"
			ref={popoverRef}
			style={popoverPosition}
		>
			<ul className="list-group list-group-flush mb-0">
				{min > 1 && (
					<li className="list-group-item px-0 py-1 text-truncate">
						<small
							className={classNames({
								'text-danger': errors.includes('min'),
							})}
							dangerouslySetInnerHTML={{
								__html: sub(
									Liferay.Language.get(
										'min-quantity-per-order-is-x'
									),
									`<b>${min}</b>`
								),
							}}
						/>
					</li>
				)}

				{max && (
					<li className="list-group-item px-0 py-1 text-truncate">
						<small
							className={classNames({
								'text-danger': errors.includes('max'),
							})}
							dangerouslySetInnerHTML={{
								__html: sub(
									Liferay.Language.get(
										'max-quantity-per-order-is-x'
									),
									`<b>${max}</b>`
								),
							}}
						/>
					</li>
				)}

				{multiple > 1 && (
					<li className="list-group-item px-0 py-1 text-truncate">
						<small
							className={classNames({
								'text-danger': errors.includes('multiple'),
							})}
							dangerouslySetInnerHTML={{
								__html: sub(
									sub(
										Liferay.Language.get(
											'quantity-must-be-a-multiple-of-x'
										),
										`<b>${multiple}</b>`
									)
								),
							}}
						/>
					</li>
				)}
			</ul>
		</ClayPopover>
	);

	if (alignment) {
		return popover;
	}

	return <ReactPortal container={document.body}>{popover}</ReactPortal>;
}
