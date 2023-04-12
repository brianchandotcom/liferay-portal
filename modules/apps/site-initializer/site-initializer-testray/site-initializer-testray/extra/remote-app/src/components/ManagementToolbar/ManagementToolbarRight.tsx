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
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayPopover from '@clayui/popover';
import {ReactNode, useCallback, useContext, useState} from 'react';
import {ListViewContext, ListViewTypes} from '~/context/ListViewContext';
import {Liferay} from '~/services/liferay';

import i18n from '../../i18n';
import {FilterSchema} from '../../schema/filter';
import {Column} from '../Table';
import ManagementToolbarColumns from './ManagementToolbarColumns';
import ManagementToolbarFilter from './ManagementToolbarFilter';

export type IItem = {
	active?: boolean;
	checked?: boolean;
	disabled?: boolean;
	href?: string;
	items?: IItem[];
	label?: string;
	name?: string;
	onChange?: Function;
	onClick?: (event: React.MouseEvent<HTMLElement, MouseEvent>) => void;
	symbolLeft?: string;
	symbolRight?: string;
	type?:
		| 'checkbox'
		| 'contextual'
		| 'divider'
		| 'group'
		| 'item'
		| 'radio'
		| 'radiogroup';
	value?: string;
};

type ManagementToolbarRightProps = {
	actions: any;
	addButton?: () => void;
	buttons?: ReactNode | ((actions: any) => ReactNode);
	columns: Column[];
	disabled: boolean;
	display?: {
		columns?: boolean;
	};
	filterSchema?: FilterSchema;
};

const ManagementToolbarRight: React.FC<ManagementToolbarRightProps> = ({
	actions,
	addButton,
	buttons,
	display = {columns: true},
	columns,
	filterSchema,
}) => {
	const [{filters, pin}, dispatch] = useContext(ListViewContext);
	const [columnsDropdownVisible, setColumnsDropdownVisible] = useState(false);

	const hasAppliedFilters = !!filters.entries.length;

	const onPin = useCallback(() => {
		if (hasAppliedFilters) {
			dispatch({type: ListViewTypes.SET_PIN});

			return Liferay.Util.openToast({
				message: i18n.translate(
					pin
						? 'filters-unpinned-successfully'
						: 'filters-pinned-successfully'
				),
				type: 'success',
			});
		}

		Liferay.Util.openToast({
			message: i18n.translate(
				'you-must-select-one-or-more-filters-before-pinning'
			),
			type: 'danger',
		});
	}, [dispatch, hasAppliedFilters, pin]);

	return (
		<ClayManagementToolbar.ItemList>
			{filterSchema?.fields?.length && (
				<>
					<ClayManagementToolbar.Item>
						<ClayButtonWithIcon
							aria-label={i18n.translate('add-pin')}
							className="nav-btn nav-btn-monospaced"
							displayType="unstyled"
							onClick={onPin}
							symbol={i18n.translate(pin ? 'unpin' : 'pin')}
							title={i18n.translate(pin ? 'unpin' : 'pin')}
						/>
					</ClayManagementToolbar.Item>

					<ManagementToolbarFilter filterSchema={filterSchema} />
				</>
			)}

			{display.columns && (
				<ClayPopover
					alignPosition="bottom-right"
					className="body-columns popover-management-toolbar"
					closeOnClickOutside
					onShowChange={setColumnsDropdownVisible}
					show={columnsDropdownVisible}
					trigger={
						<ClayButton
							className="d-flex management-toolbar-buttons nav-link"
							displayType="unstyled"
						>
							<span className="navbar-breakpoint-down-d-none">
								<ClayIcon
									className="inline-item inline-item-after"
									symbol="columns"
								/>
							</span>

							<span className="navbar-breakpoint-d-none">
								<ClayIcon symbol="columns" />
							</span>
						</ClayButton>
					}
				>
					<ManagementToolbarColumns
						columns={columns}
						onClose={() => setColumnsDropdownVisible(false)}
					/>
				</ClayPopover>
			)}

			{typeof buttons === 'function' ? buttons(actions) : buttons}

			{actions?.create && addButton && (
				<ClayManagementToolbar.Item
					className="ml-2"
					onClick={addButton}
				>
					<ClayButtonWithIcon
						className="nav-btn nav-btn-monospaced"
						symbol="plus"
					/>
				</ClayManagementToolbar.Item>
			)}
		</ClayManagementToolbar.ItemList>
	);
};

export default ManagementToolbarRight;
