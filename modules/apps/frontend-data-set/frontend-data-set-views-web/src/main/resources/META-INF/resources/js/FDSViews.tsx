/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLink from '@clayui/link';
import ClayModal from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {IInternalRenderer, fetch, navigate, openModal} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import {API_URL, FDS_DEFAULT_PROPS, OBJECT_RELATIONSHIP} from './Constants';
import {FDSEntryType} from './FDSEntries';
import RequiredMark from './components/RequiredMark';
import openDefaultFailureToast from './utils/openDefaultFailureToast';
import openDefaultSuccessToast from './utils/openDefaultSuccessToast';

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
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
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

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const fdsView = await response.json();

		if (fdsView?.id) {
			closeModal();

			openDefaultSuccessToast();

			loadData();
		}
		else {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();
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
				{Liferay.Language.get('new-data-set-view')}
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
							disabled={saveButtonDisabled}
							onClick={() => {
								setSaveButtonDisabled(true);

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
	const getEditURL = (itemData: FDSViewType) => {
		const url = new URL(fdsViewURL);

		url.searchParams.set(`${namespace}fdsEntryId`, fdsEntryId);
		url.searchParams.set(`${namespace}fdsEntryLabel`, fdsEntryLabel);
		url.searchParams.set(`${namespace}fdsViewId`, itemData.id);
		url.searchParams.set(`${namespace}fdsViewLabel`, itemData.label);

		return url;
	};

	const onEditClick = ({itemData}: {itemData: FDSViewType}) => {
		navigate(getEditURL(itemData));
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
				'deleting-a-data-set-view-is-an-action-that-cannot-be-reversed'
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
								openDefaultSuccessToast();

								loadData();
							})
							.catch(openDefaultFailureToast);
					},
				},
			],
			status: 'danger',
			title: Liferay.Language.get('delete-data-set-view'),
		});
	};

	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-data-set-view'),
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

	const TitleRenderer = ({itemData}: {itemData: FDSViewType}) => {
		return (
			<div className="table-list-title">
				<ClayLink href={getEditURL(itemData).toString()}>
					{itemData.label}
				</ClayLink>
			</div>
		);
	};

	const views = [
		{
			contentRenderer: 'list',
			name: 'list',
			schema: {
				description: 'description',
				symbol: 'symbol',
				title: 'label',
				titleRenderer: {
					component: TitleRenderer,
					label: Liferay.Language.get('title'),
					name: 'title',
					type: 'internal',
				} as IInternalRenderer,
			},
		},
	];

	return (
		<FrontendDataSet
			{...FDS_DEFAULT_PROPS}
			apiURL={`${API_URL.FDS_VIEWS}/?filter=(${OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW_ID} eq '${fdsEntryId}')`}
			creationMenu={creationMenu}
			emptyState={{
				description: Liferay.Language.get(
					'start-creating-one-to-show-your-data'
				),
				image: '/states/empty_state.gif',
				title: Liferay.Language.get('no-views-created'),
			}}
			header={{
				title: Liferay.Language.get('views'),
			}}
			id={`${namespace}FDSViews`}
			itemsActions={[
				{
					icon: 'pencil',
					label: Liferay.Language.get('edit'),
					onClick: onEditClick,
				},
				{
					separator: true,
					type: 'group',
				},
				{
					icon: 'trash',
					label: Liferay.Language.get('delete'),
					onClick: onDeleteClick,
				},
			]}
			sorts={[{direction: 'desc', key: 'dateModified'}]}
			views={views}
		/>
	);
};

export {FDSViewType};
export default FDSViews;
