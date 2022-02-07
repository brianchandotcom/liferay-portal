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

import ClayAlert from '@clayui/alert';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import {useApplicationProvider} from '../../../../../common/context/AppPropertiesProvider';
import {getExportedLicenseKeys} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import downloadFromBlob from '../../../../../common/utils/downloadFromBlob';
import ButtonDropDown from '../../../components/ButtonDropDown';

import {
	ALERT_DOWNLOAD_TYPE,
	AUTO_CLOSE_DOWNLOAD_ALERT_TIME,
	EXTENSION_FILE_TYPES,
	STATUS_CODE,
} from '../../../utils/constants';
import {ALERT_ACTIVATION_KEYS_DOWNLOAD_TEXT} from '../utils/constants';

const ActivationKeysActionsButton = ({accountKey, sessionId}) => {
	const {licenseKeyDownloadURL} = useApplicationProvider();
	const [active, setActive] = useState(false);
	const [
		activationKeysdownloadStatus,
		setActivationKeysdownloadStatus,
	] = useState('');

	const downloadAllKeysDetails = async () => {
		const license = await getExportedLicenseKeys(
			accountKey,
			licenseKeyDownloadURL,
			sessionId
		);

		if (license.status === STATUS_CODE.sucess) {
			const contentType = license.headers.get('content-type');
			const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
			const licenseBlob = await license.blob();

			setActivationKeysdownloadStatus(ALERT_DOWNLOAD_TYPE.success);

			return downloadFromBlob(
				licenseBlob,
				`activation-keys${extensionFile}`
			);
		}

		setActivationKeysdownloadStatus(ALERT_DOWNLOAD_TYPE.danger);
	};

	const activationKeysActionsItems = [
		{
			icon: (
				<ClayIcon className="mr-1 text-neutral-4" symbol="download" />
			),
			label: 'Export All Key Details (csv)',
			onClick: downloadAllKeysDetails,
		},
	];

	return (
		<>
			{activationKeysdownloadStatus && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={
							AUTO_CLOSE_DOWNLOAD_ALERT_TIME[
								activationKeysdownloadStatus
							]
						}
						className="cp-activation-key-download-alert px-4 py-3 text-paragraph"
						displayType={
							ALERT_DOWNLOAD_TYPE[activationKeysdownloadStatus]
						}
						onClose={() => setActivationKeysdownloadStatus('')}
					>
						{
							ALERT_ACTIVATION_KEYS_DOWNLOAD_TEXT[
								activationKeysdownloadStatus
							]
						}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
			<ButtonDropDown
				active={active}
				items={activationKeysActionsItems}
				label="Actions"
				menuElementAttrs={{
					className: 'p-0',
				}}
				setActive={setActive}
			/>
		</>
	);
};

export default ActivationKeysActionsButton;
