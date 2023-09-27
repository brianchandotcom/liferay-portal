/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React, {useState} from 'react';

import {API_URL, FDS_DEFAULT_PROPS} from '../../js/Constants';

import './FDSViewItemSelector.scss';

interface ISelectedItem {
	externalReferenceCode: string;
	id: string;
}

const views = [
	{
		contentRenderer: 'list',
		name: 'list',
		schema: {
			description: 'description',
			symbol: 'symbol',
			title: 'label',
		},
	},
];

const FDSViewItemSelector = ({
	className,
	classNameId,
	namespace,
}: {
	className: String;
	classNameId: String;
	namespace: String;
}) => {
	const [selectedItem, setSelectedItem] = useState<ISelectedItem>();

	return (
		<div className="fds-view-item-selector">
			<ClayModal.Body>
				<FrontendDataSet
					{...FDS_DEFAULT_PROPS}
					apiURL={API_URL.FDS_VIEWS}
					id={`${namespace}FDSViewsItemSelector`}
					onSelect={({
						selectedItems,
					}: {
						selectedItems: Array<ISelectedItem>;
					}) => {
						setSelectedItem({
							externalReferenceCode:
								selectedItems[0].externalReferenceCode,
							id: selectedItems[0].id,
						});
					}}
					selectedItemsKey="id"
					selectionType="single"
					views={views}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							className="btn-cancel"
							displayType="secondary"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							className="item-preview selector-button"
							data-value={`{
								"className": "${className}", 
								"classNameId": "${classNameId}", 
								"classPK": "${selectedItem?.id}", 
								"externalReferenceCode": "${selectedItem?.externalReferenceCode}"}`}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
};

export default FDSViewItemSelector;
