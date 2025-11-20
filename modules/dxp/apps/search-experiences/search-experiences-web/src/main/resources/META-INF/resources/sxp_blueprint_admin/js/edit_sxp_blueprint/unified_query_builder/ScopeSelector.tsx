/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Body, Cell, Head, Row, Table} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import {openSelectionModal} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import ThemeContext from '../../shared/ThemeContext';

type Status = 'active' | 'inactive';

type Scope = {
	erc: string;
	id: string;
	name: string;
	scopelabel: string;
	status: Status;
};

type Sorting = {
	column: React.Key;
	direction: 'ascending' | 'descending';
};

export default function ScopeSelector({
	initialScopeERCs = ['L_GUEST', 'd1bf5865-247d-4072-789c-f80dacb0b916'],
}: {
	initialScopeERCs?: string[];
}) {
	const [collapseSection, setCollapseSection] = useState(false);
	const [sort, setSort] = useState<Sorting>();
	const [scope, setScope] = useState<Scope[]>([]);

	const {
		namespace,
		selectScopeURL,
	}: {namespace: string; selectScopeURL: string} = useContext(ThemeContext);

	const filteredItems = useMemo(() => {
		if (!sort) {
			return scope;
		}

		return scope.sort((a, b) => {
			let cmp = new Intl.Collator('en', {numeric: true}).compare(
				a[sort.column as keyof Scope],
				b[sort.column as keyof Scope]
			);

			if (sort.direction === 'descending') {
				cmp *= -1;
			}

			return cmp;
		});
	}, [sort, scope]);

	const _handleSelectScope = () => {
		openSelectionModal({
			id: `${namespace}selectScope`,
			onSelect: (selectedItem: {
				groupdescriptivename: string;
				groupexternalreferencecode: string;
				groupid: string;
				groupscopelabel: string;
				type?: string;
			}) => {
				if (!selectedItem) {
					return;
				}

				console.log(selectedItem);

				if (
					scope.find(
						(item) =>
							item.externalReferenceCode ===
							selectedItem.groupexternalreferencecode
					)
				) {
					return;
				}

				setScope([
					...scope,
					{
						erc: selectedItem.groupexternalreferencecode,
						id: selectedItem.groupid,
						name: selectedItem.groupdescriptivename,
						scopelabel: selectedItem.groupscopelabel,
						status: 'active',
					},
				]);
			},
			selectEventName: `${namespace}selectScope`,
			title: Liferay.Language.get('select-scope'),
			url: selectScopeURL,
		});
	};

	const _handleRemoveScope = (index: number) => {
		const newScope = [...scope];

		newScope.splice(index, 1);

		setScope(newScope);
	};

	useEffect(() => {
		const fetchAllScope = async () => {
			const fetchScope = async (erc: string) => {
				try {

					// Enable Feature Flag LPD-41306 to use the new Headless API

					const response = await fetch(
						`/o/headless-admin-site/v1.0/sites/by-external-reference-code/${erc}`,
						{
							headers: new Headers({
								'Accept-Language':
									Liferay.ThemeDisplay.getBCP47LanguageId(),
								'Content-Type': 'application/json',
							}),
							method: 'GET',
						}
					);

					if (!response.ok) {
						return null;
					}

					const data = await response.json();

					return data;
				}
				catch (error) {
					console.error(
						`Error fetching site with ERC ${erc}:`,
						error
					);

					return null;
				}
			};

			const responses = await Promise.all(
				initialScopeERCs.map(fetchScope)
			);

			setScope(
				responses.map((item: any) => {
					if (item) {
						return {
							erc: item.externalReferenceCode,
							id: item.groupId,
							name: item.descriptiveName,
							scopelabel: '', // TODO get proper label
							status: item.active ? 'active' : 'inactive',
						};
					}
					else {
						return {
							erc: item.externalReferenceCode,
							id: '',
							name: item.externalReferenceCode,
							scopelabel: '',
							status: 'inactive',
						};
					}
				})
			);
		};

		fetchAllScope();
	}, []);

	return (
		<div className="sheet">
			<ClayLayout.Row justify="between">
				<ClayLayout.Col size={6}>
					<span className="text-6 text-weight-bold">
						{Liferay.Language.get('scope')}
					</span>
				</ClayLayout.Col>

				<ClayLayout.Col size={6}>
					<ClayButton
						aria-label={Liferay.Language.get('collapse')}
						className="c-ml-2 component-action float-right"
						displayType="unstyled"
						onClick={() => setCollapseSection(!collapseSection)}
					>
						<ClayIcon
							symbol={
								collapseSection ? 'angle-right' : 'angle-down'
							}
						/>
					</ClayButton>
				</ClayLayout.Col>
			</ClayLayout.Row>

			{!collapseSection && (
				<>
					<span className="text-4 text-secondary">
						{Liferay.Language.get('scope-selector-description')}
					</span>

					<div className="c-mt-4">
						<ClayButton
							displayType="secondary"
							onClick={_handleSelectScope}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol="plus" />
							</span>

							{Liferay.Language.get('select-scope')}
						</ClayButton>
					</div>

					{!!scope.length && (
						<Table
							className="c-mt-4"
							columnsVisibility={false}
							onSortChange={
								setSort as (sorting: Sorting | null) => void
							}
							sort={sort}
						>
							<Head
								items={[
									{
										id: 'name',
										name: Liferay.Language.get('name'),
										sortable: true,
									},
									{
										id: 'scopelabel',
										name: Liferay.Language.get('type'),
										sortable: true,
									},
									{
										id: 'status',
										name: Liferay.Language.get('status'),
										sortable: true,
										width: '20%',
									},
									{
										id: 'options',
										name: Liferay.Language.get('options'),
										sortable: false,
										width: '100px',
									},
								]}
							>
								{(column) => (
									<Cell
										key={column.id}
										sortable={column.sortable}
										width={column.width}
									>
										{column.name}
									</Cell>
								)}
							</Head>

							<Body>
								{filteredItems.map((scopeItem, index) => (
									<Row key={index}>
										<Cell>{scopeItem.name}</Cell>

										<Cell>{scopeItem.scopelabel}</Cell>

										<Cell>
											{scopeItem.status === 'active' ? (
												<ClayLabel displayType="success">
													{Liferay.Language.get(
														'active'
													)}
												</ClayLabel>
											) : (
												<ClayLabel displayType="secondary">
													{Liferay.Language.get(
														'inactive'
													)}
												</ClayLabel>
											)}
										</Cell>

										<Cell align="center">
											<ClayButton
												aria-label={Liferay.Language.get(
													'remove'
												)}
												className="component-action"
												displayType="unstyled"
												onClick={() =>
													_handleRemoveScope(index)
												}
											>
												<ClayIcon symbol="times-circle" />
											</ClayButton>
										</Cell>
									</Row>
								))}
							</Body>
						</Table>
					)}
				</>
			)}
		</div>
	);
}
