/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

type OrderByType = 'ASC' | 'DESC';

const ORDER_BY_OPTIONS: Array<{label: string; value: string}> = [
	{label: Liferay.Language.get('title'), value: 'title'},
	{label: Liferay.Language.get('create-date'), value: 'createDate'},
	{label: Liferay.Language.get('modified-date'), value: 'modifiedDate'},
	{label: Liferay.Language.get('publish-date'), value: 'publishDate'},
	{label: Liferay.Language.get('expiration-date'), value: 'expirationDate'},
	{label: Liferay.Language.get('priority'), value: 'priority'},
];

interface OrderByFieldProps {
	columnName: string;
	columnValue: string;
	label: string;
	onColumnChange: (column: string) => void;
	onTypeChange: (type: OrderByType) => void;
	typeName: string;
	typeValue: OrderByType;
}

function OrderByField({
	columnName,
	columnValue,
	label,
	onColumnChange,
	onTypeChange,
	typeName,
	typeValue,
}: OrderByFieldProps) {
	const ascending = typeValue === 'ASC';

	return (
		<div className="col-md-6">
			<div className="h5">{label}</div>

			<div className="d-inline-flex">
				<Picker
					aria-label={label}
					items={ORDER_BY_OPTIONS}
					onSelectionChange={(key) => onColumnChange(key as string)}
					selectedKey={columnValue}
				>
					{(item) => <Option key={item.value}>{item.label}</Option>}
				</Picker>
			</div>

			<input name={columnName} type="hidden" value={columnValue} />

			<div className="d-inline-flex">
				<ClayButton
					aria-label={
						ascending
							? Liferay.Language.get('ascending')
							: Liferay.Language.get('descending')
					}
					borderless
					displayType="secondary"
					monospaced
					onClick={() => onTypeChange(ascending ? 'DESC' : 'ASC')}
					title={
						ascending
							? Liferay.Language.get('ascending')
							: Liferay.Language.get('descending')
					}
				>
					<ClayIcon
						symbol={ascending ? 'order-list-up' : 'order-list-down'}
					/>
				</ClayButton>
			</div>

			<input name={typeName} type="hidden" value={typeValue} />
		</div>
	);
}

interface CollectionOrderingProps {
	initialOrderByColumn1?: string;
	initialOrderByColumn2?: string;
	initialOrderByType1?: OrderByType;
	initialOrderByType2?: OrderByType;
	namespace: string;
}

export default function CollectionOrdering({
	initialOrderByColumn1 = 'title',
	initialOrderByColumn2 = 'title',
	initialOrderByType1 = 'ASC',
	initialOrderByType2 = 'ASC',
	namespace,
}: CollectionOrderingProps) {
	const [orderByColumn1, setOrderByColumn1] = useState(initialOrderByColumn1);
	const [orderByColumn2, setOrderByColumn2] = useState(initialOrderByColumn2);
	const [orderByType1, setOrderByType1] =
		useState<OrderByType>(initialOrderByType1);
	const [orderByType2, setOrderByType2] =
		useState<OrderByType>(initialOrderByType2);

	return (
		<div className="row">
			<OrderByField
				columnName={`${namespace}TypeSettingsProperties--orderByColumn1--`}
				columnValue={orderByColumn1}
				label={Liferay.Language.get('order-by')}
				onColumnChange={setOrderByColumn1}
				onTypeChange={setOrderByType1}
				typeName={`${namespace}TypeSettingsProperties--orderByType1--`}
				typeValue={orderByType1}
			/>

			<OrderByField
				columnName={`${namespace}TypeSettingsProperties--orderByColumn2--`}
				columnValue={orderByColumn2}
				label={Liferay.Language.get('and-then-by')}
				onColumnChange={setOrderByColumn2}
				onTypeChange={setOrderByType2}
				typeName={`${namespace}TypeSettingsProperties--orderByType2--`}
				typeValue={orderByType2}
			/>
		</div>
	);
}
