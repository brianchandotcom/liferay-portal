/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useContext, useEffect, useState} from 'react';

import {headers} from '../../../../../../util/fetchUtil';
import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import SidebarPanel from '../../../SidebarPanel';

const User = ({identifier, index, sectionsLength, setSections}) => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const [search, setSearch] = useState('');
	const [networkStatus, setNetworkStatus] = useState(4);
	const [user, setUser] = useState({
		emailAddress: '',
		screenName: '',
		userId: null,
	});

	const {resource} = useResource({
		fetchOptions: {
			headers: {
				...headers,
				'accept': `application/json`,
				'x-csrf-token': Liferay.authToken,
			},
		},
		link: `${window.location.origin}/o/headless-admin-user/v1.0/user-accounts`,
		onNetworkStatusChange: setNetworkStatus,
		variables: {search},
	});

	useEffect(() => {
		setUser((prev) => {
			if (selectedItem.data.assignments?.usersData) {
				return {
					emailAddress:
						selectedItem.data.assignments?.usersData[index]
							?.emailAddress,
					screenName:
						selectedItem.data.assignments?.usersData[index]
							?.screenName,
					userId:
						selectedItem.data.assignments?.usersData[index]?.userId,
				};
			} else {
				return {
					emailAddress: prev.emailAddress,
					screenName: prev.screenName,
					userId: prev.userId,
				};
			}
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const updateSelectedItem = (values) => {
		setSelectedItem((previousItem) => ({
			...previousItem,
			data: {
				...previousItem.data,
				assignments: {
					assignmentType: ['user'],
					emailAddress: values.map(({emailAddress}) => emailAddress),
					usersData: values.map((values) => values),
				},
			},
		}));
	};

	const onSelectUser = (item) => () => {
		setUser(item);

		setSections((prev) => {
			prev[index] = {
				...prev[index],
				...item,
			};

			updateSelectedItem(prev);

			return prev;
		});

		setSearch('');
	};

	const deleteSection = () => {
		setSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			updateSelectedItem(newSections);

			return newSections;
		});
	};

	const initialLoading = networkStatus === 1;
	const loading = networkStatus < 4;
	const error = networkStatus === 5;

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('section')}>
			<ClayForm.Group>
				<label htmlFor="search">{Liferay.Language.get('search')}</label>

				<ClayAutocomplete>
					<ClayAutocomplete.Input
						autoComplete="off"
						id="search"
						onChange={(event) => setSearch(event.target.value)}
						value={search}
					/>

					<ClayAutocomplete.DropDown
						active={(!!resource && !!search) || initialLoading}
					>
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

			<ClayForm.Group>
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
			</ClayForm.Group>

			<ClayForm.Group>
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
			</ClayForm.Group>

			<ClayForm.Group>
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
			</ClayForm.Group>

			<div className="section-buttons-area">
				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() =>
						setSections((prev) => {
							return [
								...prev,
								{identifier: `${Date.now()}-${prev.length}`},
							];
						})
					}
				>
					{Liferay.Language.get('new-section')}
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
		</SidebarPanel>
	);
};

export default User;
