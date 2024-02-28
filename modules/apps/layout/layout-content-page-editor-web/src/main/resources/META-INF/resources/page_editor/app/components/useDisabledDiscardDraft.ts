/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';
import {config} from '../config/index';
import {useSelector} from '../contexts/StoreContext';

export default function useDisabledDiscardDraft() {
	const [enableDiscard, setEnableDiscard] = useState(false);

	const draft = useSelector((state) => state.draft);
	const network = useSelector((state) => state.network);

	useEffect(() => {
		setEnableDiscard(
			network.status === SERVICE_NETWORK_STATUS_TYPES.draftSaved ||
				draft ||
				config.isConversionDraft
		);
	}, [network, draft]);

	return !enableDiscard;
}
