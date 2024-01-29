/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ReadOnlyInput from './ReadOnlyInput';

interface IEditClientDetailsProps extends React.HTMLAttributes<HTMLElement> {
	baseResourceURL: string;
	clientId: string;
	clientSecret: string;
	portletNamespace: string;
}

const EditClientDetails: React.FC<IEditClientDetailsProps> = (props) => {
	return (
		<>
			<ReadOnlyInput
				alertText={Liferay.Language.get(
					'if-changed-clients-with-the-old-client-id-will-no-longer-be-able-to-request-new-tokens-after-you-save-the-application-details'
				)}
				id={`${props.portletNamespace}clientId`}
				initialValue={props.clientId}
				label={Liferay.Language.get('client-id')}
				title={Liferay.Language.get('edit-client-id')}
				tooltip={Liferay.Language.get('client-id-help[oauth2]')}
			/>

			<ReadOnlyInput
				alertText={Liferay.Language.get(
					'if-changed-clients-with-the-old-client-secret-will-no-longer-be-able-to-request-new-tokens-after-you-save-the-application-details'
				)}
				baseResourceURL={props.baseResourceURL}
				id={`${props.portletNamespace}clientSecret`}
				initialValue={props.clientSecret}
				isSecret={true}
				label={Liferay.Language.get('client-secret')}
				title={Liferay.Language.get('edit-client-secret')}
				tooltip={Liferay.Language.get('client-secret-help[oauth2]')}
				type="password"
			/>
		</>
	);
};

export default EditClientDetails;
