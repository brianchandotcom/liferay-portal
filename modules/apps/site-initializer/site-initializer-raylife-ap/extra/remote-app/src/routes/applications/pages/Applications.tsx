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
import {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';

import {Liferay} from '../../../common/services/liferay/liferay';
import {redirectTo} from '../../../common/utils/liferay';
import LoadingIndicator from '../components/LoadingIndicator';
import Modal from '../components/Modal';
import InsuranceCard from '../contents/InsuranceCard';
import InsuranceProducts from '../contents/InsuranceProducts';

enum ModalType {
	insurance = 1,
	insuranceProducts = 2,
}
const Applications = () => {
	const [visible, setVisible] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const [contentModal, setContentModal] = useState(ModalType.insurance);
	const [selectedCard, setSelectedCard] = useState<any[]>([]);

	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const handleNextClick = () => {
		setContentModal(ModalType.insuranceProducts);
	};

	const handleRedirectToForms = () => {
		redirectTo('app-edit');
	};

	const handlePreviousClick = () => {
		setContentModal(ModalType.insurance);
	};

	const getSelectedCard = (selectCard: any[]) => {
		setSelectedCard(selectCard);
	};

	const ButtonsInsurance = () => (
		<>
			<ClayButton
				className="border-white"
				displayType="secondary"
				onClick={() => onClose()}
			>
				Cancel
			</ClayButton>
			<ClayButton displayType="primary" onClick={() => handleNextClick()}>
				Next
			</ClayButton>
		</>
	);

	const ButtonsInsuranceProducts = () => (
		<>
			<ClayButton
				className="border-white m-1"
				displayType="secondary"
				onClick={() => onClose()}
			>
				Cancel
			</ClayButton>
			<ClayButton
				className="m-1"
				displayType="secondary"
				onClick={() => handlePreviousClick()}
			>
				Previous
			</ClayButton>
			<ClayButton
				className="m-1"
				displayType="primary"
				onClick={() => handleRedirectToForms()}
			>
				Next
			</ClayButton>
		</>
	);

	useEffect(() => {
		localStorage.removeItem('raylife-ap-storage');
		const handler = () => setVisible(!visible);
		setContentModal(ModalType.insurance);

		setIsLoading(true);
		setTimeout(() => setIsLoading(false), 1000);

		Liferay.on('openModalEvent', handler);

		return () => Liferay.detach('openModalEvent', handler);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [visible]);

	return (
		<>
			<Modal
				Buttons={() =>
					contentModal === ModalType.insurance ? (
						<ButtonsInsurance />
					) : (
						<ButtonsInsuranceProducts />
					)
				}
				modalStyle="modal-clay"
				observer={observer}
				size="full-screen"
				title="New Application"
				visible={visible}
			>
				{isLoading ? (
					<LoadingIndicator />
				) : contentModal === ModalType.insurance ? (
					<InsuranceCard
						getSelectedCard={getSelectedCard}
						loadedCategories={selectedCard}
					/>
				) : (
					<InsuranceProducts
						selectedCard={selectedCard.filter(
							(card) => card.active
						)}
					/>
				)}
			</Modal>
		</>
	);
};

export default Applications;
