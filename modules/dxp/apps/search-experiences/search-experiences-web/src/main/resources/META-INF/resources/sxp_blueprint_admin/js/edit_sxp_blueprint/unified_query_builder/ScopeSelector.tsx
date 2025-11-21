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
import React, {useContext, useMemo, useState} from 'react';

import ThemeContext from '../../shared/ThemeContext';
import {STATUS} from '../UQBEditSXPBlueprintForm';

type Status = STATUS.ACTIVE | STATUS.INACTIVE;

type Scope = {
	externalReferenceCode: string;
	name: string;
	status: Status;
	type: string;
};

type Sorting = {
	column: React.Key;
	direction: 'ascending' | 'descending';
};

export default function ScopeSelector({
	scope,
	setScope,
}: {
	scope: Scope[];
	setScope: (scope: Scope[]) => void;
}) {
	const [collapseSection, setCollapseSection] = useState(false);
	const [sort, setSort] = useState<Sorting>();

	const {
		namespace,
		selectScopeURL,
	}: {namespace: string; selectScopeURL: string} = useContext(ThemeContext);

	const filteredScope = useMemo(() => {
		if (!sort) {
			return scope;
		}

		return scope.slice().sort((a, b) => {
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
			}) => {
				if (!selectedItem) {
					return;
				}

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
						externalReferenceCode:
							selectedItem.groupexternalreferencecode,
						name: selectedItem.groupdescriptivename,
						status: 'active',
						type: selectedItem.groupscopelabel,
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

	return (
		<div className="scope-selector sheet">
			<ClayLayout.SheetHeader className="mb-3">
				<span className="text-6 text-weight-bold">
					{Liferay.Language.get('scope')}
				</span>

				<ClayButton
					aria-label={Liferay.Language.get('collapse')}
					className="c-ml-2 component-action float-right"
					displayType="unstyled"
					onClick={() => setCollapseSection(!collapseSection)}
				>
					<ClayIcon
						symbol={collapseSection ? 'angle-right' : 'angle-down'}
					/>
				</ClayButton>
			</ClayLayout.SheetHeader>

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
										id: 'type',
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
								{filteredScope.map((scopeItem, index) => (
									<Row key={index}>
										<Cell>{scopeItem.name}</Cell>

										<Cell>{scopeItem.type}</Cell>

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
