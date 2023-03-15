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
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import fuzzy from 'fuzzy';
import React, {useRef, useState} from 'react';

import '../css/FDSEntries.scss';
import {OBJECT_RELATIONSHIP, PAGINATION_PROPS} from './Constants';
import {TFDSView} from './FDSViews';
import RequiredMark from './RequiredMark';

const FUZZY_OPTIONS = {
	post: '</strong>',
	pre: '<strong>',
};

type HeadlessResource = {
	bundleLabel: string;
	entityClassName: string;
	name: string;
	version: string;
};

interface IHeadlessResourceItemProps {
	headlessResource: HeadlessResource;
	query: string;
}

const HeadlessResourceItem = ({
	headlessResource,
	query,
}: IHeadlessResourceItemProps) => {
	const fuzzyNameMatch = fuzzy.match(
		query,
		headlessResource.name,
		FUZZY_OPTIONS
	);

	const fuzzyBundleLabelMatch = fuzzy.match(
		query,
		headlessResource.bundleLabel,
		FUZZY_OPTIONS
	);

	return (
		<ClayLayout.ContentRow className="headless-resource">
			{fuzzyNameMatch ? (
				<span
					dangerouslySetInnerHTML={{
						__html: fuzzyNameMatch.rendered,
					}}
				/>
			) : (
				<span>{headlessResource.name}</span>
			)}

			<span className="context">
				{fuzzyBundleLabelMatch ? (
					<span
						dangerouslySetInnerHTML={{
							__html: fuzzyBundleLabelMatch.rendered,
						}}
					/>
				) : (
					<span>{headlessResource.bundleLabel}</span>
				)}

				{` ${headlessResource.version}`}
			</span>
		</ClayLayout.ContentRow>
	);
};

interface IDropdownMenuProps {
	headlessResources: Array<HeadlessResource>;
	setHeadlessResourceValidationError: Function;
	setSelectedHeadlessResource: Function;
}

const DropdownMenu = ({
	headlessResources: initialHeadlessResources,
	setHeadlessResourceValidationError,
	setSelectedHeadlessResource,
}: IDropdownMenuProps) => {
	const [headlessResources, setHeadlessResources] = useState<
		Array<HeadlessResource>
	>(initialHeadlessResources || []);
	const [query, setQuery] = useState('');

	const onSearch = (query: string) => {
		setQuery(query);

		const regexp = new RegExp(query, 'i');

		setHeadlessResources(
			query
				? initialHeadlessResources.filter(
						({bundleLabel, name}: HeadlessResource) => {
							return (
								bundleLabel.match(regexp) || name.match(regexp)
							);
						}
				  ) || []
				: initialHeadlessResources
		);
	};

	return (
		<>
			<ClayDropDown.Search
				aria-label={Liferay.Language.get('search')}
				onChange={onSearch}
				value={query}
			/>

			<ClayDropDown.ItemList items={headlessResources} role="listbox">
				{(item: HeadlessResource) => (
					<ClayDropDown.Item
						key={item.entityClassName}
						onClick={() => {
							setSelectedHeadlessResource(item);

							setHeadlessResourceValidationError(false);
						}}
						roleItem="option"
					>
						<HeadlessResourceItem
							headlessResource={item}
							query={query}
						/>
					</ClayDropDown.Item>
				)}
			</ClayDropDown.ItemList>
		</>
	);
};

interface IFDSEntriesProps {
	fdsEntriesAPIURL: string;
	fdsViewsURL: string;
	headlessResources: Array<HeadlessResource>;
	namespace: string;
}

