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
import {useState} from 'react';

const cardsPersona = ['Auto', 'Home', 'Property', 'Life', 'Health'];

const ProductCardPersona = () => {
	const [cardPersonaSelected, setCardPersonaSelected] = useState<string>(
		'Auto'
	);

	const onClickCard = (index: number) => {
		setCardPersonaSelected(cardsPersona[index]);
	};

	return (
		<>
			<ClayModal.Body className="align-items-center d-flex flex-column mb-5">
				<div className="align-items-center d-flex justify-content-center mb-5 mt-7">
					What insurance product does this customer need?
				</div>

				<div className="align-items-center d-flex flex-wrap justify-content-center">
					{cardsPersona.map((cardPersona, index) => {
						return (
							<div className="px-2 row" key={index}>
								<div className="col">
									<ClayCard
										className={classNames(
											'application-card card-hover border border-secondary',
											{
												active:
													cardPersonaSelected ===
													cardPersona,
											}
										)}
										onClick={() => onClickCard(index)}
									>
										<ClayCard.Body>
											<div className="autofit-col autofit-col-expand border-dark">
												<section className="autofit-section">
													<h6 className="align-items-center d-flex justify-content-center mt-4">
														{cardPersona}
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
			</ClayModal.Body>
		</>
	);
};

export default ProductCardPersona;
