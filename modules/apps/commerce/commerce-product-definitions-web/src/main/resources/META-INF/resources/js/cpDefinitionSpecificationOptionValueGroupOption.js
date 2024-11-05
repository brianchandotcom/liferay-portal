/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import React from 'react';

const CPDefinitionSpecificationOptionValueGroupOption = ({
	portletNamespace,
	selectOptionsMap,
}) => {
	return (
		<>
			<ClayForm.Group aria-required={true}>
				<label
					aria-required={true}
					className="control-label"
					htmlFor="value"
					id="value-label"
				>
					{Liferay.Language.get('value')}
				</label>

				<ClaySelect name={`${portletNamespace}listTypeEntriesSelect`}>
					<ClaySelect.Option label="Select an option" />

					{Object.entries(selectOptionsMap).map(([key, value]) => (
						<ClaySelect.OptGroup key={key} label={key}>
							{value.map((selectOption) => (
								<ClaySelect.Option
									key={selectOption.value}
									label={selectOption.label}
									selected={selectOption.selected}
									value={selectOption.value}
								/>
							))}
						</ClaySelect.OptGroup>
					))}
				</ClaySelect>
			</ClayForm.Group>
		</>
	);
};

export default CPDefinitionSpecificationOptionValueGroupOption;
