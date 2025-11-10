/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {NextConfig} from 'next';

const imageDomain = process.env.LIFERAY_HOST
	? new URL(process.env.LIFERAY_HOST).hostname
	: '';

const nextConfig: NextConfig = {
	images: {
		domains: [imageDomain],
	},
};

export default nextConfig;
