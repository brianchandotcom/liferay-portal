/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	precision,
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
				{errors.includes('decimals') && (
					<li className="list-group-item px-0 py-1 text-truncate">
						<small
							className={classNames({
								'text-danger': true,
							})}
							dangerouslySetInnerHTML={{
								__html: sub(
									Liferay.Language.get(
										'quantity-allows-for-x-decimal-places'
									),
									`<b>${precision}</b>`
								),
							}}
						/>
					</li>
				)}

				{min > 0 && (
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

				{multiple > 0 && (
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
