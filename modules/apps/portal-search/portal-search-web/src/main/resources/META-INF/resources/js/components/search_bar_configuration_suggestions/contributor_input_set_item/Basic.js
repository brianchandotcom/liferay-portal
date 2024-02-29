/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {CONTRIBUTOR_TYPES} from '../../../utils/types/contributorTypes';
import InputSetItemHeader from './InputSetItemHeader';
import CharacterThresholdInput from './inputs/CharacterThresholdInput';
import DisplayGroupNameInput from './inputs/DisplayGroupNameInput';
import SizeInput from './inputs/SizeInput';

function Basic({index, onBlur, onInputSetItemChange, touched, value}) {
	const _handleChangeAttribute = (property) => (event) => {
		onInputSetItemChange(index, {
			attributes: {
				...value.attributes,
				[property]: event.target.value,
			},
		});
	};

	const _handleLabelChange = (value) => {
		let title = '';
		let description = '';

		if (value.contributorName === CONTRIBUTOR_TYPES.COMMERCE) {
			title = Liferay.Language.get('commerce-suggestions-contributor');
			description = Liferay.Language.get(
				'commerce-suggestions-contributor-help'
			);
		}
		else {
			title = Liferay.Language.get('basic-suggestions-contributor');
			description = Liferay.Language.get(
				'basic-suggestions-contributor-help'
			);
		}

		return (
			<InputSetItemHeader>
				<InputSetItemHeader.Title>{title}</InputSetItemHeader.Title>

				<InputSetItemHeader.Description>
					{description}
				</InputSetItemHeader.Description>
			</InputSetItemHeader>
		);
	};

	return (
		<>
			{_handleLabelChange(value)}

			<div className="c-mb-3 form-group-autofit">
				<DisplayGroupNameInput
					onBlur={onBlur('displayGroupName')}
					onChange={onInputSetItemChange(index, 'displayGroupName')}
					touched={touched.displayGroupName}
					value={value.displayGroupName}
				/>

				<SizeInput
					onBlur={onBlur('size')}
					onChange={onInputSetItemChange(index, 'size')}
					touched={touched.size}
					value={value.size}
				/>
			</div>

			<div className="c-mb-0 form-group-autofit">
				<CharacterThresholdInput
					onBlur={onBlur('attributes.characterThreshold')}
					onChange={_handleChangeAttribute('characterThreshold')}
					touched={touched['attributes.characterThreshold']}
					value={value.attributes?.characterThreshold}
				/>
			</div>
		</>
	);
}

export default Basic;
