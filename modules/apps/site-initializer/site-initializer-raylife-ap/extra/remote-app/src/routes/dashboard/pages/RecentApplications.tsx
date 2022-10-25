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
import {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';

import Header from '../../../common/components/header';
import Table from '../../../common/components/table';
import {
	deleteApplicationByExternalReferenceCode,
	getApplications,
} from '../../../common/services';
import formatDate from '../../../common/utils/dateFormatter';
import {redirectTo} from '../../../common/utils/liferay';
import LoadingIndicator from '../../applications/components/LoadingIndicator';
import Modal from '../../applications/components/Modal';
import InsuranceCard from '../../applications/contents/InsuranceCard';
import InsuranceProducts from '../../applications/contents/InsuranceProducts';

const HEADERS = [
	{
		clickable: true,
		greyColor: true,
		key: 'applicationCreateDate',
		type: 'link',
		value: 'Date',
	},
	{
		key: 'productName',
		value: 'Product',
	},
	{
		bold: true,
		clickable: true,
		key: 'externalReferenceCode',
		type: 'link',
		value: 'Application Number',
	},
	{
		greyColor: true,
		key: 'name',
		type: 'status',
		value: 'Status',
	},
];

const STATUS_DISABLED = ['Bound', 'Quoted'];

const PARAMETERS = {
	sort: 'applicationCreateDate:desc',
};

type RecentApplication = {
	applicationCreateDate: Date;
	applicationStatus: {name: string};
	externalReferenceCode: string;
	productName: string;
};

type TableContent = {[keys: string]: string};

enum ModalType {
	insurance = 1,
	insuranceProducts = 2,
}

type TableItemType = {
	bold: boolean;
	clickable: boolean;
	key: string;
	type: string;
	value: string;
};

type TableRowContentType = {[keys: string]: string};

const RecentApplications = () => {
	const [applications, setApplications] = useState<TableContent[]>([]);
	const [visible, setVisible] = useState(false);
	const [contentModal, setContentModal] = useState(ModalType.insurance);
	const [selectedCard, setSelectedCard] = useState<any[]>([]);
	const [isLoading, setIsLoading] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const handleDeleteApplication = (externalReferenceCode: string) => {
		deleteApplicationByExternalReferenceCode(externalReferenceCode);

		const filteredApplications = applications.filter(
			(application) => !(application.key === externalReferenceCode)
		);

		setApplications(filteredApplications);
	};

	const handleModal = () => {
		setContentModal(ModalType.insurance);
		setIsLoading(true);
		setVisible(!visible);
		setTimeout(() => setIsLoading(false), 1000);
	};

	const handleEditApplication = (externalReferenceCode: string) => {
		alert(`Edit ${externalReferenceCode} Action`);
	};

	const setDisabledAction = (identifier: string) => {
		const application = applications.find(
			(application) => application.key === identifier
		) as TableContent;

		return STATUS_DISABLED.includes(application.name);
	};

	useEffect(() => {
		localStorage.removeItem('raylife-ap-storage');
		getApplications(PARAMETERS).then((results) => {
			const applicationsList: TableContent[] = [];
			results?.data?.items.forEach(
				({
					applicationCreateDate,
					applicationStatus: {name},
					externalReferenceCode,
					productName,
				}: RecentApplication) =>
					applicationsList.push({
						applicationCreateDate: formatDate(
							new Date(applicationCreateDate)
						),
						externalReferenceCode,
						key: externalReferenceCode,
						name,
						productName,
					})
			);
			setApplications(applicationsList);
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

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

	const handleRedirectToGmail = (email: string) => {
		window.location.href = `mailto:${email}`;
	};

	const handleRedirectToDetailsPages = (
		externalReferenceCode: string,
		entity: string
	) => {
		redirectTo(`${entity}?externalReferenceCode=${externalReferenceCode}`);
	};

	const onClickRules = (
		item: TableItemType,
		rowContent: TableRowContentType
	) => {
		if (item.clickable && item.key === 'email') {
			handleRedirectToGmail(rowContent[item.key]);
		}

		if (
			((item.clickable && rowContent['name'] === 'Incomplete') ||
				rowContent['name'] === 'Bound') &&
			(item.key === 'externalReferenceCode' ||
				item.key === 'applicationCreateDate')
		) {
			handleRedirectToDetailsPages(
				rowContent['externalReferenceCode'],
				'app-details'
			);
		}
	};

	return (
		<div className="px-3 ray-dashboard-recent-applications">
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

			<Header className="mb-5 pt-3" title="Recent Applications">
				<button
					className="btn btn-outline-primary text-paragraph text-uppercase"
					onClick={() => handleModal()}
				>
					<ClayIcon className="mr-md-2" symbol="plus" />

					<span className="d-md-inline d-none">Application</span>
				</button>
			</Header>

			<Table
				actions={[
					{
						action: handleEditApplication,
						disabled: setDisabledAction,
						value: 'Edit',
					},
					{
						action: handleDeleteApplication,
						value: 'Delete',
					},
				]}
				data={applications.slice(0, 6)}
				headers={HEADERS}
				onClickRules={onClickRules}
			/>

			<div className="align-items-center bottom-container d-flex justify-content-end mt-4 pb-3 px-3">
				<button
					className="btn btn-inverted btn-solid btn-style-primary text-paragraph text-uppercase"
					onClick={() => redirectTo('Applications')}
				>
					All Applications
					<ClayIcon className="ml-2" symbol="order-arrow-right" />
				</button>
			</div>
		</div>
	);
};

export default RecentApplications;
