/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React, {useEffect, useState} from 'react';

import AccountSticker from '../../../common/components/AccountSticker';

import './../../../../css/components/LatestActivity.scss';
import useAnalyticsQuery from '../../../common/hooks/useAnalyticsQuery';
import {TLatestActivity} from '../../../common/utils/types';
import LatestActivityQuery from '../queries/LatestActivityQuery';
import {BASE_URL} from '../utils/constants';
import AnalyticsFrame from './AnalyticsFrame';
import Loader from './Loader';
import {TimeDataRenderer} from './data_renderers/TimeDataRenderer';

const LatestActivity = ({
	dsrDevEnvEnabled: useDevEnvData,
	namespace,
}: {
	dsrDevEnvEnabled: boolean;
	namespace: string;
}) => {
	const [data, setData] = useState<TLatestActivity[]>([]);
	const [element, setElement] = useState<HTMLElement | null>(null);

	const {isLoading, response} = useAnalyticsQuery({
		element,
		query: LatestActivityQuery,
		settings: {
			checkViewportVisibility: true,
			useDevEnvData,
		},
		variables: {
			channelId: '',
			includeAnonymousUsers: false,
			page: 0,
			rangeKey: 7,
			size: 10,
		},
	});

	useEffect(() => {
		if (response) {
			setData(response);
		}

		return () => {};
	}, [response, setData]);

	return (
		<AnalyticsFrame
			icon="click"
			title={Liferay.Language.get('latest-activity')}
			url={`${BASE_URL}/view-timeline`}
		>
			<div className="latest-activity-container" ref={setElement}>
				{isLoading ? (
					<Loader />
				) : !data?.length ? (
					<p className="mt-3 text-center text-muted">
						{Liferay.Language.get('no-data-available')}
					</p>
				) : (
					<div className="latest-activity-fds">
						<FrontendDataSet
							customDataRenderers={{
								timeDataRenderer: TimeDataRenderer,
							}}
							customRenderers={{
								tableCell: [
									{
										component: ({
											itemData,
										}: {
											itemData: TLatestActivity;
										}) => (
											<div className="d-flex inline-item">
												<AccountSticker
													logoURL={itemData.logoURL}
													name={itemData.name}
													shape="user-icon"
												/>

												<p className="font-weight-semi-bold inline-item-after mb-0">
													{Liferay.Language.get(
														itemData.name
													)}
												</p>
											</div>
										),
										name: 'userLatestActivity',
										type: 'internal',
									},
								],
							}}
							id={namespace}
							items={data}
							showManagementBar={false}
							showPagination={false}
							showSearch={false}
							showSelectAll={false}
							views={[
								{
									contentRenderer: 'table',
									label: Liferay.Language.get('table'),
									name: 'table',
									schema: {
										fields: [
											{
												contentRenderer:
													'userLatestActivity',
												fieldName: 'name',
												label: `${Liferay.Language.get('name')}`,
											},
											{
												fieldName: 'action',
												label: `${Liferay.Language.get('action')}`,
											},
											{
												contentRenderer:
													'timeDataRenderer',
												fieldName: 'createDate',
												label: `${Liferay.Language.get('time')}`,
											},
										],
									},
									thumbnail: 'table',
								},
							]}
						/>
					</div>
				)}
			</div>
		</AnalyticsFrame>
	);
};

export default LatestActivity;
