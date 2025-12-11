/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayLabel from '@clayui/label';
import {createResourceURL, dateUtils, fetch, sub} from 'frontend-js-web';
import React, {useContext} from 'react';
import {Link, useNavigate} from 'react-router';

import {AppContext} from '../../AppContext';
import ListView from '../../components/list-view/ListView';
import {DOCUSIGN_STATUS} from '../../utils/contants';
import {toDateFromNow} from '../../utils/moment';

const COLUMNS = [
	{
		key: 'name',
		value: Liferay.Language.get('name'),
	},
	{
		key: 'emailSubject',
		value: Liferay.Language.get('email-subject'),
	},
	{
		key: 'senderEmailAddress',
		value: Liferay.Language.get('sender'),
	},
	{
		key: 'recipients',
		value: Liferay.Language.get('recipients'),
	},
	{
		key: 'status',
		value: Liferay.Language.get('status'),
	},
	{
		key: 'createdLocalDateTime',
		sortable: true,
		value: Liferay.Language.get('create-date'),
	},
];

const intl = new Intl.DateTimeFormat(
	Liferay.ThemeDisplay.getBCP47LanguageId(),
	{
		day: '2-digit',
		month: '2-digit',
		year: 'numeric',
	}
);

const FILTERS = [
	{
		defaultText: Liferay.Language.get('all'),
		items: [
			{
				label: sub(Liferay.Language.get('last-x-months'), 12),
				value: intl.format(dateUtils.subMonths(new Date(), 12)),
			},
			{
				label: sub(Liferay.Language.get('last-x-months'), 6),
				value: intl.format(dateUtils.subMonths(new Date(), 6)),
			},
			{
				label: sub(Liferay.Language.get('last-x-days'), 30),
				value: intl.format(dateUtils.subMonths(new Date(), 1)),
			},
			{
				label: Liferay.Language.get('last-week'),
				value: intl.format(dateUtils.subDays(new Date(), 7)),
			},
			{
				label: sub(Liferay.Language.get('last-x-hours'), 24),
				value: intl.format(dateUtils.subDays(new Date(), 1)),
			},
		],
		key: 'from_date',
		name: Liferay.Language.get('filter-by-date'),
	},
	{
		items: [
			{label: 'Completed', value: 'completed'},
			{label: 'Created', value: 'created'},
			{label: 'Declined', value: 'declined'},
			{label: 'Deleted', value: 'deleted'},
			{label: 'Delivered', value: 'delivered'},
			{label: 'Processing', value: 'processing'},
			{label: 'Sent', value: 'sent'},
			{label: 'Signed', value: 'signed'},
			{label: 'Template', value: 'template'},
			{label: 'Voided', value: 'voided'},
		],
		key: 'status',
		multiple: false,
		name: Liferay.Language.get('filter-by-status'),
	},
];
const getEnvelopeStatus = (status) =>
	DOCUSIGN_STATUS[status] || {color: 'secondary', label: status};

const EnvelopeList = () => {
	const {baseResourceURL} = useContext(AppContext);

	const navigate = useNavigate();

	return (
		<div className="envelope-list">
			<ListView
				actions={[
					{
						action: async ({envelopeId}) => {
							window.open(
								createResourceURL(baseResourceURL, {
									dsEnvelopeId: envelopeId,
									p_p_resource_id:
										'/digital_signature/get_ds_documents_as_bytes',
								}),
								'_blank'
							);
						},
						name: Liferay.Language.get('download'),
					},
				]}
				addButton={() => (
					<ClayButtonWithIcon
						className="nav-btn nav-btn-monospaced"
						onClick={() => navigate('/new-envelope')}
						symbol="plus"
					/>
				)}
				columns={COLUMNS}
				customFetch={async ({params}) => {
					const response = await fetch(
						createResourceURL(baseResourceURL, {
							p_p_resource_id:
								'/digital_signature/get_ds_envelopes',
							...params,
						})
					);

					return response.json();
				}}
				defaultSort="desc"
				filters={FILTERS}
			>
				{({
					envelopeId,
					emailSubject,
					name = Liferay.Language.get('untitled-envelope'),
					createdLocalDateTime,
					senderEmailAddress,
					status,
					recipients: {signers = []},
				}) => ({
					createdLocalDateTime: toDateFromNow(createdLocalDateTime),
					emailSubject,
					envelopeId,
					name: <Link to={`/envelope/${envelopeId}`}>{name}</Link>,
					recipients: (
						<span className="d-flex flex-wrap">
							{signers[0]?.name}

							{signers.length > 1 && (
								<ClayBadge
									className="ml-1"
									displayType="primary"
									label={`+${signers.length - 1}`}
								/>
							)}
						</span>
					),
					senderEmailAddress,
					status: (
						<ClayLabel
							displayType={getEnvelopeStatus(status).color}
						>
							{getEnvelopeStatus(status).label}
						</ClayLabel>
					),
				})}
			</ListView>
		</div>
	);
};
export default EnvelopeList;
