/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useContext} from 'react';

import {useToaster} from '../../../../shared/components/toaster/hooks/useToaster.es';
import {useDelete} from '../../../../shared/hooks/useDelete.es';
import {SLAListPageContext} from '../SLAListPage.es';

export default function DeleteSLAModal() {
	const {itemToRemove, setVisible, visible} = useContext(SLAListPageContext);
	const deleteSLA = useDelete({
		callback: () => {
			onClose();
			toaster.success(Liferay.Language.get('sla-was-deleted'));
		},
		url: `/slas/${itemToRemove}`,
	});
	const toaster = useToaster();

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
		},
	});

	const removeItem = () => {
		deleteSLA().catch(() =>
			toaster.danger(Liferay.Language.get('your-request-has-failed'))
		);
	};

	return (
		visible && (
			<ClayModal observer={observer} size="lg">
				<ClayModal.Body>
					<p>
						{Liferay.Language.get(
							'deleting-slas-will-reflect-on-report-data'
						)}
					</p>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								id="removeSlaButton"
								onClick={removeItem}
							>
								{Liferay.Language.get('ok')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayModal>
		)
	);
}
