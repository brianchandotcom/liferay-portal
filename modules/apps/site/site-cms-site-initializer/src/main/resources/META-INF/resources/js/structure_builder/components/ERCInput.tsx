/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useId} from 'frontend-js-components-web';
import React, {useState} from 'react';

export default function ERCInput({
	onValueChange,
	value: initialValue,
}: {
	onValueChange: (value: string) => void;
	value: string;
}) {
	const id = useId();

	const [value, setValue] = useState(initialValue);

	return (
		<ClayForm.Group>
			<label htmlFor={id}>
				{Liferay.Language.get('erc')}

				<ClayIcon
					className="ml-1 reference-mark"
					focusable="false"
					role="presentation"
					symbol="asterisk"
				/>

				<ClayIcon
					className="lfr-portal-tooltip ml-1 text-secondary"
					data-title={Liferay.Language.get(
						'unique-key-for-referencing-the-object-definition'
					)}
					focusable="false"
					symbol="question-circle"
				/>
			</label>

			<ClayInput
				id={id}
				onBlur={() => onValueChange(value)}
				onChange={(event) => setValue(event.target.value)}
				type="text"
				value={value}
			/>
		</ClayForm.Group>
	);
}
