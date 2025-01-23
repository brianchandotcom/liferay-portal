/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React, {useContext, useState} from 'react';

import '../css/DataSets.scss';

import ClayButton from '@clayui/button';
import {ClayRadio} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import ClayModal from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {fetch, navigate, openModal} from 'frontend-js-web';

import {
	API_URL,
	DEFAULT_FETCH_HEADERS,
	FDS_DEFAULT_PROPS,
} from './utils/constants';
import openDefaultFailureToast from './utils/openDefaultFailureToast';
import openDefaultSuccessToast from './utils/openDefaultSuccessToast';
import {IDataSet, ISystemDataSet} from './utils/types';

interface IFrontendDataSetContext {
	onSelect: Function;
	selectItems: Function;
	selectable: boolean;
	selectedItemsKey: keyof ISystemDataSet;
	selectedItemsValue: Array<any>;
}

const SystemDataSetModalList = ({
	frontendDataSetContext,
	items,
}: {
	frontendDataSetContext: any;
	items: Array<ISystemDataSet>;
}) => {
	const {
		onSelect,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
	} = useContext(frontendDataSetContext) as IFrontendDataSetContext;

	return (
		<ClayList>
			{items.map((item) => {
				return (
					<ClayList.Item
						className={classNames({
							disabled: item.created,
							selectable,
							selected: selectedItemsValue?.includes(
								item[selectedItemsKey]
							),
						})}
						flex
						key={item.name}
						onClick={() => {
							if (selectable) {
								selectItems(item[selectedItemsKey]);

								onSelect({selectedItems: [item]});
							}
						}}
					>
						<ClayList.ItemField className="justify-content-center selection-control">
							<ClayRadio
								checked={
									selectedItemsValue
										? selectedItemsValue
												.map((element) =>
													String(element)
												)
												.includes(
													String(
														item[selectedItemsKey]
													)
												)
										: false
								}
								onChange={() => {}}
								value=""
							/>
						</ClayList.ItemField>

						<ClayList.ItemField>
							<ClaySticker displayType="dark">
								<ClayIcon symbol={item.symbol} />
							</ClaySticker>
						</ClayList.ItemField>

						<ClayList.ItemField
							className="justify-content-center"
							expand
						>
							<ClayList.ItemTitle>
								{item.title}
							</ClayList.ItemTitle>

							<ClayList.ItemText>
								{item.description}
							</ClayList.ItemText>
						</ClayList.ItemField>

						{item.created && (
							<ClayList.ItemField>
								<ClayLabel
									className="created-label"
									displayType="warning"
								>
									{Liferay.Language.get('created')}
								</ClayLabel>
							</ClayList.ItemField>
						)}
					</ClayList.Item>
				);
			})}
		</ClayList>
	);
};

