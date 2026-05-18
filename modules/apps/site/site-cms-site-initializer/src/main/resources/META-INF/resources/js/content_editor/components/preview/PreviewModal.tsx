/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {LinkOrButton} from '@clayui/shared';
import {sub} from 'frontend-js-web';
import React from 'react';

import PreviewSelectors from './PreviewSelectors';
import usePreviewState from './usePreviewState';

type Props = {
	getPreviewDataURL: string;
	languageId: Liferay.Language.Locale;
	onCloseModal: () => void;
	title: string;
};

export default function PreviewModal({
	getPreviewDataURL,
	languageId,
	onCloseModal,
	title,
}: Props) {
	const {
		displayPageTemplates,
		isDisplayPageTemplatesListEmpty,
		previewURL,
		...selectorProps
	} = usePreviewState(getPreviewDataURL, languageId);

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	return (
		<ClayModal center observer={observer}>
			<ClayModal.Header>
				{sub(Liferay.Language.get('preview-x'), title)}
			</ClayModal.Header>

			<ClayModal.Body className="p-4">
				<PreviewSelectors
					{...selectorProps}
					displayPageTemplates={displayPageTemplates}
					previewURL={previewURL}
					vertical
				/>

				{isDisplayPageTemplatesListEmpty ? (
					<ClayAlert
						className="mb-0 mt-3"
						displayType="info"
						title={Liferay.Language.get('info')}
					>
						{Liferay.Language.get(
							'no-display-page-templates-are-available-for-preview-in-this-channel'
						)}
					</ClayAlert>
				) : null}
			</ClayModal.Body>

			<ClayModal.Footer
				className="px-4 py-3"
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<LinkOrButton
							button
							disabled={!previewURL}
							href={previewURL}
							linkDisplayType="primary"
							rel="noopener noreferrer"
							target="_blank"
						>
							{Liferay.Language.get('preview-in-a-new-tab')}

							<ClayIcon className="ml-2" symbol="shortcut" />
						</LinkOrButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
