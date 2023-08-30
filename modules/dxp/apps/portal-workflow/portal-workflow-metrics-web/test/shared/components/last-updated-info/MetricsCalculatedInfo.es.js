/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import MetricsCalculatedInfo from '../../../../src/main/resources/META-INF/resources/js/shared/components/last-updated-info/MetricsCalculatedInfo.es';
import moment from '../../../../src/main/resources/META-INF/resources/js/shared/util/moment.es';
import {MockRouter} from '../../../../test/mock/MockRouter.es';

describe('MetricsCalculatedInfo', () => {
	test.skip('will be correctly rendered', async () => {
		const date = moment(new Date()).format(
			Liferay.Language.get('mmm-dd-hh-mm-a')
		);

		fetch.mockResolvedValueOnce({
			json: () => Promise.resolve(date),
			ok: true,
		});

		const {getByText} = render(
			<MockRouter>
				<MetricsCalculatedInfo dateModified={new Date()} />
			</MockRouter>
		);

		const labelText = await getByText(/metrics-calculated/);

		expect(labelText).toBeTruthy();
	});
});
