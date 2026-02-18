/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IItemSelectorModalProps} from './ItemSelectorModal';

export type TDetachedItemSelectorModal<T> = Omit<
	IItemSelectorModalProps<T>,
	'observer' | 'onOpenChange' | 'open'
>;
