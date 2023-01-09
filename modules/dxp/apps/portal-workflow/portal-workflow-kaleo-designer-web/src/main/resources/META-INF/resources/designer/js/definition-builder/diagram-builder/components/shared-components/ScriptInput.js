/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

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
	showSelectScriptLanguage = false,
	updateSelectedItem,
}) => {
	const [script, setScript] = useState(inputValue);
	const [scriptLanguage, setScriptLanguage] = useState(
		defaultScriptLanguage || 'groovy'
	);

	return (
		<>
			{showSelectScriptLanguage && (
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
						onClickCapture={() =>
							handleClickCapture(scriptLanguage)
						}
					>
						{scriptLanguageOptions.map((item) => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClaySelect>
				</>
			)}

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
