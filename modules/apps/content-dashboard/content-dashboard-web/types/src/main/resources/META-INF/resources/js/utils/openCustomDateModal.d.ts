/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare type Props = {
	dateTypes: Array<{
		label: string;
		value: string;
	}>;
	filterUrl: string;
	namespace: string;
	selectedDateType: string | undefined;
	selectedEndDate: string | undefined;
	selectedStartDate: string | undefined;
};
export default function openCustomDateModal(props: Props): void;
export {};
