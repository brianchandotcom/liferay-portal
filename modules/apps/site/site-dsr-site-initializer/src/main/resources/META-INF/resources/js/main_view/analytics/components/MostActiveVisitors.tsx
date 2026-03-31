/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import './../../../../css/components/MostActiveVisitors.scss';
import {visitorSticker} from './cell_renderers/VisitorSticker';

type TVisitor = {
	activitiesCount: number;
	emailAddress: string;
	firstName: string;
	lastName: string;
	logoURL: string | undefined;
};

const MostActiveVisitors = ({
	items = [],
	namespace,
}: {
	items: TVisitor[];
	namespace: string;
}) => {
	return (
		<div className="most-active-visitors-fds">
			<FrontendDataSet
				customRenderers={{
					tableCell: [
						{
							component: visitorSticker,
							name: 'visitorSticker',
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
									contentRenderer: 'visitorSticker',
									fieldName: 'title',
									label: '',
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

export default MostActiveVisitors;
