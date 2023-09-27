/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar, {
	ClayResultsBar,
} from '@clayui/management-toolbar';
import React, {useState} from 'react';

import {Events, useData, useDispatch} from './Context';
import {TColumn} from './types';
import {getOrderBy, getOrderBySymbol, getResultsLanguage} from './utils';

interface IManagementToolbarProps {
	addItemTitle?: string;
	columns: TColumn[];
	disabled: boolean;
	onAddItem?: () => void;
	showCheckbox: boolean;
}

const ManagementToolbar: React.FC<IManagementToolbarProps> = ({
	addItemTitle = Liferay.Language.get('add-item'),
	columns,
	disabled,
	onAddItem,
	showCheckbox,
}) => {
	const {
		filter,
		globalChecked,
		keywords: storedKeywords,
		pagination: {totalCount},
	} = useData();
	const dispatch = useDispatch();

	const [keywords, setKeywords] = useState('');
	const [searchMobile, setSearchMobile] = useState(false);

	return (
		<>
			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					{showCheckbox && (
						<ClayManagementToolbar.Item>
							<ClayCheckbox
								checked={globalChecked}
								data-testid="globalCheckbox"
								disabled={disabled}
								onChange={() => {
									dispatch({
										type: Events.ToggleGlobalCheckbox,
									});
								}}
							/>
						</ClayManagementToolbar.Item>
					)}

					<ClayDropDownWithItems
						items={columns
							.map(
								({
									expanded: _expanded,
									show: _show,
									...column
								}) => ({
									...column,
									onClick: () => {
										dispatch({
											payload: {
												value: column.id,
											},
											type: Events.ChangeFilter,
										});
									},
								})
							)
							.filter(({sortable = true}) => sortable)}
						trigger={
							<ClayButton
								className="nav-link"
								disabled={disabled}
								displayType="unstyled"
							>
								<span className="navbar-breakpoint-down-d-none">
									<span className="navbar-text-truncate">
										{Liferay.Language.get(
											'filter-and-order'
										)}
									</span>

									<ClayIcon
										className="inline-item inline-item-after"
										symbol="caret-bottom"
									/>
								</span>

								<span className="navbar-breakpoint-d-none">
									<ClayIcon symbol="filter" />
								</span>
							</ClayButton>
						}
					/>

					<ClayManagementToolbar.Item>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('sort')}
							className="nav-link nav-link-monospaced"
							disabled={disabled}
							displayType="unstyled"
							onClick={() => {
								dispatch({
									payload: {type: getOrderBy(filter)},
									type: Events.ChangeFilter,
								});
							}}
							symbol={getOrderBySymbol(filter)}
						/>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.Search
					onSubmit={(event) => {
						event.preventDefault();

						dispatch({
							payload: keywords,
							type: Events.ChangeKeywords,
						});
					}}
					showMobile={searchMobile}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={Liferay.Language.get('search')}
								className="form-control input-group-inset input-group-inset-after"
								disabled={disabled}
								onChange={({target: {value}}) =>
									setKeywords(value)
								}
								placeholder={Liferay.Language.get('search')}
								type="text"
								value={keywords}
							/>

							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('close')}
									className="navbar-breakpoint-d-none"
									disabled={disabled}
									displayType="unstyled"
									onClick={() => setSearchMobile(false)}
									symbol="times"
									title={Liferay.Language.get('close')}
								/>

								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('search')}
									disabled={disabled}
									displayType="unstyled"
									symbol="search"
									title={Liferay.Language.get('search-for')}
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayManagementToolbar.Search>

				{onAddItem && (
					<ClayManagementToolbar.ItemList>
						<ClayManagementToolbar.Item>
							<ClayButtonWithIcon
								aria-label={addItemTitle}
								className="nav-btn nav-btn-monospaced"
								data-tooltip-align="top"
								onClick={onAddItem}
								symbol="plus"
								title={addItemTitle}
							/>
						</ClayManagementToolbar.Item>
					</ClayManagementToolbar.ItemList>
				)}
			</ClayManagementToolbar>

			{storedKeywords && (
				<ClayResultsBar>
					<ClayResultsBar.Item expand>
						<span
							className="component-text text-truncate-inline"
							data-testid="subnav-description"
						>
							<span className="text-truncate">
								<span>{getResultsLanguage(totalCount)}</span>

								<strong>{` "${storedKeywords}"`}</strong>
							</span>
						</span>
					</ClayResultsBar.Item>

					<ClayResultsBar.Item>
						<ClayButton
							className="component-link tbar-link"
							data-testid="subnav-clear-button"
							displayType="unstyled"
							onClick={() => {
								dispatch({
									payload: '',
									type: Events.ChangeKeywords,
								});

								setKeywords('');
							}}
						>
							{Liferay.Language.get('clear')}
						</ClayButton>
					</ClayResultsBar.Item>
				</ClayResultsBar>
			)}
		</>
	);
};

export default ManagementToolbar;
