/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiSelect from '@clayui/multi-select';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';

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

interface BlockedDomainsContainerProps {
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (value: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function BlockedDomainsContainer({
	onSubmit,
	setValues,
	values,
}: BlockedDomainsContainerProps) {
	const settings = normalizeFieldSettings(values.objectFieldSettings);

	const items = domainsToItems((settings.blockedDomains as string) || '');

	const handleChange = (newItems: DomainItem[]) => {
		const domainsValue = itemsToDomains(newItems);

		const updatedSettings = domainsValue
			? updateFieldSettings(values.objectFieldSettings, {
					name: 'blockedDomains',
					value: domainsValue,
				})
			: removeFieldSettings(['blockedDomains'], values);

		setValues({objectFieldSettings: updatedSettings});

		if (onSubmit) {
			onSubmit({...values, objectFieldSettings: updatedSettings});
		}
	};

	return (
		<FieldBase label={Liferay.Language.get('domains')}>
			<ClayMultiSelect
				items={items}
				onItemsChange={handleChange}
				placeholder="@example.com"
			/>
		</FieldBase>
	);
}
