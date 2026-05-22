/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useEffect} from 'react';

import TreeItemSelectorModal, {
	ITreeItemSelectorModalProps,
} from './TreeItemSelectorModal';

export type TDetachedTreeItemSelectorModal = Omit<
	ITreeItemSelectorModalProps,
	'observer' | 'onOpenChange' | 'open'
>;

const DetachedTreeItemSelectorModal = (
	props: TDetachedTreeItemSelectorModal
) => {
	const {observer, onOpenChange, open} = useModal();

	useEffect(() => {
		onOpenChange(true);
	}, [onOpenChange]);

	return (
		<>
			{open && (
				<TreeItemSelectorModal
					{...props}
					observer={observer}
					onOpenChange={onOpenChange}
					open={open}
				/>
			)}
		</>
	);
};

export default DetachedTreeItemSelectorModal;
