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
import {memo} from 'react';
import {
	ALERT_DOWNLOAD_TYPE,
	AUTO_CLOSE_ALERT_TIME,
} from '../../../../utils/constants';

const DownloadAlert = ({downloadStatus, message, setDownloadStatus}) => (
	<ClayAlert.ToastContainer>
		<ClayAlert
			autoClose={AUTO_CLOSE_ALERT_TIME[downloadStatus]}
			className="cp-activation-key-download-alert"
			displayType={ALERT_DOWNLOAD_TYPE[downloadStatus]}
			onClose={() => setDownloadStatus('')}
		>
			{message}
		</ClayAlert>
	</ClayAlert.ToastContainer>
);

export default memo(DownloadAlert);
