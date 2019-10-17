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

import React from 'react';

import useSelector from '../../../store/hooks/useSelector.es';
import SidebarHeader from '../SidebarHeader.es';
import {NoPageContents} from './NoPageContents.es';
import {PageContents} from './PageContents.es';

const SidebarPageContents = () => {
	const pageContents = useSelector(state => state.pageContents);
	let view = <NoPageContents />;

	if (pageContents.length) {
		view = <PageContents pageContents={pageContents} />;
	}

	return (
		<>
			<SidebarHeader>{Liferay.Language.get('contents')}</SidebarHeader>

			{view}
		</>
	);
};

export {SidebarPageContents};
export default SidebarPageContents;
