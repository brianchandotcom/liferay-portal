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

import {Link} from 'react-router-dom';
import PageSizeItem from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PageSizeItem.es';
import React from 'react';
import {MockRouter as Router} from '../../../mock/MockRouter.es';

test('Should test component click', () => {
	const onChangePageSize = () => pageSize => pageSize;

	const component = mount(
		<Router>
			<PageSizeItem onChangePageSize={onChangePageSize()} pageSize="5" />
		</Router>
	);

	component.find(Link).simulate('click');
});
