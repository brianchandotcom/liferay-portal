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
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayPopover from '@clayui/popover';
import {useCallback, useContext, useMemo, useState} from 'react';

import {ListViewContext, ListViewTypes} from '../../context/ListViewContext';
import useFormActions from '../../hooks/useFormActions';
import i18n from '../../i18n';
import {FilterSchema} from '../../schema/filter';
import {SearchBuilder} from '../../util/search';
import Form from '../Form';
import {RendererFields} from '../Form/Renderer';
import {FieldOptions} from '../Form/Renderer/Renderer';

type ManagementToolbarFilterProps = {
	filterSchema?: FilterSchema;
};

const ManagementToolbarFilter: React.FC<ManagementToolbarFilterProps> = ({
	filterSchema,
}) => {
	const fields = useMemo(() => filterSchema?.fields as RendererFields[], [
		filterSchema?.fields,
	]);

	const initialFilters = useMemo(() => {
		const initialValues: {[key: string]: string} = {};

		for (const field of fields) {
			initialValues[field.name] = '';
		}

		return initialValues;
	}, [fields]);

	const [listViewContext, dispatch] = useContext(ListViewContext);
	const [fieldOptions, setFieldOptions] = useState<FieldOptions>({});
	const [filter, setFilter] = useState('');
	const [form, setForm] = useState(listViewContext.filters.filter);
	const formActions = useFormActions();

	const onChange = formActions.form.onChange({form, setForm});

	const onClear = () => {
		setForm(initialFilters);

		dispatch({
			payload: null,
			type: ListViewTypes.SET_CLEAR,
		});
	};

	const onApply = useCallback(() => {
		const filterCleaned = SearchBuilder.removeEmptyFilter(form);

		const entries = Object.keys(filterCleaned).map((key) => {
			const field = fields?.find(({name}) => name === key);

			const value = filterCleaned[key];

			return {
				label: field?.label,
				name: key,
				value,
			};
		});

		dispatch({
			payload: {filters: {entries, filter: filterCleaned}},
			type: ListViewTypes.SET_UPDATE_FILTERS_AND_SORT,
		});
	}, [dispatch, fields, form]);

	return (
		<ClayPopover
			alignPosition="bottom-right"
			className="filter-popover"
			closeOnClickOutside
			disableScroll={false}
			trigger={
				<div>
					<ClayManagementToolbar.Item>
						<ClayButtonWithIcon
							aria-label={i18n.translate('filter')}
							className="nav-btn nav-btn-monospaced"
							displayType="unstyled"
							symbol="filter"
							title={i18n.translate('filter')}
						/>
					</ClayManagementToolbar.Item>
				</div>
			}
		>
			<div className="dropdown-header filter-search">
				<p className="font-weight-bold my-2">
					{i18n.translate('filter-results')}
				</p>

				<Form.Input
					name="search-filter"
					onChange={({target: {value}}) => setFilter(value)}
					placeholder={i18n.translate('search-filters')}
					value={filter}
				/>

				<Form.Divider />
			</div>

			<div className="form-filters">
				<Form.Renderer
					fieldOptions={fieldOptions}
					fields={fields}
					filter={filter}
					form={form}
					onChange={onChange}
					setFieldOptions={setFieldOptions}
				/>
			</div>

			<div className="popover-footer">
				<Form.Divider />

				<ClayButton onClick={onApply}>
					{i18n.translate('apply')}
				</ClayButton>

				<ClayButton
					className="ml-3"
					displayType="secondary"
					onClick={onClear}
				>
					{i18n.translate('clear')}
				</ClayButton>
			</div>
		</ClayPopover>
	);
};

export default ManagementToolbarFilter;
