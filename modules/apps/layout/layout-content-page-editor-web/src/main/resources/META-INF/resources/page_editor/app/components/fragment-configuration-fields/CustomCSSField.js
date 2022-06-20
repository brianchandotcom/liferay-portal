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

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

import {useId} from '../../utils/useId';

export default function CustomCSSField() {
	const id = useId();

	return (
		<ClayForm.Group className="page-editor__custom-css-field" small>
			<label htmlFor={id}>
				{Liferay.Language.get('custom-css')} <CustomCSSHelp />
			</label>

			<textarea className="form-control" id={id} />
		</ClayForm.Group>
	);
}

function CustomCSSHelp() {
	const [showPopover, setShowPopover] = useState(false);

	return (
		<ClayPopover
			alignPosition="top"
			className="position-fixed"
			header={Liferay.Language.get('custom-css')}
			onShowChange={setShowPopover}
			show={showPopover}
			trigger={
				<span
					onMouseEnter={() => setShowPopover(true)}
					onMouseLeave={() => setShowPopover(false)}
				>
					<ClayIcon
						className="text-secondary"
						symbol="info-panel-open"
					/>
				</span>
			}
		>
			{Liferay.Language.get(
				'you-can-add-your-own-css-and-include-variables-to-use-existing-tokens'
			)}
		</ClayPopover>
	);
}
