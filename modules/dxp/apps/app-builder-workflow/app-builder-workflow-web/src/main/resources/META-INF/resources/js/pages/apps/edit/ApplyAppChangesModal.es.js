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
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext} from 'react';

export default function ApplyAppChangesModal({onSave}) {
	const {isAppChangesModalVisible, setAppChangesModalVisible} = useContext(
		EditAppContext
	);

	const {observer, onClose} = useModal({
		onClose: () => setAppChangesModalVisible(false),
	});

	if (!isAppChangesModalVisible) {
		return <></>;
	}

	return (
		<ClayModal className="save-app-modal" observer={observer}>
			<ClayModal.Header className="border-0">
				<ClayIcon
					className="circle-icon info mr-3"
					fontSize="26px"
					symbol="exclamation-full"
				/>
				{Liferay.Language.get('applying-app-updates')}
			</ClayModal.Header>

			<ClayModal.Body>
				<span className="text-secondary">
					{Liferay.Language.get(
						'some-of-the-updates-cannot-be-applied-to-existing-app-data'
					)}
				</span>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<>
						<ClayButton
							className="mr-3"
							displayType="secondary"
							onClick={onClose}
							small
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							onClick={() => onSave(setAppChangesModalVisible)}
							small
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}
