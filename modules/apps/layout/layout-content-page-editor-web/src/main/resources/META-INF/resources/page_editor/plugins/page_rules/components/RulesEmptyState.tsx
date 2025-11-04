/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import React, {useState} from 'react';

import RulesModal from './RulesModal';

export default function RulesEmptyState() {
	const [modalVisible, setModalVisible] = useState(false);

	return (
		<>
			<div className="align-items-center d-flex flex-column justify-content-between">
				<ClayEmptyState
					className="mb-0"
					description={Liferay.Language.get(
						'fortunately-it-is-very-easy-to-add-new-ones'
					)}
					imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/empty_state.svg`}
					small
					title={Liferay.Language.get('no-rules-yet')}
				/>

				<ClayButton
					className="mt-2"
					displayType="secondary"
					onClick={() => setModalVisible(true)}
					size="sm"
				>
					{Liferay.Language.get('new-rule')}
				</ClayButton>
			</div>

			{modalVisible && (
				<RulesModal onCloseModal={() => setModalVisible(false)} />
			)}
		</>
	);
}
