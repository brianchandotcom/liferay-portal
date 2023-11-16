/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Action} from '../../plugins/page_rules/components/Action';
declare type Item = {
	label: string;
	value: string;
};
declare type Props = {
	actions: Action[];
	items: Item[];
};
export default function useActionValues({
	actions,
	items,
}: Props): {
	action: string | undefined;
	description: string;
	id: string;
	item: string | undefined;
	prefix: string;
	type: string | undefined;
}[];
export {};
