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
