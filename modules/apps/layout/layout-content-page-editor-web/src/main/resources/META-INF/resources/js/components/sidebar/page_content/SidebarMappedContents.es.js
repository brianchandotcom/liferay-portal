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
import {MappedContents} from './MappedContents.es';
import {NoMappedContents} from './NoMappedContents.es';
import useSelector from '../../../store/hooks/useSelector.es';
import SidebarHeader from '../SidebarHeader.es';

const SidebarMappedContents = () => {
	const mappedContents = useSelector(state => state.mappedContents);
	let view = <NoMappedContents />;

	if (mappedContents.length) {
		view = <MappedContents mappedContents={mappedContents} />;
	}

	return (
		<>
			<SidebarHeader>{Liferay.Language.get('contents')}</SidebarHeader>

			{view}
		</>
	);
};

export {SidebarMappedContents};
export default SidebarMappedContents;
