/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
