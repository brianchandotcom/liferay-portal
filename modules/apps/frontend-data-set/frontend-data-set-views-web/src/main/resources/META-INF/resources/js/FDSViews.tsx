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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP, PAGINATION_PROPS} from './Constants';
import {FDSEntryType} from './FDSEntries';
import RequiredMark from './components/RequiredMark';

const LIST_OF_ITEMS_PER_PAGE = '4, 8, 20, 40, 60';
const DEFAULT_ITEMS_PER_PAGE = 20;

type FDSViewType = {
	[OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW]: FDSEntryType;
	defaultItemsPerPage: number;
	description: string;
	externalReferenceCode: string;
	fdsFiltersOrder: string;
	fdsSortsOrder: string;
	id: string;
	label: string;
	listOfItemsPerPage: string;
};

interface IAddFDSViewModalContentInterface {
	closeModal: Function;
	fdsEntryId: string;
	loadData: Function;
	namespace: string;
}

const AddFDSViewModalContent = ({
	closeModal,
	fdsEntryId,
	loadData,
	namespace,
}: IAddFDSViewModalContentInterface) => {
	const [labelValidationError, setLabelValidationError] = useState(false);

	const fdsViewDescriptionRef = useRef<HTMLInputElement>(null);
	const fdsViewLabelRef = useRef<HTMLInputElement>(null);

	const addFDSView = async () => {
		const body = {
			defaultItemsPerPage: DEFAULT_ITEMS_PER_PAGE,
			description: fdsViewDescriptionRef.current?.value,
			label: fdsViewLabelRef.current?.value,
			listOfItemsPerPage: LIST_OF_ITEMS_PER_PAGE,
			r_fdsEntryFDSViewRelationship_c_fdsEntryId: fdsEntryId,
			symbol: 'catalog',
		};

		const response = await fetch(API_URL.FDS_VIEWS, {
			body: JSON.stringify(body),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});

		const fdsView = await response.json();

		if (fdsView?.id) {
			closeModal();

			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});

			loadData();
		}
		else {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});
		}
	};

	const validate = () => {
		if (!fdsViewLabelRef.current?.value) {
			setLabelValidationError(true);

			return false;
		}

		return true;
	};

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-dataset-view')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group
					className={classNames({
						'has-error': labelValidationError,
					})}
				>
					<label htmlFor={`${namespace}fdsViewLabelInput`}>
						{Liferay.Language.get('name')}

						<RequiredMark />
					</label>

					<ClayInput
						id={`${namespace}fdsViewLabelInput`}
						onBlur={() =>
							setLabelValidationError(
								!fdsViewLabelRef.current?.value
							)
						}
						ref={fdsViewLabelRef}
						type="text"
					/>

					{labelValidationError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get('this-field-is-required')}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={`${namespace}fdsViewDesctiptionInput`}>
						{Liferay.Language.get('description')}
					</label>

					<ClayInput
						id={`${namespace}fdsViewDesctiptionInput`}
						ref={fdsViewDescriptionRef}
						type="text"
					/>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							onClick={() => {
								const success = validate();

								if (success) {
									addFDSView();
								}
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

interface IFDSViewsInterface {
	fdsEntryId: string;
	fdsEntryLabel: string;
	fdsViewURL: string;
	namespace: string;
}

const FDSViews = ({
	fdsEntryId,
	fdsEntryLabel,
	fdsViewURL,
	namespace,
}: IFDSViewsInterface) => {
	const onViewClick = ({itemData}: {itemData: FDSViewType}) => {
		const url = new URL(fdsViewURL);

		url.searchParams.set(`${namespace}fdsEntryId`, fdsEntryId);
		url.searchParams.set(`${namespace}fdsEntryLabel`, fdsEntryLabel);
		url.searchParams.set(`${namespace}fdsViewId`, itemData.id);
		url.searchParams.set(`${namespace}fdsViewLabel`, itemData.label);

		navigate(url);
	};

	const onDeleteClick = ({
		itemData,
		loadData,
	}: {
		itemData: FDSViewType;
		loadData: Function;
	}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'deleting-a-dataset-view-is-an-action-that-cannot-be-reversed'
			),
			buttons: [
				{
					autoFocus: true,
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'danger',
					label: Liferay.Language.get('delete'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();

						fetch(`${API_URL.FDS_VIEWS}/${itemData.id}`, {
							method: 'DELETE',
						})
							.then(() => {
								openToast({
									message: Liferay.Language.get(
										'your-request-completed-successfully'
									),
									type: 'success',
								});

								loadData();
							})
							.catch(() =>
								openToast({
									message: Liferay.Language.get(
										'your-request-failed-to-complete'
									),
									type: 'danger',
								})
							);
					},
				},
			],
			status: 'danger',
			title: Liferay.Language.get('delete-dataset-view'),
		});
	};

	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-dataset-view'),
				onClick: ({loadData}: {loadData: Function}) => {
					openModal({
						contentComponent: ({
							closeModal,
						}: {
							closeModal: Function;
						}) => (
							<AddFDSViewModalContent
								closeModal={closeModal}
								fdsEntryId={fdsEntryId}
								loadData={loadData}
								namespace={namespace}
							/>
						),
					});
				},
			},
		],
	};

	const views = [
		{
			contentRenderer: 'list',
			name: 'list',
			schema: {
				description: 'description',
				symbol: 'symbol',
				title: 'label',
			},
		},
	];

	return (
		<FrontendDataSet
			apiURL={`${API_URL.FDS_VIEWS}/?filter=(${OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW_ID} eq '${fdsEntryId}')`}
			creationMenu={creationMenu}
			id={`${namespace}FDSViews`}
			itemsActions={[
				{
					icon: 'view',
					label: Liferay.Language.get('view'),
					onClick: onViewClick,
				},
				{
					icon: 'trash',
					label: Liferay.Language.get('delete'),
					onClick: onDeleteClick,
				},
			]}
			style="fluid"
			views={views}
			{...PAGINATION_PROPS}
		/>
	);
};

export {FDSViewType};
export default FDSViews;
