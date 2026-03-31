/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {forwardRef, useEffect} from 'react';

import ItemSelectorModal from './ItemSelectorModal';
import {TDetachedItemSelectorModal} from './types';

const DetachedItemSelectorModal = forwardRef(
	<T extends Record<string, any>>(props: TDetachedItemSelectorModal<T>) => {
		const {observer, onOpenChange, open} = useModal();

		useEffect(() => {
			onOpenChange(true);
		}, [onOpenChange]);

		return (
			<>
				{open && (
					<ItemSelectorModal<T>
						{...props}
						observer={observer}
						onOpenChange={onOpenChange}
						open={open}
					/>
				)}
			</>
		);
	}
);

export default DetachedItemSelectorModal;
