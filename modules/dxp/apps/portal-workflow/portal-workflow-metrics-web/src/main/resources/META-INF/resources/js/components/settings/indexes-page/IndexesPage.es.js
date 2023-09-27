/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useMemo} from 'react';

import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import Body from './IndexesPageBody.es';

function IndexesPage() {
	const {data, fetchData} = useFetch({url: '/indexes'});

	const promises = useMemo(
		() => [fetchData()],

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	return (
		<ClayLayout.ContainerFluid>
			<h3 className="font-weight-semi-bold my-4">
				{Liferay.Language.get('workflow-index-actions')}
			</h3>

			<PromisesResolver promises={promises}>
				<IndexesPage.Body {...data} />
			</PromisesResolver>
		</ClayLayout.ContainerFluid>
	);
}

IndexesPage.Body = Body;

export default IndexesPage;
