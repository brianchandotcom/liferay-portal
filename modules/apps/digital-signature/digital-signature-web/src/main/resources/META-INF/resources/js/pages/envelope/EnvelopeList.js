/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayBadge from '@clayui/badge';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayLabel from '@clayui/label';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useContext} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext';
import ListView from '../../components/list-view/ListView';
import {DOCUSIGN_STATUS} from '../../utils/contants';
import {getDateFromNow} from '../../utils/moment';
const COLUMNS = [
	{
		key: 'name',
		sortable: true,
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
		key: 'createdAt',
		value: Liferay.Language.get('create-date'),
	},
];
const getEnvelopeStatus = (status) =>
	DOCUSIGN_STATUS[status] || {color: 'secondary', label: status};
const EnvelopeList = ({history}) => {
	const {baseResourceURL} = useContext(AppContext);

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
						name: Liferay.Language.get('download-files'),
					},
				]}
				addButton={() => (
					<ClayButtonWithIcon
						className="nav-btn nav-btn-monospaced"
						onClick={() => history.push('/new-envelope')}
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
				history={history}
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
					createdAt: getDateFromNow(createdLocalDateTime),
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
