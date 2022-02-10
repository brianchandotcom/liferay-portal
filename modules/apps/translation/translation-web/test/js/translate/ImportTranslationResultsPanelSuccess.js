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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ImportTranslationResultsPanelSuccess from '../../../src/main/resources/META-INF/resources/js/ImportTranslationResultsPanelSuccess';

const baseProps = {
	defaultExpanded: false,
	files: [
		'filename-en_US-ar_SA.xlf',
		'filename-en_US-ca_ES.xlf',
		'filename-en_US-de_DE.xlf',
		'filename-en_US-es_ES.xlf',
		'filename-en_US-fi_FI.xlf',
		'filename-en_US-fr_FR.xlf',
		'filename-en_US-hu_HU.xlf',
		'filename-en_US-ja_JP.xlf',
		'filename-en_US-nl_NL.xlf',
		'filename-en_US-pt_BR.xlf',
		'filename-en_US-sv_SE.xlf',
		'filename-en_US-zh_CN.xlf',
	],
	title: 'All Files Published',
};

const renderComponent = (props) =>
	render(<ImportTranslationResultsPanelSuccess {...props} />);

describe('ImportTranslationResultsPanelSuccess', () => {
	afterEach(cleanup);

	it('renders closed (default)', () => {
		const {asFragment} = renderComponent(baseProps);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders expanded', () => {
		const {asFragment} = renderComponent({
			...baseProps,
			defaultExpanded: true,
		});

		expect(asFragment()).toMatchSnapshot();
	});
});
