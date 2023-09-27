/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';

import Header from '../../../common/components/header';
import Table, {TableRowContentType} from '../../../common/components/table';
import {
	deleteApplicationByExternalReferenceCode,
	getApplications,
} from '../../../common/services';
import {Liferay} from '../../../common/services/liferay/liferay';
import formatDate from '../../../common/utils/dateFormatter';
import {redirectTo} from '../../../common/utils/liferay';
import LoadingIndicator from '../../applications/components/LoadingIndicator';
import Modal from '../../applications/components/Modal';
import InsuranceCard from '../../applications/contents/InsuranceCard';
import InsuranceProducts from '../../applications/contents/InsuranceProducts';

const HEADERS = [
	{
		clickable: true,
		clickableSort: false,
		greyColor: true,
		key: 'applicationCreateDate',
		requestLabel: 'applicationCreateDate',
		type: 'link',
		value: 'Date',
	},
	{
		clickableSort: false,
		key: 'productName',
		requestLabel: 'productName',
		value: 'Product',
	},
	{
		bold: true,
		clickable: true,
		clickableSort: false,
		key: 'externalReferenceCode',
		requestLabel: 'externalReferenceCode',
		type: 'link',
		value: 'Application Number',
	},
	{
		clickableSort: false,
		greyColor: true,
		key: 'name',
		requestLabel: 'name',
		type: 'hasBubble',
		value: 'Status',
	},
];

const STATUS_EDIT_DISABLED = ['Bound', 'Quoted'];

const STATUS_DELETE_DISABLED = [
	'Bound',
	'Quoted',
	'Rejected',
	'Reviewed',
	'Underwriting',
];

const PARAMETERS = {
	sort: 'applicationCreateDate:desc',
};

type RecentApplication = {
	applicationCreateDate: Date;
	applicationStatus: {name: string};
	externalReferenceCode: string;
	productName: string;
};

type TableContent = {[keys: string]: string | any};

enum ModalType {
	insurance = 1,
	insuranceProducts = 2,
}

type TableItemType = {
	centered?: boolean;
	clickable?: boolean;
	clickableSort?: boolean;
	greyColor?: boolean;
	hasSort?: boolean;
	icon?: boolean;
	key: string;
	redColor?: boolean;
	requestLabel: string;
	type?: string;
	value: string;
};

type StateSortType = {
	[keys: string]: boolean;
};

const RecentApplications = () => {
	const [sortState, setSortState] = useState<StateSortType>({
		commission: false,
		externalReferenceCode: false,
		monthlyPremium: false,
		policyOwnerName: false,
		policyPeriod: true,
		policyStatus: false,
		productName: false,
		renewalDue: true,
	});

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

	const setDisabledEditAction = (identifier: string) => {
		const application = applications.find(
			(application) => application.key === identifier
		) as TableContent;

		return STATUS_EDIT_DISABLED.includes(application.name);
	};

	const setDisabledDeleteAction = (externalReferenceCode: string) => {
		const application = applications.find(
			(application) => application.key === externalReferenceCode
		) as TableContent;

		return STATUS_DELETE_DISABLED.includes(application.name);
	};

	useEffect(() => {
		Liferay.Util.LocalStorage.removeItem('raylife-ap-storage');
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
						isClickable: true,
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
			handleRedirectToGmail(rowContent[item.key] as string);
		}

		if (
			((item.clickable && rowContent['name'] === 'Incomplete') ||
				rowContent['name'] === 'Bound') &&
			(item.key === 'externalReferenceCode' ||
				item.key === 'applicationCreateDate')
		) {
			handleRedirectToDetailsPages(
				rowContent['externalReferenceCode'] as string,
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
						disabled: setDisabledEditAction,
						value: 'Edit',
					},
					{
						action: handleDeleteApplication,
						disabled: setDisabledDeleteAction,
						value: 'Delete',
					},
				]}
				data={applications.slice(0, 6)}
				headers={HEADERS}
				onClickRules={onClickRules}
				setSort={setSortState}
				sort={sortState}
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
