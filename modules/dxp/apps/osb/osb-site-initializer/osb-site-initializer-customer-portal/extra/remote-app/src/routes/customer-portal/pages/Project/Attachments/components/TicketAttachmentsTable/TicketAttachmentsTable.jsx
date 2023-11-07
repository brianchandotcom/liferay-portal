/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import Table from '../../../../../../../common/components/Table';
import {getTicketAttachments} from '../../../../../../../common/services/liferay/api'
import useMyUserAccountByAccountExternalReferenceCode from '../../../../Project/TeamMembers/components/TeamMembersTable/hooks/useMyUserAccountByAccountExternalReferenceCode';
import getAttachmentFormattedDateTime from './utils/getAttachmentFormattedDateTime';
import {getColumns} from './utils/getColumns';

const TicketAttachmentsTable = ({
	koroneikiAccount,
	loading: koroneikiAccountLoading,
}) => {

	const {
		data: myUserAccountData,
		loading: myUserAccountLoading,
	} = useMyUserAccountByAccountExternalReferenceCode(
		koroneikiAccountLoading,
		koroneikiAccount?.accountKey
	);

	const loggedUserAccount = myUserAccountData?.myUserAccount;

	const loading = myUserAccountLoading;

	const [ticketAttachments, setTicketAttachments] = useState();

	useEffect(() => {
		fetchTicketAttachments();
	}, []);

	const fetchTicketAttachments = async () => {
		const ticketAttachmentsResponse = await getTicketAttachments(1, 5, koroneikiAccount.accountKey);

		const ticketAttachmentsData = await ticketAttachmentsResponse.json();

		setTicketAttachments(ticketAttachmentsData.items);
	};

	return (
		<>
			{(ticketAttachments !== undefined && !loading) ? (
				<div className="cp-ticket-attachments-table-wrapper">
					<Table
						className="border-0"
						columns={getColumns(
							loggedUserAccount?.selectedAccountSummary
								.hasAdministratorRole
						)}
						hasPagination
						isLoading={loading}
						rows={ticketAttachments?.map(
							(ticketAttachment) => ({
								attached: (
									<div className="d-flex flex-column">
										<div className="m-0 text-neutral-10 text-truncate">
											{ticketAttachment.dateCreated}
										</div>

										<div className="m-0 text-neutral-7 text-paragraph-sm text-truncate">
											By {ticketAttachment.creator.name}
										</div>
									</div>
								),
								fileName: (
									<a className="m-0 text-truncate" href={ticketAttachment.storageBucket}>
										{ticketAttachment.fileName}
									</a>
								),
								fileSize: (
									<div className="m-0 text-neutral-10 text-paragraph text-truncate">
										{ticketAttachment.fileSize}
									</div>
								),
								ticket: (
									<a className="m-0 text-truncate" href="/link-to-ticket">
										{ticketAttachment.zendeskTicketId}
									</a>
								),
							})
						)}
					/>
				</div>
			) : (
				<div>
					loading
				</div>
			)}
		</>
	);

};

export default TicketAttachmentsTable;