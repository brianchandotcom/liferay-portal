/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import AddOptionModal from './AddOptionModal';

export default function PicklistOptions() {
	const [modalVisible, setModalVisible] = useState<Boolean>(false);

	return (
		<>
			{modalVisible && (
				<AddOptionModal
					onCloseModal={() => {
						setModalVisible(false);
					}}
					option={null}
				/>
			)}

			<div className="panel-unstyled">
				<h3 className="panel-header panel-title text-secondary">
					{sub(
						Liferay.Language.get('x-options'),
						Liferay.Language.get('picklist')
					)}
				</h3>
			</div>

			<FrontendDataSet
				creationMenu={{
					primaryItems: [
						{
							label: Liferay.Language.get('add-new'),
							onClick: () => setModalVisible(true),
						},
					],
				}}
				emptyState={{
					description: Liferay.Language.get(
						'fortunately-it-is-very-easy-to-add-new-ones'
					),
					image: '/states/cms_empty_state_picklist_options.svg',
					title: Liferay.Language.get('there-are-no-options-yet'),
				}}
				id="optionList"
				style="fluid"
				views={[
					{
						contentRenderer: 'table',
						name: 'table',
						schema: {
							fields: [
								{
									fieldName: 'name',
									label: 'Name',
									sortable: true,
								},
								{
									fieldName: 'key',
									label: 'Key',
								},
								{
									fieldName: 'erc',
									label: 'ERC',
								},
							],
						},
					},
				]}
			/>
		</>
	);
}
