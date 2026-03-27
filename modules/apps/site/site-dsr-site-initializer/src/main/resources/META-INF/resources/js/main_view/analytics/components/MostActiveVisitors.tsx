/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import AccountSticker from '../../../common/components/AccountSticker';

import './../../../../css/components/MostActiveVisitors.scss';

type TVisitor = {
	activitiesCount: number;
	emailAddress: string;
	firstName: string;
	lastName: string;
	logoURL: string | undefined;
};

function visitorSticker({itemData}: {itemData: TVisitor}) {
	return (
		<div className="d-flex inline-item">
			<AccountSticker
				logoURL={itemData.logoURL}
				name={itemData.firstName}
				shape="user-icon"
			/>

			<div className="ml-3">
				<div className="align-items-center font-weight-semi-bold visitors-full-name">
					<span className="mb-0 mr-1">
						{Liferay.Language.get(itemData.firstName)}
					</span>

					<span className="mb-0">
						{Liferay.Language.get(itemData.lastName)}
					</span>
				</div>

				<div className="align-items-center">
					<span className="mb-0 mr-1">
						{itemData.activitiesCount}
					</span>

					<span className="mb-0">
						{Liferay.Language.get('actions')}
					</span>
				</div>

				<p className="email-text mb-0 text-secondary">
					{Liferay.Language.get(itemData.emailAddress)}
				</p>
			</div>
		</div>
	);
}

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
