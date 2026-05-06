/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {Toggle} from '@liferay/object-js-components-web';
import {FieldBase} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {
	DomainItem,
	domainsToItems,
	itemsToDomains,
} from '../../../../utils/domains';
import {
	normalizeFieldSettings,
	removeFieldSettings,
	updateFieldSettings,
} from '../../../../utils/fieldSettings';

interface AutocompleteContainerProps {
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (value: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function AutocompleteContainer({
	onSubmit,
	setValues,
	values,
}: AutocompleteContainerProps) {
	const settings = normalizeFieldSettings(values.objectFieldSettings);

	const [autocompleteEnabled, setAutocompleteEnabled] = useState(
		String(settings.autocompleteEnabled) === 'true'
	);

	const items = domainsToItems(String(settings.autocompleteDomains || ''));

	const handleChange = (newItems: DomainItem[]) => {
		const domainsValue = itemsToDomains(newItems);

		const updatedSettings = domainsValue
			? updateFieldSettings(values.objectFieldSettings, {
					name: 'autocompleteDomains',
					value: domainsValue,
				})
			: removeFieldSettings(['autocompleteDomains'], values);

		setValues({objectFieldSettings: updatedSettings});

		if (onSubmit) {
			onSubmit({...values, objectFieldSettings: updatedSettings});
		}
	};

	const handleToggle = (toggled: boolean) => {
		setAutocompleteEnabled(toggled);

		let updatedSettings: ObjectFieldSetting[];

		if (toggled) {
			updatedSettings = updateFieldSettings(values.objectFieldSettings, {
				name: 'autocompleteEnabled',
				value: true,
			});
		}
		else {
			updatedSettings = removeFieldSettings(
				['autocompleteEnabled', 'autocompleteDomains'],
				values
			);
		}

		setValues({objectFieldSettings: updatedSettings});

		if (onSubmit) {
			onSubmit({...values, objectFieldSettings: updatedSettings});
		}
	};

	return (
		<>
			<ClayForm.Group>
				<Toggle
					label={Liferay.Language.get('enable-autocomplete')}
					onToggle={handleToggle}
					toggled={autocompleteEnabled}
				/>
			</ClayForm.Group>

			{autocompleteEnabled && (
				<FieldBase label={Liferay.Language.get('domains')}>
					<ClayMultiSelect
						items={items}
						onItemsChange={handleChange}
						placeholder="@liferay.com"
					/>
				</FieldBase>
			)}
		</>
	);
}
