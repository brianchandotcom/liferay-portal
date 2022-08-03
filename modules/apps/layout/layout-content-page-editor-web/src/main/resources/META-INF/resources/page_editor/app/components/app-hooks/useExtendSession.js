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

import {config} from '../../config/index';

const DEFAULT_SESSION_LENGTH = 60 * 1000;

export default function useExtendSession() {
	useEffect(() => {
		if (Liferay.Session && config.autoExtendSessionEnabled) {
			const sessionLength =
				Liferay.Session.get('sessionLength') || DEFAULT_SESSION_LENGTH;

			const interval = setInterval(() => {
				Liferay.Session.extend();
			}, sessionLength / 2);

			return () => clearInterval(interval);
		}
	}, []);
}
