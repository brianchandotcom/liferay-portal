/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import React, {useState} from 'react';

import {useLayoutContext} from '../objectLayoutContext';
import {ModalAddObjectLayoutTab} from './ModalAddObjectLayoutTab';

export function AddNewTabButton() {
	const [{isViewOnly}] = useLayoutContext();
	const [visibleModal, setVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			<div className="layout-tab__add-tab-btn">
				<ClayButton
					disabled={isViewOnly}
					displayType="secondary"
					onClick={() => setVisibleModal(true)}
				>
					<ClayIcon symbol="plus" />

					<span className="ml-2">
						{Liferay.Language.get('add-tab')}
					</span>
				</ClayButton>
			</div>

			{visibleModal && (
				<ModalAddObjectLayoutTab
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
}
