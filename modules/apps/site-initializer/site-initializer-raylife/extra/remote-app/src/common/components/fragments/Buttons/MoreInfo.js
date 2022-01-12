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

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React, {useContext} from 'react';
import {WebContentContext} from '../../../../routes/get-a-quote/context/WebContentProvider';

export function MoreInfoButton({
	callback,
	event,
	label = 'More Info',
	selected,
	value,
}) {
	const [, dispatchEvent] = useContext(WebContentContext);

	const updateState = () => {
		dispatchEvent(
			{
				...value,
				hide: selected,
			},
			event
		);
		callback();
	};

	return (
		<ClayLabel
			className={classNames(
				'btn-info-panel rounded-sm m-0 p-0 justify-content-center ms-auto',
				{
					'label-inverse-primary': selected,
					'label-tonal-primary': !selected,
				}
			)}
			onClick={updateState}
		>
			<div className="align-items-center d-flex justify-content-center m-0 px-1 px-lg-2 px-sm-2 py-1 py-lg-1 py-sm-1">
				<span className="p-0 text-center text-paragraph-sm">
					{label}
				</span>

				<span className="inline-item inline-item-after">
					<ClayIcon
						symbol={
							selected
								? 'question-circle-full'
								: 'question-circle'
						}
					/>
				</span>
			</div>
		</ClayLabel>
	);
}
