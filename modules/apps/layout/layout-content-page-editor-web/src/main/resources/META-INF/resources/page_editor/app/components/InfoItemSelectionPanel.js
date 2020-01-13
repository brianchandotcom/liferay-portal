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
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext} from 'react';

import {openInfoItemSelector} from '../../core/openInfoItemSelector';
import {ConfigContext} from '../config/index';

export default function InfoItemSelectionPanel({
	backgroundImageTitle = Liferay.Language.get('none'),
	onItemSelectorChanged
}) {
	const config = useContext(ConfigContext);

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor="itemSelectorInput">
					{Liferay.Language.get('content')}
				</label>
				<div className="d-flex">
					<ClayInput
						className="mr-2"
						id="itemSelectorInput"
						readOnly
						type="text"
						value={backgroundImageTitle}
					/>

					<ClayButton.Group>
						<ClayButton
							displayType="secondary"
							onClick={() =>
								openInfoItemSelector(
									onItemSelectorChanged,
									config
								)
							}
							small
						>
							<ClayIcon symbol="plus" />
						</ClayButton>
					</ClayButton.Group>
				</div>
			</ClayForm.Group>
			<ClayForm.Group small>
				<label htmlFor="containerBackgroundImageFieldSelect">
					{Liferay.Language.get('field')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('field')}
					disabled
					id="containerBackgroundImageFieldSelect"
					onChange={({target: {value}}) => value}
					options={[
						{
							label: Liferay.Language.get('manual-selection'),
							value: 'manual_selection'
						},
						{
							label: Liferay.Language.get('content-mapping'),
							value: 'content_mapping'
						}
					]}
				/>
			</ClayForm.Group>
		</>
	);
}
