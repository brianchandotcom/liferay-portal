/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

function SearchInput({disabled, onChange, onEnter}) {
	const [value, setValue] = useState('');

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem>
				<ClayInput
					aria-label={Liferay.Language.get('search')}
					className="form-control input-group-inset input-group-inset-after"
					disabled={disabled}
					onChange={(event) => {
						onChange(event.target.value);
						setValue(event.target.value);
					}}
					onKeyDown={(event) => {
						if (event.key === 'Enter') {
							event.preventDefault();

							if (onEnter) {
								onEnter();
							}
						}
					}}
					placeholder={Liferay.Language.get('search')}
					type="text"
					value={value}
				/>

				{value && !onEnter ? (
					<ClayInput.GroupInsetItem after tag="span">
						<ClayButton
							aria-label={Liferay.Language.get('clear')}
							displayType="unstyled"
							onClick={() => {
								onChange('');
								setValue('');
							}}
							title={Liferay.Language.get('clear')}
						>
							<ClayIcon symbol="times-circle" />
						</ClayButton>
					</ClayInput.GroupInsetItem>
				) : (
					<ClayInput.GroupInsetItem after tag="span">
						<ClayButton
							aria-label={Liferay.Language.get('search')}
							disabled={disabled}
							displayType="unstyled"
							onClick={onEnter}
							title={Liferay.Language.get('search')}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ClayInput.GroupInsetItem>
				)}
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

export default SearchInput;
