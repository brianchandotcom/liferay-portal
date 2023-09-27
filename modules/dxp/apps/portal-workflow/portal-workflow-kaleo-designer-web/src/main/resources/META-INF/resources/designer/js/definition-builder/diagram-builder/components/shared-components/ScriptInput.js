/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {DEFAULT_LANGUAGE} from '../../../source-builder/constants';

const scriptLanguageOptions = [
	{
		label: Liferay.Language.get('groovy'),
		value: 'groovy',
	},
	{
		label: Liferay.Language.get('java'),
		value: 'java',
	},
];

const ScriptInput = ({
	defaultScriptLanguage,
	handleClickCapture,
	inputValue,
	updateSelectedItem,
}) => {
	const [script, setScript] = useState(inputValue);
	const [scriptLanguage, setScriptLanguage] = useState(
		defaultScriptLanguage || DEFAULT_LANGUAGE
	);

	return (
		<>
			<label htmlFor="script-language">
				{Liferay.Language.get('script-language')}
			</label>

			<ClaySelect
				aria-label="Select"
				defaultValue={scriptLanguage}
				id="script-language"
				onChange={({target}) => {
					setScriptLanguage(target.value);
				}}
				onClickCapture={() => handleClickCapture(scriptLanguage)}
			>
				{scriptLanguageOptions.map((item) => (
					<ClaySelect.Option
						key={item.value}
						label={item.label}
						value={item.value}
					/>
				))}
			</ClaySelect>

			<ClayForm.Group>
				<label htmlFor="nodeScript">
					{Liferay.Language.get('script')}
				</label>

				<ClayInput
					component="textarea"
					id="nodeScript"
					onChange={(event) => {
						updateSelectedItem(event);
						setScript(event.target.value);
					}}
					placeholder='returnValue = "Transition Name";'
					type="text"
					value={script}
				/>
			</ClayForm.Group>
		</>
	);
};

export default ScriptInput;

ScriptInput.propTypes = {
	inputValue: PropTypes.oneOfType([PropTypes.object, PropTypes.string]),
	updateSelectedItem: PropTypes.func,
};
