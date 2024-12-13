/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React, {useEffect, useState} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
import {useSelector} from '../../../../../../app/contexts/StoreContext';
import {hasLocalizableFields} from '../../../../../../app/utils/hasLocalizableFields';

export default function LocalizationSelectAlert() {
	const [loading, setLoading] = useState(true);
	const [containsLocalizableFields, setContainsLocalizableFields] =
		useState(false);
	const state = useSelector((state) => state);

	useEffect(() => {
		const compute = async () => {
			for (const item of Object.values(state.layoutData.items)) {
				if (item.type === LAYOUT_DATA_ITEM_TYPES.form) {
					if (await hasLocalizableFields(state, item.itemId)) {
						return true;
					}
				}
			}

			return false;
		};

		compute().then((result) => {
			setContainsLocalizableFields(result);
			setLoading(false);
		});
	}, [state]);

	if (loading) {
		return null;
	}

	return (
		<ClayAlert displayType="info" title={Liferay.Language.get('info')}>
			{containsLocalizableFields
				? Liferay.Language.get(
						'localization-applies-to-all-form-fields-on-the-page-defined-as-localizable'
					)
				: Liferay.Language.get(
						'display-at-least-one-localizable-form-field-on-the-page-to-activate-this-configuration'
					)}
		</ClayAlert>
	);
}
