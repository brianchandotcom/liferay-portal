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

import {Align} from '@clayui/drop-down';
import {useAtom} from 'jotai';
import {headerAtom} from '~/atoms';

import DropDown from '../../DropDown';
import HeaderBreadcrumbTrigger from './HeaderBreadcrumbTrigger';

const HeaderDropDown = () => {
	const [dropdown] = useAtom(headerAtom.dropdown);
	const [heading] = useAtom(headerAtom.heading);
	const [symbol] = useAtom(headerAtom.symbol);

	const breadcrumbTriggerProps = {
		symbol,
		title: heading[0]?.title,
	};

	return (
		<div className="align-items-center d-flex justify-content-center mx-3">
			{dropdown.length ? (
				<DropDown
					items={dropdown}
					position={Align.BottomLeft}
					trigger={
						<div>
							<HeaderBreadcrumbTrigger
								displayCarret
								{...breadcrumbTriggerProps}
							/>
						</div>
					}
				/>
			) : (
				<HeaderBreadcrumbTrigger {...breadcrumbTriggerProps} />
			)}
		</div>
	);
};

export default HeaderDropDown;
