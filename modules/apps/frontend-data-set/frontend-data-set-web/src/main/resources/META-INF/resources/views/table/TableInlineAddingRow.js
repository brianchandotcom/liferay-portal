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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {getInputRendererById} from '../../utils/renderer';
import DndTable from './dnd_table/index';

function TableInlineAddingRow({fields, selectable}) {
	const {
		createInlineItem,
		itemsChanges,
		toggleItemInlineEdit,
		updateItem,
	} = useContext(FrontendDataSetContext);

	const isMounted = useIsMounted();
	const [loading, setLoading] = useState(false);
	const itemHasChanged =
		itemsChanges[0] && !!Object.keys(itemsChanges[0]).length;

	return (
		<DndTable.Row>
			{selectable && (
				<DndTable.Cell
					className="item-selector"
					columnName="item-selector"
				/>
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
					<DndTable.Cell
						columnName={String(field.fieldName)}
						key={field.fieldName}
					>
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
					</DndTable.Cell>
				);
			})}

			<DndTable.Cell className="item-actions" columnName="item-actions">
				<div className="d-flex ml-auto">
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
			</DndTable.Cell>
		</DndTable.Row>
	);
}

export default TableInlineAddingRow;
