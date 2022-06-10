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

import {faker} from '@faker-js/faker';
import {fireEvent, render} from '@testing-library/react';
import i18n from '../../../../../../common/I18n';
import SearchBar from './';

describe('render Search bar on Overview page', () => {
	test('update onChange', () => {
		const functionMock = jest.fn();
		const fakerSearchOnChange = faker.name.firstName();

		const {queryByPlaceholderText} = render(
			<SearchBar onSearchSubmit={functionMock} />
		);

		const searchInput = queryByPlaceholderText(
			i18n.translate('find-a-project')
		);

		fireEvent.change(searchInput, {target: {value: fakerSearchOnChange}});
		expect(searchInput.value).toBe(fakerSearchOnChange);

		expect(functionMock).toHaveBeenCalled();
	});
});
