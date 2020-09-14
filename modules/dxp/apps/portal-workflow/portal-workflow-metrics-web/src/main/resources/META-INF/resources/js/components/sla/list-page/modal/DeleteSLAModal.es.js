/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useContext} from 'react';

import {useToaster} from '../../../../shared/components/toaster/hooks/useToaster.es';
import {useDelete} from '../../../../shared/hooks/useDelete.es';
import {SLAListPageContext} from '../SLAListPage.es';

const DeleteSLAModal = () => {
	const {itemToRemove, setVisible, visible} = useContext(SLAListPageContext);
	const deleteSLA = useDelete({url: `/slas/${itemToRemove}`});
	const toaster = useToaster();

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
		},
	});

	const removeItem = () => {
		deleteSLA()
			.then(() => {
				onClose();
				toaster.success(Liferay.Language.get('sla-was-deleted'));
			})
			.catch(() =>
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
};

export default DeleteSLAModal;
