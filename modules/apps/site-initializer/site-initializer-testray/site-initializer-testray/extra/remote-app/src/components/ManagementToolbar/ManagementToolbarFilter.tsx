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
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import {ListViewContext, ListViewTypes} from '../../context/ListViewContext';
import SearchBuilder from '../../core/SearchBuilder';
import useFormActions from '../../hooks/useFormActions';
import i18n from '../../i18n';
import {FilterSchema} from '../../schema/filter';
import Form from '../Form';
import {RendererFields} from '../Form/Renderer';
import {FieldOptions} from '../Form/Renderer/Renderer';

type ManagementToolbarFilterProps = {
	filterSchema?: FilterSchema;
};

type FilterBody = {
	buttonRef: React.RefObject<HTMLButtonElement>;
	filterSchema: FilterSchema | undefined;
	setPosition: React.Dispatch<React.SetStateAction<number>>;
};

const FilterBody = ({buttonRef, filterSchema, setPosition}: FilterBody) => {
	const [filter, setFilter] = useState('');

	const fields = useMemo(() => filterSchema?.fields as RendererFields[], [
		filterSchema?.fields,
	]);

	useEffect(() => {
		const container = document.querySelector('.testray-page');

		const scrollHandler = () => {
			const screenHeight = (container as any)?.offsetHeight;
			const buttonRelativePosition =
				buttonRef?.current?.getBoundingClientRect().bottom ?? 0;

			setPosition(screenHeight - buttonRelativePosition);
		};

		container?.addEventListener('scroll', scrollHandler);

		return () => {
			container?.removeEventListener('scroll', scrollHandler);
		};
	}, [buttonRef, setPosition]);

	const initialFilters = useMemo(() => {
		const initialValues: {[key: string]: string} = {};

		for (const field of fields) {
			initialValues[field.name] = '';
		}

		return initialValues;
	}, [fields]);

	const [fieldOptions, setFieldOptions] = useState<FieldOptions>({});
	const formActions = useFormActions();
	const [listViewContext, dispatch] = useContext(ListViewContext);
	const [form, setForm] = useState(() => ({
		...initialFilters,
		...listViewContext.filters.filter,
	}));

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
		<div className="align-content-between d-flex flex-column">
			<div className="dropdown-header">
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

			<div className="body-filters">
				<div className="popover-filter-content">
					<Form.Renderer
						fieldOptions={fieldOptions}
						fields={fields}
						filter={filter}
						form={form}
						onChange={onChange}
						setFieldOptions={setFieldOptions}
					/>
				</div>
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
		</div>
	);
};

const MENU_POPOVER_HEIGHT = 580;

const ManagementToolbarFilter: React.FC<ManagementToolbarFilterProps> = ({
	filterSchema,
}) => {
	const ref = useRef<HTMLButtonElement>(null);

	const [position, setPosition] = useState<number>(MENU_POPOVER_HEIGHT);

	const popoverAlignPosition =
		position < MENU_POPOVER_HEIGHT ? 'top-right' : 'bottom-right';

	return (
		<ClayPopover
			alignPosition={popoverAlignPosition}
			className="popover-filter"
			closeOnClickOutside
			disableScroll
			show={position !== undefined}
			trigger={
				<ClayButton
					className="filter-button nav-link"
					displayType="unstyled"
					ref={ref}
				>
					<span className="navbar-breakpoint-down-d-none">
						<ClayIcon
							className="inline-item inline-item-after inline-item-before"
							symbol="filter"
						/>
					</span>

					<span className="navbar-breakpoint-d-none">
						<ClayIcon symbol="filter" />
					</span>
				</ClayButton>
			}
		>
			<FilterBody
				buttonRef={ref}
				filterSchema={filterSchema}
				setPosition={setPosition}
			/>
		</ClayPopover>
	);
};

export default ManagementToolbarFilter;
