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
import {ClayCheckbox, ClayInput, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClaySticker from '@clayui/sticker';
import ClayToolbar from '@clayui/toolbar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useState} from 'react';

const DEFAULT_DELTA = 10;

const SCOPES = {
	ALL: 'all',
	SELECTED: 'selected',
};

function InstanceSelector({selected, setSelected, virtualInstances}) {
	const [activePage, setActivePage] = useState(1);
	const [currentVirtualInstances, setCurrentVirtualInstances] = useState(
		virtualInstances
	);
	const [delta, setDelta] = useState(DEFAULT_DELTA);
	const [searchValue, setSearchValue] = useState('');

	const instanceMap = virtualInstances.reduce(
		(acc, curr) => ({
			...acc,
			[`${curr.id}`]: curr.name,
		}),
		{}
	);

	const _handleRemoveSelect = (id) =>
		setSelected(selected.filter((item) => id !== item));

	const _handleToggleSelect = (id) =>
		setSelected(
			selected.includes(id)
				? selected.filter((item) => id !== item)
				: [...selected, id]
		);

	const _handleToggleSelectAll = () => {
		const currentVirtualInstanceIds = currentVirtualInstances
			.slice(activePage * delta - delta, activePage * delta)
			.map((item) => item.id);

		const clearCurrentFromSelected = selected.filter(
			(id) => !currentVirtualInstanceIds.includes(id)
		);

		setSelected(
			currentVirtualInstanceIds.every((id) => selected.includes(id))
				? clearCurrentFromSelected
				: [...clearCurrentFromSelected, ...currentVirtualInstanceIds]
		);
	};

	const _isSelectAllChecked = () =>
		currentVirtualInstances.length > 0 &&
		currentVirtualInstances
			.slice(activePage * delta - delta, activePage * delta)
			.every(({id}) => selected.includes(id));

	useEffect(() => {
		if (searchValue) {
			const searchValueWordsArray = searchValue
				.toLowerCase()
				.split(/[\s,]+/);

			setCurrentVirtualInstances(
				virtualInstances.filter(({id, name}) =>
					searchValueWordsArray.some(
						(word) =>
							String(id).includes(word) ||
							name.toLowerCase().includes(word)
					)
				)
			);
		}
		else {
			setCurrentVirtualInstances(virtualInstances);
		}
	}, [virtualInstances, searchValue]);

	return (
		<>
			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<ClayCheckbox
							aria-label={Liferay.Language.get('toggle')}
							checked={_isSelectAllChecked()}
							onChange={_handleToggleSelectAll}
						/>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.Search>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={Liferay.Language.get('search')}
								className="form-control input-group-inset input-group-inset-after"
								onChange={(event) =>
									setSearchValue(event.target.value)
								}
								onKeyDown={(event) => {
									if (event.key === 'Enter') {
										event.preventDefault();
									}
								}}
								placeholder={Liferay.Language.get('search')}
								type="text"
								value={searchValue}
							/>

							<ClayInput.GroupInsetItem after tag="span">
								{searchValue ? (
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'clear'
										)}
										displayType="unstyled"
										onClick={() => setSearchValue('')}
										symbol="times-circle"
									/>
								) : (
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'search'
										)}
										displayType="unstyled"
										symbol="search"
									/>
								)}
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayManagementToolbar.Search>
			</ClayManagementToolbar>

			{selected.length > 0 && (
				<ClayToolbar subnav={{displayType: 'primary'}}>
					<ClayToolbar.Nav>
						<ClayToolbar.Item expand>
							<ClayToolbar.Section>
								<span className="component-text text-truncate-inline">
									<span className="text-truncate">
										{Liferay.Util.sub(
											Liferay.Language.get(
												'x-instances-selected'
											),
											selected.length
										)}
									</span>
								</span>
							</ClayToolbar.Section>
						</ClayToolbar.Item>

						<ClayToolbar.Item>
							<ClayButton
								className="component-link tbar-link"
								displayType="unstyled"
								onClick={() => setSelected([])}
							>
								{Liferay.Language.get('deselect-all')}
							</ClayButton>
						</ClayToolbar.Item>
					</ClayToolbar.Nav>

					{!!selected.length && (
						<div className="instance-labels">
							{selected.map((id) => (
								<ClayLabel
									className="component-label tbar-label"
									closeButtonProps={{
										onClick: () => _handleRemoveSelect(id),
									}}
									displayType="unstyled"
									key={`label-${id}`}
								>
									{instanceMap[id]}
								</ClayLabel>
							))}
						</div>
					)}
				</ClayToolbar>
			)}

			{currentVirtualInstances.length ? (
				<>
					<ClayList>
						{currentVirtualInstances
							.slice(
								activePage * delta - delta,
								activePage * delta
							)
							.map(({id, name}) => (
								<ClayList.Item
									flex
									key={`list-item-${id}`}
									onClick={() => _handleToggleSelect(id)}
								>
									<ClayList.ItemField>
										<ClayCheckbox
											aria-label={`toggle-${id}`}
											checked={selected.includes(id)}
											onChange={() =>
												_handleToggleSelect(id)
											}
										/>
									</ClayList.ItemField>

									<ClayList.ItemField expand>
										<ClayList.ItemTitle>
											{name}
										</ClayList.ItemTitle>

										<ClayList.ItemText>
											{Liferay.Util.sub(
												Liferay.Language.get(
													'instance-id-x'
												),
												id
											)}
										</ClayList.ItemText>
									</ClayList.ItemField>
								</ClayList.Item>
							))}
					</ClayList>

					<ClayPaginationBarWithBasicItems
						activeDelta={delta}
						activePage={activePage}
						ellipsisBuffer={1}
						labels={{
							paginationResults: Liferay.Language.get(
								'showing-x-to-x-of-x-entries'
							),
							perPageItems: Liferay.Language.get('x-entries'),
							selectPerPageItems: Liferay.Language.get(
								'x-entries'
							),
						}}
						onDeltaChange={setDelta}
						onPageChange={setActivePage}
						totalItems={currentVirtualInstances.length}
					/>
				</>
			) : (
				<ClayList>
					<ClayList.Item flex>
						<ClayList.ItemField expand>
							<ClayList.ItemText>
								{Liferay.Language.get('no-results-found')}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>
				</ClayList>
			)}
		</>
	);
}

