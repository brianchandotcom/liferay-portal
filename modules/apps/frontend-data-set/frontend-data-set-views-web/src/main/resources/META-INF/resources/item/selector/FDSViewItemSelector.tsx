/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React, {useState} from 'react';

import {API_URL, PAGINATION_PROPS} from '../../js/Constants';

import './FDSViewItemSelector.scss';

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
}: {
	className: String;
	classNameId: String;
}) => {
	const [selectedId, setSelectedId] = useState<String>();

	return (
		<div className="fds-view-item-selector">
			<ClayModal.Body>
				<FrontendDataSet
					apiURL={API_URL.FDS_VIEWS}
					id="FDSViewsItemSelector"
					onSelect={({
						selectedItems,
					}: {
						selectedItems: Array<{id: string}>;
					}) => {
						setSelectedId(selectedItems[0].id);
					}}
					selectedItemsKey="id"
					selectionType="single"
					style="fluid"
					views={views}
					{...PAGINATION_PROPS}
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
							data-value={`{"classPK": "${selectedId}", "className": "${className}",  "classNameId": "${classNameId}"}`}
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
