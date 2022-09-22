/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useContext} from 'react';

import {AppContext} from '../../App';
import BasePage from '../../components/BasePage';
import {deleteConnection} from '../../utils/api';

const WorkspaceConnection: React.FC = () => {
	const {liferayAnalyticsURL, token} = useContext(AppContext);
	const {observer, onOpenChange, open} = useModal();

	return (
		<BasePage
			description={Liferay.Language.get(
				'use-the-token-genereted-in-your-analytics-cloud-to-connect-this-workspace'
			)}
			title={Liferay.Language.get('workspace-connection')}
		>
			<ClayAlert
				displayType="success"
				title={Liferay.Language.get('connected')}
			/>

			<ClayForm>
				<ClayForm.Group>
					<label className="disabled" htmlFor="inputToken">
						{Liferay.Language.get('analytics-cloud-token')}
					</label>

					<ClayInput
						disabled
						id="inputToken"
						type="text"
						value={token}
					/>

					<label className="disabled">
						<small>
							{Liferay.Language.get('analytics-cloud-token-help')}
						</small>
					</label>
				</ClayForm.Group>
			</ClayForm>

			<BasePage.Footer>
				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() => window.open(liferayAnalyticsURL)}
				>
					{Liferay.Language.get('go-to-workspace')}

					<ClayIcon className="ml-2" symbol="shortcut" />
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => onOpenChange(true)}
				>
					{Liferay.Language.get('disconnect')}
				</ClayButton>
			</BasePage.Footer>

			{open && (
				<ClayModal center observer={observer} status="warning">
					<ClayModal.Header>
						{Liferay.Language.get('disconnecting-data-source')}
					</ClayModal.Header>

					<ClayModal.Body>
						<h4 className="modal-description">
							{Liferay.Language.get(
								'are-you-sure-you-want-to-disconnect-your-analytics-cloud-workspace-from-this-dxp-instance'
							)}
						</h4>

						<p className="text-secondary">
							{Liferay.Language.get(
								'this-will-stop-any-syncing-of-analytics-or-contact-data-to-your-analytics-cloud-workspace'
							)}
						</p>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={() => {
										onOpenChange(false);
									}}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="warning"
									onClick={() => {
										deleteConnection();
										onOpenChange(false);
										window.location.reload();
									}}
								>
									{Liferay.Language.get('disconnect')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</BasePage>
	);
};

export default WorkspaceConnection;
