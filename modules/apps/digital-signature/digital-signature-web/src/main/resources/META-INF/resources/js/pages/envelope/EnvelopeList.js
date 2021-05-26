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
import ListView from 'data-engine-js-components-web/js/components/list-view/ListView.es';
import {createResourceURL, fetch} from 'frontend-js-web';
import React from 'react';
import {Link} from 'react-router-dom';

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

const EnvelopeList = ({baseResourceURL, history}) => (
	<div className="envelope-list">
		<ListView
			actions={[]}
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
						p_p_resource_id: '/digital_signature/get_ds_envelopes',
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
				name,
				createdLocalDateTime,
				senderEmailAddress,
				status,
				recipients: {signers = []},
			}) => {
				const {color} =
					DOCUSIGN_STATUS[status] || DOCUSIGN_STATUS.other;

				return {
					createdAt: getDateFromNow(createdLocalDateTime),
					emailSubject,
					name: <Link to={`/envelope/${envelopeId}`}>{name}</Link>,
					recipients: (
						<span className="d-flex">
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
					status: <ClayLabel displayType={color}>{status}</ClayLabel>,
				};
			}}
		</ListView>
	</div>
);

export default EnvelopeList;
