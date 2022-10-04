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

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';

import {deleteConnection} from '../utils/api';
import LoadingInline from './LoadingInline';

interface IDisconnectModalProps {
	observer: any;
	onOpenChange: (value: boolean) => void;
}

const DisconnectModal: React.FC<IDisconnectModalProps> = ({
	observer,
	onOpenChange,
}) => {
	const [submitting, setSubmitting] = useState(false);

	return (
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
							onClick={() => onOpenChange(false)}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={submitting}
							displayType="warning"
							onClick={async () => {
								setSubmitting(true);

								await deleteConnection();

								setSubmitting(false);

								onOpenChange(false);

								window.location.reload();
							}}
						>
							{submitting && <LoadingInline />}

							{Liferay.Language.get('disconnect')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default DisconnectModal;
