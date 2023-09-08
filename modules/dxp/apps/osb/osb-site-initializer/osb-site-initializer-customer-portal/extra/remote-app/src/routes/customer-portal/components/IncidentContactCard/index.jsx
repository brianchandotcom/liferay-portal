/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import SearchBuilder from '~/common/core/SearchBuilder';
import {getHighPriorityContacts} from '~/common/services/liferay/api';
import i18n from '../../../../common/I18n';
import useCurrentKoroneikiAccount from '../../../../common/hooks/useCurrentKoroneikiAccount';
import {useCustomerPortal} from '../../../../routes/customer-portal/context';
import useMyUserAccountByAccountExternalReferenceCode from '../../../customer-portal/pages/Project/TeamMembers/components/TeamMembersTable/hooks/useMyUserAccountByAccountExternalReferenceCode';
import {HIGH_PRIORITY_CONTACT_CATEGORIES} from '../../utils/getHighPriorityContacts';
import IncidentContactEditForm from './components/IncidentContactEditModal';
import IncidentContactsButton from './components/IncidentContactsButton';

const IncidentContactCard = ({
	accountSubscriptionGroupsNames,
	hasActiveProduct,
}) => {
	const [{project}] = useCustomerPortal();

	const incidentContactStandard = 2;
	const {loading} = useCurrentKoroneikiAccount();

	const {
		data: myUserAccountData,
	} = useMyUserAccountByAccountExternalReferenceCode(
		loading,
		project?.accountKey
	);

	const loggedUserAccount = myUserAccountData?.myUserAccount;

	const hasAdministratorRole =
		loggedUserAccount?.selectedAccountSummary.hasAdministratorRole;

	const [
		currentHighPriorityContacts,
		setCurrentHighPriorityContacts,
	] = useState({
		criticalIncidentContact: [],
		privacyBreachContact: [],
		securityBreachContact: [],
	});
	const [modalFilter, setModalFilter] = useState();
	const [modalMonitoring, setModalMonitoring] = useState();
	const {observer, onOpenChange, open} = useModal();

	const openModal = () => {
		onOpenChange(true);
		setModalMonitoring(true);
	};
	const closeModal = () => {
		onOpenChange(false);
		setModalMonitoring(false);
	};

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
			filterRequest: new SearchBuilder()
				.eq('contactsCategory', lowerCaseFirstLetter.replace(/\s/g, ''))
				.and()
				.eq(
					'r_accountEntryToHighPriorityContacts_accountEntryERC',
					project.accountKey
				)
				.build(),
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
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [modalMonitoring, !project?.accountKey]);

	const generateContactBody = ({contact, email, index, label}) => (
		<div className="customer-portal-cards" key={index}>
			<h4>{email}</h4>

			<h5>{label}</h5>

			{contact.length ? (
				<h5>{contact}</h5>
			) : (
				<p className="text-warning">
					<ClayIcon symbol="warning-full" />
					&nbsp;
					{i18n.translate('phone-number-is-missing')}
				</p>
			)}
		</div>
	);
	const criticalIncidentContacts = currentHighPriorityContacts.criticalIncident?.map(
		generateContactBody
	);

	const privacyBreachContacts = currentHighPriorityContacts?.privacyBreach?.map(
		generateContactBody
	);
	const securityBreachContacts = currentHighPriorityContacts?.securityBreach?.map(
		generateContactBody
	);

	const hasCriticalIncidentContact = !!currentHighPriorityContacts
		.criticalIncident?.length;

	const hasPrivacyBreachContact = !!currentHighPriorityContacts.privacyBreach
		?.length;

	const hasSecurityBreachContact = !!currentHighPriorityContacts
		.securityBreach?.length;

	const handleOnClick = (highPriorityContactsCategory) => {
		setModalFilter(highPriorityContactsCategory);
		openModal();
	};

	const HighPriorityContactsModal = () => {
		return (
			<ClayModal
				center
				className="high-priority-contacts-modal"
				observer={observer}
				onClose={closeModal}
			>
				<IncidentContactEditForm
					close={closeModal}
					hasCriticalIncidentContact={hasCriticalIncidentContact}
					hasPrivacyBreachContact={hasPrivacyBreachContact}
					hasSecurityBreachContact={hasSecurityBreachContact}
					leftButton={i18n.translate('cancel')}
					modalFilter={modalFilter}
				/>
			</ClayModal>
		);
	};

	return (
		<>
			{hasActiveProduct && (
				<div
					className={classNames('customer-portal-card-footer', {
						'customer-portal-card-footer-style-ac': !isLXCEnvironment,
						'customer-portal-card-footer-style-lxc': isLXCEnvironment,
					})}
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
							<div className="customer-portal-card-title row">
								<div
									className={classNames(
										'customer-portal-card-description',
										{
											'col': !isLXCEnvironment,
											'col-4': isLXCEnvironment,
										}
									)}
								>
									<h3 className="pb-1">
										{i18n.translate(
											'critical-incident-contacts'
										)}

										{hasCriticalIncidentContact &&
											hasAdministratorRole && (
												<ClayIcon
													onClick={() =>
														handleOnClick(
															HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncident
														)
													}
													symbol="pencil"
												/>
											)}
									</h3>

									<div
										className={classNames('pr-1', {
											'customer-portal-card-description-scroll scroller':
												currentHighPriorityContacts
													.criticalIncident?.length >
												incidentContactStandard,
										})}
									>
										{hasCriticalIncidentContact
											? criticalIncidentContacts
											: hasAdministratorRole && (
													<IncidentContactsButton
														onClick={() =>
															handleOnClick(
																HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncident
															)
														}
													/>
											  )}
									</div>
								</div>

								{isLXCEnvironment && (
									<>
										<div className="col customer-portal-card-description pl-4">
											<h3 className="pb-1">
												{i18n.translate(
													'security-breach'
												)}

												{hasSecurityBreachContact &&
													hasAdministratorRole && (
														<ClayIcon
															onClick={() =>
																handleOnClick(
																	HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreach
																)
															}
															symbol="pencil"
														/>
													)}
											</h3>

											<div
												className={classNames('pr-1', {
													'customer-portal-card-description-scroll scroller':
														currentHighPriorityContacts
															.securityBreach
															?.length >
														incidentContactStandard,
												})}
											>
												{hasSecurityBreachContact
													? securityBreachContacts
													: hasAdministratorRole && (
															<IncidentContactsButton
																onClick={() =>
																	handleOnClick(
																		HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreach
																	)
																}
															/>
													  )}
											</div>
										</div>

										<div className="col customer-portal-card-description pl-4">
											<h3 className="pb-1">
												{i18n.translate(
													'privacy-breach'
												)}

												{hasPrivacyBreachContact &&
													hasAdministratorRole && (
														<ClayIcon
															onClick={() =>
																handleOnClick(
																	HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreach
																)
															}
															symbol="pencil"
														/>
													)}
											</h3>

											<div
												className={classNames('pr-1', {
													'customer-portal-card-description-scroll scroller':
														currentHighPriorityContacts
															.privacyBreach
															?.length >
														incidentContactStandard,
												})}
											>
												{hasPrivacyBreachContact
													? privacyBreachContacts
													: hasAdministratorRole && (
															<IncidentContactsButton
																onClick={() =>
																	handleOnClick(
																		HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreach
																	)
																}
															/>
													  )}
											</div>
										</div>

										{open && HighPriorityContactsModal()}
									</>
								)}
							</div>
						</div>
					</>
				</div>
			)}
		</>
	);
};

export default IncidentContactCard;
