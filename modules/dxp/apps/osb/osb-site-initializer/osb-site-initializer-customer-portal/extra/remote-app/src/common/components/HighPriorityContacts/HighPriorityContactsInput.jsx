/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MultiSelect, Skeleton} from '..';
import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';
import useUserAccountsByAccountExternalReferenceCode from '../../../routes/customer-portal/pages/Project/TeamMembers/components/TeamMembersTable/hooks/useUserAccountsByAccountExternalReferenceCode';
import i18n from '../../I18n';
import getKebabCase from '../../utils/getKebabCase';

const HighPriorityContactsInput = ({
	currentHighPriorityContacts,
	disableSubmit,
	inputName,
	isCriticalIncidentCard,
	koroneikiAccount,
	setContactList,
}) => {
	const [sourceItems, setSourceItems] = useState([]);
	const [loaded, setLoaded] = useState(false);
	const [items, setItems] = useState([]);
	const [
		,
		{data: userAccountsData, search},
	] = useUserAccountsByAccountExternalReferenceCode(
		koroneikiAccount?.accountKey
	);

	const handleMetaErrorChange = (error) => {
		disableSubmit(error, inputName);
	};

	const handleMultiSelectChange = (value) => {
		search(value);
	};

	useEffect(() => {
		setItems(currentHighPriorityContacts);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentHighPriorityContacts]);

	useEffect(() => {
		setCriticalIncidentContactList(items);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items, sourceItems]);

	const setCriticalIncidentContactList = (contactList) => {
		return setContactList(contactList);
	};

	useEffect(() => {
		const teamMembers = userAccountsData?.accountUserAccountsByExternalReferenceCode?.items.map(
			(account) => {
				const {emailAddress, id, name} = account;

				return {
					email: emailAddress,
					id,
					label: name,
					value: id,
				};
			}
		);
		setSourceItems(teamMembers);
	}, [userAccountsData]);

	useEffect(() => {
		if (sourceItems.length) {
			setLoaded(true);
		}
	}, [sourceItems]);

	return loaded ? (
		<ClayForm>
			<MultiSelect
				groupStyle="pb-1"
				helper={i18n.translate('please-enter-name-or-email-address')}
				items={items}
				label={
					isCriticalIncidentCard
						? i18n.translate('contacts')
						: i18n.translate(`${getKebabCase(inputName)}-contact`)
				}
				metaErrorCallback={handleMetaErrorChange}
				name={`${inputName}Contact`}
				onChange={handleMultiSelectChange}
				onItemsChange={setItems}
				placeholder={i18n.translate('enter-name-or-email-address')}
				required
				sourceItems={sourceItems}
				type="email"
				values={items}
			/>
		</ClayForm>
	) : (
		<Skeleton className="mb-3 py-1" height={45} width={560} />
	);
};

export default HighPriorityContactsInput;
