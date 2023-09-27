/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';
import {ListViewContext, ListViewTypes} from '~/context/ListViewContext';
import SearchBuilder from '~/core/SearchBuilder';
import useFormActions from '~/hooks/useFormActions';
import i18n from '~/i18n';
import {FilterSchema} from '~/schema/filter';

import Form from '../Form';
import {RendererFields} from '../Form/Renderer';
import {FieldOptions} from '../Form/Renderer/Renderer';
type ManagementToolbarFilterProps = {
	filterSchema?: FilterSchema;
};

type FilterBodyProps = {
	buttonRef: React.RefObject<HTMLButtonElement>;
	filterSchema: FilterSchema | undefined;
	setPosition: React.Dispatch<React.SetStateAction<number>>;
	setVisible: React.Dispatch<React.SetStateAction<boolean>>;
};

const FilterBody: React.FC<FilterBodyProps> = ({
	buttonRef,
	filterSchema,
	setPosition,
	setVisible,
}) => {
	const [filter, setFilter] = useState('');

	const fields = useMemo(() => filterSchema?.fields as RendererFields[], [
		filterSchema?.fields,
	]);

	useEffect(() => {
		const container = document.querySelector('.tr-main__body__page');

		const scrollHandler = () => {
			const screenHeight = (container as any)?.offsetHeight;
			const buttonRelativePosition =
				buttonRef?.current?.getBoundingClientRect().bottom ?? 0;

			const calculatePosition = screenHeight - buttonRelativePosition;

			const position = calculatePosition > 0 ? calculatePosition : 1;

			setPosition(position);
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
	};

	const clearDisabled = Object.values(form).every(
		(value) => !value || !value.length
	);

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
			type: ListViewTypes.SET_FILTERS,
		});

		setVisible(false);
	}, [dispatch, fields, form, setVisible]);

	return (
		<div className="align-content-between d-flex flex-column">
			<div className="dropdown-header">
				{fields.length > 1 && (
					<>
						<p className="font-weight-bold my-2">
							{i18n.translate('filter-results')}
						</p>

						<Form.Input
							name="search-filter"
							onChange={({target: {value}}) => setFilter(value)}
							placeholder={i18n.translate('search-filters')}
							value={filter}
						/>

						<ClayButtonWithIcon
							aria-label={i18n.translate('clear')}
							className="clear-button"
							displayType="unstyled"
							onClick={() => setFilter('')}
							symbol="times"
							title={i18n.translate('clear')}
						/>

						<Form.Divider />
					</>
				)}
			</div>

			<div className="management-toolbar-body">
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

				<ClayButton disabled={clearDisabled} onClick={onApply}>
					{i18n.translate('apply')}
				</ClayButton>

				<ClayButton
					className="ml-3"
					disabled={clearDisabled}
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
	const [visible, setVisible] = useState(false);
	const ref = useRef<HTMLButtonElement>(null);

	const [position, setPosition] = useState<number>(MENU_POPOVER_HEIGHT);

	const popoverAlignPosition =
		position < MENU_POPOVER_HEIGHT ? 'top-right' : 'bottom-right';

	return (
		<ClayPopover
			alignPosition={popoverAlignPosition}
			className={classNames('popover-management-toolbar', {
				'popover-management-toolbar-small':
					filterSchema?.fields?.length === 1,
			})}
			closeOnClickOutside
			disableScroll
			onShowChange={setVisible}
			show={visible && position > 0}
			trigger={
				<ClayButton
					className="management-toolbar-buttons nav-link"
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
				setVisible={setVisible}
			/>
		</ClayPopover>
	);
};

export default ManagementToolbarFilter;
