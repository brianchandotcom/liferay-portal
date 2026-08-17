/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {renderBrokenLinks} from '../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/BrokenLinksFDSPropsTransformer';

describe('[CMS Broken Links] BrokenLinksFDSPropsTransformer', () => {
	beforeEach(() => {
		jest.spyOn(Liferay.Language, 'get');
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('names the broken link when a content has a single one', () => {
		render(
			<>
				{renderBrokenLinks({
					brokenLinkCount: 1,
					brokenLinkTitle: 'Expired Banner',
				})}
			</>
		);

		expect(screen.getByText('x-expired-asset')).toBeInTheDocument();
		expect(Liferay.Language.get).not.toHaveBeenCalledWith('untitled-asset');
	});

	it('counts the expired assets when a content has more than one', () => {
		render(
			<>
				{renderBrokenLinks({
					brokenLinkCount: 3,
					brokenLinkTitle: 'Expired Banner',
				})}
			</>
		);

		expect(screen.getByText('x-expired-assets')).toBeInTheDocument();
		expect(screen.queryByText('x-expired-asset')).not.toBeInTheDocument();
	});

	it('calls the single broken link untitled when it has no title', () => {
		render(<>{renderBrokenLinks({brokenLinkCount: 1})}</>);

		expect(screen.getByText('x-expired-asset')).toBeInTheDocument();
		expect(Liferay.Language.get).toHaveBeenCalledWith('untitled-asset');
	});

	it('counts zero when the content carries no expired asset', () => {
		render(<>{renderBrokenLinks({})}</>);

		expect(screen.getByText('x-expired-assets')).toBeInTheDocument();
	});
});
