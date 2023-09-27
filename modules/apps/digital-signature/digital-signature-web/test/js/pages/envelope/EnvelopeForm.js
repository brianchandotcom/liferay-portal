/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import {AppContext} from '../../../../src/main/resources/META-INF/resources/js/AppContext';
import EnvelopeForm from '../../../../src/main/resources/META-INF/resources/js/pages/envelope/EnvelopeForm';

const EnvelopeViewWithProvider = (props) => (
	<AppContext.Provider value={{}}>
		<EnvelopeForm {...props} />
	</AppContext.Provider>
);

describe('EnvelopeForm', () => {
	it('renders', () => {
		const {asFragment} = render(<EnvelopeViewWithProvider />);

		expect(asFragment()).toMatchSnapshot();
	});
});
