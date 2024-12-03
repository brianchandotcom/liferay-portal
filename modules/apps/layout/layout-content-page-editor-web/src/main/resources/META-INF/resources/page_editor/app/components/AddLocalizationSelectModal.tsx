/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {default as ClayButton} from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useDispatch, useSelectorRef} from '../contexts/StoreContext';
import {addFragment} from '../js-index';
import {hasLocalizationSelect} from '../utils/hasLocalizationSelect';

interface ModalProps {
	formId: string;
	onCloseModal: () => void;
}

export default function AddLocalizationSelectModal({
	formId,
	onCloseModal,
}: ModalProps) {
	const dispatch = useDispatch();
	const fragmentEntryLinksRef: React.Ref<FragmentEntryLinkMap> =
		useSelectorRef((state) => state.fragmentEntryLinks);

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	if (
		fragmentEntryLinksRef.current &&
		hasLocalizationSelect(fragmentEntryLinksRef.current)
	) {
		return null;
	}

	const title = sub(
		Liferay.Language.get('add-x'),
		Liferay.Language.get('localization-select')
	);

	return (
		<ClayModal observer={observer} status="info">
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				<p>
					{Liferay.Language.get(
						'at-least-one-localizable-form-field-has-been-added-to-the-page'
					)}
				</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="info"
							onClick={() => {
								dispatch(
									addFragment({
										fieldTypes: ['localizationSelect'],
										fragmentEntryKey: 'localization-select',
										itemType:
											LAYOUT_DATA_ITEM_TYPES.fragment,
										parentItemId: formId,
										type: 'input',
									})
								);

								onCloseModal();
							}}
						>
							{title}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
