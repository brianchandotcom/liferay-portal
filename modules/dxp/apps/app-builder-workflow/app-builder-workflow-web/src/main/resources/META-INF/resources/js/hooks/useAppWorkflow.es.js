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

import {getItem} from 'app-builder-web/js/utils/client.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {useEffect, useState} from 'react';

export default function useAppWorkflow(appId) {
	const [appWorkflow, setAppWorkflow] = useState({});

	useEffect(() => {
		if (appId) {
			getItem(`/o/app-builder-workflow/v1.0/apps/${appId}/app-workflows`)
				.then(setAppWorkflow)
				.catch(() => errorToast());
		}
	}, [appId]);

	return appWorkflow;
}
