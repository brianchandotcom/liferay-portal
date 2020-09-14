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
import React, {useEffect, useState} from 'react';

import {useFetch} from '../../../shared/hooks/useFetch.es';

const BlockedSLAInfo = ({processId}) => {
	const [visible, setVisible] = useState(true);

	const {data, fetchData} = useFetch({
		url: `/processes/${processId}/slas?page=1&pageSize=1&status=2`,
	});

	useEffect(() => {
		fetchData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		data.totalCount > 0 &&
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
};

export default BlockedSLAInfo;