const SelectSystemDataSetModalContent = ({
	closeModal,
	getSystemDataSetsURL,
	importSystemDataSetURL,
	loadData,
	namespace,
}: {
	closeModal: Function;
	getSystemDataSetsURL: string;
	importSystemDataSetURL: string;
	loadData: Function;
	namespace: string;
}) => {
	const [createButtonDisabled, setCreateButtonDisabled] = useState(true);
	const [selectedSystemDataSet, setSelectedSystemDataSet] =
		useState<ISystemDataSet | null>(null);

	const onCreateButtonClick = async () => {
		if (!selectedSystemDataSet) {
			return;
		}

		setCreateButtonDisabled(true);

		const formData = new FormData();

		formData.append(`${namespace}name`, selectedSystemDataSet.name);

		const response = await fetch(importSystemDataSetURL, {
			body: formData,
			method: 'POST',
		});

		if (response.ok) {
			closeModal();

			openDefaultSuccessToast();

			loadData();
		}
		else {
			openDefaultFailureToast();

			setCreateButtonDisabled(false);
		}
	};

	return (
		<div className="select-system-data-set-modal-content">
			<ClayModal.Header>
				{Liferay.Language.get('create-system-data-set-customization')}
			</ClayModal.Header>

			<ClayModal.Body>
				<FrontendDataSet
					{...FDS_DEFAULT_PROPS}
					apiURL={getSystemDataSetsURL}
					id="SystemDataSets"
					onSelect={({
						selectedItems,
					}: {
						selectedItems: Array<ISystemDataSet>;
					}) => {
						setSelectedSystemDataSet(selectedItems[0]);
					}}
					selectedItemsKey="name"
					selectionType="single"
					views={[
						{
							component: SystemDataSetModalList,
						},
					]}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							className="btn-cancel"
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={
								createButtonDisabled && !selectedSystemDataSet
							}
							onClick={onCreateButtonClick}
						>
							{Liferay.Language.get('create')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
};

const SystemDataSets = ({
	editDataSetURL,
	getSystemDataSetsURL,
	importSystemDataSetURL,
	namespace,
	systemDataSets,
}: {
	editDataSetURL: string;
	getSystemDataSetsURL: string;
	importSystemDataSetURL: string;
	namespace: string;
	systemDataSets: Array<ISystemDataSet>;
}) => {
	const getAPIURL = () => {
		if (!systemDataSets.length) {
			return undefined;
		}

		const systemDataSetNames: string = systemDataSets
			.map((systemDataSet) => `'${systemDataSet.name}'`)
			.join(',');

		return `${API_URL.DATA_SETS}?filter=externalReferenceCode in (${systemDataSetNames})`;
	};

	const getEditURL = (itemData: IDataSet) => {
		const url = new URL(editDataSetURL);

		url.searchParams.set(
			`${namespace}dataSetERC`,
			itemData.externalReferenceCode
		);
		url.searchParams.set(`${namespace}dataSetLabel`, itemData.label);

		return url;
	};

	const onDeleteClick = ({
		itemData,
		loadData,
	}: {
		itemData: IDataSet;
		loadData: Function;
	}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
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

						fetch(itemData.actions.delete.href, {
							headers: DEFAULT_FETCH_HEADERS,
							method: itemData.actions.delete.method,
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
			title: Liferay.Language.get('delete-data-set'),
		});
	};

	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get(
					'create-system-data-set-customization'
				),
				onClick: ({loadData}: {loadData: Function}) => {
					openModal({
						contentComponent: ({
							closeModal,
						}: {
							closeModal: Function;
						}) => (
							<SelectSystemDataSetModalContent
								closeModal={closeModal}
								getSystemDataSetsURL={getSystemDataSetsURL}
								importSystemDataSetURL={importSystemDataSetURL}
								loadData={loadData}
								namespace={namespace}
							/>
						),
						size: 'lg',
					});
				},
			},
		],
	};

	const views = [
		{
			contentRenderer: 'table',
			name: 'table',
			schema: {
				fields: [
					{
						actionId: 'edit',
						contentRenderer: 'actionLink',
						fieldName: 'label',
						label: Liferay.Language.get('name'),
						sortable: true,
					},
					{
						fieldName: 'restApplication',
						label: Liferay.Language.get('rest-application'),
						sortable: true,
					},
					{
						fieldName: 'restSchema',
						label: Liferay.Language.get('rest-schema'),
						sortable: true,
					},
					{
						fieldName: 'restEndpoint',
						label: Liferay.Language.get('rest-endpoint'),
						sortable: true,
					},
					{
						fieldName: 'status',
						label: Liferay.Language.get('status'),
						sortable: true,
					},
					{
						contentRenderer: 'dateTime',
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified-date'),
						sortable: true,
					},
				],
			},
		},
	];

	return (
		<div className="data-sets system-data-sets">
			<FrontendDataSet
				{...FDS_DEFAULT_PROPS}
				apiURL={getAPIURL()}
				creationMenu={creationMenu}
				emptyState={{
					description: Liferay.Language.get(
						'start-creating-one-to-show-your-data'
					),
					image: '/states/empty_state.svg',
					title: Liferay.Language.get('no-system-data-sets-created'),
				}}
				id="CreatedSystemDataSets"
				itemsActions={[
					{
						data: {
							id: 'edit',
							permissionKey: 'update',
						},
						icon: 'pencil',
						label: Liferay.Language.get('edit'),
						onClick: ({itemData}: {itemData: IDataSet}) => {
							navigate(getEditURL(itemData));
						},
					},
					{
						data: {
							permissionKey: 'delete',
						},
						icon: 'trash',
						label: Liferay.Language.get('delete'),
						onClick: onDeleteClick,
					},
				]}
				sorts={[{direction: 'desc', key: 'dateCreated'}]}
				views={views}
			/>
		</div>
	);
};

export default SystemDataSets;
