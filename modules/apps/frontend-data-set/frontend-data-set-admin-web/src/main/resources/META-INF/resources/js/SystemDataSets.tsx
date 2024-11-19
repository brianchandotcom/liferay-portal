/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import '../css/DataSets.scss';

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {openModal} from 'frontend-js-web';

import {API_URL, FDS_DEFAULT_PROPS} from './utils/constants';

interface ISystemDataSet {
	additionalAPIURLParameters: string;
	defaultItemsPerPage: number;
	description: string;
	name: string;
	restApplication: string;
	restEndpoint: string;
	restSchema: string;
	symbol: string;
	title: string;
}

const SelectSystemDataSetModalContent = ({
	closeModal,
	loadData,
	systemDataSets,
}: {
	closeModal: Function;
	loadData: Function;
	systemDataSets: Array<ISystemDataSet>;
}) => {
	return (
		<div className="select-system-data-set-modal-content">
			<ClayModal.Header>
				{Liferay.Language.get('create-system-data-set-customization')}
			</ClayModal.Header>

			<ClayModal.Body>
				<FrontendDataSet
					{...FDS_DEFAULT_PROPS}
					id="SystemDataSets"
					items={systemDataSets}
					selectedItemsKey="name"
					selectionType="single"
					views={[
						{
							contentRenderer: 'list',
							name: 'list',
							schema: {
								description: 'description',
								symbol: 'symbol',
								title: 'title',
							},
						},
					]}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							className="btn-cancel"
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={() => loadData()}>
							{Liferay.Language.get('create')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
};

const SystemDataSets = ({
	systemDataSets,
}: {
	systemDataSets: Array<ISystemDataSet>;
}) => {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get(
					'create-system-data-set-customization'
				),
				onClick: ({loadData}: {loadData: Function}) => {
					openModal({
						contentComponent: ({
							closeModal,
						}: {
							closeModal: Function;
						}) => (
							<SelectSystemDataSetModalContent
								closeModal={closeModal}
								loadData={loadData}
								systemDataSets={systemDataSets}
							/>
						),
					});
				},
			},
		],
	};

	const views = [
		{
			contentRenderer: 'table',
			name: 'table',
			schema: {
				fields: [
					{
						actionId: 'edit',
						contentRenderer: 'actionLink',
						fieldName: 'label',
						label: Liferay.Language.get('name'),
						sortable: true,
					},
					{
						fieldName: 'restApplication',
						label: Liferay.Language.get('rest-application'),
						sortable: true,
					},
					{
						fieldName: 'restSchema',
						label: Liferay.Language.get('rest-schema'),
						sortable: true,
					},
					{
						fieldName: 'restEndpoint',
						label: Liferay.Language.get('rest-endpoint'),
						sortable: true,
					},
					{
						fieldName: 'status',
						label: Liferay.Language.get('status'),
						sortable: true,
					},
					{
						contentRenderer: 'dateTime',
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified-date'),
						sortable: true,
					},
				],
			},
		},
	];

	return (
		<div className="data-sets system-data-sets">
			<FrontendDataSet
				{...FDS_DEFAULT_PROPS}
				apiURL={API_URL.DATA_SETS}
				creationMenu={creationMenu}
				emptyState={{
					description: Liferay.Language.get(
						'start-creating-one-to-show-your-data'
					),
					image: '/states/empty_state.svg',
					title: Liferay.Language.get('no-system-data-sets-created'),
				}}
				id="CustomizedSystemDataSets"
				itemsActions={[]}
				sorts={[{direction: 'desc', key: 'dateCreated'}]}
				views={views}
			/>
		</div>
	);
};

export default SystemDataSets;
