/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useCustomerPortal} from '~/routes/customer-portal/context';
import {useAppPropertiesContext} from '../contexts/AppPropertiesContext';
import ProvisioningLicenseKeys from '../services/liferay/rest/raysource/ProvisioningLicenseKeys';

const useProvisioningLicenseKeys = () => {
	const {provisioningServerAPI} = useAppPropertiesContext();
	const [{sessionId}] = useCustomerPortal();

	const provisioningLicenseKeysService = useMemo(
		() =>
			new ProvisioningLicenseKeys({
				provisioningServerAPI,
				sessionId,
			}),
		[provisioningServerAPI, sessionId]
	);

	return provisioningLicenseKeysService;
};

export default useProvisioningLicenseKeys;
