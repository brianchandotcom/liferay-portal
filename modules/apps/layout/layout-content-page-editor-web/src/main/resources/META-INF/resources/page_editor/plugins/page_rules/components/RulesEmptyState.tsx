/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

import {useRulesModal} from '../../../app/contexts/RulesModalContext';

export default function RulesEmptyState({isSearching}: {isSearching: boolean}) {
	const {openRulesModal} = useRulesModal();

	return (
		<>
			<div className="align-items-center d-flex flex-column justify-content-between px-3">
				{isSearching ? (
					<ClayEmptyState
						description={Liferay.Language.get(
							'try-again-with-a-different-search'
						)}
						imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.svg`}
						small
						title={Liferay.Language.get('no-results-found')}
					/>
				) : (
					<>
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
							onClick={() => openRulesModal()}
							size="sm"
						>
							{Liferay.Language.get('new-rule')}
						</ClayButton>
					</>
				)}
			</div>
		</>
	);
}
