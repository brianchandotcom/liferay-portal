/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React, {useEffect, useState} from 'react';

import CodeMirrorEditor from '../CodeMirrorEditor';

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
				autocompleteSchema={autocompleteSchema}
				onChange={setEditValue}
				readOnly={readOnly}
				value={editValue}
			/>
		</div>
	);
}

export default JSONInput;
