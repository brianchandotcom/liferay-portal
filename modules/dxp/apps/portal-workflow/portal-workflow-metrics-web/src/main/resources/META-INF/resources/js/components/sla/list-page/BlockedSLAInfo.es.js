/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React, {useEffect, useState} from 'react';

import {useFetch} from '../../../shared/hooks/useFetch.es';

export default function BlockedSLAInfo({processId}) {
	const [visible, setVisible] = useState(true);

	const {data, fetchData} = useFetch({
		url: `/processes/${processId}/slas?page=1&pageSize=1&status=2`,
	});

	useEffect(() => {
		fetchData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		data?.totalCount > 0 &&
		visible && (
			<ClayAlert
				displayType="danger"
				onClose={() => setVisible(false)}
				title={Liferay.Language.get('error')}
			>
				{Liferay.Language.get(
					'fix-blocked-slas-to-resume-accurate-reporting'
				)}
			</ClayAlert>
		)
	);
}
