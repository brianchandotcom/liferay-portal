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
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {SearchInput} from 'data-engine-taglib';
import React, {useContext, useRef, useState} from 'react';

import {getDataDefinitionField} from '../../utils/dataDefinition.es';
import EditTableViewContext, {
	REMOVE_FILTER_VALUE,
	UPDATE_FILTER_VALUE
} from './EditTableViewContext.es';

export const MultipleSelectFilter = ({dataDefinitionField, useFieldLabel}) => {
	const [{dataListView}, dispatch] = useContext(EditTableViewContext);

	const {
		customProperties: {options = {}},
		label: fieldLabel,
		name: fieldName
	} = dataDefinitionField;
	const localizedOptions = options[themeDisplay.getLanguageId()] || [];

	const values = dataListView.appliedFilters[fieldName] || [];

	const [active, setActive] = useState(false);

	const onClickItem = optionValue => {
		let newValue;

		if (values.includes(optionValue)) {
			newValue = values.filter(item => item !== optionValue);
		}
		else {
			newValue = [...values, optionValue];
		}

		if (newValue.length) {
			dispatch({
				payload: {fieldName, value: newValue},
				type: UPDATE_FILTER_VALUE
			});
		}
		else {
			dispatch({
				payload: {fieldName},
				type: REMOVE_FILTER_VALUE
			});
		}
	};

	const alignElementRef = useRef();
	const dropdownMenuRef = useRef();

	const [keywords, setKeywords] = useState('');
	const searchRegex = new RegExp(keywords, 'ig');

	const onClickClear = () => {
		dispatch({
			payload: {fieldName},
			type: REMOVE_FILTER_VALUE
		});
	};

	const onClickSelectAll = () => {
		dispatch({
			payload: {
				fieldName,
				value: localizedOptions.map(({value}) => value)
			},
			type: UPDATE_FILTER_VALUE
		});
	};

	return (
		<div className="multiple-select-filter table-view-filter">
			{useFieldLabel ? (
				<label>
					{fieldLabel[themeDisplay.getLanguageId()] || fieldName}
				</label>
			) : (
				<label>
					{Liferay.Language.get('filter-entries')}

					<span
						className="lfr-portal-tooltip tooltip-icon"
						data-title={Liferay.Language.get(
							'filter-entries-by-setting-a-value-for-this-column'
						)}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>
			)}

			<ClayButton
				className={classNames('multiple-select-filter-trigger', {
					empty: values.length === 0
				})}
				displayType="secondary"
				onClick={() => setActive(!active)}
				ref={alignElementRef}
			>
				<span className="multiple-select-filter-values">
					{values.length === 0
						? Liferay.Language.get('choose-options')
						: localizedOptions
								.filter(({value}) => values.includes(value))
								.map(({label, value}) => label || value)
								.join(', ')}
				</span>

				<ClayIcon symbol="caret-bottom" />
			</ClayButton>

			<ClayDropDown.Menu
				active={active}
				alignElementRef={alignElementRef}
				alignmentPosition={Align.BottomCenter}
				autoBestAlign={true}
				className="multiple-select-filter-dropdown"
				hasRightSymbols
				onSetActive={newVal => setActive(newVal)}
				ref={dropdownMenuRef}
			>
				<ClayDropDown.ItemList className="multiple-select-filter-dropdown-items">
					<ClayDropDown.Item className="dropdown-search" key="search">
						<SearchInput
							onChange={setKeywords}
							searchText={keywords}
						/>
					</ClayDropDown.Item>

					<ClayDropDown.Item
						className={classNames('dropdown-select-all', {
							clearable: values.length > 0
						})}
						key="all"
					>
						<ClayButton
							className="all text-secondary"
							displayType="unstyled"
							onClick={onClickSelectAll}
						>
							{Liferay.Language.get('select-all')}
						</ClayButton>

						{values.length > 0 && (
							<ClayButton
								className="clear text-secondary"
								displayType="unstyled"
								onClick={onClickClear}
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						)}
					</ClayDropDown.Item>

					<ClayDropDown.Divider />

					{localizedOptions
						.filter(({label}) => searchRegex.test(label))
						.map(({label, value}, index) => (
							<ClayDropDown.Item
								key={index}
								onClick={event => {
									event.preventDefault();

									onClickItem(value);
								}}
								symbolRight={
									values.includes(value) ? 'check' : ''
								}
							>
								{label || value}
							</ClayDropDown.Item>
						))}
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</div>
	);
};

const RENDERERS = {
	radio: MultipleSelectFilter,
	select: MultipleSelectFilter
};

export const FilterRenderer = ({fieldName, useFieldLabel}) => {
	const [{dataDefinition}] = useContext(EditTableViewContext);

	const dataDefinitionField = getDataDefinitionField(
		dataDefinition,
		fieldName
	);

	const Renderer = RENDERERS[dataDefinitionField.fieldType];

	if (!Renderer) {
		return null;
	}

	return (
		<Renderer
			dataDefinitionField={dataDefinitionField}
			useFieldLabel={useFieldLabel}
		/>
	);
};

export const TableViewFiltersList = () => {
	const [
		{
			dataListView: {fieldNames},
			focusedColumn
		}
	] = useContext(EditTableViewContext);

	const filteredFieldNames = focusedColumn
		? fieldNames.filter(fieldName => fieldName === focusedColumn)
		: fieldNames;

	return (
		<div
			className={classNames('table-view-filters-list', {
				'single-field': !!focusedColumn
			})}
		>
			{!focusedColumn && (
				<h4 className="table-view-filters-header">
					{Liferay.Language.get('filter-entries-by-columns')}

					<span
						className="lfr-portal-tooltip tooltip-icon"
						data-title={Liferay.Language.get(
							'filter-entries-by-setting-filters-for-each-column'
						)}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</h4>
			)}

			{filteredFieldNames.map(fieldName => (
				<FilterRenderer
					fieldName={fieldName}
					key={fieldName}
					useFieldLabel={!focusedColumn}
				/>
			))}
		</div>
	);
};

export default TableViewFiltersList;
