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

import {useEffect, useState} from 'react';
import {useApplicationProvider} from '../../../../../common/context/AppPropertiesProvider';
import {getActivationLicenseKey} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';

const MAX_ITEMS = 9999;
const PAGE = 1;

export default function useGetActivationKeysData(
	project,
	sessionId,
	productName
) {
	const {licenseKeyDownloadURL} = useApplicationProvider();

	const [loading, setLoading] = useState(true);
	const [activationKeys, setActivationKeys] = useState([]);
	const [filterTerm, setFilterTerm] = useState(
		`active eq true and startswith(productName,'${productName}')`
	);

	useEffect(() => {
		setLoading(true);
		const fetchActivationKeysData = async () => {
			const {items} = await getActivationLicenseKey(
				project?.accountKey,
				licenseKeyDownloadURL,
				encodeURI(filterTerm),
				PAGE,
				MAX_ITEMS,
				sessionId
			);

			if (items) {
				setActivationKeys(items);
			}

			setLoading(false);
		};

		fetchActivationKeysData();
	}, [filterTerm, licenseKeyDownloadURL, project?.accountKey, sessionId]);

	return {
		activationKeysState: [activationKeys, setActivationKeys],
		loading,
		setFilterTerm,
	};
}
