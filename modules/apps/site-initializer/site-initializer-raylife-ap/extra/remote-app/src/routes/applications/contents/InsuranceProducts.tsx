/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import {
	getChannelId,
	getProductsByCategory,
} from '../../../common/services/commerce-catalog';
import {Liferay} from '../../../common/services/liferay/liferay';
import LoadingIndicator from '../components/LoadingIndicator';

type InsuranceProductsProps = {
	selectedCard: any[];
};

type NewApplicationStorageTypes = {
	productName: string;
};

enum CARD {
	PERSONAL = 'Personal',
	BUSINESS = 'Business',
}

enum PRODUCTS {
	AUTO = 'Auto',
	BUSINESS_OWNERS_POLICY = 'Business Owners Policy',
}

const InsuranceProducts: React.FC<InsuranceProductsProps> = ({
	selectedCard,
}) => {
	const [products, setProducts] = useState([]);

	const cardNameSelected =
		selectedCard[0].name === CARD.PERSONAL
			? PRODUCTS.AUTO
			: PRODUCTS.BUSINESS_OWNERS_POLICY;

	const [cardSelected, setCardSelected] = useState<string>(cardNameSelected);

	const [isLoading, setIsLoading] = useState(false);

	const getProducts = async (channelId: number, categoryId: number) => {
		const products = await getProductsByCategory(channelId, categoryId);

		setProducts(
			products?.data?.items.sort(
				(productNameA: any, productNameCompare: any) =>
					productNameA.name > productNameCompare.name ? 1 : -1
			)
		);
		setIsLoading(false);
	};

	useEffect(() => {
		const newApplicationStorage: NewApplicationStorageTypes = {
			productName: cardSelected,
		};

		Liferay.Util.LocalStorage.setItem(
			'raylife-ap-storage',
			JSON.stringify(newApplicationStorage),
			Liferay.Util.LocalStorage.TYPES.NECESSARY
		);

		getChannelId(selectedCard[0].channelName).then((response) => {
			getProducts(response.data.items[0].id, selectedCard[0].id);
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const onClickCard = (name: string) => {
		setCardSelected(name);

		const newApplicationStorage: NewApplicationStorageTypes = {
			productName: name,
		};

		Liferay.Util.LocalStorage.setItem(
			'raylife-ap-storage',
			JSON.stringify(newApplicationStorage),
			Liferay.Util.LocalStorage.TYPES.NECESSARY
		);
	};

	return (
		<>
			<ClayModal.Body className="align-items-center d-flex flex-column mb-5">
				<>
					<div className="align-items-center d-flex justify-content-center mb-5 mt-7">
						What insurance product does this customer need?
					</div>
					<div className="align-items-center d-flex flex-wrap justify-content-center">
						{isLoading ? (
							<LoadingIndicator />
						) : (
							products.map((cardPersonal: any, index: number) => {
								return (
									<div className="px-2 row" key={index}>
										<div className="col">
											<ClayCard
												className={classNames(
													'application-card border border-secondary',
													{
														'active':
															cardSelected ===
																cardPersonal.name ||
															(index === 0 &&
																!cardSelected),
														'application-card-opacity':
															cardPersonal.name !==
															PRODUCTS.AUTO,
													}
												)}
												onClick={() =>
													cardPersonal.name ===
														PRODUCTS.AUTO &&
													onClickCard(
														cardPersonal.name
													)
												}
											>
												<ClayCard.Body className="d-flex h-100 justify-content-center">
													<div className="border-dark text-break text-center">
														<section className="align-items-center autofit-section d-flex h-100">
															<h6 className="my-0">
																{
																	cardPersonal.name
																}
															</h6>
														</section>
													</div>
												</ClayCard.Body>
											</ClayCard>
										</div>
									</div>
								);
							})
						)}
					</div>
				</>
			</ClayModal.Body>
		</>
	);
};

export default InsuranceProducts;
