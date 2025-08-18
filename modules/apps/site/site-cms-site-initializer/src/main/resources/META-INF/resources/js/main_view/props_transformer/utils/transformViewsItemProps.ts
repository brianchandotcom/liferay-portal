/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IView} from '@liferay/frontend-data-set-web';

import dateFormat from '../../../common/utils/dateFormat';

export default function transformViewsItemProps(views: IView[]) {
	return views.map((view) => {
		if (view.name === 'cards') {
			view.setItemComponentProps = ({item, props}) => {
				return {
					...props,
					description: dateFormat(item.dateModified),
				};
			};
		}

		return view;
	});
}
