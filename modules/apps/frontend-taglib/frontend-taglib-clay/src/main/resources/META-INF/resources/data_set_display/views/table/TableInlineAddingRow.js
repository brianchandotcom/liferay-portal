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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import {useIsMounted} from 'frontend-js-react-web';
import React, {useContext, useState} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';
import {getInputRendererById} from '../../utils/dataRenderers';

function TableInlineAddingRow({fields, selectable}) {
	const {
		createInlineItem,
		itemsChanges,
		toggleItemInlineEdit,
		updateItem,
	} = useContext(DataSetDisplayContext);
	const isMounted = useIsMounted();
	const [loading, setLoading] = useState(false);
	const itemHasChanged =
		itemsChanges[0] && !!Object.keys(itemsChanges[0]).length;

	return (
		<ClayTable.Row>
			{selectable && (
				<ClayTable.Cell className="data-set-item-selector-wrapper" />
			)}
			{fields.map((field) => {
				let InputRenderer = null;

				if (field.inlineEditSettings?.type) {
					InputRenderer = getInputRendererById(
						field.inlineEditSettings.type
					);
				}

				const valuePath = Array.isArray(field.fieldName)
					? field.fieldName.map((property) =>
							property === 'LANG'
								? Liferay.ThemeDisplay.getDefaultLanguageId()
								: property
					  )
					: [field.fieldName];

				const rootPropertyName = valuePath[0];

				const newItem = itemsChanges[0] || {};

				return (
					<ClayTable.Cell key={field.fieldName}>
						{InputRenderer ? (
							<InputRenderer
								updateItem={(value) => {
									updateItem(
										0,
										rootPropertyName,
										valuePath,
										value
									);
								}}
								value={
									newItem[rootPropertyName] &&
									newItem[rootPropertyName].value
								}
								valuePath={rootPropertyName}
							/>
						) : null}
					</ClayTable.Cell>
				);
			})}
			<ClayTable.Cell className="data-set-item-actions-wrapper">
				<div className="d-flex justify-content-end">
					<ClayButtonWithIcon
						className="mr-1"
						disabled={!itemHasChanged}
						displayType="secondary"
						onClick={() => {
							toggleItemInlineEdit(0);
						}}
						small
						symbol="times-small"
					/>
					{loading ? (
						<ClayButton disabled monospaced small>
							<ClayLoadingIndicator small />
						</ClayButton>
					) : (
						<ClayButtonWithIcon
							disabled={loading || !itemHasChanged}
							onClick={() => {
								setLoading(true);
								createInlineItem().finally(() => {
									if (isMounted()) {
										setLoading(false);
									}
								});
							}}
							small
							symbol="check"
						/>
					)}
				</div>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

export default TableInlineAddingRow;
