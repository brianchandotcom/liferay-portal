/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import {contextUrl} from '../../../../../constants';
import {headers, userBaseURL} from '../../../../../util/fetchUtil';

const BaseUser = ({
	errors,
	emailAddress = '',
	identifier,
	index,
	notificationIndex,
	reassignment,
	screenName = '',
	sectionsLength,
	setSections,
	setErrors,
	updateSelectedItem = () => {},
	userId = null,
}) => {
	const [search, setSearch] = useState('');
	const [networkStatus, setNetworkStatus] = useState(4);
	const [user, setUser] = useState({
		emailAddress,
		screenName,
		userId,
	});

	const {resource} = useResource({
		fetchOptions: {
			headers: {
				...headers,
				'accept': `application/json`,
				'x-csrf-token': Liferay.authToken,
			},
		},
		link: `${window.location.origin}${contextUrl}${userBaseURL}/user-accounts`,
		onNetworkStatusChange: setNetworkStatus,
		variables: {search},
	});

	const onSelectUser = (item) => () => {
		setUser(item);

		setSections((prev) => {
			const updatedSections = [...prev];
			updatedSections[index] = {
				...updatedSections[index],
				...item,
			};

			updateSelectedItem(updatedSections);

			return updatedSections;
		});

		setSearch('');
	};

	const checkSearchErrors = (errors, user) => {
		const temp = errors?.user ? [...errors.user] : [];

		if (!temp[notificationIndex]) {
			temp[notificationIndex] = [];
		}
		if (!temp[notificationIndex][index]) {
			temp[notificationIndex][index] = [];
		}
		temp[notificationIndex][index] = user.screenName === '';

		return {...errors, user: temp};
	};

	useEffect(() => {
		setErrors(checkSearchErrors(errors, user));

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [search, user]);

	const deleteSection = () => {
		setSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			updateSelectedItem(newSections);

			return newSections;
		});
	};

	const loading = networkStatus < 4;
	const error = networkStatus === 5;

	return (
		<>
			<ClayForm.Group>
				<label htmlFor="search">{Liferay.Language.get('search')}</label>

				<ClayAutocomplete>
					<ClayAutocomplete.Input
						autoComplete="off"
						id="search"
						onBlur={(event) => {
							setSearch(event.target.value);
							setErrors(checkSearchErrors(errors, user));
						}}
						onChange={(event) => {
							setSearch(event.target.value);
						}}
						value={search}
					/>

					<ClayAutocomplete.DropDown active={!!resource && !!search}>
						<ClayDropDown.ItemList>
							{(error || !resource?.items?.length) && (
								<ClayDropDown.Item className="disabled">
									{Liferay.Language.get('no-results-found')}
								</ClayDropDown.Item>
							)}

							{!error &&
								resource?.items &&
								resource.items?.map((item) => (
									<ClayAutocomplete.Item
										key={item.id}
										match={search}
										onClick={onSelectUser({
											emailAddress: item.emailAddress,
											name: item.name,
											screenName: item.alternateName,
											userId: item.id,
										})}
										value={item.name}
									>
										{item.name}
									</ClayAutocomplete.Item>
								))}
						</ClayDropDown.ItemList>
					</ClayAutocomplete.DropDown>

					{loading && <ClayAutocomplete.LoadingIndicator />}
				</ClayAutocomplete>
			</ClayForm.Group>

			<ClayForm.Group
				className={
					errors?.user?.[notificationIndex]?.[index]
						? 'has-error'
						: ''
				}
			>
				<label htmlFor="screen-name">
					{Liferay.Language.get('screen-name')}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<ClayInput
					disabled
					id="screen-name"
					placeholder="Jon Doe"
					type="text"
					value={user?.screenName}
				/>

				<ClayForm.FeedbackItem>
					{errors?.user?.[notificationIndex]?.[index] && (
						<>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get('this-field-is-required')}
						</>
					)}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>

			<ClayForm.Group
				className={
					errors?.user?.[notificationIndex]?.[index]
						? 'has-error'
						: ''
				}
			>
				<label htmlFor="email-address">
					{Liferay.Language.get('email-address')}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<ClayInput
					disabled
					id="email-address"
					placeholder="example@liferay.com"
					type="text"
					value={user?.emailAddress}
				/>

				<ClayForm.FeedbackItem>
					{errors?.user?.[notificationIndex]?.[index] && (
						<>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get('this-field-is-required')}
						</>
					)}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>

			<ClayForm.Group
				className={
					errors?.user?.[notificationIndex]?.[index]
						? 'has-error'
						: ''
				}
			>
				<label htmlFor="user-id">
					{Liferay.Language.get('user-id')}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<ClayInput
					disabled
					id="user-id"
					placeholder="201153234"
					type="text"
					value={user?.userId}
				/>

				<ClayForm.FeedbackItem>
					{errors?.user?.[notificationIndex]?.[index] && (
						<>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get('this-field-is-required')}
						</>
					)}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>

			<div className="section-buttons-area">
				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() =>
						setSections((prev) => {
							return [
								...prev,
								{
									identifier: `${Date.now()}-${prev.length}`,
								},
							];
						})
					}
				>
					{reassignment
						? Liferay.Language.get('new-user')
						: Liferay.Language.get('new-section')}
				</ClayButton>

				{sectionsLength > 1 && (
					<ClayButtonWithIcon
						className="delete-button"
						displayType="unstyled"
						onClick={deleteSection}
						symbol="trash"
					/>
				)}
			</div>
		</>
	);
};

export default BaseUser;
