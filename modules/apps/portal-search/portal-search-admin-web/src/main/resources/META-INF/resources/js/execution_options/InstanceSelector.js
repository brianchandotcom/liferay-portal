/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayToolbar from '@clayui/toolbar';
import {ManagementToolbar} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const DEFAULT_DELTA = 10;

/**
 * A paginated list of virtual instances.
 */
function InstanceSelector({selected, setSelected, virtualInstances}) {
	const [activePage, setActivePage] = useState(1);
	const [currentVirtualInstances, setCurrentVirtualInstances] = useState(
		virtualInstances
	); // The virtual instances currently in view.
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
		!!currentVirtualInstances.length &&
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
			<ManagementToolbar.Container>
				<ManagementToolbar.ItemList>
					<ManagementToolbar.Item>
						<ClayCheckbox
							aria-label={Liferay.Language.get('toggle')}
							checked={_isSelectAllChecked()}
							onChange={_handleToggleSelectAll}
						/>
					</ManagementToolbar.Item>
				</ManagementToolbar.ItemList>

				<ManagementToolbar.Search>
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
				</ManagementToolbar.Search>
			</ManagementToolbar.Container>

			{!!selected.length && (
				<ClayToolbar subnav={{displayType: 'primary'}}>
					<ClayToolbar.Nav>
						<ClayToolbar.Item expand>
							<ClayToolbar.Section>
								<span className="component-text text-truncate-inline">
									<span className="text-truncate">
										{sub(
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
											{sub(
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

export default InstanceSelector;
