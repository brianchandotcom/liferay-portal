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

import React, {useMemo} from 'react';

import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {Body} from './IndexesPageBody.es';

const IndexesPage = () => {
	const {data, fetchData} = useFetch({url: '/indexes'});

	const promises = useMemo(() => [fetchData()], [fetchData]);

	return (
		<div className="container-fluid-1280">
			<h3 className="font-weight-semi-bold my-4" data-testid="pageTitle">
				{Liferay.Language.get('workflow-index-actions')}
			</h3>

			<PromisesResolver promises={promises}>
				<IndexesPage.Body {...data} />
			</PromisesResolver>
		</div>
	);
};

IndexesPage.Body = Body;

export default IndexesPage;
