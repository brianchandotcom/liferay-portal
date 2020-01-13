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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useContext} from 'react';

import {openImageSelector} from '../../core/openImageSelector';
import {ConfigContext} from '../config/index';

export function ManualSelectionPanel({
	backgroundImageTitle = Liferay.Language.get('none'),
	onClearButtonPressed,
	onImageSelected
}) {
	const {imageSelectorURL, portletNamespace} = useContext(ConfigContext);

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor="containerBackgroundImageTitle">
					{Liferay.Language.get('image')}
				</label>
				<ClayInput
					id="containerBackgroundImageTitle"
					placeholder={Liferay.Language.get('none')}
					readOnly
					sizing="sm"
					value={backgroundImageTitle}
				/>
			</ClayForm.Group>
			<ClayButton.Group>
				<div className="btn-group-item">
					<ClayButton
						displayType="secondary"
						onClick={() =>
							openImageSelector(
								{imageSelectorURL, portletNamespace},
								image => {
									onImageSelected(image);
								}
							)
						}
						small
					>
						{Liferay.Language.get('select')}
					</ClayButton>
				</div>
				<div className="btn-group-item">
					<ClayButton
						borderless
						displayType="secondary"
						onClick={onClearButtonPressed}
						small
					>
						{Liferay.Language.get('clear')}
					</ClayButton>
				</div>
			</ClayButton.Group>
		</>
	);
}
