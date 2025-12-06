/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import {DSRInitializer} from '../index';

export default function propsTransformer({
	creationMenu,
	...otherProps
}: {
	creationMenu: any;
}) {
	return {
		...otherProps,
		creationMenu: {
			...creationMenu,
			primaryItems: creationMenu.primaryItems.map(
				(item: {data?: {action?: string}}) => {
					return {
						...item,
						onClick() {
							const action = item?.data?.action;

							if (action && action === 'addDigitalSalesRoom') {
								return openModal({
									containerProps: {
										className: '',
									},
									contentComponent: ({
										closeModal,
									}: {
										closeModal: () => void;
									}) =>
										DSRInitializer({
											closeModal,
											numberOfSteps: 2,
										}),
									size: 'md',
								});
							}
						},
					};
				}
			),
		},
	};
}
