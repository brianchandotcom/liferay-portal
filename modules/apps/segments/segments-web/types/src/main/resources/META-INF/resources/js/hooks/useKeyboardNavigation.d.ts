/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ItemTypeValues} from '../utils/listItemTypes';
interface Props {
	handleOpen: (key: string, editing: boolean) => void;
	key: string;
	type: ItemTypeValues;
}
export default function useKeyboardNavigation({
	handleOpen,
	key,
	type,
}: Props): {
	isTarget: boolean;
	setElement: import('react').Dispatch<
		import('react').SetStateAction<HTMLElement | null>
	>;
};
export {};
