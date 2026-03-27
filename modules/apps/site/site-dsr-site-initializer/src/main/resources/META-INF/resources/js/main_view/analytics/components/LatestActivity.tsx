/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import AccountSticker from '../../../common/components/AccountSticker';

import './../../../../css/components/LatestActivity.scss';
import {TLatestActivity} from '../../../common/utils/types';
import {timestampDataRenderer} from './data_renderers/TimestampDataRenderer';

const LatestActivity = ({
	items = [],
	namespace,
}: {
	items: TLatestActivity[];
	namespace: string;
}) => {
	return (
		<div className="latest-activity-fds">
			<FrontendDataSet
				customDataRenderers={{
					timestampDataRenderer,
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
										{Liferay.Language.get(itemData.name)}
									</p>
								</div>
							),
							name: 'userLatestActivity',
							type: 'internal',
						},
					],
				}}
				id={namespace}
				items={items}
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
									contentRenderer: 'userLatestActivity',
									fieldName: 'name',
									label: `${Liferay.Language.get('name')}`,
								},
								{
									fieldName: 'action',
									label: `${Liferay.Language.get('action')}`,
								},
								{
									contentRenderer: 'timestampDataRenderer',
									fieldName: 'createDate',
									label: `${Liferay.Language.get('timestamp')}`,
								},
							],
						},
						thumbnail: 'table',
					},
				]}
			/>
		</div>
	);
};

export default LatestActivity;
