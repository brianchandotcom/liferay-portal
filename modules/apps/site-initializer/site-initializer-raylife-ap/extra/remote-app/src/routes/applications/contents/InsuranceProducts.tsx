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

import ClayCard from '@clayui/card';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import {
	getChannelId,
	getProductsByCategory,
} from '../../../common/services/commerce-catalog';
import LoadingIndicator from '../components/LoadingIndicator';

type InsuranceProductsProps = {
	selectedCard: any[];
};

type NewApplicationStorageTypes = {
	productName: string;
};

const InsuranceProducts: React.FC<InsuranceProductsProps> = ({
	selectedCard,
}) => {
	const [products, setProducts] = useState([]);

	const [cardPersonalSelected, setCardPersonalSelected] = useState<string>(
		'Auto'
	);

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
			productName: cardPersonalSelected,
		};

		localStorage.setItem(
			'raylife-ap-storage',
			JSON.stringify(newApplicationStorage)
		);

		getChannelId(selectedCard[0].channelName).then((response) => {
			getProducts(response.data.items[0].id, selectedCard[0].id);
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const onClickCard = (name: string) => {
		setCardPersonalSelected(name);

		const newApplicationStorage: NewApplicationStorageTypes = {
			productName: name,
		};

		localStorage.setItem(
			'raylife-ap-storage',
			JSON.stringify(newApplicationStorage)
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
													'application-card card-hover border border-secondary',
													{
														active:
															cardPersonalSelected ===
															cardPersonal.name,
													}
												)}
												onClick={() =>
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
