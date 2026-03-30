/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import {TRoomDocumentsStatisticsProps} from '../../../common/utils/types';
import {AverageTimeDataRenderer} from './data_renderers/AverageTimeDataRenderer';
import {DocumentTitleDataRenderer} from './data_renderers/DocumentTitleDataRenderer';
import {LastViewedDataRenderer} from './data_renderers/LastViewedDataRenderer';
import {UserInvolvedDataRenderer} from './data_renderers/UserInvolvedDataRenderer';

import '../../../../css/components/DocumentsStatistics.scss';

const RoomDocumentsStatistics = ({
	items = [],
	namespace,
}: {
	items?: TRoomDocumentsStatisticsProps;
	namespace: string;
}) => {
	return (
		<div className="document-statistics-fds">
			<FrontendDataSet
				customDataRenderers={{
					averageTimeDataRenderer: AverageTimeDataRenderer,
					documentNameDataRenderer: DocumentTitleDataRenderer,
					lastViewedDataRenderer: LastViewedDataRenderer,
					userInvolvedDataRenderer: UserInvolvedDataRenderer,
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
									contentRenderer: 'documentNameDataRenderer',
									fieldName: 'title',
									label: Liferay.Language.get('title'),
								},
								{
									fieldName: 'totalViews',
									label: Liferay.Language.get('total-views'),
								},
								{
									contentRenderer: 'lastViewedDataRenderer',
									fieldName: 'lastViewed',
									label: Liferay.Language.get('last-viewed'),
								},
								{
									fieldName: 'download',
									label: Liferay.Language.get('download'),
								},
								{
									contentRenderer: 'averageTimeDataRenderer',
									fieldName: 'averageTime',
									label: Liferay.Language.get('average-time'),
								},
								{
									contentRenderer: 'userInvolvedDataRenderer',
									fieldName: 'userInvolved',
									label: Liferay.Language.get(
										'user-involved'
									),
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

export default RoomDocumentsStatistics;
