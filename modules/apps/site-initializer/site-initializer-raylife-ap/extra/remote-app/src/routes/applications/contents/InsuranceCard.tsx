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
import {useCallback, useEffect, useState} from 'react';

import {getCategoriesByVocabulary} from '../../../common/services/taxonomy-category';
import {getVocabulariesByExternalReferenceCode} from '../../../common/services/taxonomy-vocabulary';

type InsuranceProps = {
	getSelectedCard: (index: any[]) => void;
	loadedCategories: any[];
};

type CategoriesCardsTypes = {
	active: boolean;
	channelName: string;
	id: number;
	name: string;
};

type CategoryType = {[keys: string]: string};

const InsuranceCard: React.FC<InsuranceProps> = ({
	getSelectedCard,
	loadedCategories,
}) => {
	const [insuranceCards, setInsuranceCards] = useState<
		CategoriesCardsTypes[]
	>([]);

	const insuranceLineExternalReferenceCode = 'RAYAPVOC0001';

	const getCategories = async () => {
		const vocabulary = await getVocabulariesByExternalReferenceCode(
			`${insuranceLineExternalReferenceCode}`
		);

		const vocabularyId = vocabulary?.data?.id;

		const categories = await getCategoriesByVocabulary(vocabularyId);

		const sortCategoriesByExternalReferenceCode = categories?.data?.items.sort(
			(personal: any, business: any) =>
				personal.externalReferenceCode > business.externalReferenceCode
					? 1
					: business.externalReferenceCode >
					  personal.externalReferenceCode
					? -1
					: 0
		);

		const payload: CategoriesCardsTypes[] = sortCategoriesByExternalReferenceCode?.map(
			(category: CategoryType, index: number) => {
				return {
					active: index === 0,
					channelName: `Raylife AP`,
					id: category.id,
					name: category.name,
				};
			}
		);

		return payload;
	};

	const loadCards = useCallback(() => {
		if (loadedCategories.length) {
			return setInsuranceCards(loadedCategories);
		}

		getCategories().then((response) => {
			const listCategories = [...insuranceCards, ...response];

			setInsuranceCards(listCategories);
			getSelectedCard(listCategories);
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [insuranceCards]);

	const handleClickCard = (cardIndex: number) => {
		const selectCard = insuranceCards.map((insuranceCard, index) => {
			return {
				...insuranceCard,
				active: cardIndex === index,
			};
		});

		setInsuranceCards(selectCard);
		getSelectedCard(selectCard);
	};

	useEffect(() => {
		loadCards();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<ClayModal.Body className="align-items-center d-flex flex-column mb-5">
				<div className="align-items-center d-flex justify-content-center mb-5 mt-7">
					What type of insurance does this customer need?
				</div>

				{!!insuranceCards.length && (
					<div className="align-items-center d-flex flex-wrap justify-content-center">
						{insuranceCards.map((insuranceCard, index) => {
							return (
								<div className="px-2 row" key={index}>
									<div className="col">
										<ClayCard
											className={classNames(
												'application-card card-hover border border-secondary',
												{
													active:
														insuranceCard.active,
												}
											)}
											onClick={() =>
												handleClickCard(index)
											}
										>
											<ClayCard.Body className="d-flex h-100 justify-content-center">
												<div className="border-dark text-break text-center">
													<section className="align-items-center autofit-section d-flex h-100">
														<h6 className="my-0">
															{insuranceCard.name}
														</h6>
													</section>
												</div>
											</ClayCard.Body>
										</ClayCard>
									</div>
								</div>
							);
						})}
					</div>
				)}
			</ClayModal.Body>
		</>
	);
};

export default InsuranceCard;
