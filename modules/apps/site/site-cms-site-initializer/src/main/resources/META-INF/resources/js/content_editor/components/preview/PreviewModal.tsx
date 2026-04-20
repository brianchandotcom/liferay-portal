/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
	onCloseModal: () => void;
	title: string;
};

export default function PreviewModal({
	getPreviewDataURL,
	onCloseModal,
	title,
}: Props) {
	const {previewURL, ...selectorProps} = usePreviewState(getPreviewDataURL);

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
					previewURL={previewURL}
					vertical
				/>
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
