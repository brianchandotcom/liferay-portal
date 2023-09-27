/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Liferay.Util.LocalStorage.removeItem('raylife-ap-storage');

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
