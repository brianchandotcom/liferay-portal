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
import ClayCard from '@clayui/card';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useState} from 'react';

const cardsBusiness = ['Auto', 'Property', 'Health', "Workers' Compensation"];

const ProductCardBusiness = () => {
	const [cardBusinessSelected, setCardBusinessSelected] = useState<string>(
		"Workers' Compensation"
	);
	const onClickCard = (index: number) => {
		setCardBusinessSelected(cardsBusiness[index]);
	};

	const onCancel = () => {};

	const onPrevious = () => {};

	const onNextForm = () => {};

	const [buttons] = useState([
		{
			atributesClassName: 'border-white',
			displayType: 'secondary',
			onAction: () => onCancel(),
			title: 'Cancel',
		},
		{
			atributesClassName: 'border-black',
			displayType: 'secondary',
			onAction: () => onPrevious(),
			title: 'Previous',
		},
		{
			onAction: () => onNextForm(),
			title: 'Next',
		},
	]);

	return (
		<>
			<ClayModal.Body className="align-items-center d-flex flex-column mb-5">
				<div className="align-items-center d-flex justify-content-center mb-5 mt-7">
					What insurance product does this customer need?
				</div>

				<div className="align-items-center d-flex flex-wrap justify-content-center">
					{cardsBusiness.map((cardBusiness, index) => {
						return (
							<div className="px-2 row" key={index}>
								<div className="col">
									<ClayCard
										className={classNames(
											'application-card card-hover border border-secondary',
											{
												active:
													cardBusinessSelected ===
													cardBusiness,
											}
										)}
										onClick={() => onClickCard}
									>
										<ClayCard.Body>
											<div className="autofit-col autofit-col-expand border-dark">
												<section className="autofit-section">
													<h6 className="align-items-center d-flex justify-content-center mt-4">
														{cardBusiness}
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
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						{buttons.map((button: any, index: any) => {
							return (
								<div key={index}>
									<ClayButton
										className={button?.atributesClassName}
										displayType={button?.displayType}
										onClick={() => button.onAction()}
									>
										{button?.title}
									</ClayButton>
								</div>
							);
						})}
					</ClayButton.Group>
				}
			/>
		</>
	);
};

export default ProductCardBusiness;
