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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useMemo, useState} from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import {useId} from '../../utils/useId';

export default function CustomCSSField({field, item, onValueSelect, value}) {
	const id = useId();

	const defaultValue = useMemo(() => {
		return `.${getLayoutDataItemUniqueClassName(item.itemId)} {\n\n}`;
	}, [item.itemId]);

	const [customCSS, setCustomCSS] = useControlledState(value || defaultValue);

	return (
		<ClayForm.Group className="page-editor__custom-css-field" small>
			<div className="align-items-end d-flex justify-content-between">
				<label htmlFor={id}>
					{Liferay.Language.get('custom-css')} <CustomCSSHelp />
				</label>

				<ClayButtonWithIcon
					className="mb-2 p-0 page-editor__custom-css-field__expand-button"
					displayType="unstyled"
					monospaced
					small
					symbol="expand"
				/>
			</div>

			<textarea
				className="form-control text-3"
				id={id}
				onBlur={() => {
					if (defaultValue.trim() !== customCSS?.trim()) {
						onValueSelect(field.name, customCSS);
					}
				}}
				onChange={(event) => setCustomCSS(event.target.value)}
				value={customCSS}
			/>
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
