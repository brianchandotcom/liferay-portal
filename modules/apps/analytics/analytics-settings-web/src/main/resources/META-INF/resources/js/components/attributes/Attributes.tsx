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
import React, {useMemo} from 'react';

import AccountsAttributesModal from './AccountsAttributesModal';
import OrderAttributsModal from './OrderAttributsModal';
import PeopleAttributesModal from './PeopleAttributesModal';
import ProductsAttributesModal from './ProductsAttributesModal';

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

	const attributesList = useMemo(
		() => [

			// TODO: Remove mocked data on "count" property

			{
				count: 15,
				icon: 'users',
				onOpenModal: () => onOpenChangePeopleAttributes(true),
				title: Liferay.Language.get('people'),
			},
			{
				count: 3,
				icon: 'briefcase',
				onOpenModal: () => onOpenChangeAccountsAttributes(true),
				title: Liferay.Language.get('account'),
			},
			{
				count: 7,
				icon: 'categories',
				onOpenModal: () => onOpenChangeProductsAttributes(true),
				title: Liferay.Language.get('products'),
			},
			{
				count: 13,
				icon: 'shopping-cart',
				onOpenModal: () => onOpenChangeOrderAttributes(true),
				title: Liferay.Language.get('order'),
			},
		],
		[
			onOpenChangeAccountsAttributes,
			onOpenChangeOrderAttributes,
			onOpenChangePeopleAttributes,
			onOpenChangeProductsAttributes,
		]
	);

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

							<ClayList.ItemText>
								{`${count} ${Liferay.Language.get('selected')}`}
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
					onCloseModal={() => onOpenChangeAccountsAttributes(false)}
				/>
			)}

			{openPeopleAttributes && (
				<PeopleAttributesModal
					observer={observerPeopleAttributes}
					onCloseModal={() => onOpenChangePeopleAttributes(false)}
				/>
			)}

			{openProductsAttributes && (
				<ProductsAttributesModal
					observer={observerProductsAttributes}
					onCloseModal={() => onOpenChangeProductsAttributes(false)}
				/>
			)}

			{openOrderAttributes && (
				<OrderAttributsModal
					observer={observerOrderAttributes}
					onCloseModal={() => onOpenChangeOrderAttributes(false)}
				/>
			)}
		</>
	);
};

export default Attributes;
