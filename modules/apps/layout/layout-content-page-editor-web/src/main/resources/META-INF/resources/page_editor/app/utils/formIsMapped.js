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

import {FORM_MAPPING_SOURCES} from '../config/constants/formMappingSources';
import {LAYOUT_TYPES} from '../config/constants/layoutTypes';
import {config} from '../config/index';

export function formIsMapped(item) {
	return (
		(config.layoutType === LAYOUT_TYPES.display &&
			item.config.formConfig === FORM_MAPPING_SOURCES.displayPage) ||
		(item.config.classNameId && item.config.classNameId !== '0')
	);
}
