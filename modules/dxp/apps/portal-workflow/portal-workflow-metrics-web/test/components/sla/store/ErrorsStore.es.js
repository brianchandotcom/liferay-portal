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

import {act, renderHook} from '@testing-library/react-hooks';
import {useErrors} from '../../../../src/main/resources/META-INF/resources/js/components/sla/store/ErrorsStore.es';

test('Should test with error', () => {
	const {result} = renderHook(() => useErrors());

	act(() => result.current.setErrors({test: 'test'}));
	expect(result.current.errors).toMatchObject({test: 'test'});
});

test('Should test without error', () => {
	const {result} = renderHook(() => useErrors());

	act(() => result.current.setErrors({}));
	expect(result.current.errors).toMatchObject({});
});
