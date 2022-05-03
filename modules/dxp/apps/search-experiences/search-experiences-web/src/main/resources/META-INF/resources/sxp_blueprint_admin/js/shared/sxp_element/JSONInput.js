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

import getCN from 'classnames';
import React, {useContext, useEffect, useState} from 'react';

import CodeMirrorEditor from '../CodeMirrorEditor';
import ThemeContext from '../ThemeContext';

function JSONInput({
	autocompleteSchema,
	disabled,
	value,
	label = Liferay.Language.get('json[stands-for]'),
	nullable,
	readOnly = false,
	required = true,
	name,
	setFieldValue,
	setFieldTouched,
}) {
	const [editValue, setEditValue] = useState(value);
	const {featureFlagLps148749} = useContext(ThemeContext);

	useEffect(() => {
		setFieldValue(name, editValue);
	}, [editValue, name, setFieldValue]);

	// Adding useEffect since CodeMirrorEditor has issues with updating the 'name' prop
	// when called directly inside its onChange

	return (
		<div
			className={getCN('custom-json', {
				disabled,
			})}
			onBlur={() => setFieldTouched(name)}
		>
			<label>
				{label}

				{(!required || nullable) && (
					<span className="optional-text">
						{Liferay.Language.get('optional')}
					</span>
				)}
			</label>

			<CodeMirrorEditor
				autocompleteSchema={
					featureFlagLps148749 ? autocompleteSchema : null
				}
				onChange={setEditValue}
				readOnly={readOnly}
				value={editValue}
			/>
		</div>
	);
}

export default JSONInput;
