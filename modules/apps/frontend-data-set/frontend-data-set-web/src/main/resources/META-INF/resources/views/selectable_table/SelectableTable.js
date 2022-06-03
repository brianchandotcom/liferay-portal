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

import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';

function SelectableTable({dataLoading, items: itemsProp, schema, style}) {
	const {namespace} = useContext(FrontendDataSetContext);
	const {selectedItemsKey} = useContext(FrontendDataSetContext);
	const [items, setItems] = useState(null);

	useEffect(() => {
		setItems(itemsProp);
	}, [itemsProp]);

	function handleCheckboxChange(itemField, itemId, value) {
		const updatedItems = items.map((item) => {
			const currentItemId = item[selectedItemsKey];
			if (!itemId || currentItemId === itemId) {
				return {
					...item,
					restrictionFields: item.restrictionFields.map(
						(currentField) => {
							if (itemField !== currentField.name) {
								return currentField;
							}

							return {
								...currentField,
								value:
									typeof value === 'boolean'
										? value
										: !currentField.value,
							};
						}
					),
				};
			}

			return item;
		});

		setItems(updatedItems);
	}

	if (dataLoading) {
		return <ClayLoadingIndicator className="mt-7" />;
	}

	if (!items || items?.length === 0) {
		return (
			<ClayEmptyState
				description={Liferay.Language.get(
					'sorry,-no-results-were-found'
				)}
				imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
				title={Liferay.Language.get('no-results-found')}
			/>
		);
	}

	return (
		<div className={`table-style-${style}`}>
			<ClayTable borderless hover={false} responsive={false}>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell
							className="table-cell-expand-smaller"
							headingCell
							headingTitle
						>
							{schema.firstColumnLabel}
						</ClayTable.Cell>

						{items[0].restrictionFields.map((columnField) => {
							const checkedItems = items.reduce(
								(checked, item) => {
									const field = item.restrictionFields.find(
										(itemField) =>
											itemField.name === columnField.name
									);

									return checked + (field.value ? 1 : 0);
								},
								0
							);

							return (
								<ClayTable.Cell
									className="table-cell-expand-smaller"
									headingCell
									key={columnField.name}
								>
									<ClayCheckbox
										checked={checkedItems === items.length}
										className="mr-2"
										indeterminate={
											checkedItems > 0 &&
											checkedItems < items.length
										}
										label={columnField.label}
										name={`${columnField.name}_column`}
										onChange={() =>
											handleCheckboxChange(
												columnField.name,
												null,
												checkedItems === items.length
													? false
													: true
											)
										}
									/>
								</ClayTable.Cell>
							);
						})}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{items.map((item, i) => {
						const itemId = item[selectedItemsKey];

						return (
							<ClayTable.Row key={i}>
								<ClayTable.Cell>
									{item[schema.firstColumnName]}
								</ClayTable.Cell>

								{item.restrictionFields.map((field) => {
									return (
										<ClayTable.Cell key={field.name}>
											<ClayCheckbox
												checked={field.value}
												name={namespace + itemId}
												onChange={() => {
													handleCheckboxChange(
														field.name,
														itemId
													);
												}}
												value={field.name}
											/>
										</ClayTable.Cell>
									);
								})}
							</ClayTable.Row>
						);
					})}
				</ClayTable.Body>
			</ClayTable>
		</div>
	);
}

SelectableTable.propTypes = {
	items: PropTypes.arrayOf(PropTypes.object),
	itemsActions: PropTypes.array,
	schema: PropTypes.shape({
		firstColumnLabel: PropTypes.string.isRequired,
		firstColumnName: PropTypes.string.isRequired,
	}).isRequired,
	style: PropTypes.string.isRequired,
};

SelectableTable.defaultProps = {
	items: [],
};

export default SelectableTable;
