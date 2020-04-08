/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {useContext, useEffect} from 'react';

import {AppContext} from '../../AppContext.es';
import {Body} from './SLAFormPageBody.es';
import SLAFormPageProvider from './SLAFormPageProvider.es';

const SLAFormPage = props => {
	const {setTitle} = useContext(AppContext);

	useEffect(() => {
		const title = props.id
			? Liferay.Language.get('edit-sla')
			: Liferay.Language.get('new-sla');

		setTitle(title);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="sla-form">
			<SLAFormPageProvider {...props}>
				<SLAFormPage.Body {...props} />
			</SLAFormPageProvider>
		</div>
	);
};

SLAFormPage.Body = Body;

export default SLAFormPage;
