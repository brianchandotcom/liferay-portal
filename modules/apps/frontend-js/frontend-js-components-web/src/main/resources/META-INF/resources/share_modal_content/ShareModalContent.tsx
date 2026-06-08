/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import ClayPanel from '@clayui/panel';
import ClaySticker from '@clayui/sticker';
import {sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import openToast from '../toast/openToast';
import ExpirationDateSelector, {
	formatDateForView,
} from './ExpirationDateSelector';
import PermissionSelector from './PermissionSelector';
import {
	AutocompleteItem,
	COLLABORATOR_TYPE,
	Collaborator,
	CollaboratorBadgeProps,
	CollaboratorIconProps,
	CollaboratorType,
	PermissionOption,
	ShareModalContentProps,
	ShareModalUserAccount,
	ShareModalUserGroup,
} from './types';

import './ShareModalContent.scss';

const _emptyTransformSourceItems = (
	_items: any[],
	_query: string
): AutocompleteItem[] => [];

const noop = () => {};

function CollaboratorStickerIcon({type, user}: CollaboratorIconProps) {
	if (type === COLLABORATOR_TYPE.USER_GROUP) {
		return <ClayIcon symbol="users" />;
	}

	if ('image' in user && user.image) {
		return (
			<img
				alt={user.name}
				className="sticker-img"
				src={(user as ShareModalUserAccount).image}
			/>
		);
	}

	return <ClayIcon symbol="user" />;
}

const _defaultCollaboratorStickerIcon = ({
	type,
	user,
}: CollaboratorIconProps) => (
	<CollaboratorStickerIcon type={type} user={user} />
);

const _defaultCollaboratorBadgeText = ({
	toBeShared,
}: CollaboratorBadgeProps): string | null =>
	toBeShared ? Liferay.Language.get('to-be-shared') : null;

const _defaultCollaboratorNameSuffix = (
	_props: CollaboratorIconProps
): string | null => null;

const _defaultAutocompleteItem = ({type, user}: CollaboratorIconProps) => (
	<div className="autofit-row autofit-row-center">
		<div className="autofit-col c-mr-1">
			<ClaySticker className="sticker-user-icon" size="sm">
				{type === COLLABORATOR_TYPE.USER ? (
					'image' in user && user.image ? (
						<div className="sticker-overlay">
							<img
								alt={user.name}
								className="sticker-img"
								src={user.image}
							/>
						</div>
					) : (
						<ClayIcon symbol="user" />
					)
				) : (
					<ClayIcon symbol="users" />
				)}
			</ClaySticker>
		</div>

		<div className="autofit-col">
			<span className="text-weight-semibold">
				<span className="c-mr-1">{user.name}</span>

				{'emailAddress' in user && `(${user.emailAddress})`}
			</span>
		</div>
	</div>
);

function CollaboratorListItem({
	actionIds,
	alwaysShowPermissionSelector = false,
	canManageCollaborators = true,
	collaboratorBadgeText,
	collaboratorNameSuffix,
	collaboratorStickerIcon,
	dateExpired,
	error,
	onChangeUser,
	onRemoveUser,
	permissionOptions,
	share,
	showAllowResharing = true,
	showExpirationDate = true,
	toBeShared,
	type = COLLABORATOR_TYPE.USER,
	user,
}: {
	actionIds: string;
	alwaysShowPermissionSelector?: boolean;
	canManageCollaborators?: boolean;
	collaboratorBadgeText: (props: CollaboratorBadgeProps) => string | null;
	collaboratorNameSuffix: (props: CollaboratorIconProps) => string | null;
	collaboratorStickerIcon: (props: CollaboratorIconProps) => React.ReactNode;
	dateExpired?: string;
	error?: string;
	onChangeUser: (
		user: ShareModalUserAccount | ShareModalUserGroup,
		property: Partial<Collaborator>
	) => void;
	onRemoveUser: (user: ShareModalUserAccount | ShareModalUserGroup) => void;
	permissionOptions: PermissionOption[];
	share: boolean;
	showAllowResharing?: boolean;
	showExpirationDate?: boolean;
	toBeShared?: boolean;
	type: CollaboratorType;
	user: ShareModalUserAccount | ShareModalUserGroup;
}) {
	const handleChangeUserProperties = (propertyObj: Partial<Collaborator>) => {
		onChangeUser(user, propertyObj);
	};

	const badgeText = collaboratorBadgeText({toBeShared, type, user});
	const nameSuffix = collaboratorNameSuffix({type, user});

	return (
		<li
			className="border-0 c-px-0 c-py-1 list-group-item list-group-item-flex"
			key={`collaborator-${user.id}`}
		>
			<div className="autofit-col pl-0">
				<ClaySticker displayType="secondary" shape="circle" size="sm">
					{collaboratorStickerIcon({type, user})}
				</ClaySticker>
			</div>

			<div className="autofit-col autofit-col-expand">
				<div className="align-items-center d-flex justify-content-between">
					<div className="d-flex text-truncate">
						<span className="text-3 text-truncate text-weight-semi-bold">
							{user.name}
						</span>

						{nameSuffix ? (
							<span className="c-ml-1 text-3 text-secondary">
								{nameSuffix}
							</span>
						) : null}

						{badgeText ? (
							<ClayBadge
								className="inline-item inline-item-after"
								displayType="secondary"
								label={badgeText}
							/>
						) : null}
					</div>

					{alwaysShowPermissionSelector ||
					permissionOptions.length > 1 ? (
						<div>
							{canManageCollaborators ? (
								<PermissionSelector
									actionIds={actionIds}
									onChange={handleChangeUserProperties}
									options={permissionOptions}
								/>
							) : (
								<span className="permissions-picker text-2 text-secondary text-weight-semi-bold">
									{
										permissionOptions.find(
											(option) =>
												option.value === actionIds
										)?.label
									}
								</span>
							)}
						</div>
					) : null}
				</div>

				{showExpirationDate ? (
					error ? (
						<div className="text-2 text-danger">{error}</div>
					) : (
						dateExpired && (
							<div className="text-2">
								{sub(Liferay.Language.get('access-expires-x'), [
									formatDateForView(dateExpired),
								])}

								<ClayButtonWithIcon
									aria-label={sub(
										Liferay.Language.get('clear-x'),
										[
											Liferay.Language.get(
												'expiration-date'
											),
										]
									)}
									borderless
									className="c-ml-1 inline-item"
									displayType="secondary"
									monospaced
									onClick={() =>
										handleChangeUserProperties({
											dateExpired: '',
											error: '',
										})
									}
									size="xs"
									symbol="trash"
								/>
							</div>
						)
					)
				) : null}
			</div>

			{canManageCollaborators && (
				<div className="autofit-col p-0">
					<div className="d-flex">
						{showExpirationDate ? (
							<ExpirationDateSelector
								dateExpired={dateExpired}
								onChange={handleChangeUserProperties}
							/>
						) : null}

						{showAllowResharing ? (
							<ClayDropDown
								hasLeftSymbols={true}
								trigger={
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'more-options'
										)}
										borderless
										displayType="secondary"
										monospaced
										size="xs"
										symbol="ellipsis-v"
									/>
								}
							>
								<ClayDropDown.ItemList>
									<ClayDropDown.Item
										aria-label={Liferay.Language.get(
											'allow-resharing'
										)}
										key={`share-${user.id}`}
										onClick={() =>
											handleChangeUserProperties({
												share: !share,
											})
										}
										symbolLeft={share ? 'check-small' : ''}
									>
										{Liferay.Language.get(
											'allow-resharing'
										)}
									</ClayDropDown.Item>

									<ClayDropDown.Item
										aria-label={Liferay.Language.get(
											'remove-access'
										)}
										key={`remove-${user.id}`}
										onClick={() => onRemoveUser(user)}
									>
										{Liferay.Language.get('remove-access')}
									</ClayDropDown.Item>
								</ClayDropDown.ItemList>
							</ClayDropDown>
						) : (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'remove-access'
								)}
								borderless
								displayType="secondary"
								monospaced
								onClick={() => onRemoveUser(user)}
								size="xs"
								symbol="times-circle"
							/>
						)}
					</div>
				</div>
			)}
		</li>
	);
}

