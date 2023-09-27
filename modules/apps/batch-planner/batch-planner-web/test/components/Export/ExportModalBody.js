/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {EXPORT_FILE_NAME} from '../../../src/main/resources/META-INF/resources/js/constants';
import ExportModalBody from '../../../src/main/resources/META-INF/resources/js/export/ExportModalBody';

describe('ExportModalBody', () => {
	afterEach(cleanup);

	it('must show error when setted', () => {
		const errorMessage = 'Error Occurred';
		const {getByText} = render(
			<ExportModalBody errorMessage={errorMessage} percentage={0} />
		);

		getByText(errorMessage);
	});

	it('must show percentage when in progress', () => {
		const {getByText} = render(
			<ExportModalBody contentType="CSV" percentage={50} />
		);

		getByText(EXPORT_FILE_NAME);
		getByText('50%');
		getByText(Liferay.Language.get('running'));
		getByText(Liferay.Language.get('export-file-is-being-created'));
	});

	it('must show "Completed" when export task completed', () => {
		const {getByText} = render(
			<ExportModalBody
				contentType="CSV"
				percentage={100}
				readyToDownload={true}
			/>
		);

		getByText(EXPORT_FILE_NAME);
		getByText(Liferay.Language.get('completed'));
		getByText(
			Liferay.Language.get(
				'your-file-has-been-generated-and-is-ready-to-download'
			)
		);
	});
});
