/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';

function SelectableTable({dataLoading, items: itemsProp, schema, style}) {
	const {namespace, selectedItemsKey} = useContext(FrontendDataSetContext);

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
												aria-label={sub(
													Liferay.Language.get(
														'select-x'
													),
													`${
														item[
															schema
																.firstColumnName
														]
													} ${field.label}`
												)}
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
