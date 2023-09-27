/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				'btn-info-panel flex-shrink-0 rounded-sm m-0 p-0 justify-content-center ms-auto',
				{
					'label-inverse-primary': selected,
					'label-tonal-primary': !selected,
				}
			)}
			onClick={updateState}
		>
			<div className="align-items-center d-flex justify-content-center m-0 px-1 px-lg-2 px-sm-2 py-1 py-lg-1 py-sm-1">
				<span className="flex-shrink-0 p-0 text-center text-paragraph-sm">
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
