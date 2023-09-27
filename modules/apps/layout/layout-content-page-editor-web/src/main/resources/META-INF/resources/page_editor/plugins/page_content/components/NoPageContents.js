/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useSelector} from '../../../app/contexts/StoreContext';
import {formIsRestricted} from '../../../app/utils/formIsRestricted';

export default function NoPageContents() {
	const layoutData = useSelector((state) => state.layoutData);
	const masterLayoutData = useSelector((state) => state.masterLayoutData);

	const items = [
		...Object.values(layoutData.items),
		...Object.values(masterLayoutData?.items ?? {}),
	];

	const hasRestrictedForm = items.some(
		(item) =>
			item.type === LAYOUT_DATA_ITEM_TYPES.form && formIsRestricted(item)
	);

	return hasRestrictedForm && Liferay.FeatureFlags['LPS-169923'] ? (
		<ClayAlert aria-live="polite" className="m-3" displayType="secondary">
			{Liferay.Language.get(
				'this-content-cannot-be-displayed-due-to-permission-restrictions'
			)}
		</ClayAlert>
	) : (
		<ClayAlert
			aria-live="polite"
			className="m-3"
			displayType="info"
			title={Liferay.Language.get('info')}
		>
			{Liferay.Language.get('there-is-no-content-on-this-page')}
		</ClayAlert>
	);
}