export default function ShareModalContent({
	alwaysShowPermissionSelector = false,
	autocompleteHelpText,
	autocompleteItem = _defaultAutocompleteItem,
	autocompleteLabel = Liferay.Language.get('add-people-to-collaborate'),
	autocompleteURL = '',
	canManageCollaborators = true,
	closeModal,
	collaboratorBadgeText = _defaultCollaboratorBadgeText,
	collaboratorNameSuffix = _defaultCollaboratorNameSuffix,
	collaboratorStickerIcon = _defaultCollaboratorStickerIcon,
	collaboratorsListTitle = Liferay.Language.get('who-has-access'),
	creator,
	initialCollaborators = [],
	onAutocompleteChange = noop,
	onCollaboratorsUpdate,
	permissionOptions,
	showAllowResharing = true,
	showExpirationDate = true,
	title = '',
	transformSourceItems = _emptyTransformSourceItems,
}: ShareModalContentProps) {
	const [autocompleteValue, setAutocompleteValue] = useState('');
	const [autocompleteNetworkStatus, setAutocompleteNetworkStatus] =
		useState(4);
	const [collaborators, setCollaborators] =
		useState<Collaborator[]>(initialCollaborators);
	const [loading, setLoading] = useState(false);

	const {resource: users} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: `${window.location.origin}${autocompleteURL}`,
		onNetworkStatusChange: setAutocompleteNetworkStatus,
		variables: {search: autocompleteValue},
	});

	const handleAddUser = (
		user: ShareModalUserAccount | ShareModalUserGroup,
		type: CollaboratorType
	) => {
		setCollaborators((collaborators) => {
			return collaborators.every(
				(collaborator) => collaborator.user.id !== user.id
			) && creator.id !== user.id
				? [
						{
							actionIds: 'VIEW',
							share: false,
							toBeShared: true,
							type,
							user,
						},
						...collaborators,
					]
				: collaborators;
		});

		setAutocompleteValue('');
	};

	const handleRemoveUser = async (
		user: ShareModalUserAccount | ShareModalUserGroup
	): Promise<void> => {
		setCollaborators((collaborator) =>
			collaborator.filter(
				(collaborator) => collaborator.user.id !== user.id
			)
		);
	};

	const handleChangeUser = (
		user: ShareModalUserAccount | ShareModalUserGroup,
		property: Partial<Collaborator>
	) => {
		setCollaborators((collaborator) =>
			collaborator.map((item) => {
				if (item.user.id === user.id) {
					return {
						...item,
						...property,
					};
				}

				return item;
			})
		);
	};

	const handleSubmit = async (event: React.FormEvent) => {
		event.preventDefault();

		if (collaborators.some(({error}) => !!error)) {
			return;
		}

		setLoading(true);

		const {error} = await onCollaboratorsUpdate(collaborators);

		setLoading(false);

		if (error) {
			openToast({
				message:
					error ||
					Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}
		else {
			openToast({
				message: sub(
					collaborators.some(({toBeShared}) => !!toBeShared)
						? Liferay.Language.get('x-was-shared-successfully')
						: Liferay.Language.get('x-was-updated-successfully'),
					title
				),
				type: 'success',
			});

			closeModal();
		}
	};

	const _isCollaboratorsUpdated = () =>
		JSON.stringify(collaborators) !== JSON.stringify(initialCollaborators);

	const sourceItems = useMemo(
		() => transformSourceItems(users?.items ?? [], autocompleteValue),
		[users, autocompleteValue, transformSourceItems]
	);

	return (
		<div className="share-modal-content">
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{sub(Liferay.Language.get('share-x'), `"${title}"`)}
			</ClayModal.Header>

			<ClayModal.Body scrollable={true}>
				<ClayForm.Group>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<div className="align-items-center d-flex mb-1">
								<label
									className="mb-0"
									htmlFor="collaboratorAutocomplete"
								>
									{autocompleteLabel}
								</label>

								{autocompleteHelpText ? (
									<ClayIcon
										className="lfr-portal-tooltip ml-1 text-secondary"
										data-title={autocompleteHelpText}
										focusable="false"
										role="dialog"
										symbol="question-circle-full"
										tabIndex={0}
									/>
								) : null}
							</div>

							<ClayMultiSelect
								id="collaboratorAutocomplete"
								items={[]}
								loadingState={autocompleteNetworkStatus}
								onChange={setAutocompleteValue}
								onItemsChange={(items) => {
									const lastItem = items[items.length - 1] as
										| AutocompleteItem
										| undefined;

									if (lastItem?.type && lastItem.user) {
										handleAddUser(
											lastItem.user,
											lastItem.type
										);
									}

									onAutocompleteChange(lastItem);
								}}
								placeholder={Liferay.Language.get(
									'enter-name-email-or-groups'
								)}
								sourceItems={sourceItems}
								value={autocompleteValue}
							>
								{({
									type,
									user,
								}: {
									type: CollaboratorType;
									user:
										| ShareModalUserAccount
										| ShareModalUserGroup;
								}) => (
									<ClayMultiSelect.Item
										key={`autocomplete-${type}-${user.id}`}
										onClick={() =>
											handleAddUser(user, type)
										}
										textValue={user.name}
									>
										{autocompleteItem({type, user})}
									</ClayMultiSelect.Item>
								)}
							</ClayMultiSelect>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayPanel
						className="border-0"
						collapsable
						defaultExpanded={true}
						displayTitle={
							<div className="panel-title text-secondary">
								{collaboratorsListTitle +
									` (` +
									sub(
										Liferay.Language.get('x-users'),
										collaborators.length + 1
									) +
									`)`}
							</div>
						}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<ul className="c-mb-0 list-group">
								{collaborators.map((item) => (
									<CollaboratorListItem
										alwaysShowPermissionSelector={
											alwaysShowPermissionSelector
										}
										canManageCollaborators={
											canManageCollaborators
										}
										collaboratorBadgeText={
											collaboratorBadgeText
										}
										collaboratorNameSuffix={
											collaboratorNameSuffix
										}
										collaboratorStickerIcon={
											collaboratorStickerIcon
										}
										key={`listItem-${item.type}-${item.user.id}`}
										onChangeUser={handleChangeUser}
										onRemoveUser={handleRemoveUser}
										permissionOptions={
											typeof permissionOptions ===
											'function'
												? permissionOptions(item)
												: permissionOptions
										}
										showAllowResharing={showAllowResharing}
										showExpirationDate={showExpirationDate}
										{...item}
									/>
								))}

								{creator.id && (
									<li
										className="border-0 c-px-0 c-py-1 list-group-item list-group-item-flex"
										key={`listItem-creator-${creator.id}`}
									>
										<div className="autofit-col pl-0">
											<ClaySticker
												displayType="secondary"
												shape="circle"
												size="sm"
											>
												{creator.contentType ===
												'UserAccount' ? (
													'image' in creator &&
													creator.image ? (
														<img
															alt={creator.name}
															className="sticker-img"
															src={creator.image}
														/>
													) : (
														<ClayIcon symbol="user" />
													)
												) : (
													<ClayIcon symbol="users" />
												)}
											</ClaySticker>
										</div>

										<div className="autofit-col autofit-col-expand">
											<span className="text-3 text-truncate text-weight-semi-bold">
												{creator.name}
											</span>
										</div>

										<div className="autofit-col">
											<span className="text-2 text-secondary text-weight-semi-bold">
												{Liferay.Language.get('owner')}
											</span>
										</div>
									</li>
								)}
							</ul>
						</ClayPanel.Body>
					</ClayPanel>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={loading || !_isCollaboratorsUpdated()}
							displayType="primary"
							onClick={handleSubmit}
							type="submit"
						>
							{loading && (
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
							)}

							{Liferay.Language.get('share')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
}