function ExecutionScope({
	initialCompanyIds = [],
	initialScope,
	portletNamespace,
	virtualInstances = [],
}) {
	const [selected, setSelected] = useState(initialCompanyIds);
	const [scope, setScope] = useState(initialScope || SCOPES.ALL);

	return (
		<div className="execution-scope-sheet sheet sheet-lg">
			<h2 className="sheet-title">
				<span>{Liferay.Language.get('execution-scope')}</span>

				<ClayTooltipProvider>
					<ClaySticker
						data-tooltip-align="bottom-left"
						displayType="secondary"
						size="md"
						title={Liferay.Language.get('execution-scope-help')}
					>
						<ClayIcon symbol="question-circle-full" />
					</ClaySticker>
				</ClayTooltipProvider>
			</h2>

			<ClayRadioGroup
				name={`${portletNamespace}scope`}
				onSelectedValueChange={(newScope) => setScope(newScope)}
				selectedValue={scope}
			>
				<ClayRadio
					label={Liferay.Language.get('all-instances')}
					value={SCOPES.ALL}
				/>

				<ClayRadio
					label={Liferay.Language.get('selected-instances')}
					value={SCOPES.SELECTED}
				/>
			</ClayRadioGroup>

			{scope === SCOPES.SELECTED && (
				<InstanceSelector
					selected={selected}
					setSelected={setSelected}
					virtualInstances={virtualInstances}
				/>
			)}

			<input
				name={`${portletNamespace}companyIds`}
				type="hidden"
				value={
					scope === SCOPES.ALL
						? virtualInstances.map(({id}) => id).toString()
						: selected.toString()
				}
			/>
		</div>
	);
}

export default ExecutionScope;
