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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import useIsMounted from '../../../src/main/resources/META-INF/resources/js/hooks/useIsMounted';

const {useEffect} = React;

type ChildProps = {
	isMounted?: ReturnType<typeof useIsMounted>;
};

describe('useIsMounted()', () => {
	afterEach(cleanup);

	// Regression test for LPS-105721.

	it("can be used in a child component's useEffect callback", () => {
		let mounted = false;

		const Parent = ({
			children,
		}: {
			children: React.ReactElement<ChildProps>;
		}) => {
			const isMounted = useIsMounted();

			const child = React.Children.only(children);

			return React.cloneElement(child, {isMounted});
		};

		const Child = ({isMounted}: ChildProps) => {
			useEffect(() => {
				mounted = isMounted!();
			}, [isMounted]);

			return <></>;
		};

		render(
			<Parent>
				<Child />
			</Parent>
		);

		expect(mounted).toBe(true);
	});
});
