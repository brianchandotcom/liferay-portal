/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useOutletContext} from 'react-router-dom';
import i18n from '../../../../../common/I18n';
import useCurrentKoroneikiAccount from '../../../../../common/hooks/useCurrentKoroneikiAccount';
import TicketAttachmentsTable from './components/TicketAttachmentsTable/TicketAttachmentsTable';

const Attachments = () => {
	const {setHasSideMenu} = useOutletContext();
	const {data, loading} = useCurrentKoroneikiAccount();
	const koroneikiAccount = data?.koroneikiAccountByExternalReferenceCode;

	useEffect(() => {
		setHasSideMenu(true);
	}, [setHasSideMenu]);

	return (
		<>
			<h1>{i18n.translate('attachments')}</h1>

			<div className="mt-4">
				<TicketAttachmentsTable
					koroneikiAccount={koroneikiAccount}
					loading={loading}
				/>
			</div>
		</>
	);
};

export default Attachments;
