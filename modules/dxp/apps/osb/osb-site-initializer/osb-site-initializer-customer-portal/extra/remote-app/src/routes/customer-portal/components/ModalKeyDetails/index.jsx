/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';
import Button from '../../../../common/components/Button';
import {AUTO_CLOSE_ALERT_TIME} from '../../utils/constants/autoCloseAlertTime';
import TableKeyDetails from '../TableKeyDetails';

const ModalKeyDetails = ({
	activationKeys,
	downloadActivationLicenseKey,
	observer,
	onClose,
	provisioningServerAPI,
	sessionId,
}) => {
	const [valueToCopyToClipboard, setValueToCopyToClipboard] = useState('');

	return (
		<ClayModal center observer={observer} size="lg">
			<div className="pt-4 px-4">
				<div className="d-flex justify-content-between mb-4">
					<div className="flex-row mb-1">
						<h6 className="text-brand-primary">
							ACTIVATION KEY DETAILS
						</h6>

						<h2 className="text-neutral-10">
							{activationKeys.name}
						</h2>

						<p>{activationKeys.description}</p>
					</div>

					<Button
						appendIcon="times"
						aria-label="close"
						className="align-self-start"
						displayType="unstyled"
						onClick={onClose}
					/>
				</div>

				<TableKeyDetails
					activationKeys={activationKeys}
					setValueToCopyToClipboard={setValueToCopyToClipboard}
				/>

				<div className="d-flex justify-content-end my-4">
					<Button displayType="secondary" onClick={onClose}>
						Close
					</Button>

					<Button
						appendIcon="download"
						className="ml-2"
						onClick={() =>
							downloadActivationLicenseKey(
								activationKeys.id,
								provisioningServerAPI,
								sessionId
							)
						}
					>
						Download Key
					</Button>
				</div>
			</div>

			{valueToCopyToClipboard && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={AUTO_CLOSE_ALERT_TIME.success}
						displayType="success"
						onClose={() => setValueToCopyToClipboard(false)}
					>
						{valueToCopyToClipboard} copied to clipboard
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</ClayModal>
	);
};
export default ModalKeyDetails;
