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

import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

const ScriptInput = ({inputValue, updateSelectedItem}) => (
	<ClayForm.Group>
		<label htmlFor="nodeScript">
			{`${Liferay.Language.get('script')} (${Liferay.Language.get(
				'groovy'
			)})`}
		</label>

		<ClayInput
			component="textarea"
			id="nodeScript"
			onChange={updateSelectedItem}
			placeholder='returnValue = "Transition Name";'
			type="text"
			value={inputValue}
		/>
	</ClayForm.Group>
);

export default ScriptInput;

ScriptInput.propTypes = {
	inputValue: PropTypes.oneOfType([PropTypes.object, PropTypes.string]),
	updateSelectedItem: PropTypes.func,
};
