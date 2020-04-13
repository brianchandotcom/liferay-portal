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
import React, {useContext, useEffect, useState} from 'react';

import ChildLink from '../../shared/components/router/ChildLink.es';
import {useFetch} from '../../shared/hooks/useFetch.es';
import {sub} from '../../shared/util/lang.es';
import {AppContext} from '../AppContext.es';

const SLAInfo = ({processId}) => {
	const [alert, setAlert] = useState(null);
	const {defaultDelta} = useContext(AppContext);

	const url = `/processes/${processId}/slas?page=1&pageSize=1`;

	const {fetchData} = useFetch({url});
	const {fetchData: fetchSLABlocked} = useFetch({url: `${url}&status=2`});

	const getSLACount = () => {
		fetchData().then(({totalCount}) => {
			if (totalCount === 0) {
				setAlert({
					content: `${Liferay.Language.get(
						'no-slas-are-defined-for-this-process'
					)}`,
					link: `/sla/${processId}/new`,
					linkText: Liferay.Language.get('add-a-new-sla'),
				});
			}
			else {
				getSLABlockedCount();
			}
		});
	};

	const getSLABlockedCount = () => {
		fetchSLABlocked().then(({totalCount}) => {
			if (totalCount > 0) {
				setAlert({
					content: `${sub(
						totalCount > 1
							? Liferay.Language.get('x-slas-are-blocked')
							: Liferay.Language.get('x-sla-is-blocked'),
						[totalCount]
					)} ${Liferay.Language.get(
						'fix-the-sla-configuration-to-resume-accurate-reporting'
					)}`,
					link: `/sla/${processId}/list/${defaultDelta}/1`,
					linkText: Liferay.Language.get('set-up-slas'),
				});
			}
		});
	};

	useEffect(() => {
		getSLACount();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			{alert && (
				<div className="container-fluid-1280">
					<ClayAlert
						className="mb-0"
						data-testid="slaInfoAlert"
						displayType="warning"
						onClose={() => setAlert()}
						title={Liferay.Language.get('warning')}
					>
						{alert.content}{' '}
						<ChildLink
							className="font-weight-bold"
							data-testid="slaInfoLink"
							to={alert.link}
						>
							{alert.linkText}
						</ChildLink>
					</ClayAlert>
				</div>
			)}
		</>
	);
};

export default SLAInfo;
