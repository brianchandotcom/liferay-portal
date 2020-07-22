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
import {DeploySettings} from 'app-builder-web/js/pages/apps/edit/DeployApp.es';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext, useState} from 'react';

export default function DeployAppModal({onSave}) {
	const {
		isDeployModalVisible,
		setDeployModalVisible,
		state: {app},
	} = useContext(EditAppContext);

	const [isDeploying, setDeploying] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setDeploying(false);
			setDeployModalVisible(false);
		},
	});

	if (!isDeployModalVisible) {
		return <></>;
	}

	const onDone = () => {
		setDeploying(true);

		if (!app.active) {
			onSave(onClose, true);
		}
		else {
			onClose();
		}
	};

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('deploy')}
			</ClayModal.Header>

			<div className="modal-body px-0">
				<DeploySettings />
			</div>

			<ClayModal.Footer
				last={
					<>
						<ClayButton
							className="mr-3"
							disabled={isDeploying}
							displayType="secondary"
							onClick={onClose}
							small
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={
								isDeploying || app.appDeployments.length === 0
							}
							onClick={onDone}
							small
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}
