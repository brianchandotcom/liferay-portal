/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import {getHighPriorityContacts} from '~/common/services/liferay/api';
import i18n from '../../../../common/I18n';

export const HIGH_PRIORITY_CONTACT_CATEGORIES = {
	criticalIncident: i18n.translate('critical-incident'),
	privacyBreach: i18n.translate('privacy-breach'),
	securityBreach: i18n.translate('security-breach'),
};

const IncidentContactCard = ({accountSubscriptionGroupsNames}) => {
	const incidentContactStandard = 2;

	const [
		currentHighPriorityContacts,
		setCurrentHighPriorityContacts,
	] = useState({
		criticalIncidentContact: [],
		privacyBreachContact: [],
		securityBreachContact: [],
	});

	const isLXCEnvironment = accountSubscriptionGroupsNames?.includes(
		'Liferay Experience Cloud'
	);

	const mapFilterToContactsCategory = (filter) => {
		const lowerCaseFirstLetter =
			filter.charAt(0).toLowerCase() + filter.slice(1);

		return {
			contactsCategory: {
				key: lowerCaseFirstLetter.replace(/\s/g, ''),
				name: `${filter}`,
			},
			filterRequest: `contactsCategory eq '${lowerCaseFirstLetter.replace(
				/\s/g,
				''
			)}'`,
		};
	};

	const getHighPriorityContactsByFilter = async (filter) => {
		try {
			const {filterRequest} = mapFilterToContactsCategory(filter);
			const response = await getHighPriorityContacts(filterRequest);
			const highPriorityContactsFiltered = response.items;

			const mappedContacts = highPriorityContactsFiltered.map(
				(contact, index) => {
					const {r_userToHighPriorityContacts_user} = contact;

					const primaryPhoneNumber = r_userToHighPriorityContacts_user.userAccountContactInformation?.telephones.map(
						(phone) => (phone.primary ? phone.phoneNumber : [])
					);

					return {
						contact: primaryPhoneNumber ?? [],
						email: r_userToHighPriorityContacts_user?.emailAddress,
						id: r_userToHighPriorityContacts_user?.id,
						label: `${r_userToHighPriorityContacts_user?.givenName} ${r_userToHighPriorityContacts_user?.familyName}`,
						value: (index + 1).toString(),
					};
				}
			);

			return mappedContacts;
		} catch (error) {
			console.error(
				i18n.translate('error-high-priority-contacts'),
				error
			);
		}
	};

	useEffect(() => {
		const fetchHighPriorityContacts = async () => {
			try {
				const updatedFilteredContacts = {};

				for (const filter of Object.keys(
					HIGH_PRIORITY_CONTACT_CATEGORIES
				)) {
					const contacts = await getHighPriorityContactsByFilter(
						filter
					);
					updatedFilteredContacts[filter] = contacts;
				}

				setCurrentHighPriorityContacts(updatedFilteredContacts);
			} catch (error) {
				console.error(
					i18n.translate('error-fetching-high-priority-contacts'),
					error
				);
			}
		};

		fetchHighPriorityContacts();
	}, []);

	const criticalIncidentContacts = currentHighPriorityContacts.criticalIncident?.map(
		({contact, email, index, label}) => {
			const criticalIncidentContactsBody = (
				<div key={index}>
					<div className="customer-portal-card-lexicon d-flex">
						<h4>{email}</h4>
					</div>

					<h5>{label}</h5>

					{contact.length ? (
						<h5>{contact}</h5>
					) : (
						<>
							<p className="text-warning">
								<ClayIcon symbol="warning-full" />
								&nbsp;
								{i18n.translate('phone-number-is-missing')}
							</p>
						</>
					)}

					<br></br>
				</div>
			);

			return criticalIncidentContactsBody;
		}
	);

	return (
		<div
			className={`customer-portal-card-footer ${
				isLXCEnvironment
					? 'customer-portal-card-footer-style-lxc'
					: 'customer-portal-card-footer-style-ac'
			}`}
		>
			<div className="customer-portal-card-footer-title">
				<h1>{i18n.translate('incident-contacts')}</h1>
			</div>
			<>
				<div className="customer-portal-card-footer-description">
					<p>
						{i18n.translate(
							'team-members-who-can-be-contacted-with-high-priority-messages'
						)}
					</p>
				</div>

				<div className="w-100">
					<div className="customer-portal-card-title pt-2 row">
						<div
							className={`customer-portal-card-description ${
								isLXCEnvironment ? 'col-4' : 'col'
							}`}
						>
							<h3>
								{i18n.translate('critical-incident-contacts')}

								<ClayIcon symbol="pencil" />
							</h3>

							<div
								className={`${
									criticalIncidentContacts?.length >
									incidentContactStandard
										? 'customer-portal-card-description-scroll scroller'
										: ''
								}`}
							>
								{criticalIncidentContacts}
							</div>
						</div>

						{isLXCEnvironment && (
							<>
								<div className="col customer-portal-card-description pl-4">
									<h3>
										{i18n.translate('security-breach')}

										<ClayIcon symbol="pencil" />
									</h3>

									<div
										className={`${
											currentHighPriorityContacts
												.privacyBreach?.length >
											incidentContactStandard
												? 'customer-portal-card-description-scroll scroller'
												: ''
										}`}
									>
										{currentHighPriorityContacts?.privacyBreach?.map(
											({
												contact,
												email,
												index,
												label,
											}) => (
												<div key={index}>
													<h4>{email}</h4>

													<h5>{label}</h5>

													{contact.length ? (
														<h5>{contact}</h5>
													) : (
														<>
															<p className="text-warning">
																<ClayIcon symbol="warning-full" />
																&nbsp;
																{i18n.translate(
																	'phone-number-is-missing'
																)}
															</p>
														</>
													)}

													<br></br>
												</div>
											)
										)}
									</div>
								</div>

								<div className="col customer-portal-card-description pl-4">
									<h3>
										{i18n.translate('privacy-breach')}

										<ClayIcon symbol="pencil" />
									</h3>

									<div
										className={`${
											currentHighPriorityContacts
												.securityBreach?.length >
											incidentContactStandard
												? 'customer-portal-card-description-scroll scroller'
												: ''
										}`}
									>
										{currentHighPriorityContacts?.securityBreach?.map(
											({
												contact,
												email,
												index,
												label,
											}) => (
												<div key={index}>
													<h4>{email}</h4>

													<h5>{label}</h5>

													{contact.length ? (
														<h5>{contact}</h5>
													) : (
														<>
															<p className="text-warning">
																<ClayIcon symbol="warning-full" />
																&nbsp;
																{i18n.translate(
																	'phone-number-is-missing'
																)}
															</p>
														</>
													)}

													<br></br>
												</div>
											)
										)}
									</div>
								</div>
							</>
						)}
					</div>
				</div>
			</>
		</div>
	);
};

export default IncidentContactCard;
