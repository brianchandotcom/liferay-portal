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

import {useSelector} from '../../../app/contexts/StoreContext';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import NoPageContents from '../../browser/components/contents/components/NoPageContents';
import PageContents from './PageContents';

export default function ContentsSidebar() {
	const pageContents = useSelector((state) => state.pageContents);
	let view = <NoPageContents />;

	if (pageContents.length) {
		view = <PageContents pageContents={pageContents} />;
	}

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('contents')}
			</SidebarPanelHeader>

			<SidebarPanelContent padded={false}>{view}</SidebarPanelContent>
		</>
	);
}
