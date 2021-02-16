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

import {useEffect} from 'react';

/**
 * This is a fake component that only takes advantage of the React lifecycle to
 * manipulate the visibility of the report page, it is currently rendered via
 * JSP and it is necessary to control visibility via JavaScript.
 */
export const Report = () => {
	useEffect(() => {
		const formReport = document.querySelector(
			'#container-portlet-ddm-form-report'
		);

		if (formReport) {
			formReport.classList.remove('hide');
		}

		return () => {
			if (formReport) {
				formReport.classList.add('hide');
			}
		};
	}, []);

	return null;
};
