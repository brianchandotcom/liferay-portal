/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';

const LoadingIndicator = () => {
	return (
		<ClayModal.Body className="align-items-center d-flex flex-column mb-5">
			<div className="align-items-center d-flex flex-wrap justify-content-center loading-modal">
				<ClayLoadingIndicator displayType="secondary" size="md" />
			</div>
		</ClayModal.Body>
	);
};

export default LoadingIndicator;
