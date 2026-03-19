/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import {FindAndReplaceContext} from '../contexts/FindAndReplaceContext';

export function NoMatches() {
	const {closeModal, search} = useContext(FindAndReplaceContext);

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('find-and-replace')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="m-0">
					{sub(
						Liferay.Language.get('no-exact-matches-for-x'),
						search
					)}
				</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton displayType="danger" onClick={closeModal}>
						{Liferay.Language.get('ok')}
					</ClayButton>
				}
			/>
		</>
	);
}
