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
import ClayList from '@clayui/list';
import {useModal} from '@clayui/modal';
import React, {useEffect, useMemo, useState} from 'react';

import {fetchSelectedFields} from '../../utils/api';
import Loading from '../Loading';
import AccountsAttributesModal from './AccountsAttributesModal';
import OrderAttributsModal from './OrderAttributsModal';
import PeopleAttributesModal from './PeopleAttributesModal';
import ProductsAttributesModal from './ProductsAttributesModal';

enum EFields {
	Account = 'account',
	Order = 'order',
	People = 'people',
	Product = 'product',
}

const Attributes: React.FC = () => {
	const {
		observer: observerAccountsAttributes,
		onOpenChange: onOpenChangeAccountsAttributes,
		open: openAccountsAttributes,
	} = useModal();
	const {
		observer: observerPeopleAttributes,
		onOpenChange: onOpenChangePeopleAttributes,
		open: openPeopleAttributes,
	} = useModal();
	const {
		observer: observerProductsAttributes,
		onOpenChange: onOpenChangeProductsAttributes,
		open: openProductsAttributes,
	} = useModal();
	const {
		observer: observerOrderAttributes,
		onOpenChange: onOpenChangeOrderAttributes,
		open: openOrderAttributes,
	} = useModal();

	const [selectedFields, setSelectedFields] = useState<
		{[key in EFields]: number | React.ReactNode}
	>({
		[EFields.Account]: <Loading inline />,
		[EFields.Order]: <Loading inline />,
		[EFields.People]: <Loading inline />,
		[EFields.Product]: <Loading inline />,
	});

	const syncData = async () => {
		const selectedFields = await fetchSelectedFields();

		setSelectedFields(selectedFields);
	};

	const handleCloseModal = (
		key: EFields,
		closeFn: (value: boolean) => void
	) => {
		closeFn(false);

		setSelectedFields({
			...selectedFields,
			[key]: <Loading inline />,
		});

		setTimeout(syncData, 1000);

		Liferay.Util.openToast({
			message: Liferay.Language.get('attributes-have-been-saved'),
		});
	};

	useEffect(() => {
		syncData();
	}, []);

	const attributesList = useMemo(() => {
		const {account, order, people, product} = selectedFields;

		return [
			{
				count: people,
				icon: 'users',
				onOpenModal: () => onOpenChangePeopleAttributes(true),
				title: Liferay.Language.get('people'),
			},
			{
				count: account,
				icon: 'briefcase',
				onOpenModal: () => onOpenChangeAccountsAttributes(true),
				title: Liferay.Language.get('account'),
			},
			{
				count: product,
				icon: 'categories',
				onOpenModal: () => onOpenChangeProductsAttributes(true),
				title: Liferay.Language.get('products'),
			},
			{
				count: order,
				icon: 'shopping-cart',
				onOpenModal: () => onOpenChangeOrderAttributes(true),
				title: Liferay.Language.get('order'),
			},
		];
	}, [
		onOpenChangeAccountsAttributes,
		onOpenChangeOrderAttributes,
		onOpenChangePeopleAttributes,
		onOpenChangeProductsAttributes,
		selectedFields,
	]);

	return (
		<>
			<ClayList className="mb-0">
				{attributesList.map(({count, icon, onOpenModal, title}) => (
					<ClayList.Item
						className="align-items-center"
						flex
						key={title}
					>
						<ClayList.ItemField className="mr-2">
							<ClayIcon symbol={icon} />
						</ClayList.ItemField>

						<ClayList.ItemField expand>
							<ClayList.ItemTitle>{title}</ClayList.ItemTitle>

							<ClayList.ItemText className="text-secondary">
								<span className="mr-1">{count}</span>

								<span>{Liferay.Language.get('selected')}</span>
							</ClayList.ItemText>
						</ClayList.ItemField>

						<ClayList.ItemField>
							<ClayButton
								displayType="secondary"
								onClick={onOpenModal}
							>
								{Liferay.Language.get('select-attributes')}
							</ClayButton>
						</ClayList.ItemField>
					</ClayList.Item>
				))}
			</ClayList>

			{openAccountsAttributes && (
				<AccountsAttributesModal
					observer={observerAccountsAttributes}
					onCancel={() => onOpenChangeAccountsAttributes(false)}
					onSubmit={() =>
						handleCloseModal(
							EFields.Account,
							onOpenChangeAccountsAttributes
						)
					}
				/>
			)}

			{openOrderAttributes && (
				<OrderAttributsModal
					observer={observerOrderAttributes}
					onCancel={() => onOpenChangeOrderAttributes(false)}
					onSubmit={() =>
						handleCloseModal(
							EFields.Order,
							onOpenChangeOrderAttributes
						)
					}
				/>
			)}

			{openPeopleAttributes && (
				<PeopleAttributesModal
					observer={observerPeopleAttributes}
					onCancel={() => onOpenChangePeopleAttributes(false)}
					onSubmit={() =>
						handleCloseModal(
							EFields.People,
							onOpenChangePeopleAttributes
						)
					}
				/>
			)}

			{openProductsAttributes && (
				<ProductsAttributesModal
					observer={observerProductsAttributes}
					onCancel={() => onOpenChangeProductsAttributes(false)}
					onSubmit={() =>
						handleCloseModal(
							EFields.Product,
							onOpenChangeProductsAttributes
						)
					}
				/>
			)}
		</>
	);
};

export default Attributes;
