/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayModal from '@clayui/modal';
import React, {useContext} from 'react';

import {
	FindAndReplaceContext,
	useCancelDiscard,
} from '../contexts/FindAndReplaceContext';

export function Discard() {
	const {closeModal} = useContext(FindAndReplaceContext);

	const cancelDiscard = useCancelDiscard();

	return (
		<>
			<ClayModal.Header withTitle={false}>
				<ClayModal.ItemGroup>
					<ClayModal.Item>
						<ClayModal.TitleSection>
							<ClayModal.Title>
								{Liferay.Language.get('discard-changes')}
							</ClayModal.Title>
						</ClayModal.TitleSection>
					</ClayModal.Item>
				</ClayModal.ItemGroup>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('close')}
					className="close"
					displayType="unstyled"
					onClick={cancelDiscard}
					size="sm"
					symbol="times"
				/>
			</ClayModal.Header>

			<ClayModal.Body>
				{Liferay.Language.get(
					'you-are-about-to-exit-find-and-replace.-no-replacements-will-be-applied-to-the-selected-assets'
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={cancelDiscard}
						>
							{Liferay.Language.get('continue-review')}
						</ClayButton>

						<ClayButton displayType="danger" onClick={closeModal}>
							{Liferay.Language.get('discard-changes')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
