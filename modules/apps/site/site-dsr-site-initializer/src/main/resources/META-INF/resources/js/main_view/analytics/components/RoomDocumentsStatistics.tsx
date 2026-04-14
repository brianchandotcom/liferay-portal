/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React, {useEffect, useState} from 'react';

import {TRoomDocumentsStatistics} from '../../../common/utils/types';
import {AverageTimeDataRenderer} from './data_renderers/AverageTimeDataRenderer';
import {DocumentTitleDataRenderer} from './data_renderers/DocumentTitleDataRenderer';
import {LastViewedDataRenderer} from './data_renderers/LastViewedDataRenderer';
import {UserInvolvedDataRenderer} from './data_renderers/UserInvolvedDataRenderer';

import '../../../../css/components/RoomDocumentsStatistics.scss';
import useAnalyticsQuery from '../../../common/hooks/useAnalyticsQuery';
import RoomDocumentsStatisticsQuery from '../queries/RoomDocumentsStatisticsQuery';
import AnalyticsFrame from './AnalyticsFrame';
import Loader from './Loader';

const RoomDocumentsStatistics = ({
	dsrDevEnvEnabled: useDevEnvData,
	namespace,
}: {
	dsrDevEnvEnabled: boolean;
	namespace: string;
}) => {
	const [data, setData] = useState<TRoomDocumentsStatistics[]>([]);
	const [element, setElement] = useState<HTMLElement | null>(null);

	const {isLoading, response} = useAnalyticsQuery({
		element,
		query: RoomDocumentsStatisticsQuery,
		settings: {
			checkViewportVisibility: true,
			useDevEnvData,
		},
		variables: {
			channelId: '',
			keywords: '',
			rangeEnd: null,
			rangeKey: 7,
			rangeStart: null,
			size: 20,
			sort: {
				column: 'downloadsMetric',
				type: 'DESC',
			},
			start: 0,
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
			icon="documents-and-media"
			title={Liferay.Language.get('most-engaged-documents')}
		>
			<div
				className="room-documents-statistics-container"
				ref={setElement}
			>
				{isLoading ? (
					<Loader />
				) : !data?.length ? (
					<p className="mt-3 text-center text-muted">
						{Liferay.Language.get('no-data-available')}
					</p>
				) : (
					<div className="room-document-statistics-fds">
						<FrontendDataSet
							customDataRenderers={{
								averageTimeDataRenderer:
									AverageTimeDataRenderer,
								documentNameDataRenderer:
									DocumentTitleDataRenderer,
								lastViewedDataRenderer: LastViewedDataRenderer,
								userInvolvedDataRenderer:
									UserInvolvedDataRenderer,
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
													'documentNameDataRenderer',
												fieldName: 'title',
												label: Liferay.Language.get(
													'title'
												),
											},
											{
												fieldName: 'totalViews',
												label: Liferay.Language.get(
													'total-views'
												),
											},
											{
												contentRenderer:
													'lastViewedDataRenderer',
												fieldName: 'lastViewed',
												label: Liferay.Language.get(
													'last-viewed'
												),
											},
											{
												fieldName: 'download',
												label: Liferay.Language.get(
													'download'
												),
											},
											{
												contentRenderer:
													'averageTimeDataRenderer',
												fieldName: 'averageTime',
												label: Liferay.Language.get(
													'average-time'
												),
											},
											{
												contentRenderer:
													'userInvolvedDataRenderer',
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
				)}
			</div>
		</AnalyticsFrame>
	);
};

export default RoomDocumentsStatistics;