const FDSEntries = ({
	fdsEntriesAPIURL,
	fdsViewsURL,
	headlessResources,
	namespace,
}: IFDSEntriesProps) => {
	const headlessResourcesMapRef = useRef<Map<string, HeadlessResource>>(
		new Map(
			headlessResources.map((headlessResource) => [
				headlessResource.entityClassName,
				headlessResource,
			])
		)
	);

	type TFDSEntry = {
		[OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW]: Array<TFDSView>;
		actions: {
			delete: {
				href: string;
				method: string;
			};
		};
		entityClassName: string;
		id: string;
		label: string;
	};

	const ProviderRenderer = ({itemData}: {itemData: TFDSEntry}) => {
		const headlessResource = headlessResourcesMapRef.current.get(
			itemData.entityClassName
		);

		return `${headlessResource?.name} (${headlessResource?.bundleLabel} ${headlessResource?.version})`;
	};

	const ViewsCountRenderer = ({itemData}: {itemData: TFDSEntry}) => {
		const count = itemData[OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW].length;

		return (
			<span
				className={classNames('count', {
					'count-zero': !count,
				})}
			>
				{count}
			</span>
		);
	};

	interface IAddFDSEntryModalContentProps {
		closeModal: Function;
		loadData: Function;
	}

	const AddFDSEntryModalContent = ({
		closeModal,
		loadData,
	}: IAddFDSEntryModalContentProps) => {
		const [
			selectedHeadlessResource,
			setSelectedHeadlessResource,
		] = useState<HeadlessResource>();
		const [labelValidationError, setLabelValidationError] = useState(false);
		const [
			headlessResourceValidationError,
			setHeadlessResourceValidationError,
		] = useState(false);

		const fdsEntryLabelRef = useRef<HTMLInputElement>(null);

		const addFDSEntry = async () => {
			const body = {
				entityClassName: selectedHeadlessResource?.entityClassName,
				label: fdsEntryLabelRef.current?.value,
			};

			const response = await fetch(fdsEntriesAPIURL, {
				body: JSON.stringify(body),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'POST',
			});

			const fdsEntry = await response.json();

			if (fdsEntry?.id) {
				closeModal();

				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
					type: 'success',
				});

				loadData();
			}
			else {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});
			}
		};

		const validate = () => {
			if (!fdsEntryLabelRef.current?.value) {
				setLabelValidationError(true);
			}

			if (!selectedHeadlessResource) {
				setHeadlessResourceValidationError(true);
			}

			if (!fdsEntryLabelRef.current?.value || !selectedHeadlessResource) {
				return false;
			}

			return true;
		};

		const Dropdown = () => (
			<ClayDropDown
				menuElementAttrs={{
					className: 'headless-resources-dropdown-menu',
				}}
				trigger={
					<ClayButton
						aria-labelledby={`${namespace}fdsHeadlessResourcesLabel`}
						className="form-control form-control-select form-control-select-secondary"
						displayType="secondary"
						id={`${namespace}fdsHeadlessResourcesSelect`}
					>
						{selectedHeadlessResource ? (
							<HeadlessResourceItem
								headlessResource={selectedHeadlessResource}
								query=""
							/>
						) : (
							Liferay.Language.get('choose-an-option')
						)}
					</ClayButton>
				}
			>
				<DropdownMenu
					headlessResources={headlessResources}
					setHeadlessResourceValidationError={
						setHeadlessResourceValidationError
					}
					setSelectedHeadlessResource={setSelectedHeadlessResource}
				/>
			</ClayDropDown>
		);

		return (
			<>
				<ClayModal.Header>
					{Liferay.Language.get('new-dataset')}
				</ClayModal.Header>

				<ClayModal.Body>
					<ClayForm.Group
						className={classNames({
							'has-error': labelValidationError,
						})}
					>
						<label htmlFor={`${namespace}fdsEntryLabelInput`}>
							{Liferay.Language.get('name')}

							<RequiredMark />
						</label>

						<ClayInput
							id={`${namespace}fdsEntryLabelInput`}
							onBlur={() => {
								setLabelValidationError(
									!fdsEntryLabelRef.current?.value
								);
							}}
							ref={fdsEntryLabelRef}
							type="text"
						/>

						{labelValidationError && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="exclamation-full" />

									{Liferay.Language.get(
										'this-field-is-required'
									)}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayForm.Group>

					<ClayForm.Group
						className={classNames({
							'has-error': headlessResourceValidationError,
						})}
					>
						<label
							htmlFor={`${namespace}fdsHeadlessResourcesSelect`}
							id={`${namespace}fdsHeadlessResourcesLabel`}
						>
							{Liferay.Language.get('provider')}

							<RequiredMark />
						</label>

						<Dropdown />

						{headlessResourceValidationError && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="exclamation-full" />

									{Liferay.Language.get(
										'this-field-is-required'
									)}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayForm.Group>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								onClick={() => {
									const success = validate();

									if (success) {
										addFDSEntry();
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

	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-dataset'),
				onClick: ({loadData}: {loadData: Function}) => {
					openModal({
						contentComponent: ({
							closeModal,
						}: {
							closeModal: Function;
						}) => (
							<AddFDSEntryModalContent
								closeModal={closeModal}
								loadData={loadData}
							/>
						),
					});
				},
			},
		],
	};

	const onViewClick = ({itemData}: {itemData: TFDSEntry}) => {
		const url = new URL(fdsViewsURL);

		url.searchParams.set(`${namespace}fdsEntryId`, itemData.id);
		url.searchParams.set(`${namespace}fdsEntryLabel`, itemData.label);

		navigate(url);
	};

	const onDeleteClick = ({
		itemData,
		loadData,
	}: {
		itemData: TFDSEntry;
		loadData: Function;
	}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'deleting-a-dataset-is-an-action-that-cannot-be-reversed'
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
							method: itemData.actions.delete.method,
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
			title: Liferay.Language.get('delete-dataset'),
		});
	};

	const views = [
		{
			contentRenderer: 'table',
			name: 'table',
			schema: {
				fields: [
					{fieldName: 'label', label: Liferay.Language.get('name')},
					{
						contentRenderer: 'provider',
						fieldName: 'provider',
						label: Liferay.Language.get('provider'),
					},
					{
						contentRenderer: 'viewsCount',
						fieldName: OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW,
						label: Liferay.Language.get('views'),
					},
					{
						contentRenderer: 'dateTime',
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified-date'),
					},
				],
			},
		},
	];

	return (
		<div className="fds-entries">
			<FrontendDataSet
				apiURL={`${fdsEntriesAPIURL}?nestedFields=${OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW}`}
				creationMenu={creationMenu}
				customDataRenderers={{
					provider: ProviderRenderer,
					viewsCount: ViewsCountRenderer,
				}}
				id={`${namespace}FDSEntries`}
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
		</div>
	);
};

export default FDSEntries;
