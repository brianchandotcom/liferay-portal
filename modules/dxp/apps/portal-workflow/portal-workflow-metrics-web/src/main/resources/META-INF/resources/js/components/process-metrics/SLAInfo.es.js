/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/empty-line-between-elements */

import ClayAlert from '@clayui/alert';
import ClayLayout from '@clayui/layout';
import React, {useContext, useEffect, useState} from 'react';

import ChildLink from '../../shared/components/router/ChildLink.es';
import {useFetch} from '../../shared/hooks/useFetch.es';
import {sub} from '../../shared/util/lang.es';
import {AppContext} from '../AppContext.es';

function SLAInfo({processId}) {
	const [alert, setAlert] = useState(null);
	const {defaultDelta, setFetchDateModified} = useContext(AppContext);

	const url = `/processes/${processId}/slas?page=1&pageSize=1`;

	const {fetchData} = useFetch({
		callback: ({totalCount}) => {
			if (totalCount === 0) {
				setAlert({
					content: `${Liferay.Language.get(
						'no-slas-are-defined-for-this-process'
					)}`,
					link: `/sla/${processId}/new`,
					linkText: Liferay.Language.get('add-a-new-sla'),
				});

				setFetchDateModified(false);
			}
			else {
				setFetchDateModified(true);
				fetchSLABlocked();
			}
		},
		url,
	});
	const {fetchData: fetchSLABlocked} = useFetch({
		callback: ({totalCount}) => {
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
		},
		url: `${url}&status=2`,
	});

	useEffect(() => {
		fetchData();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			{alert && (
				<ClayLayout.ContainerFluid>
					<ClayAlert
						className="mb-0"
						displayType="warning"
						onClose={() => setAlert()}
						title={Liferay.Language.get('warning')}
					>
						{alert.content}{' '}
						<ChildLink
							className="font-weight-bold"
							query={{slaInfoLink: true}}
							to={alert.link}
						>
							{alert.linkText}
						</ChildLink>
					</ClayAlert>
				</ClayLayout.ContainerFluid>
			)}
		</>
	);
}

export default SLAInfo;
