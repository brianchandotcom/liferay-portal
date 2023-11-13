/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState, useMemo} from 'react';
import i18n from '../../../../../../../common/I18n';
import Table from '../../../../../../../common/components/Table';
import {getTicketAttachments} from '../../../../../../../common/services/liferay/api'
import useMyUserAccountByAccountExternalReferenceCode from '../../../../Project/TeamMembers/components/TeamMembersTable/hooks/useMyUserAccountByAccountExternalReferenceCode';
import getAttachmentFormattedDateTime from './utils/getAttachmentFormattedDateTime';
import {getColumns} from './utils/getColumns';
import usePagination from './hooks/usePaginationTicketAttachments';
import useSort from './hooks/useSortTicketAttachments';
import TicketAttachmentsTableEmpty from './components/TicketAttachmentsTableEmpty';

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

	const [ticketAttachments, setTicketAttachments] = useState([]);

	const {handleSortChange, sortConfig} = useSort();

	const {paginationConfig, sortedTicketAttachmentsFilteredPerPage} = usePagination(sortConfig, ticketAttachments);

	useEffect(() => {
		const fetchTicketAttachments = async () => {
			const ticketAttachmentsResponse = await getTicketAttachments(koroneikiAccount?.accountKey);

			const ticketAttachmentsData = await ticketAttachmentsResponse.json();

			const ticketAttachments = ticketAttachmentsData.items.map(ticketAttachment => ({
				accountKey: ticketAttachment.accountKey,
				creatorName: ticketAttachment.creator.name,
				dateCreated: ticketAttachment.dateCreated,
				fileName: ticketAttachment.fileName,
				fileSize: ticketAttachment.fileSize,
				storageBucket: ticketAttachment.storageBucket,
				zendeskTicketId: ticketAttachment.zendeskTicketId
			}));

			setTicketAttachments(ticketAttachments);
		};
		fetchTicketAttachments();
	}, [koroneikiAccount?.accountKey, paginationConfig.activePage, paginationConfig.itemsPerPage]);

	return (
		<>
			{(sortedTicketAttachmentsFilteredPerPage && (paginationConfig.totalCount > 0) && !loading) ? (
				<div className="cp-ticket-attachments-table-wrapper">
					<Table
						className="border-0"
						columns={getColumns(
							loggedUserAccount?.selectedAccountSummary
								.hasAdministratorRole
						)}
						handleSortChange={handleSortChange}
						hasPagination
						hasSorting
						isLoading={loading}
						paginationConfig={paginationConfig}
						rows={sortedTicketAttachmentsFilteredPerPage?.map(
							(ticketAttachment) => ({
								attached: (
									<div className="d-flex flex-column">
										<div className="m-0 text-neutral-10 text-truncate">
											{getAttachmentFormattedDateTime(ticketAttachment?.dateCreated)}
										</div>

										<div className="m-0 text-neutral-7 text-paragraph-sm text-truncate">
											{i18n.translate('by')}

											<span> </span>

											{ticketAttachment?.creatorName}
										</div>
									</div>
								),
								fileName: (
									<a className="m-0 text-truncate" href={ticketAttachment?.storageBucket}>
										{ticketAttachment?.fileName}
									</a>
								),
								fileSize: (
									<div className="m-0 text-neutral-10 text-paragraph text-truncate">
										{ticketAttachment?.fileSize}
									</div>
								),
								ticket: (
									<a className="m-0 text-truncate" href={PAGE_ROUTER_TYPES.request(ticketAttachment?.zendeskTicketId)}>
										{'#' + ticketAttachment?.zendeskTicketId}
									</a>
								),
							})
						)}
					/>
				</div>
			) : !sortedTicketAttachmentsFilteredPerPage || (paginationConfig.totalCount === 0 && !loading) ? (
				<TicketAttachmentsTableEmpty
					description={i18n.translate("there-are-no-items-to-display")}
					title={i18n.translate("no-attachments-yet")}
				/>
			) : (
				<div>loading</div>
			)}
		</>
	);
};

export default TicketAttachmentsTable;