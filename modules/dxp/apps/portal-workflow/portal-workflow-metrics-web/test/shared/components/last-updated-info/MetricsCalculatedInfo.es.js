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

import {render} from '@testing-library/react';
import React from 'react';

import MetricsCalculatedInfo from '../../../../src/main/resources/META-INF/resources/js/shared/components/last-updated-info/MetricsCalculatedInfo.es';
import moment from '../../../../src/main/resources/META-INF/resources/js/shared/util/moment.es';
import {MockRouter} from '../../../../test/mock/MockRouter.es';

describe('MetricsCalculatedInfo', () => {
	test('will be correctly rendered', async () => {
		const date = moment
			.utc(new Date())
			.format(Liferay.Language.get('mmm-dd-hh-mm-a'));

		fetch.mockResolvedValueOnce({
			json: () => Promise.resolve(date),
			ok: true,
		});

		const {findByText} = render(
			<MockRouter>
				<MetricsCalculatedInfo date={date} />
			</MockRouter>
		);

		const labelText = await findByText(
			Liferay.Language.get('metrics-calculated')
		);

		expect(labelText).toBeTruthy();
	});
});
